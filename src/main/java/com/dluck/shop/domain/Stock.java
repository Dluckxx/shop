package com.dluck.shop.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Stock {
	private int sid;
	private String name;
	private int number;
	private double buyPrice;
	private double referencePrice;

	@Id
	@Column(name = "sid")
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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
	@Column(name = "number")
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Basic
	@Column(name = "buy_price")
	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

	@Basic
	@Column(name = "reference_price")
	public double getReferencePrice() {
		return referencePrice;
	}

	public void setReferencePrice(double referencePrice) {
		this.referencePrice = referencePrice;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Stock stock = (Stock) o;
		return sid == stock.sid &&
				number == stock.number &&
				Double.compare(stock.buyPrice, buyPrice) == 0 &&
				Objects.equals(name, stock.name) &&
				Objects.equals(referencePrice, stock.referencePrice);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sid, name, number, buyPrice, referencePrice);
	}
}
