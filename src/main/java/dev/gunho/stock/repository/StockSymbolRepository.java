package dev.gunho.stock.repository;

import dev.gunho.stock.entity.StockSymbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockSymbolRepository extends JpaRepository<StockSymbol, String> {

}
