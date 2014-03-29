package edu.mit.menyou.orderedDish;

import com.parse.ParseObject;

import edu.mit.menyou.R;
import edu.mit.menyou.R.id;
import edu.mit.menyou.R.layout;
import edu.mit.menyou.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderedDish extends Activity {
	
	private TextView DishName;
	private String restID;
	private String restName;
	private String dishName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ordered_dish);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		DishName = (TextView) findViewById(R.id.dishName);
		
        restID = getIntent().getExtras().getString("restID");
        restName = getIntent().getExtras().getString("restName");
        dishName = getIntent().getExtras().getString("dishName");
        
        DishName.setText("You ordered the " + dishName + "!");
        
        final Button button = (Button) findViewById(R.id.submitDishReview);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	ParseObject testObject = new ParseObject("TestObject");
            	testObject.put("foo", "bar");
            	testObject.saveInBackground();
            	            	
//            	RestaurantParseObject justOrdered = new RestaurantParseObject();
            	
            	//TODO: after creation of a parseObject, make intent to go back to the home page
            }
        });
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}