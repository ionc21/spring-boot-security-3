package com.pluralsight.security.model;

import lombok.NonNull;

public class AddTransactionToPortfolioDto {

	@NonNull
	private String cryptoSymbol;
	@NonNull
	private String quantity;
	@NonNull
	private String price;
	@NonNull
	private String transactionType;
	private String username;

	public AddTransactionToPortfolioDto(@NonNull String cryptoSymbol, @NonNull String quantity, @NonNull String price, @NonNull String transactionType) {
		this.cryptoSymbol = cryptoSymbol;
		this.quantity = quantity;
		this.price = price;
		this.transactionType = transactionType;
	}

	public AddTransactionToPortfolioDto() {
	}

	public @NonNull String getCryptoSymbol() {
		return this.cryptoSymbol;
	}

	public @NonNull String getQuantity() {
		return this.quantity;
	}

	public @NonNull String getPrice() {
		return this.price;
	}

	public @NonNull String getTransactionType() {
		return this.transactionType;
	}

	public String getUsername() {
		return this.username;
	}

	public void setCryptoSymbol(@NonNull String cryptoSymbol) {
		this.cryptoSymbol = cryptoSymbol;
	}

	public void setQuantity(@NonNull String quantity) {
		this.quantity = quantity;
	}

	public void setPrice(@NonNull String price) {
		this.price = price;
	}

	public void setTransactionType(@NonNull String transactionType) {
		this.transactionType = transactionType;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
