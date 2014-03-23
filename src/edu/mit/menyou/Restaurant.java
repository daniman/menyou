package edu.mit.menyou;

import java.io.Serializable;

public class Restaurant implements Serializable {

	private String name;
	private String description;
	private String id;

	public Restaurant(String name, String description, String id) {
		super();
		this.name = name;
		this.description = description;
		this.id = id;
	}

	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
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

//=======
//import android.os.Bundle;
//import android.app.Activity;
//import android.content.Intent;
//import android.util.Log;
//import android.view.Menu;
//import android.widget.TextView;
//
//public class Restaurant extends Activity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_restaurant);
//		
//		TextView name = (TextView) findViewById(R.id.rest_textView1);
//		
//		Intent i = getIntent();
//        //Receiving the Data
//        String restaurantName = i.getStringExtra("name");
//        Log.e("Restaurant", restaurantName);
//        // Displaying Received data
//        name.setText("This page will show the menu for "+restaurantName);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.restaurant, menu);
//		return true;
//	}
//>>>>>>> 7986fa54450e8434206c364b9f714ed6fbf41ae7
}

