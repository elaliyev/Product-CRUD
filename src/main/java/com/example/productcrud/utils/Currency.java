package com.example.productcrud.utils;

public enum  Currency {

    USD(0),
    EURO(1);

    private final int currency;

    Currency(int currency){
        this.currency=currency;
    }

    public int getCurrency() {
        return this.currency;
    }
}
