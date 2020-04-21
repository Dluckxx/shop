package com.dluck.shop.domain;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@DynamicInsert
public class Expenditure {
	private int eid;
	private String type;
	private String detail;
	private Timestamp time;
	private double price;
	private Employee employeeByEmployee;

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
	@Column(name = "detail")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Basic
	@Column(name = "time")
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Basic
	@Column(name = "price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Expenditure that = (Expenditure) o;
		return eid == that.eid &&
				Double.compare(that.price, price) == 0 &&
				Objects.equals(type, that.type) &&
				Objects.equals(detail, that.detail) &&
				Objects.equals(time, that.time);
	}

	@Override
	public int hashCode() {
		return Objects.hash(eid, type, detail, time, price);
	}

	@ManyToOne
	@JoinColumn(name = "employee", referencedColumnName = "eid", nullable = false)
	public Employee getEmployeeByEmployee() {
		return employeeByEmployee;
	}

	public void setEmployeeByEmployee(Employee employeeByPerson) {
		this.employeeByEmployee = employeeByPerson;
	}
}
