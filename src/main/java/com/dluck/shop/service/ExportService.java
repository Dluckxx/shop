package com.dluck.shop.service;

import com.dluck.shop.domain.Export;
import com.dluck.shop.domain.Stock;
import com.dluck.shop.repository.ExportRepository;
import com.dluck.shop.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class ExportService {
	@Autowired
	ExportRepository exportRepository;

	//添加一条销售记录
	public void sellAnItem(Stock stock, int number, float price) {
		System.out.println("添加一条销售记录：" + stock.getName() + " 数目：" + number + " 价格" + price);
		Export export = new Export();
		export.setStockByStock(stock);
		export.setNumber(number);
		export.setPrice(price);
		exportRepository.save(export);
	}

	//获取最近100条销售记录
	public List<Export> getTop100ExportList() {
		return exportRepository.findTop100ByOrderByTimeDesc();
	}

	//获取今日收入
	public float getTodayIncome() {
//		System.out.print("获取今日销售收入......");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = Calendar.getInstance(Locale.CHINA).getTime();
		Timestamp timestamp;

		try {
			timestamp = new Timestamp(sdf.parse(sdf.format(date)).getTime());
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			return 0;
		}

		List<Export> exportList = exportRepository.findByTimeAfter(timestamp);

		float sum = 0;
		for (Export e : exportList) {
			sum += (float) e.getPrice();
		}

//		System.out.println("成功！" + "赚了" + sum);
		return sum;
	}

	//根据ID删除一条销售记录
	public void deleteByEid(int eid) {
		Export record = exportRepository.findById(eid).orElse(null);
		if (record != null) {
			exportRepository.delete(record);
			System.out.println("删除了一条销售记录:" + record.getTime() + "----" + record.getStockByStock().getName() + "----" + record.getNumber());
		} else {
			System.err.println("没有找到要删除的记录！！！");
		}
	}

	//获取总销售收入之和
	public int getAllExportSum() {
		List<Export> list = exportRepository.findAll();

		float sum = 0;
		for (Export e : list) {
			sum += e.getPrice();
		}
		return (int) sum;
	}

	//获取月度销售收入和
	public int getAllExportSum(Date now) {
		List<Export> exportList = exportRepository.findByTimeBetween(TimeUtils.getTimestampThisMonthStart(now), TimeUtils.getTimestampNextMonthStart(now));;

		float sum = 0;

		for (Export e : exportList) {
			sum += e.getPrice();
		}
		return (int) sum;
	}

	//获取涉及到某商品的全部记录
	public List<Export> getAllExportWithStock(Stock stock){
		return exportRepository.findByStockByStockIs(stock);
	}
}
