package com.dluck.shop.service;

import com.dluck.shop.domain.Employee;
import com.dluck.shop.domain.Shift;
import com.dluck.shop.repository.ShiftRepository;
import com.dluck.shop.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ShiftService {
	@Autowired
	ShiftRepository shiftRepository;

	//返回-1 ～ 5天的值班表
	public List<Shift> get7DaysShiftList() {

		//获取当前时间
		Date now = Calendar.getInstance(Locale.CHINA).getTime();
//		System.out.println("Java当前时间：" + now);

		//计算开始和结束时间
		java.sql.Date start = new java.sql.Date(TimeUtils.getDateAfter(now, -1).getTime());
		java.sql.Date end = new java.sql.Date(TimeUtils.getDateAfter(now, 5).getTime());

		return shiftRepository.findByDateBetween(start, end);
	}

	//检查并在数据库创建新的行，直到达到目标天数
	public void insertDayCheck() {

//		System.out.print("开始自动更新数据库天数......");

		Date now = Calendar.getInstance(Locale.CHINA).getTime();
//		System.out.println("当前Java时间:" + now);
		java.sql.Date targetDate = new java.sql.Date(TimeUtils.getDateAfter(now, 5).getTime());
//		System.out.println("数据库的最后记录日期日期是:" + shiftRepository.findFirstByDateIsNotNullOrderByDateDesc().getDate());

		while (true) {
			Shift shift = new Shift();
			java.sql.Date nextDay = new java.sql.Date((TimeUtils.getDateAfter(new Date(shiftRepository.findFirstByDateIsNotNullOrderByDateDesc().getDate().getTime()), 1)).getTime());
			shift.setDate(nextDay);

//			System.out.println("下一天是:" + nextDay);

			if (nextDay.compareTo(targetDate) > 0)
				break;

			shiftRepository.save(shift);
//			System.out.println("添加成功！");
		}

//		System.out.println("完成！现在数据库的最后记录日期是:" + shiftRepository.findFirstByDateIsNotNullOrderByDateDesc().getDate());
	}

	//更改值班表
	public String insertPerson(java.sql.Date date, String shift, Employee employee) {

		//检查正在修改的是否是自己的班次
		System.out.print(employee.getName() + "正在修改" + date + "的值班表----");

		Shift s = shiftRepository.findByDate(date);

		if (s == null) {
			System.err.println("未找到相应记录，卒～～～");
			return "未找到相应记录，卒～～～";
		}

		switch (shift) {
			case ("a"):
				if (s.getEmployeeByA() == null) {
					s.setEmployeeByA(employee);
					break;
				} else if (s.getEmployeeByA().getEid() == employee.getEid()) {
					s.setEmployeeByA(null);
					shiftRepository.save(s);
					System.out.println("班次已消除！");
					return "已经消除你的班次";
				} else {
					System.out.println("班次已被占用！");
					return "你不能选择别人已经选择的班次";
				}
			case ("b"):
				if (s.getEmployeeByB() == null) {
					s.setEmployeeByB(employee);
					break;
				} else if (s.getEmployeeByB().getEid() == employee.getEid()) {
					s.setEmployeeByB(null);
					shiftRepository.save(s);
					System.out.println("班次已消除！");
					return "已经消除你的班次";
				} else {
					System.out.println("班次已被占用！");
					return "你不能选择别人已经选择的班次";
				}
			case ("c"):
				if (s.getEmployeeByC() == null) {
					s.setEmployeeByC(employee);
					break;
				} else if (s.getEmployeeByC().getEid() == employee.getEid()) {
					s.setEmployeeByC(null);
					shiftRepository.save(s);
					System.out.println("班次已消除！");
					return "已经消除你的班次";
				} else {
					System.out.println("班次已被占用！");
					return "你不能选择别人已经选择的班次";
				}
			case ("d"):
				if (s.getEmployeeByD() == null) {
					s.setEmployeeByD(employee);
					break;
				} else if (s.getEmployeeByD().getEid() == employee.getEid()) {
					s.setEmployeeByD(null);
					shiftRepository.save(s);
					System.out.println("班次已消除！");
					return "已经消除你的班次";
				} else {
					System.out.println("班次已被占用！");
					return "你不能选择别人已经选择的班次";
				}
			case ("e"):
				if (s.getEmployeeByE() == null) {
					s.setEmployeeByE(employee);
					break;
				} else if (s.getEmployeeByE().getEid() == employee.getEid()) {
					s.setEmployeeByE(null);
					shiftRepository.save(s);
					System.out.println("班次已消除！");
					return "已经消除你的班次";
				} else {
					System.out.println("班次已被占用！");
					return "你不能选择别人已经选择的班次";
				}
			case ("f"):
				if (s.getEmployeeByF() == null) {
					s.setEmployeeByF(employee);
					break;
				} else if (s.getEmployeeByF().getEid() == employee.getEid()) {
					s.setEmployeeByF(null);
					shiftRepository.save(s);
					System.out.println("班次已消除！");
					return "已经消除你的班次";
				} else {
					System.out.println("班次已被占用！");
					return "你不能选择别人已经选择的班次";
				}
			case ("g"):
				if (s.getEmployeeByG() == null) {
					s.setEmployeeByG(employee);
					break;
				} else if (s.getEmployeeByG().getEid() == employee.getEid()) {
					s.setEmployeeByG(null);
					shiftRepository.save(s);
					System.out.println("班次已消除！");
					return "已经消除你的班次";
				} else {
					System.out.println("班次已被占用！");
					return "你不能选择别人已经选择的班次";
				}
		}

		//检查7天内此人值班次数是否超过7次
		int count = 0;
		for (Shift ss : get7DaysShiftList()) {
			if (ss.getEmployeeByA() != null && ss.getEmployeeByA().getEid() == employee.getEid())
				count++;
			if (ss.getEmployeeByB() != null && ss.getEmployeeByB().getEid() == employee.getEid())
				count++;
			if (ss.getEmployeeByC() != null && ss.getEmployeeByC().getEid() == employee.getEid())
				count++;
			if (ss.getEmployeeByD() != null && ss.getEmployeeByD().getEid() == employee.getEid())
				count++;
			if (ss.getEmployeeByE() != null && ss.getEmployeeByE().getEid() == employee.getEid())
				count++;
			if (ss.getEmployeeByF() != null && ss.getEmployeeByF().getEid() == employee.getEid())
				count++;
			if (ss.getEmployeeByG() != null && ss.getEmployeeByG().getEid() == employee.getEid())
				count++;
		}
		if (count > 7) {
			System.out.println("检查到值班次数过多");
			return "你的值班次数太多了(>7),把机会留给别人吧！";
		}

		shiftRepository.save(s);
		System.out.println("成功--" + s.getDate() + "--" + shift);

		return "修改成功!" + s.getDate() + "的" + shift + "班次已经是" + employee.getName() + "的了!";
	}

	//获取当前值班人员,找到返回Employee，否则返回其他字符串
	public String getEmployeeNowIsWorking() {
//		System.out.print("正在查找当前值班人员......");

		Date date = Calendar.getInstance(Locale.CHINA).getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH");

		java.sql.Date today;
		try {
			today = new java.sql.Date(sdf1.parse(sdf1.format(date)).getTime());
		} catch (ParseException e) {
			System.err.println(e.getMessage());
			return "日期解析异常";
		}

		//获取当前天班次
		Shift shift = shiftRepository.findByDate(today);

		if (shift == null) {
			System.err.println("没有找到相应记录！");
			return "查无记录";
		}

//		System.out.println(date);
//		System.out.println(sdf2.format(date));

		Employee employee;

		switch (sdf2.format(date)) {
			case ("08"):
			case ("09"):
				employee = shift.getEmployeeByA();
				break;
			case ("10"):
			case ("11"):
				employee = shift.getEmployeeByB();
				break;
			case ("12"):
			case ("13"):
				employee = shift.getEmployeeByC();
				break;
			case ("14"):
			case ("15"):
				employee = shift.getEmployeeByD();
				break;
			case ("16"):
			case ("17"):
				employee = shift.getEmployeeByE();
				break;
			case ("18"):
			case ("19"):
				employee = shift.getEmployeeByF();
				break;
			case ("20"):
			case ("21"):
				employee = shift.getEmployeeByG();
				break;
			default:
				return "无人";
		}

		if (employee != null) {
			return employee.getName();
		} else {
			return "无人";
		}
	}

	//获取指定月份的值班表
	public List<Shift> getShiftListByMonth(Date date) {
		return shiftRepository.findByDateBetween(TimeUtils.getSqlDateThisMonthStart(date), TimeUtils.getSqlDateNextMonthStart(date));
	}
}
