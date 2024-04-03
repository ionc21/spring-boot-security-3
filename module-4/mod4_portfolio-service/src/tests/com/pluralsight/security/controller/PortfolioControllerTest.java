package com.pluralsight.security.controller;

import com.pluralsight.security.model.PortfolioPositionsDto;
import com.pluralsight.security.model.PositionDto;
import com.pluralsight.security.repository.CryptoCurrencyRepository;
import com.pluralsight.security.service.PortfolioCommandService;
import com.pluralsight.security.service.PortfolioQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.pluralsight.security.TestConstants.*;
import static com.pluralsight.security.TestUtil.getMockJwt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PortfolioController.class)
@ActiveProfiles("test")
class PortfolioControllerTest {

    @MockBean
    private PortfolioQueryService portfolioService;
    @MockBean
    private PortfolioCommandService commandService;
    @MockBean
    private  CryptoCurrencyRepository cryptoRepository;
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Test
    public void testPortfolioPositionsNoJwtUnauthorized() throws Exception {
        mockMvc.perform(get("/portfolio")).andExpect(status().isUnauthorized());
    }

    @Test
    public void testPortfolioPositionsJwtWrongAudience() throws Exception {
        this.mockMvc.perform(put("/portfolio").header(HttpHeaders.AUTHORIZATION,"Bearer "+getJwtWithWrongAudience()).with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    public void testAccessToPortfolioApiWithExpiredJwtIsUnauthorized() throws Exception {
        this.mockMvc.perform(put("/portfolio").header(HttpHeaders.AUTHORIZATION,"Bearer "+getExpiredJwt()).with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    public void testPortfolioPositions() throws Exception {
        List<PositionDto> positions = new ArrayList<>();
        positions.add(new PositionDto(LTC_DTO, BigDecimal.ONE,new BigDecimal("102.03")));
        positions.add(new PositionDto(BTC_DTO, BigDecimal.ONE,new BigDecimal("1502.10")));
        PortfolioPositionsDto portfolioPositions = new PortfolioPositionsDto(positions,CRYPTO_CURRENCY_MAP);
        when(portfolioService.getPortfolioPositionsForUser("davo")).thenReturn(portfolioPositions);
        mockMvc.perform(get("/portfolio").header(HttpHeaders.AUTHORIZATION,"Bearer "+getValidJwt())).andExpect(status().isOk());
    }

    private String getValidJwt() throws Exception {
        return getMockJwt(CURRENT_DATE_PLUS_5_MIN,"portfolio-service");
    }

    private String getExpiredJwt() throws Exception {
        return getMockJwt(CURRENT_DATE_MINUS_5_MIN,"portfolio-service");
    }

    private String getJwtWithWrongAudience() throws Exception {
        return getMockJwt(CURRENT_DATE_PLUS_5_MIN,"wrong-service");
    }

}