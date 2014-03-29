package edu.mit.menyou;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Meal")
public class Eaten extends ParseObject {
 
    public Eaten() {
        // A default constructor is required.
    }
 
    public String getTitle() {
        return getString("title");
    }
 
    public void setTitle(String title) {
        put("title", title);
    }
 
    public ParseUser getAuthor() {
        return getParseUser("author");
    }
 
    public void setAuthor(ParseUser user) {
        put("author", user);
    }
 
    public String getRating() {
        return getString("rating");
    }
 
    public void setRating(String rating) {
        put("rating", rating);
    }
 
}