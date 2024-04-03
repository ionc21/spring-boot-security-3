package com.pluralsight.security.model;

import java.math.BigDecimal;
import java.util.Objects;

public class PositionDto {
	
	private final CryptoCurrencyDto cryptoCurrency;
	private final BigDecimal quantity;
	private final BigDecimal value;

	public PositionDto(CryptoCurrencyDto cryptoCurrency, BigDecimal quantity, BigDecimal value) {
		this.cryptoCurrency = cryptoCurrency;
		this.quantity = quantity;
		this.value = value;
	}

	public CryptoCurrencyDto getCryptoCurrency() {
		return cryptoCurrency;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PositionDto that = (PositionDto) o;
		return cryptoCurrency.equals(that.cryptoCurrency) && quantity.equals(that.quantity) && value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cryptoCurrency, quantity, value);
	}

}
