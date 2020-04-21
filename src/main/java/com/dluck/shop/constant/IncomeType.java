package com.dluck.shop.constant;

public enum IncomeType {
	REPAIR("repair", "维修", 0.5f),
	PRINT("print", "打印", 0),
	OTHER("other", "其他", 0);

	private final String name;
	private final String description;
	private final float scale;

	IncomeType(String name, String description, float scale) {
		this.name = name;
		this.description = description;
		this.scale = scale;
	}

	public float getScale() {
		return scale;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
