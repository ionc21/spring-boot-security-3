package com.pluralsight.security.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
public class Portfolio {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String userId;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Transaction> transactions;

	public Portfolio() {}

	public Portfolio(String userId, List<Transaction> transactions) {
		this.userId = userId;
		this.transactions = transactions;
	}

	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}
	
	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(transactions);
	}
	
	public List<Transaction> getTransactionsForCoin(String symbol) {
		return transactions.stream().filter(transaction -> 
			transaction.getCryptoCurrency().getSymbol().equals(symbol)).collect(Collectors.toList());
	}
	
	public void deleteTransaction(Transaction transaction) {
		transactions.remove(transaction);
	}
	
	public Optional<Transaction> getTransactionById(String id) {
		return transactions.stream().filter(transaction -> id.equals(transaction.getId())).findFirst();
	}

	public Long getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

}
