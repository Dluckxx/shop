package com.dluck.shop.repository;

import com.dluck.shop.domain.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
	List<Income> findTop100ByTypeOrderByTimeDesc(String type);

	List<Income> findByTimeAfter(Timestamp timestamp);

	List<Income> findByTimeBetween(Timestamp start, Timestamp end);

	List<Income> findByType(String type);
}
