package com.pluralsight.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class CryptoCurrency {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String symbol;
	private String name;

	public CryptoCurrency() {}

	public CryptoCurrency(String symbol, String name) {
		this.symbol = symbol;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CryptoCurrency that = (CryptoCurrency) o;
		return id.equals(that.id) && symbol.equals(that.symbol) && name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, symbol, name);
	}

	@Override
	public String toString() {
		return "CryptoCurrency{" +
				"id='" + id + '\'' +
				", symbol='" + symbol + '\'' +
				", name='" + name + '\'' +
				'}';
	}

}
