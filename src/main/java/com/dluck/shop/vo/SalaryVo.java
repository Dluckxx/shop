package com.dluck.shop.vo;

public class SalaryVo {
	private int id;
	private String name;
	private String job;
	private float shift_salary;
	private float income_salary;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public float getShift_salary() {
		return shift_salary;
	}

	public void setShiftSalary(float shift_salary) {
		this.shift_salary = shift_salary;
	}

	public float getIncome_salary() {
		return income_salary;
	}

	public void setIncomeSalary(float other_salary) {
		this.income_salary = other_salary;
	}
}
