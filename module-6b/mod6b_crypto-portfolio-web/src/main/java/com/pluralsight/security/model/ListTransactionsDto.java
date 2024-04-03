package com.pluralsight.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ListTransactionsDto {

	private final String username;
	private final List<TransactionDetailsDto> transactions;
	
}
