package com.epam.task.controller;

import com.epam.task.util.CSVHelper;
import com.epam.task.service.CSVService;
import com.epam.task.message.ResponseMessage;
import com.epam.task.model.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api")
public class CSVController {

    @Autowired
    CSVService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/download/")
                        .path(Objects.requireNonNull(file.getOriginalFilename()))
                        .toUriString();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, fileDownloadUri));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, ""));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, ""));
    }

    @GetMapping("/prices")
    public ResponseEntity<List<Crypto>> getAllPrices() {
        try {
            List<Crypto> cryptos = fileService.getAllCryptos();

            if (cryptos.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cryptos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cryptos/{symbol}/prices/max")
    public ResponseEntity<BigDecimal> getMaxPrice(@PathVariable String symbol) {
        try {
            BigDecimal price = fileService.findMaxPrice(symbol);

            if (price == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(price, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cryptos/{symbol}/prices/min")
    public ResponseEntity<BigDecimal> getMinPrice(@PathVariable String symbol) {
        try {
            BigDecimal price = fileService.findMinPrice(symbol);

            if (price == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(price, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/cryptos/{symbol}/prices/newest")
    public ResponseEntity<BigDecimal> getNewestPrice(@PathVariable String symbol) {
        try {
            BigDecimal price = fileService.findNewestPrice(symbol);

            if (price == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(price, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cryptos/{symbol}/prices/oldest")
    public ResponseEntity<BigDecimal> getOldestPrice(@PathVariable String symbol) {
        try {
            BigDecimal price = fileService.findOldestPrice(symbol);

            if (price == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(price, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cryptos/prices/max")
    public ResponseEntity<List<Crypto>> getMaxPriceAll() {
        try {
            List<Crypto> prices = fileService.findMaxPriceAll();

            if (prices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(prices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cryptos/prices/min")
    public ResponseEntity<List<Crypto>> getMinPriceAll() {
        try {
            List<Crypto> prices = fileService.findMinPriceAll();

            if (prices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(prices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cryptos/prices/newest")
    public ResponseEntity<List<Crypto>> getNewestPriceAll() {
        try {
            List<Crypto> prices = fileService.findNewestPriceAll();

            if (prices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(prices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cryptos/prices/oldest")
    public ResponseEntity<List<Crypto>> getOldestPriceAll() {
        try {
            List<Crypto> prices = fileService.findOldestPriceAll();

            if (prices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(prices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cryptos/prices/normalized")
    public ResponseEntity<List<BigDecimal>> getNormalizedCryptos() {
        try {
            List<BigDecimal> prices = fileService.findNormalizedValues();

            if (prices == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(prices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cryptos/prices/normalizedByDate")
    public ResponseEntity<BigDecimal> getNormalizedCryptoByDate(String timestamp) {
        try {
            BigDecimal prices = fileService.findNormalizedValue(timestamp);

            if (prices == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(prices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        InputStreamResource file = new InputStreamResource(fileService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}

