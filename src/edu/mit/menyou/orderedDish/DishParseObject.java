package edu.mit.menyou.orderedDish;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Meal")
public class DishParseObject extends ParseObject {
 
	public DishParseObject(String name, int rating, String description, String eater) {
        this.setName(name);
    }
 
    public String getName() {
        return getString("name");
    }
 
    private void setName(String name) {
        put("name", name);
    }
 
}