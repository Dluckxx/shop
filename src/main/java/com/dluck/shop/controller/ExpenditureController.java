package com.dluck.shop.controller;

import com.dluck.shop.constant.ExpenditureType;
import com.dluck.shop.domain.Expenditure;
import com.dluck.shop.service.EmployeeService;
import com.dluck.shop.service.ExpenditureService;
import com.dluck.shop.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("expenditure")
public class ExpenditureController {
	@Autowired
	private ExpenditureService expenditureService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ShiftService shiftService;

	//跳转到支出表
	@RequestMapping
	public void income(HttpServletResponse httpServletResponse) {
		try {
			httpServletResponse.sendRedirect("expenditure/salary");
		} catch (IOException e) {
			e.getCause();
		}
	}

	//支出表
	@RequestMapping("{type}")
	public String expenditure(@PathVariable("type") String type, Model model) {

		switch (type) {
			case "salary":
				model.addAttribute("tableName", "薪水支出表");
				break;
			case "other":
				model.addAttribute("tableName", "其他支出表");
				break;
			default:
				return "error";
		}

		List<Expenditure> expenditureList = expenditureService.getTop100ListByTypeOrderByTimeDesc(type);
		model.addAttribute("expenditureList", expenditureList);
		model.addAttribute("nowShift", shiftService.getEmployeeNowIsWorking());

		return "expenditure/expenditure";
	}

	//添加支出记录页面
	@RequestMapping("add")
	public String add(Model model) {

		model.addAttribute("nowShift", shiftService.getEmployeeNowIsWorking());
		ExpenditureType[] expenditureTypes = ExpenditureType.values();
		model.addAttribute("expenditureTypes", expenditureTypes);

		return "expenditure/add";
	}


	//跳转到确认删除界面
	@GetMapping("delete")
	public String delete(@RequestParam("eid") int eid,
	                     Model model) {
		//查找获取对象并绑定查找到到记录对象
		model.addAttribute("expenditure", expenditureService.getExpenditureByID(eid));
		return "expenditure/delete";
	}

	//删除一条记录
	@PostMapping("deleteCheck")
	@ResponseBody
	public String deleteCheck(@RequestParam("eid") int eid,
	                          @RequestParam("password") String password) {
		Expenditure expenditure = expenditureService.getExpenditureByID(eid);
		//验证身份
		if (!expenditure.getEmployeeByEmployee().getPassword().equals(password)) {
			return "密码错误！";
		}

		//删除该记录
		expenditureService.deleteExpenditureByID(eid);
		return "删除成功！";
	}


	//添加一条记录
	@RequestMapping("check")
	@ResponseBody
	public String add(@RequestParam("type") String type,
	                  @RequestParam("info") String info,
	                  @RequestParam("price") float price,
	                  @RequestParam("id") int id) {

		//验证学号
		if (employeeService.getEmployeeByEid(id) == null) {
			return "请输入正确的学号！";
		} else {
			expenditureService.addAnExpenditure(type, info, price, employeeService.getEmployeeByEid(id));
		}

		return "添加成功！";
	}
}
