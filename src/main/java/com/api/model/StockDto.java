package com.api.model;

import java.math.BigDecimal;

public class StockDto {

	 private Integer sid;
	 private String name;
	 private BigDecimal price;
	 
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	 
	
}
