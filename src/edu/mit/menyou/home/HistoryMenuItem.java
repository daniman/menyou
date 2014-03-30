package edu.mit.menyou.home;

import java.io.Serializable;

public class HistoryMenuItem implements Serializable {

	private String restName;
	private String dishName;
	private String rating;
	private String description;

	public HistoryMenuItem(String restName, String dishName, String rating, String description) {
		super();
		this.restName = restName;
		this.dishName = dishName;
		this.rating = rating;
		this.description = description;
	}

	public String getRestName() {
		return this.restName;
	}
	
	public String getDishName() {
		return this.dishName;
	}
	
	public String getRating() {
		return this.rating;
	}

	public String getDescription() {
		return this.description;
	}
	
	public String toString() {
		return "Restaurant: " + this.restName + "; Dish: " + this.dishName + "; Rating: " + this.rating;
	}

}
