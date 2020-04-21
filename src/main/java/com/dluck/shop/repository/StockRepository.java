package com.dluck.shop.repository;

import com.dluck.shop.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

	//获取库存大于0的数据
	List<Stock> findByNumberIsNotOrderByNameAsc(int number);

	//获取库存等于0的数据
	List<Stock> findByNumberIsOrderByNameAsc(int number);

	//根据商品名查找
	Stock findByName(String name);
}
