package edu.mit.menyou.orderedDish;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Meal")
public class UserParseObject extends ParseObject {
 
	public UserParseObject(String restID, String restName) {
		this.setObjectId(restID);
        this.setName(restName);
    }
 
    public String getName() {
        return getString("name");
    }
 
    private void setName(String name) {
        put("name", name);
    }
 
}