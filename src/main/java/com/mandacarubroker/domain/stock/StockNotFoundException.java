package com.mandacarubroker.domain.stock;

public class StockNotFoundException extends Exception {
    public StockNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
