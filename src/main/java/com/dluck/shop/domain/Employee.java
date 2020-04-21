package com.dluck.shop.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Employee {
	private int eid;
	private String type;
	private String name;
	private String password;
	private String sex;
	private String phoneNumber;
	private String qqNumber;
	private double equity;

	@Id
	@Column(name = "eid")
	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	@Basic
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Basic
	@Column(name = "phone_number")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Basic
	@Column(name = "qq_number")
	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	@Basic
	@Column(name = "equity")
	public double getEquity() {
		return equity;
	}

	public void setEquity(double equity) {
		this.equity = equity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return eid == employee.eid &&
				Double.compare(employee.equity, equity) == 0 &&
				Objects.equals(type, employee.type) &&
				Objects.equals(name, employee.name) &&
				Objects.equals(sex, employee.sex) &&
				Objects.equals(phoneNumber, employee.phoneNumber) &&
				Objects.equals(qqNumber, employee.qqNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(eid, type, name, sex, phoneNumber, qqNumber, equity);
	}
}
