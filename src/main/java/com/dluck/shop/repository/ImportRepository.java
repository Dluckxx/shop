package com.dluck.shop.repository;

import com.dluck.shop.domain.Import;
import com.dluck.shop.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ImportRepository extends JpaRepository<Import, Integer> {
	List<Import> findTop100ByOrderByTimeDesc();

	List<Import> findByTimeBetween(Timestamp start, Timestamp end);

	List<Import> findByStockByGoodsIs(Stock stock);
}
