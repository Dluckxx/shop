package com.dluck.shop.service;

import com.dluck.shop.domain.Stock;
import com.dluck.shop.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	//根据ID查找单个货物
	public Stock getStockByID(Integer id) {
		return stockRepository.findById(id).orElse(null);
	}

	//返回全部货物
	public List<Stock> getStockList() {
		List<Stock> stock = stockRepository.findByNumberIsNotOrderByNameAsc(0);
		List<Stock> zeroStock = stockRepository.findByNumberIsOrderByNameAsc(0);
		stock.addAll(zeroStock);
		return stock;
	}

	//返回有货的货物
	public List<Stock> getStockOnList() {
		return stockRepository.findByNumberIsNotOrderByNameAsc(0);
	}

	//返回无货的货物
	public List<Stock> getStockOffList() {
		return stockRepository.findByNumberIsOrderByNameAsc(0);
	}

	//按货物名查找商品
	public Stock getStockByName(String name) {
		return stockRepository.findByName(name);
	}

	//编辑一个货物
	public void editStock(Stock stock, String name, int number, float buy, float sale) {
		stock.setName(name);
		stock.setNumber(number);
		stock.setBuyPrice(buy);
		stock.setReferencePrice(sale);
		stockRepository.save(stock);
	}

	//删除一个货物
	public void deleteStock(Stock stock) {
		stockRepository.delete(stock);
	}
}
