package com.pluralsight.security.model;

public class TransactionDetailsDto {

	private final Long id;
	private final String symbol;
	private final String transactionType;
	private final String quantity;
	private final String price;

	public TransactionDetailsDto(Long id, String symbol, String transactionType, String quantity, String price) {
		this.id = id;
		this.symbol = symbol;
		this.transactionType = transactionType;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getPrice() {
		return price;
	}

}
