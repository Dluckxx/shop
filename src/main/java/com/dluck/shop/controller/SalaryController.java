package com.dluck.shop.controller;

import com.dluck.shop.domain.Employee;
import com.dluck.shop.domain.Income;
import com.dluck.shop.domain.Shift;
import com.dluck.shop.service.EmployeeService;
import com.dluck.shop.service.IncomeService;
import com.dluck.shop.service.ShiftService;
import com.dluck.shop.utils.SalaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("salary")
public class SalaryController {

	@Autowired
	private ShiftService shiftService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private IncomeService incomeService;

	//工资表
	@RequestMapping
	public String salary(@RequestParam(value = "year", required = false) String year,
	                     @RequestParam(value = "month", required = false) String month,
	                     Model model) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		SalaryUtils salaryUtils = new SalaryUtils();

		Date date;
		try {
			if (year == null || month == null) {
				date = Calendar.getInstance(Locale.CHINA).getTime();
			} else {
				date = sdf.parse(year + "-" + month);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return "error";
		}

		List<Employee> employeeList = employeeService.getAll();
		List<Shift> shiftList = shiftService.getShiftListByMonth(date);
		List<Income> incomeList = incomeService.getIncomeListByMonth(date);

		model.addAttribute("date", sdf.format(date));
		model.addAttribute("nowShift", shiftService.getEmployeeNowIsWorking());
		model.addAttribute("salaryList", salaryUtils.getSalaryList(employeeList, shiftList, incomeList));

		return "salary/salary";
	}
}
