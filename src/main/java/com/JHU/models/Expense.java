package com.JHU.models;

public class Expense {

	private Double amount;
	private String category;
	private String frequency;
	private Integer date;
	public Expense(Double amount, String category, String frequency, Integer date) {
		super();
		this.amount = amount;
		this.category = category;
		this.frequency = frequency;
		this.date = date;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}



}
