package com.dluck.shop.service;

import com.dluck.shop.domain.Employee;
import com.dluck.shop.domain.Expenditure;
import com.dluck.shop.domain.Export;
import com.dluck.shop.repository.ExpenditureRepository;
import com.dluck.shop.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExpenditureService {

	@Autowired
	private ExpenditureRepository expenditureRepository;

	//根据类型查找最近100条记录
	public List<Expenditure> getTop100ListByTypeOrderByTimeDesc(String type) {
		return expenditureRepository.findTop100ByTypeOrderByTimeDesc(type);
	}

	//添加一条支出记录
	public void addAnExpenditure(String type, String info, float price, Employee employee) {
		Expenditure expenditure = new Expenditure();
		expenditure.setDetail(info);
		expenditure.setPrice(price);
		expenditure.setType(type);
		expenditure.setEmployeeByEmployee(employee);

		System.out.println("添加了一笔支出：" + expenditure.getDetail() + "------" + expenditure.getPrice());

		expenditureRepository.save(expenditure);
	}

	//获取某一类型的总支出
	public int getExpenditureSumByType(String type) {
		List<Expenditure> list = expenditureRepository.findByType(type);

		float sum = 0;

		for (Expenditure e : list) {
			sum += e.getPrice();
		}

		return (int) sum;
	}

	//获取某一类型的总支出
	public int getExpenditureSumByType(String type, Date date) {
		List<Expenditure> list = expenditureRepository.findByTypeAndTimeBetween(type, TimeUtils.getTimestampThisMonthStart(date), TimeUtils.getTimestampNextMonthStart(date));

		float sum = 0;

		for (Expenditure e : list) {
			if (e.getType().equals(type))
				sum += e.getPrice();
		}

		return (int) sum;
	}

	//根据ID查询单条记录
	public Expenditure getExpenditureByID(int id) {
		return expenditureRepository.getOne(id);
	}

	//根据ID删除单条记录
	public void deleteExpenditureByID(int id) {
		Expenditure expenditure = expenditureRepository.getOne(id);
		System.out.println("删除了一笔收入：" + expenditure.getDetail() + "----" + expenditure.getPrice() + "----" + expenditure.getEmployeeByEmployee().getName());
		expenditureRepository.delete(expenditure);
	}
}
