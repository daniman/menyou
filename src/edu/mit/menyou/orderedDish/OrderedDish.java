package edu.mit.menyou.orderedDish;

import com.parse.Parse;
import com.parse.ParseObject;

import edu.mit.menyou.Profile;
import edu.mit.menyou.R;
import edu.mit.menyou.R.id;
import edu.mit.menyou.R.layout;
import edu.mit.menyou.R.menu;
import edu.mit.menyou.home.Home;
import edu.mit.menyou.search.Search;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class OrderedDish extends Activity {
	
	private TextView DishName;
	private String restID;
	private String restName;
	private String dishName;
	private RatingBar stars;
	private String numberOfStars;
	private EditText edit_text;
	private Button button;
	private String review;
	private String mNumber;
	private String name1;
	private String name2;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ordered_dish);
		Parse.initialize(this, "4EPEC8gdyy1UVP4yC0pRpfM30zpgGMGkoMdeu9p7", "1DxRG10TudyhJwAR4jildKVne8q3PjqNHVvpzIlY");
		getActionBar().setDisplayShowTitleEnabled(false);
		
		DishName = (TextView) findViewById(R.id.dishName);
		stars = (RatingBar) findViewById(R.id.ratingBar1);
		edit_text = (EditText) findViewById(R.id.dishDescription);
		
		/// why is the edit text called postal address?? you copy-pasting codess???
								// haha yeah oooopss...
		
        restID = getIntent().getExtras().getString("restID");
        restName = getIntent().getExtras().getString("restName");
        dishName = getIntent().getExtras().getString("dishName");
        position = getIntent().getExtras().getInt("position");
        
        DishName.setText("You ordered the " + dishName + "!");
        
        button = (Button) findViewById(R.id.submitDishReview);
        
        final SharedPreferences prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		final String number = "edu.mit.menyou.number";
		final String first = "edu.mit.menyou.first";
		final String last = "edu.mit.menyou.last";
		
        final ParseObject Reviews = new ParseObject("Reviews");

        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	numberOfStars = String.valueOf(stars.getRating());
            	review = String.valueOf(edit_text.getText());
        		mNumber = prefs.getString(number, "none");
        		name1 = prefs.getString(first, "error7");
        		name2 = prefs.getString(last, "error8");
        		String username = name1+" "+name2;
        		
            	Reviews.put("restID", restID);
            	Reviews.put("restName", restName);
            	Reviews.put("dishName", dishName);
            	Reviews.put("rating",numberOfStars);
            	Reviews.put("review", review);
            	Reviews.put("number", mNumber);
            	Reviews.put("username", username);
            	Reviews.put("position", position);
            	
            	String timeOfSwipe = "edu.mit.menyou.timeOfSwipe";
        		prefs.edit().putString(timeOfSwipe, "0").commit();
            	
            	
            	//this was crashing the app for some reason
            	Reviews.saveInBackground();
            	
            	Intent nextScreen = new Intent(getApplicationContext(), Home.class);
    			startActivity(nextScreen);
            	            	
//            	RestaurantParseObject justOrdered = new RestaurantParseObject();
            	
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
		case R.id.action_update_search:
			Intent nextScreen2 = new Intent(getApplicationContext(), Search.class);
			startActivity(nextScreen2);
			return true;
		case R.id.action_profile:
			Intent nextScreen1 = new Intent(getApplicationContext(), Profile.class);
			startActivity(nextScreen1);	           
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}