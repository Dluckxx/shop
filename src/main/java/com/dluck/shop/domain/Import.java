package com.dluck.shop.domain;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@DynamicInsert
public class Import {
	private int iid;
	private int number;
	private Timestamp time;
	private double price;
	private Stock stockByGoods;

	@Id
	@Column(name = "iid")
	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
		this.iid = iid;
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
		Import anImport = (Import) o;
		return iid == anImport.iid &&
				number == anImport.number &&
				Double.compare(anImport.price, price) == 0 &&
				Objects.equals(time, anImport.time);
	}

	@Override
	public int hashCode() {
		return Objects.hash(iid, number, time, price);
	}

	@ManyToOne
	@JoinColumn(name = "stock", referencedColumnName = "sid", nullable = false)
	public Stock getStockByGoods() {
		return stockByGoods;
	}

	public void setStockByGoods(Stock stockByGoods) {
		this.stockByGoods = stockByGoods;
	}
}
