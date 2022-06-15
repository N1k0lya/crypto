package com.epam.task.util;

import com.epam.task.model.Crypto;
import com.epam.task.model.CryptoType;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Id", "timestamp", "symbol", "price"};

    public static boolean hasCSVFormat(MultipartFile file) {
        System.out.println(file.getContentType());
        return TYPE.equals(file.getContentType())
                || "application/vnd.ms-excel".equals(file.getContentType());
    }

    public static List<Crypto> csvToCrypto(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Crypto> cryptoPriceList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                String cryptoSymbol = csvRecord.get(1);
                if(!(CryptoType.BTC.name().equals(cryptoSymbol)) ||
                        CryptoType.ETH.name().equals(cryptoSymbol)||
                        CryptoType.LTC.name().equals(cryptoSymbol)||
                        CryptoType.XRP.name().equals(cryptoSymbol)||
                        CryptoType.DOGE.name().equals(cryptoSymbol)) {
                    throw new RuntimeException("Crypto currently is not supported!");
                }
            }

            for (CSVRecord csvRecord : csvRecords) {
                Crypto cryptoPrice = new Crypto(
                        csvRecord.get("timestamp"),
                        csvRecord.get("symbol"),
                        BigDecimal.valueOf(Double.parseDouble(csvRecord.get("price"))));

                cryptoPriceList.add(cryptoPrice);
            }

            return cryptoPriceList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream cryptosToCSV(List<Crypto> cryptoPriceList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (Crypto cryptoPrice : cryptoPriceList) {
                List<String> data = Arrays.asList(
                        String.valueOf(cryptoPrice.getId()),
                        cryptoPrice.getTimestamp(),
                        cryptoPrice.getSymbol(),
                        String.valueOf(cryptoPrice.getPrice())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
