package dev.gunho.stock.repository;

import dev.gunho.stock.entity.StockSymbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockSymbolRepository extends JpaRepository<StockSymbol, String> {

    Optional<StockSymbol> findBySymbol(String symbol);
}
