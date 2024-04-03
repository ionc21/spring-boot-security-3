package com.pluralsight.security.model;

import java.util.List;
import java.util.Map;

public class PortfolioPositionsDto {

	private final List<PositionDto> positions;
	private final Map<String, String> cryptoCurrencies;

	public PortfolioPositionsDto(List<PositionDto> positions, Map<String, String> cryptoCurrencies) {
		this.positions = positions;
		this.cryptoCurrencies = cryptoCurrencies;
	}

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

	public PositionDto getPositionForCrypto(String cryptoSymbol) {
		return  positions.stream().filter(pos -> pos.getCryptoCurrency().getSymbol().equals(cryptoSymbol)).findFirst().get();
	}

	public List<PositionDto> getPositions() {
		return positions;
	}

	public Map<String, String> getCryptoCurrencies() {
		return cryptoCurrencies;
	}

}
