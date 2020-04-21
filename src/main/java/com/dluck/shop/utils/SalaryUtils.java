package com.dluck.shop.utils;

import com.dluck.shop.constant.EmployeeType;
import com.dluck.shop.constant.IncomeType;
import com.dluck.shop.domain.Employee;
import com.dluck.shop.domain.Income;
import com.dluck.shop.domain.Shift;
import com.dluck.shop.vo.SalaryVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SalaryUtils {
	public List<SalaryVo> getSalaryList(List<Employee> employeeList, List<Shift> shiftList, List<Income> incomeList) {
		List<SalaryVo> list = new ArrayList<>();

		for (Employee e : employeeList) {
			SalaryVo salaryVo = new SalaryVo();

			//获取ID
			salaryVo.setId(e.getEid());
			//获取姓名
			salaryVo.setName(e.getName());
			//获取职位
			salaryVo.setJob(Objects.requireNonNull(EmployeeType.getByName(e.getType())).getDescription());
			//获取值班收入
			salaryVo.setShiftSalary(getShiftSalary(e, shiftList));
			//获取提成收入
			salaryVo.setIncomeSalary(getIncomeSalary(e, incomeList));

			//保存一个人的收入信息
			list.add(salaryVo);
		}

		return list;
	}

	//获取shiftList内employee的值班工资
	private float getShiftSalary(Employee employee, List<Shift> shiftList) {

		int shift_count = 0;
		float shift_scale = 0f;

		for (Shift s : shiftList) {
			if (s.getEmployeeByA() != null && s.getEmployeeByA().equals(employee)) {
				shift_count++;
			}
			if (s.getEmployeeByB() != null && s.getEmployeeByB().equals(employee)) {
				shift_count++;
			}
			if (s.getEmployeeByC() != null && s.getEmployeeByC().equals(employee)) {
				shift_count++;
			}
			if (s.getEmployeeByD() != null && s.getEmployeeByD().equals(employee)) {
				shift_count++;
			}
			if (s.getEmployeeByE() != null && s.getEmployeeByE().equals(employee)) {
				shift_count++;
			}
			if (s.getEmployeeByF() != null && s.getEmployeeByF().equals(employee)) {
				shift_count++;
			}
			if (s.getEmployeeByG() != null && s.getEmployeeByG().equals(employee)) {
				shift_count++;
			}
		}

		for (EmployeeType e : EmployeeType.values()) {
			if (e.getName().equals(employee.getType())) {
				shift_scale = e.getSalaryPerShift();
			}
		}

//		System.out.println(employee.getName() + "----" + shift_count + "----" + shift_scale);

		return shift_count * shift_scale;
	}

	//获取incomeList内的提成
	private float getIncomeSalary(Employee employee, List<Income> incomeList) {

		float salary = 0f;

		//遍历收入类型的枚举，方便日后添加收入类型
		for (IncomeType incomeType : IncomeType.values()) {
			for (Income income : incomeList) {
				//判断职员是否正确
				if (income.getEmployeeByEmployee().equals(employee)) {
					//判断收入类型
					if (income.getType().equals(incomeType.getName())) {
						//计算提成,价格*该类的提成比例
						salary += income.getPrice() * incomeType.getScale();
//						if (incomeType.getScale() > 0)
//							System.out.println(employee.getName() + "--" + income.getType() + "--" + income.getPrice() + "--" + income.getDetail() + "--" + incomeType.getScale());
					}
				}
			}
		}

		return salary;
	}
}
