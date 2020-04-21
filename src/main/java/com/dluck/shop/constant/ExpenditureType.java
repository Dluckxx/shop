package com.dluck.shop.constant;

public enum ExpenditureType {
	SALARY("salary", "薪水"),
	OTHER("other", "其他");

	ExpenditureType(String name, String description) {
		this.name = name;
		this.description = description;
	}

	private final String name;
	private final String description;

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
