package com.pluralsight.security.model;

import java.util.Objects;

public class CryptoCurrencyDto {

	private final String symbol;
	private final String name;

	public CryptoCurrencyDto(String symbol, String name) {
		this.symbol = symbol;
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "CryptoCurrencyDto{" +
				"symbol='" + symbol + '\'' +
				", name='" + name + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CryptoCurrencyDto that = (CryptoCurrencyDto) o;
		return symbol.equals(that.symbol) && name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol, name);
	}

}
