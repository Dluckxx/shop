package com.dluck.shop.domain;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@DynamicInsert
public class Export {
	private int eid;
	private int number;
	private Timestamp time;
	private double price;
	private Stock stockByStock;

	@Id
	@Column(name = "eid")
	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
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
		Export export = (Export) o;
		return eid == export.eid &&
				number == export.number &&
				Double.compare(export.price, price) == 0 &&
				Objects.equals(time, export.time);
	}

	@Override
	public int hashCode() {
		return Objects.hash(eid, number, time, price);
	}

	@ManyToOne
	@JoinColumn(name = "stock", referencedColumnName = "sid", nullable = false)
	public Stock getStockByStock() {
		return stockByStock;
	}

	public void setStockByStock(Stock stockByGoods) {
		this.stockByStock = stockByGoods;
	}
}
