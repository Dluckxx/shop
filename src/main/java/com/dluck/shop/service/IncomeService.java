package com.dluck.shop.service;

import com.dluck.shop.domain.Employee;
import com.dluck.shop.domain.Income;
import com.dluck.shop.repository.IncomeRepository;
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
public class IncomeService {
	@Autowired
	IncomeRepository incomeRepository;

	//根据类型查找前100条记录
	public List<Income> getTop100ByTypeOrderByTimeDesc(String type) {
		return incomeRepository.findTop100ByTypeOrderByTimeDesc(type);
	}

	//添加一笔支出
	public void addAnIncome(String type, String info, float price, Employee employee) {
		Income income = new Income();
		income.setDetail(info);
		income.setPrice(price);
		income.setType(type);
		income.setEmployeeByEmployee(employee);

		System.out.println("添加了一笔收入：" + income.getDetail() + "------" + income.getPrice());

		incomeRepository.save(income);
	}

	//返回今日收入数量
	public float getTodayIncome() {
//		System.out.print("获取今日非销售收入......");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = Calendar.getInstance(Locale.CHINA).getTime();
		Timestamp timestamp;

		try {
			timestamp = new Timestamp(sdf.parse(sdf.format(date)).getTime());
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			return 0;
		}

		List<Income> incomeList = incomeRepository.findByTimeAfter(timestamp);

		float sum = 0;
		for (Income i : incomeList) {
			sum += (float) i.getPrice();
		}

//		System.out.println("成功！" + "赚了" + sum);
		return sum;
	}

	//根据月份查找
	public List<Income> getIncomeListByMonth(Date date) {
		return incomeRepository.findByTimeBetween(TimeUtils.getTimestampThisMonthStart(date), TimeUtils.getTimestampNextMonthStart(date));
	}

	//获取全部某类收入之和
	public int getIncomeSumByType(String type) {
		List<Income> list = incomeRepository.findByType(type);

		float sum = 0;

		for (Income i : list) {
			sum += i.getPrice();
		}

		return (int) sum;
	}

	//获取月度某类收入之和
	public int getIncomeSumByType(String type, Date date) {
		List<Income> list = getIncomeListByMonth(date);

		float sum = 0;

		for (Income i : list) {
			if (i.getType().equals(type))
				sum += i.getPrice();
		}

		return (int) sum;
	}

	//根据ID查找单条记录
	public Income getIncomeByID(int id) {
		return incomeRepository.getOne(id);
	}

	//根据ID删除单条记录
	public void deleteIncomeByID(int id) {
		Income income = incomeRepository.getOne(id);
		System.out.println("删除了一笔收入：" + income.getDetail() + "----" + income.getPrice() + "----" + income.getEmployeeByEmployee().getName());
		incomeRepository.delete(income);
	}
}
