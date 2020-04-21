package com.dluck.shop.controller;

import com.dluck.shop.domain.Export;
import com.dluck.shop.domain.Import;
import com.dluck.shop.domain.Stock;
import com.dluck.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("stock")
public class StockController {

	@Autowired
	private StockService stockService;
	@Autowired
	private ExportService exportService;
	@Autowired
	private ImportService importService;
	@Autowired
	private ShiftService shiftService;
	@Autowired
	private EmployeeService employeeService;

	//商品表
	@GetMapping
	public String goods(Model model) {
		model.addAttribute("onList", stockService.getStockOnList());
		model.addAttribute("offList", stockService.getStockOffList());
		model.addAttribute("nowShift", shiftService.getEmployeeNowIsWorking());
		return "stock/stock";
	}

	//进货记录
	@GetMapping("import")
	public String importStock(Model model) {
		model.addAttribute("importList", importService.getTop100ImportList());
		model.addAttribute("nowShift", shiftService.getEmployeeNowIsWorking());
		return "stock/import";
	}

	//出货记录
	@GetMapping("export")
	public String exportStock(Model model) {
		model.addAttribute("exportList", exportService.getTop100ExportList());
		model.addAttribute("nowShift", shiftService.getEmployeeNowIsWorking());
		return "stock/export";
	}

	//登陆界面
	@GetMapping("login")
	public String login(@RequestParam("sid") Integer sid,
	                    Model model) {
		model.addAttribute("msg", "在编辑商品之前，我们需要确认你的权限");
		model.addAttribute("sid", sid);
		return "stock/login";
	}

	//编辑界面
	@GetMapping("edit")
	public String edit(@RequestParam("sid") Integer sid,
	                   @RequestParam("eid") Integer eid,
	                   @RequestParam("password") String password,
	                   Model model) {
		model.addAttribute("sid", sid);

		if (eid == null || password.equals("")) {
			model.addAttribute("msg", "在编辑商品之前，我们需要确认你的权限");
		} else if (employeeService.verifyIdentityBoss(eid, password)) {
			//查找该商品涉及到多少条记录
			int a = importService.getAllImportWithStock(stockService.getStockByID(sid)).size();
			int b = exportService.getAllExportWithStock(stockService.getStockByID(sid)).size();
			model.addAttribute("number", a + b);
			//绑定商品ID
			model.addAttribute("stock", stockService.getStockByID(sid));
			return "stock/edit";
		} else if (!employeeService.verifyIdentityBoss(eid, password)) {
			if (employeeService.verifyIdentityAllUser(eid, password)) {
				model.addAttribute("msg", "普通员工没有权限编辑商品！");
			} else {
				model.addAttribute("msg", "用户名或密码错误！");
			}
		}

		return "stock/login";

	}

	//进货/销售了一个东西
	@ResponseBody
	@PostMapping("change")
	public String change(@RequestParam("type") String type,
	                     @RequestParam("name") String name,
	                     @RequestParam("number") Integer number,
	                     @RequestParam("price") Float price) {

		//获取目标商品
		Stock stock = stockService.getStockByName(name);

		if (type.equals("export")) {
			//添加销售记录到销售表
			exportService.sellAnItem(stock, number, price);
			return "操作成功！!";
		} else if (type.equals("import")) {
			//添加进货记录到进货表
			importService.importAnItem(stock, number, price);
			return "操作成功！!";
		} else {
			return "类型出错了！";
		}
	}

	//删除一条进/出货记录
	@ResponseBody
	@PostMapping("delete")
	public String delete(@RequestParam("type") String type,
	                     @RequestParam("id") Integer id,
	                     @RequestParam("eid") Integer eid,
	                     @RequestParam("password") String password) {

		//验证密码
		if (employeeService.verifyIdentityAllUser(eid, password)) {
			if (type.equals("import")) {
				importService.deleteByIid(id);
				return "操作成功！";
			} else if (type.equals("export")) {
				exportService.deleteByEid(id);
				return "操作成功！";
			} else {
				return "type出错了";
			}
		} else {
			return "身份验证不通过！你想干嘛呢";
		}
	}

	//编辑一件商品
	@ResponseBody
	@PostMapping("editStock")
	public String editStock(@RequestParam("sid") Integer sid,
	                        @RequestParam("name") String name,
	                        @RequestParam("number") Integer number,
	                        @RequestParam("buyPrice") Float buyPrice,
	                        @RequestParam("salePrice") Float salePrice) {

		Stock stock = stockService.getStockByID(sid);
		stockService.editStock(stock, name, number, buyPrice, salePrice);

		return "修改成功！";
	}


	//删除一件商品
	@ResponseBody
	@PostMapping("deleteStock")
	public String deleteStock(@RequestParam("sid") Integer sid) {
		//把所有涉及到的进货记录改为未知商品
		List<Import> importList = importService.getAllImportWithStock(stockService.getStockByID(sid));
		for (Import i : importList) {
			i.setStockByGoods(stockService.getStockByID(1));
		}
		//把所有涉及到的出货记录改为未知商品
		List<Export> exportList = exportService.getAllExportWithStock(stockService.getStockByID(sid));
		for (Export e : exportList) {
			e.setStockByStock(stockService.getStockByID(1));
		}
		//删除该Stock
		stockService.deleteStock(stockService.getStockByID(sid));

		return "success";
	}
}
