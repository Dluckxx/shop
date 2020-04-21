package com.dluck.shop.controller;

import com.dluck.shop.constant.IncomeType;
import com.dluck.shop.domain.Income;
import com.dluck.shop.service.EmployeeService;
import com.dluck.shop.service.IncomeService;
import com.dluck.shop.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("income")
public class IncomeController {

	@Autowired
	private IncomeService incomeService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ShiftService shiftService;

	//自动跳转到带Type的方法
	@GetMapping
	public void income(HttpServletResponse httpServletResponse) {
		try {
			httpServletResponse.sendRedirect("income/repair");
		} catch (IOException e) {
			e.getCause();
		}
	}

	//收入表
	@GetMapping("{type}")
	public String income(@PathVariable("type") String type, Model model) {

		switch (type) {
			case "repair":
				model.addAttribute("tableName", "维修记录表");
				break;
			case "print":
				model.addAttribute("tableName", "打印记录表");
				break;
			case "other":
				model.addAttribute("tableName", "其他记录表");
				break;
			default:
				return "error";
		}

		List<Income> incomeList = incomeService.getTop100ByTypeOrderByTimeDesc(type);
		model.addAttribute("incomeList", incomeList);
		model.addAttribute("nowShift", shiftService.getEmployeeNowIsWorking());

		return "income/income";
	}

	//添加支出记录页面
	@GetMapping("add")
	public String add(Model model) {

		model.addAttribute("nowShift", shiftService.getEmployeeNowIsWorking());
		IncomeType[] incomeTypes = IncomeType.values();
		model.addAttribute("incomeTypes", incomeTypes);

		return "income/add";
	}

	//跳转到确认删除界面
	@GetMapping("delete")
	public String delete(@RequestParam("iid") int iid,
	                     Model model) {
		//查找获取对象并绑定查找到到记录对象
		model.addAttribute("income", incomeService.getIncomeByID(iid));
		return "income/delete";
	}

	//删除一条记录
	@PostMapping("deleteCheck")
	@ResponseBody
	public String deleteCheck(@RequestParam("iid") int iid,
	                          @RequestParam("password") String password) {
		Income income = incomeService.getIncomeByID(iid);
		//验证身份
		if (!income.getEmployeeByEmployee().getPassword().equals(password)) {
			return "密码错误！";
		}

		//删除该记录
		incomeService.deleteIncomeByID(iid);
		return "删除成功！";
	}

	//添加记录
	@PostMapping("check")
	@ResponseBody
	public String add(@RequestParam("type") String type,
	                  @RequestParam("info") String info,
	                  @RequestParam("price") float price,
	                  @RequestParam("id") int id) {

		//验证学号
		if (employeeService.getEmployeeByEid(id) == null) {
			return "请输入正确的学号！";
		} else {
			incomeService.addAnIncome(type, info, price, employeeService.getEmployeeByEid(id));
		}

		return "添加成功！";
	}
}
