package edu.mit.menyou.menu;

import java.io.Serializable;

public class RestaurantMenuItem implements Serializable {

	private String name;
	private String description;
	private String price;

	public RestaurantMenuItem(String name, String description, String price) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}