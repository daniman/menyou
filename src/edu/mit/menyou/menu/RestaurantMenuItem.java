package edu.mit.menyou.menu;

import java.io.Serializable;

public class RestaurantMenuItem implements Serializable {

	private String name;
	private String description;

	public RestaurantMenuItem(String name, String description) {
		super();
		this.name = name;
		this.description = description;
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

}