package edu.mit.menyou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class Search extends Activity {
	
    private static final List<String> search_history = new ArrayList<String>();

  	
  	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		search_history.add("pad thai");
		ListView lv = (ListView) findViewById(R.id.listView);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, search_history);
		lv.setAdapter(adapter);
		
		final AutoCompleteTextView search_input = (AutoCompleteTextView) findViewById(R.id.search_input);
		final ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
		final ImageButton gpsButton = (ImageButton) findViewById(R.id.gps_button);
		
		
		final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, search_history);
		final AutoCompleteTextView search_input1 = (AutoCompleteTextView) findViewById(R.id.search_input);
		search_input1.setAdapter(adapter1);
	    
		//Listening to button event
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	search_history.add(search_input.getText().toString());
            	Toast.makeText(Search.this, "searching", Toast.LENGTH_SHORT).show();
            	adapter1.notifyDataSetChanged();
            }
        });
      //Listening to button event
        gpsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	Toast.makeText(Search.this, "update with gps", Toast.LENGTH_SHORT).show();
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
	        	Toast.makeText(Search.this, "search", Toast.LENGTH_SHORT).show();
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
