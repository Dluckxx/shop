package com.dluck.shop.service;

import com.dluck.shop.domain.Import;
import com.dluck.shop.domain.Stock;
import com.dluck.shop.repository.ImportRepository;
import com.dluck.shop.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ImportService {

	@Autowired
	private ImportRepository importRepository;

	//添加一条进货记录
	public void importAnItem(Stock stock, int number, float price) {
		System.out.println("添加一条进货记录：" + stock.getName() + " 数目：" + number + "单价" + price);
		Import record = new Import();
		record.setStockByGoods(stock);
		record.setNumber(number);
		record.setPrice(price);
		importRepository.save(record);
	}

	//获取前100条进货记录
	public List<Import> getTop100ImportList() {
		return importRepository.findTop100ByOrderByTimeDesc();
	}

	//根据ID删除一条进货记录
	public void deleteByIid(int iid) {
		Import record = importRepository.findById(iid).orElse(null);
		if (record != null) {
			importRepository.delete(record);
			System.out.println("删除了一条进货记录:" + record.getTime() + "----" + record.getStockByGoods().getName() + "----" + record.getNumber());
		} else {
			System.err.println("没有找到要删除的记录！！！");
		}
	}

	//获取总进货成本
	public int getAllImportSum() {
		List<Import> list = importRepository.findAll();

		float sum = 0;
		for (Import i : list) {
			sum += i.getNumber() * i.getPrice();
		}

		return (int) sum;
	}

	//获取月进货成本
	public int getAllImportSum(Date date) {
		List<Import> importList = importRepository.findByTimeBetween(TimeUtils.getTimestampThisMonthStart(date), TimeUtils.getTimestampNextMonthStart(date));

		float sum = 0;

		for (Import e : importList) {
			sum += e.getPrice()*e.getNumber();
		}
		return (int) sum;
	}

	//获取所有涉及到某商品的记录
	public List<Import> getAllImportWithStock(Stock stock){
		return importRepository.findByStockByGoodsIs(stock);
	}
}
