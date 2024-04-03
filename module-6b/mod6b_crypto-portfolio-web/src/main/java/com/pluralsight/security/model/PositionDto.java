package com.pluralsight.security.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {
	
	private CryptoCurrencyDto cryptoCurrency;
	private BigDecimal quantity;
	private BigDecimal value;

}
