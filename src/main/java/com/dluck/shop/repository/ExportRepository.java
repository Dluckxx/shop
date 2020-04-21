package com.dluck.shop.repository;

import com.dluck.shop.domain.Export;
import com.dluck.shop.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ExportRepository extends JpaRepository<Export, Integer> {
	List<Export> findByTimeAfter(Timestamp time);

	List<Export> findTop100ByOrderByTimeDesc();

	List<Export> findByTimeBetween(Timestamp start, Timestamp end);

	List<Export> findByStockByStockIs(Stock stock);
}
