package edu.mit.menyou;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import edu.mit.menyou.home.HistoryMenuAdaptor;
import edu.mit.menyou.home.HistoryMenuItem;
import edu.mit.menyou.home.Home;
import edu.mit.menyou.search.Search;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class History extends Activity {
	private HistoryMenuAdaptor adpt;

	private SharedPreferences prefs;
	private String mNumber;
	private TextView displayMeals;
	private TextView mealsWord;
	private String mealsNumber = "edu.mit.menyout.mealNumber";
	private ListView lView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Parse.initialize(this, "4EPEC8gdyy1UVP4yC0pRpfM30zpgGMGkoMdeu9p7", "1DxRG10TudyhJwAR4jildKVne8q3PjqNHVvpzIlY");
		
		prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		String number = "edu.mit.menyou.number";
		mNumber = prefs.getString(number, "none");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		displayMeals = (TextView) findViewById(R.id.hist_points);
		mealsWord = (TextView) findViewById(R.id.hist_meals);

		displayMeals.setText(prefs.getString(mealsNumber, "0"));

        adpt  = new HistoryMenuAdaptor(new ArrayList<HistoryMenuItem>(), this);
        lView = (ListView) findViewById(R.id.hist_meal_history);
        lView.setAdapter(adpt);
        
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Reviews");
        //query.setLimit(10); // limit to at most 10 results
        query.whereEqualTo("number", mNumber);
        query.addDescendingOrder("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> mealList, ParseException e) {
            	List<HistoryMenuItem> result = new ArrayList<HistoryMenuItem>();
                if (e == null) {        
                	String numberOfMeals = String.valueOf(mealList.size());
                	prefs.edit().putString(mealsNumber, numberOfMeals);
                    
                	displayMeals.setText(numberOfMeals);
                    if(mealList.size()==1){mealsWord.setText("meal");}
                    
                    for (int i=0; i<mealList.size(); i++) {
                    	ParseObject obj = mealList.get(i);
                    	String restName = obj.getString("restName");
                    	String dishName = obj.getString("dishName");
                    	String rating = obj.getString("rating");
                    	String description = obj.getString("description");
                    	//Date time = obj.getDate("date");
                    	Date time = new Date();
                    	// Create an instance of SimpleDateFormat used for formatting 
                    	// the string representation of date (month/day/year)
                    	
                    	//SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm"); 
                    	String format = "EEE MM/dd/yyyy";
                    	SimpleDateFormat df = new SimpleDateFormat(format, Locale.US);
                    	df.setTimeZone(TimeZone.getTimeZone("UTC"));

                    	// Using DateFormat format method we can create a string 
                    	// representation of a date with the defined format.
                    	
                    	String reportDate = df.format(time);
                    	
                    	HistoryMenuItem item = new HistoryMenuItem(restName, dishName, rating, description, reportDate);
                    	System.out.println(item.toString());
                    	result.add(item);
                    }
                } else {
                }
                adpt.setItemList(result);
				adpt.notifyDataSetChanged();
            }
        });    
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Intent nextScreen = new Intent(getApplicationContext(), Home.class);
                startActivity(nextScreen);
	            return true;
	        case R.id.action_search:
	        	Intent nextScreen1 = new Intent(getApplicationContext(), Search.class);
                startActivity(nextScreen1);
	            return true;
	        case R.id.action_profile:
	        	Intent nextScreen2 = new Intent(getApplicationContext(), Profile.class);
	        	startActivity(nextScreen2);
	        	//View view = findViewById(android.R.id.content);
	        	//makeScaleUpAnimation(view, 100, 100, view.getWidth(), view.getHeight());
		        //startActivity(nextScreen2, options.toBundle()\);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
