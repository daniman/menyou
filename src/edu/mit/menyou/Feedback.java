package edu.mit.menyou;

import com.parse.Parse;
import com.parse.ParseObject;

import edu.mit.menyou.home.Home;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Feedback extends Activity {
	
	private EditText edit_text;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		Parse.initialize(this, "4EPEC8gdyy1UVP4yC0pRpfM30zpgGMGkoMdeu9p7", "1DxRG10TudyhJwAR4jildKVne8q3PjqNHVvpzIlY");
		getActionBar().setDisplayShowTitleEnabled(false);
		
		edit_text = (EditText) findViewById(R.id.text);
		button = (Button) findViewById(R.id.feedback);
		
		final SharedPreferences prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		final String number = "edu.mit.menyou.number";
		final String first = "edu.mit.menyou.first";
		final String last = "edu.mit.menyou.last";
		
        final ParseObject Feedback = new ParseObject("Feedback");
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Toast.makeText(Feedback.this, "Thanks for your feedback!", Toast.LENGTH_SHORT).show();
            	
            	String words = String.valueOf(edit_text.getText());
        		String mNumber = prefs.getString(number, "none");
        		String name1 = prefs.getString(first, "error7");
        		String name2 = prefs.getString(last, "error8");
        		String username = name1+" "+name2;
        		
            	Feedback.put("feedback", words);
            	Feedback.put("number", mNumber);
            	Feedback.put("username", username);
            	
            	
            	
            	//this was crashing the app for some reason
            	Feedback.saveInBackground();
            	
            	Intent nextScreen = new Intent(getApplicationContext(), Profile.class);
    			startActivity(nextScreen);
            	            	
//            	RestaurantParseObject justOrdered = new RestaurantParseObject();
            	
            }
        });
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}

}
