package com.pluralsight.security.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne
	private CryptoCurrency cryptoCurrency;
	private Type type;
	private BigDecimal quantity;
	private BigDecimal price;
	private long timestamp;

	public Transaction() {}

	public Transaction(CryptoCurrency cryptoCurrency, Type type, BigDecimal quantity, BigDecimal price, long timestamp) {
		this.cryptoCurrency=cryptoCurrency;
		this.type=type;
		this.quantity=quantity;
		this.price=price;
		this.timestamp=timestamp;
	}

	public Long getId() {
		return id;
	}

	public CryptoCurrency getCryptoCurrency() {
		return cryptoCurrency;
	}

	public Type getType() {
		return type;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Transaction that = (Transaction) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Transaction{" +
				"id='" + id + '\'' +
				", cryptoCurrency=" + cryptoCurrency +
				", type=" + type +
				", quantity=" + quantity +
				", price=" + price +
				", timestamp=" + timestamp +
				'}';
	}

}
