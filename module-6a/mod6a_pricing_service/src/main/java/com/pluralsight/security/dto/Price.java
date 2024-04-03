package com.pluralsight.security.dto;

public class Price {

    private String symbol;
    private String price;

    public Price(String symbol, String price) {
        this.price=price;
        this.symbol=symbol;
    }

    public String getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

}
