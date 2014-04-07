package edu.mit.menyou.menu;

import java.util.List;

public class  Algorithm {
	private List<RestaurantMenuItem> listOfDishes1;
	private String[] allergiesList;
	private List<RestaurantMenuItem> listOfDishes2;
	
	
	public Algorithm(List<RestaurantMenuItem> LOD, String[] AL){
		listOfDishes1=LOD;
		allergiesList=AL;
	}
	
	
	public List<RestaurantMenuItem> calculate(){
		
		//do stuff with the first 2 lists to make the final one
		
		return listOfDishes2; 
	}

}
