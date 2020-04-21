package com.dluck.shop.repository;

import com.dluck.shop.domain.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ExpenditureRepository extends JpaRepository<Expenditure, Integer> {
	List<Expenditure> findTop100ByTypeOrderByTimeDesc(String type);

	List<Expenditure> findByType(String type);

	List<Expenditure> findByTypeAndTimeBetween(String type, Timestamp time, Timestamp time2);
}
