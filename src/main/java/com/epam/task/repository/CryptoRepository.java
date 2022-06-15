package com.epam.task.repository;

import com.epam.task.model.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Integer> {
    @Query(value = "SELECT min(price) FROM crypto c where c.symbol = :symbol", nativeQuery = true)
    BigDecimal min(@Param("symbol") String symbol);

    @Query(value = "SELECT max(price) FROM crypto c where c.symbol = :symbol", nativeQuery = true)
    BigDecimal max(@Param("symbol") String symbol);

    @Query(value = "SELECT price FROM crypto c where timestamp = (SELECT MIN(timestamp) FROM crypto) and c.symbol = :symbol", nativeQuery = true)
    BigDecimal newest(@Param("symbol") String symbol);

    @Query(value = "SELECT price FROM crypto c where timestamp = (SELECT MAX(timestamp) FROM crypto) and c.symbol = :symbol", nativeQuery = true)
    BigDecimal oldest(@Param("symbol") String symbol);

    @Query(value = "SELECT * FROM crypto where price = (select max(price) from crypto where symbol = \"BTC\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where price = (select max(price) from crypto where symbol = \"DOGE\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where price = (select max(price) from crypto where symbol = \"ETH\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where price = (select max(price) from crypto where symbol = \"LTC\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where price = (select max(price) from crypto where symbol = \"XRP\")", nativeQuery = true)
    List<Crypto> maxAll();

    @Query(value = "SELECT * FROM crypto where price = (select min(price) from crypto where symbol = \"BTC\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where price = (select min(price) from crypto where symbol = \"DOGE\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where price = (select min(price) from crypto where symbol = \"ETH\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where price = (select min(price) from crypto where symbol = \"LTC\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where price = (select min(price) from crypto where symbol = \"XRP\")", nativeQuery = true)
    List<Crypto> minAll();

    @Query(value = "SELECT * FROM crypto where timestamp = (select min(timestamp) from crypto where symbol = \"BTC\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where timestamp = (select min(timestamp) from crypto where symbol = \"DOGE\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where timestamp = (select min(timestamp) from crypto where symbol = \"ETH\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where timestamp = (select min(timestamp) from crypto where symbol = \"LTC\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where timestamp = (select min(timestamp) from crypto where symbol = \"XRP\")", nativeQuery = true)
    List<Crypto> newestAll();

    @Query(value = "SELECT * FROM crypto where timestamp = (select max(timestamp) from crypto where symbol = \"BTC\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where timestamp = (select max(timestamp) from crypto where symbol = \"DOGE\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where timestamp = (select max(timestamp) from crypto where symbol = \"ETH\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where timestamp = (select max(timestamp) from crypto where symbol = \"LTC\")\n" +
            "union all\n" +
            "SELECT * FROM crypto where timestamp = (select max(timestamp) from crypto where symbol = \"XRP\")", nativeQuery = true)
    List<Crypto> oldestAll();

    @Query(value = "select (max(price)-min(price))/min(price) as norm from crypto where symbol = \"BTC\"\n" +
            "union all\n" +
            "select (max(price)-min(price))/min(price) as norm from crypto where symbol = \"DOGE\"\n" +
            "union all\n" +
            "select (max(price)-min(price))/min(price) as norm from crypto where symbol = \"ETH\"\n" +
            "union all\n" +
            "select (max(price)-min(price))/min(price) as norm from crypto where symbol = \"LTC\"\n" +
            "union all\n" +
            "select (max(price)-min(price))/min(price) as norm from crypto where symbol = \"XRP\"\n" +
            "order by norm DESC", nativeQuery = true)
    List<BigDecimal> normalizedValues();

    @Query(value = "select max(norm) from (select symbol, (max(price)-min(price))/min(price) as norm from crypto where symbol = \"BTC\" and timestamp <= :timestamp\n" +
            "union all\n" +
            "select symbol, (max(price)-min(price))/min(price) as norm from crypto where symbol = \"DOGE\" and timestamp <= :timestamp\n" +
            "union all\n" +
            "select symbol, (max(price)-min(price))/min(price) as norm from crypto where symbol = \"ETH\" and timestamp <= :timestamp\n" +
            "union all\n" +
            "select symbol, (max(price)-min(price))/min(price) as norm from crypto where symbol = \"LTC\" and timestamp <= :timestamp\n" +
            "union all\n" +
            "select symbol, (max(price)-min(price))/min(price) as norm from crypto where symbol = \"XRP\" and timestamp <= :timestamp)\n" +
            "as result", nativeQuery = true)
    BigDecimal normalizedValue(@Param("timestamp") String timestamp);

}
