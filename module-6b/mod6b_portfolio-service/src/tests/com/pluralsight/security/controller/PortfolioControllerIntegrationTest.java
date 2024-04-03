package com.pluralsight.security.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.pluralsight.security.TestConstants.CURRENT_DATE_MINUS_5_MIN;
import static com.pluralsight.security.TestConstants.CURRENT_DATE_PLUS_5_MIN;
import static com.pluralsight.security.TestUtil.getMockJwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PortfolioControllerIntegrationTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Test
    public void testAccessToPortfolioApiWithoutJwtIsUnauthorized() throws Exception {
        this.mockMvc.perform(get("/portfolio")).andExpect(status().isUnauthorized());
        this.mockMvc.perform(get("/portfolio")).andExpect(status().isUnauthorized());
        this.mockMvc.perform(post("/portfolio/transactions").with(csrf())).andExpect(status().isUnauthorized());
        this.mockMvc.perform(put("/portfolio/transactions").with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    public void testAccessToPortfolioApiWithValidJwtIsAuthorized() throws Exception {
        this.mockMvc.perform(put("/portfolio").header(HttpHeaders.AUTHORIZATION,"Bearer "+getValidJwt()).with(csrf())).andExpect(status().isOk());
        this.mockMvc.perform(get("/portfolio").header(HttpHeaders.AUTHORIZATION,"Bearer "+getValidJwt())).andExpect(status().isOk());
    }

    @Test
    public void testAccessToPortfolioApiWithExpiredJwtIsUnauthorized() throws Exception {
        this.mockMvc.perform(put("/portfolio").header(HttpHeaders.AUTHORIZATION,"Bearer "+getExpiredJwt()).with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    public void testAccessToPortfolioApiWithWrongAudienceIsUnauthorized() throws Exception {
        this.mockMvc.perform(put("/portfolio").header(HttpHeaders.AUTHORIZATION,"Bearer "+getJwtWithWrongAudience()).with(csrf())).andExpect(status().isUnauthorized());
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