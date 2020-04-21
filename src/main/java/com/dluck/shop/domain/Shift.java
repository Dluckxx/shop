package com.dluck.shop.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Shift {
	private Date date;
	private Employee employeeByA;
	private Employee employeeByB;
	private Employee employeeByC;
	private Employee employeeByD;
	private Employee employeeByE;
	private Employee employeeByF;
	private Employee employeeByG;

	@Id
	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Shift shift = (Shift) o;
		return Objects.equals(date, shift.date);
	}

	@Override
	public int hashCode() {
		return Objects.hash(date);
	}

	@ManyToOne
	@JoinColumn(name = "a", referencedColumnName = "eid")
	public Employee getEmployeeByA() {
		return employeeByA;
	}

	public void setEmployeeByA(Employee employeeByA) {
		this.employeeByA = employeeByA;
	}

	@ManyToOne
	@JoinColumn(name = "b", referencedColumnName = "eid")
	public Employee getEmployeeByB() {
		return employeeByB;
	}

	public void setEmployeeByB(Employee employeeByB) {
		this.employeeByB = employeeByB;
	}

	@ManyToOne
	@JoinColumn(name = "c", referencedColumnName = "eid")
	public Employee getEmployeeByC() {
		return employeeByC;
	}

	public void setEmployeeByC(Employee employeeByC) {
		this.employeeByC = employeeByC;
	}

	@ManyToOne
	@JoinColumn(name = "d", referencedColumnName = "eid")
	public Employee getEmployeeByD() {
		return employeeByD;
	}

	public void setEmployeeByD(Employee employeeByD) {
		this.employeeByD = employeeByD;
	}

	@ManyToOne
	@JoinColumn(name = "e", referencedColumnName = "eid")
	public Employee getEmployeeByE() {
		return employeeByE;
	}

	public void setEmployeeByE(Employee employeeByE) {
		this.employeeByE = employeeByE;
	}

	@ManyToOne
	@JoinColumn(name = "f", referencedColumnName = "eid")
	public Employee getEmployeeByF() {
		return employeeByF;
	}

	public void setEmployeeByF(Employee employeeByF) {
		this.employeeByF = employeeByF;
	}

	@ManyToOne
	@JoinColumn(name = "g", referencedColumnName = "eid")
	public Employee getEmployeeByG() {
		return employeeByG;
	}

	public void setEmployeeByG(Employee employeeByG) {
		this.employeeByG = employeeByG;
	}
}
