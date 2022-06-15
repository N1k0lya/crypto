package com.epam.task.model;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "crypto")
public class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "price", precision = 10, scale = 4)
    private BigDecimal price;

    public Crypto() {

    }

    public Crypto(long id, String timestamp, String symbol, BigDecimal price) {
        this.id = id;
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.price = price;
    }

    public Crypto(String timestamp, String symbol, BigDecimal price) {
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Crypto price [id=" + id + ", timestamp=" + timestamp + ", symbol=" + symbol + ", price=" + price + "]";
    }
}
