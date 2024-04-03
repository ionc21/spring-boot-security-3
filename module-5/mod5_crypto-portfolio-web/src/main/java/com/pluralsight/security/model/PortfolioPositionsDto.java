package com.pluralsight.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioPositionsDto {

	private String firstName;
	private String lastname;
	private List<PositionDto> positions;
	private Map<String, String> cryptoCurrencies;
	
	public PositionDto getPositionForCrypto(CryptoCurrencyDto crypto) {
		PositionDto position = null;
		for(PositionDto pos : positions) {
			if(pos.getCryptoCurrency().equals(crypto)) {
				position = pos;
				break;
			}
		}
		return position;
	}
	
}
