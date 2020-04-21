package com.dluck.shop.constant;

public enum EmployeeType {
	BOSS("boss", "老板", 10),
	FORMAL("formal", "正式", 10),
	INTERN("intern", "实习", 8);

	EmployeeType(String name, String description, int salaryPerShift) {
		this.name = name;
		this.description = description;
		this.salaryPerShift = salaryPerShift;
	}

	private final String name;
	private final String description;
	private final int salaryPerShift;

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getSalaryPerShift() {
		return salaryPerShift;
	}

	//通过name查找,找不到返回null
	public static EmployeeType getByName(String name) {
		for (EmployeeType e : EmployeeType.values()) {
			if (e.getName().equals(name)) {
				return e;
			}
		}
		return null;
	}
}
