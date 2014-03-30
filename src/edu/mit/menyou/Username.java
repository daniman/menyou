package edu.mit.menyou;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Username extends Activity {
	private String mPhoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_username);

		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		final EditText first_name = (EditText) findViewById(R.id.edit_first);
		final EditText last_name = (EditText) findViewById(R.id.edit_last);
		Button user_button = (Button) findViewById(R.id.user_button);

		final SharedPreferences prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		final String first = "edu.mit.menyou.first";
		final String last = "edu.mit.menyou.last";
		final String number = "edu.mit.menyou.number";
		
		TelephonyManager tMgr =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		mPhoneNumber = tMgr.getLine1Number();
		
		if(mPhoneNumber!=null && !mPhoneNumber.isEmpty()){
			prefs.edit().putString(number, mPhoneNumber);

		}
		//Toast.makeText(Username.this, mPhoneNumber, Toast.LENGTH_SHORT).show();

		
		
        user_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	
            	String firstName=first_name.getText().toString();
            	String lastName=last_name.getText().toString();
            	
            	
            	if(!firstName.matches("") && !lastName.matches("")){
	            	firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
	            	lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
	            	prefs.edit().putString(first, firstName).commit();
	            	prefs.edit().putString(last, lastName).commit();
	            	
	            	if(mPhoneNumber.isEmpty()){
		            	Intent nextScreen4 = new Intent(getApplicationContext(), PhoneNumber.class);
		                startActivity(nextScreen4);
		            	}
	            	if(mPhoneNumber!=null && !mPhoneNumber.isEmpty()){
	        			prefs.edit().putString(number, mPhoneNumber).commit();
	        			Intent nextScreen = new Intent(getApplicationContext(), Home.class);
		                startActivity(nextScreen);
	        		}
            	}
            	
            	if(!firstName.matches("") && lastName.matches("")){
            		firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            		prefs.edit().putString(first, firstName).commit();
            		
            		if(mPhoneNumber.isEmpty()){
		            	Intent nextScreen3 = new Intent(getApplicationContext(), PhoneNumber.class);
		                startActivity(nextScreen3);
		            	}
	            	
	            	if(mPhoneNumber!=null && !mPhoneNumber.isEmpty()){
	        			prefs.edit().putString(number, mPhoneNumber).commit();
	        			Intent nextScreen2 = new Intent(getApplicationContext(), Home.class);
		                startActivity(nextScreen2);
	        		}
            	}
            	if(firstName.matches("")){
            		Toast.makeText(Username.this, "you should enter a name", Toast.LENGTH_SHORT).show();

            	}
            	
            	
            	
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
