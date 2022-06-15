package com.epam.task.service;

import com.epam.task.util.CSVHelper;
import com.epam.task.repository.CryptoRepository;
import com.epam.task.model.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CSVService {
    @Autowired
    CryptoRepository repository;

    public void save(MultipartFile file) {
        try {
            List<Crypto> cryptos = CSVHelper.csvToCrypto(file.getInputStream());
            repository.saveAll(cryptos);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<Crypto> cryptos = repository.findAll();

        return CSVHelper.cryptosToCSV(cryptos);
    }

    public List<Crypto> getAllCryptos() {
        return repository.findAll();
    }

    public BigDecimal findMaxPrice(String symbol) {
        return repository.max(symbol);
    }

    public BigDecimal findMinPrice(String symbol) {
        return repository.min(symbol);
    }

    public BigDecimal findNewestPrice(String symbol) {
        return repository.newest(symbol);
    }

    public BigDecimal findOldestPrice(String symbol) {
        return repository.oldest(symbol);
    }

    public List<Crypto> findMaxPriceAll() {
        return repository.maxAll();
    }

    public List<Crypto> findMinPriceAll() {
        return repository.minAll();
    }

    public List<Crypto> findNewestPriceAll() {
        return repository.newestAll();
    }

    public List<Crypto> findOldestPriceAll() {
        return repository.oldestAll();
    }

    public List<BigDecimal> findNormalizedValues() {
        return repository.normalizedValues();
    }

    public BigDecimal findNormalizedValue(String timestamp) {
        return repository.normalizedValue(timestamp);
    }
}


