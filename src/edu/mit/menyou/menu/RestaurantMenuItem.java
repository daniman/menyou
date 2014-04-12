package edu.mit.menyou.menu;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RestaurantMenuItem implements Serializable, Comparable {

	private String name;
	private String description;
	private String price;
	private int rank;

	public RestaurantMenuItem(String name, String description, String price) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		rank=100;
	}

	public String getName() {
		return this.name;
	}

	public void setRank(int r) {
		rank = r;
	}
	
	public void changeRank(int rankDelta) {
		rank = rank+rankDelta;
	}
	
	public int getRank() {
		return rank;
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

	@Override
	public int compareTo(Object compareDish) {
		int compareRank = ((RestaurantMenuItem) compareDish).getRank(); 
		 
		//ascending order
		//return this.getRank() - compareRank;
 
		//descending order
		return compareRank - this.getRank();
	}	

}