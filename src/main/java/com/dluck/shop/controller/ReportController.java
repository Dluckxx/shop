package com.dluck.shop.controller;

import com.dluck.shop.constant.ExpenditureType;
import com.dluck.shop.constant.IncomeType;
import com.dluck.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("report")
public class ReportController {

	@Autowired
	private ShiftService shiftService;
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private ExportService exportService;
	@Autowired
	private ImportService importService;
	@Autowired
	private ExpenditureService expenditureService;

	//报表首页
	@RequestMapping
	public String index(@RequestParam(value = "month", required = false) String month,
	                    @RequestParam(value = "year", required = false) String year,
	                    Model model) {

		//若请求头参数为空则选当前时间
		//否则为请求头选择时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthSdf = new SimpleDateFormat("MM");
		Date now = new Date();
		if (year != null && month != null) {
			try {
				now = sdf.parse(year + month);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		//绑定时间到表格
		model.addAttribute("year", yearSdf.format(now));
		model.addAttribute("month", monthSdf.format(now));

		//总共报表
		//绑定总销售收入
		model.addAttribute("total_income_export", exportService.getAllExportSum());
		//绑定总维修收入
		model.addAttribute("total_income_repair", incomeService.getIncomeSumByType(IncomeType.REPAIR.getName()));
		//绑定总打印收入
		model.addAttribute("total_income_print", incomeService.getIncomeSumByType(IncomeType.PRINT.getName()));
		//绑定总其他收入
		model.addAttribute("total_income_other", incomeService.getIncomeSumByType(IncomeType.OTHER.getName()));
		//绑定总进货支出
		model.addAttribute("total_expenditure_import", importService.getAllImportSum());
		//绑定总薪水支出
		model.addAttribute("total_expenditure_salary", expenditureService.getExpenditureSumByType(ExpenditureType.SALARY.getName()));
		//绑定总其他支出
		model.addAttribute("total_expenditure_other", expenditureService.getExpenditureSumByType(ExpenditureType.OTHER.getName()));

		//月度报表
		//绑定月销售收入
		model.addAttribute("month_income_export", exportService.getAllExportSum(now));
		//绑定月维修收入
		model.addAttribute("month_income_repair", incomeService.getIncomeSumByType(IncomeType.REPAIR.getName(), now));
		//绑定月打印收入
		model.addAttribute("month_income_print", incomeService.getIncomeSumByType(IncomeType.PRINT.getName(), now));
		//绑定月其他收入
		model.addAttribute("month_income_other", incomeService.getIncomeSumByType(IncomeType.OTHER.getName(), now));
		//绑定月进货支出
		model.addAttribute("month_expenditure_import", importService.getAllImportSum(now));
		//绑定月薪水支出
		model.addAttribute("month_expenditure_salary", expenditureService.getExpenditureSumByType(ExpenditureType.SALARY.getName(), now));
		//绑定月其他支出
		model.addAttribute("month_expenditure_other", expenditureService.getExpenditureSumByType(ExpenditureType.OTHER.getName(), now));

		//绑定今日值班人员
		model.addAttribute("nowShift", shiftService.getEmployeeNowIsWorking());
		return "report/report";
	}
}
