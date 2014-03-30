package edu.mit.menyou;

import java.util.Random;

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
import android.widget.TextView;
import android.widget.Toast;

public class PhoneNumber extends Activity {
	
	private String mNumber;
	private String generatedNumber;
	private TextView randomPrompt;
	private TextView hintText;
	private Button number_button;
	private EditText editNumber;
	private Button random_button;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_number);
		
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		randomPrompt = (TextView) findViewById(R.id.Text2);
		hintText = (TextView) findViewById(R.id.Text3);
		editNumber = (EditText) findViewById(R.id.edit_number);
		number_button = (Button) findViewById(R.id.number_button);
		random_button = (Button) findViewById(R.id.random_button);
		
		prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		final String number = "edu.mit.menyou.number";

	number_button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
        	
        	String mNumber=(editNumber.getText().toString());
        	
        	if(mNumber.length()==10){
            	prefs.edit().putString(number, mNumber).commit();
            	Intent nextScreen = new Intent(getApplicationContext(), Home.class);
                startActivity(nextScreen);
        	}
        	if(randomPrompt.getText().toString().length()==10){
        		mNumber=randomPrompt.getText().toString();
        		prefs.edit().putString(number, mNumber).commit();
            	Intent nextScreen = new Intent(getApplicationContext(), Home.class);
                startActivity(nextScreen);
        	}
        	if(mNumber.length()!=10){
        		Toast.makeText(PhoneNumber.this, "the entered number should be in the format \n'0123456789'\n without spaces or dashes" , Toast.LENGTH_SHORT).show();
        	}


        }
    });
	random_button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
        	
    generatedNumber = "555"; //apparently this isnt a real area code...perfect!
    
    for(int i=1;i<8;i++){
	Random random = new Random();
	int digit = random.nextInt(9 - 0 + 1) + 0;
	generatedNumber = generatedNumber+String.valueOf(digit);
    }
    editNumber.setText(generatedNumber);
    randomPrompt.setText(generatedNumber);
    number_button.setText("Use this number");
    random_button.setAlpha(0);
    hintText.setText("Hint: It will be harder to find your friends using this app without an actual phone number");
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
