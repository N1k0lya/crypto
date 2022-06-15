package com.epam.task.model;

public enum CryptoType {
    BTC("BTC"), ETH("ETH"), DOGE("DOGE"), LTC("LTC"), XRP("XRP");

    private String value;

    CryptoType(String value) {
        this.value = value;
    }
}
