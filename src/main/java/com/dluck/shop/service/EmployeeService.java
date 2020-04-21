package com.dluck.shop.service;

import com.dluck.shop.domain.Employee;
import com.dluck.shop.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;

	//根据ID查找员工
	@Transactional
	public Employee getEmployeeByEid(int id) {
		return employeeRepository.findById(id).orElse(null);
	}

	//根据ID获取员工类型
	public String getPersonType(int id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		return employee.map(Employee::getType).orElse(null);
	}

	//根据ID和密码验证身份,范围是所有人
	public boolean verifyIdentityAllUser(Integer eid, String password) {
		Employee employee = employeeRepository.findById(eid).orElse(null);

		if (employee == null) {
			return false;
		}

		return employee.getPassword().equals(password);
	}

	//根据ID和密码验证身份，仅限boss
	public boolean verifyIdentityBoss(Integer eid, String password) {
		Employee employee = employeeRepository.findById(eid).orElse(null);

		if (employee == null) {
			return false;
		} else if (!employee.getType().equals("boss")) {
			return false;
		}

		return employee.getPassword().equals(password);
	}

	//获取全部员工
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}
}
