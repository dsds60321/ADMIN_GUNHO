package dev.gunho.stock.repository;

import dev.gunho.stock.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Page<Stock> findByUserIdxOrderByRegDate(long idx, Pageable pageable);

    List<Stock> findAllByUserIdx(Long userIdx);

    Optional<Stock> findByUserIdxAndSymbol(Long userIdx, String symbol);
}
