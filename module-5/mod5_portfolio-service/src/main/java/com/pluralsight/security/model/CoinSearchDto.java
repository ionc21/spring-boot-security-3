package com.pluralsight.security.model;

import java.util.Objects;

public class CoinSearchDto {

	private String symbol;

	public CoinSearchDto(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CoinSearchDto that = (CoinSearchDto) o;
		return symbol.equals(that.symbol);
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}
}
