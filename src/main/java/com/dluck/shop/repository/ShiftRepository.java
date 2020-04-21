package com.dluck.shop.repository;

import com.dluck.shop.domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {

	List<Shift> findByDateBetween(Date start, Date end);

	Shift findFirstByDateIsNotNullOrderByDateDesc();

	Shift findByDate(Date date);

	List<Shift> findByDateStartsWith(Date date);
}
