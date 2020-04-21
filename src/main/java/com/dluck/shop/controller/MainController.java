package com.dluck.shop.controller;

import com.dluck.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

@Controller
public class MainController {
	@Autowired
	ShiftService shiftService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ExportService exportService;
	@Autowired
	IncomeService incomeService;

	//首页
	@RequestMapping("")
	public String index(Model model) {
		//检查数据库天数是否足够，如果不够则创建，直到够了为止
		shiftService.insertDayCheck();
		//将值班表信息传送到前端
		model.addAttribute("shiftList", shiftService.get7DaysShiftList());
		//今日收入
		model.addAttribute("todayIncome", exportService.getTodayIncome() + incomeService.getTodayIncome());
		//当前值班
		model.addAttribute("nowShift", shiftService.getEmployeeNowIsWorking());

		return "index";
	}

	//修改班次
	@RequestMapping("change")
	@ResponseBody
	public String change(@RequestParam("date") Date date,
	                     @RequestParam("shift") String shift,
	                     @RequestParam("id") String id) {

		//判断是否为空
		if (id.equals(""))
			return "请不要留空！";

		//判断学号是否为数字
		for (int i = id.length(); --i >= 0; ) {
			if (!Character.isDigit(id.charAt(i))) {
				return "请输入数字！";
			}
		}

		//判断学号是否不正确
		if (employeeService.getEmployeeByEid(Integer.parseInt(id)) == null) {
			return "请输入正确的学号！";
		}

		return shiftService.insertPerson(date, shift, employeeService.getEmployeeByEid(Integer.parseInt(id)));
	}
}
