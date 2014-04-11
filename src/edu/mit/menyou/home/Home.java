package edu.mit.menyou.home;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import edu.mit.menyou.First;
import edu.mit.menyou.PhoneNumber;
import edu.mit.menyou.Profile;
import edu.mit.menyou.R;
import edu.mit.menyou.R.id;
import edu.mit.menyou.R.layout;
import edu.mit.menyou.R.menu;
import edu.mit.menyou.menu.RestaurantMenu;
import edu.mit.menyou.menu.RestaurantMenuAdapter;
import edu.mit.menyou.menu.RestaurantMenuItem;
import edu.mit.menyou.orderedDish.OrderedDish;
import edu.mit.menyou.search.Search;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class Home extends Activity {
	
	private HistoryMenuAdaptor adpt;
	private ListView lView;
	private String mNumber;
	private TextView displayMeals;
	private TextView mealsWord;
	private SharedPreferences prefs;
	private String mealsNumber = "edu.mit.menyout.mealNumber";
	private HistoryMenuItem clicked;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Parse.initialize(this, "4EPEC8gdyy1UVP4yC0pRpfM30zpgGMGkoMdeu9p7", "1DxRG10TudyhJwAR4jildKVne8q3PjqNHVvpzIlY");
		
		prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		String firstTime = "edu.mit.menyou.firstTime";
		String first = "edu.mit.menyou.first";
		String number = "edu.mit.menyou.number";
		int firstCheck = prefs.getInt(firstTime, 0);
		String numberCheck = prefs.getString(number, "");
		mNumber = prefs.getString(number, "none");
		
		if(firstCheck==0){
			Intent nextScreen = new Intent(getApplicationContext(), First.class);
            startActivity(nextScreen);
		}
		if(numberCheck==""){
			Intent nextScreen = new Intent(getApplicationContext(), PhoneNumber.class);
            startActivity(nextScreen);
		}
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		getActionBar().setDisplayShowTitleEnabled(false);

		
		final Button home_button = (Button) findViewById(R.id.home_button);
		TextView name = (TextView) findViewById(R.id.home_username);
		displayMeals = (TextView) findViewById(R.id.home_points);
		mealsWord = (TextView) findViewById(R.id.home_meals);
		name.setText("Welcome "+prefs.getString(first, "Ben"));
		
		displayMeals.setText(prefs.getString(mealsNumber, "0"));
		if(Integer.parseInt(prefs.getString(mealsNumber, "0"))==1){mealsWord.setText("meal");}
		
		//Listening to button event
        home_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), Search.class);
                startActivity(nextScreen);
                }
        });
                
        adpt  = new HistoryMenuAdaptor(new ArrayList<HistoryMenuItem>(), this);
        lView = (ListView) findViewById(R.id.home_meal_history);
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
                    
                    for (int i=0; i<7; i++) {
                    	ParseObject obj = mealList.get(i);
                    	String restName = obj.getString("restName");
                    	String dishName = obj.getString("dishName");
                    	String rating = obj.getString("rating");
                    	String description = obj.getString("review");
                    	//Date time = obj.getDate("date");
                    	//Date time = new Date();
                    	Date time = obj.getCreatedAt();
                    	// Create an instance of SimpleDateFormat used for formatting 
                    	// the string representation of date (month/day/year)
                    	
                    	time.setHours(time.getHours()-4);
                    	//SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm"); 
                    	
                    	String format = "EEE  MMM-dd";
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
		
		lView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> a, View v, final int position, long id) {   
        		final int selectedPosition = position;
        		AlertDialog.Builder adb = new AlertDialog.Builder(Home.this); 
        		HistoryMenuItem obj = (HistoryMenuItem) lView.getItemAtPosition(position);
        		
        		String selectedDish = obj.getDishName();
        		
        		adb.setTitle(selectedDish);
        		adb.setMessage(obj.getDescription()+"\n"+obj.getRating().substring(0, 1)+"/5 stars"); 
        		adb.setPositiveButton("Cancel", null); 
        		adb.setNegativeButton("Recommend this to friend!", null);
        				
        				//new DialogInterface.OnClickListener() {
        			
        		//});
        		adb.show();
                 
             }
         });
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu1) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu1);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Toast.makeText(Home.this, "home", Toast.LENGTH_SHORT).show();
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

