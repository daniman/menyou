package edu.mit.menyou.menu;

import java.util.Comparator;


public class MenuItemComparator implements Comparator<RestaurantMenuItem> {
	

	@Override
	public int compare(RestaurantMenuItem lhs, RestaurantMenuItem rhs) {
		
		 	int returnVal = 0;

		    if(lhs.getRank() < rhs.getRank()){
		        returnVal =  -1;
		    }else if(lhs.getRank() > rhs.getRank()){
		        returnVal =  1;
		    }else if(lhs.getRank() == rhs.getRank()){
		        returnVal =  0;
		    }
		    
		    return returnVal;
	    
	}


}
