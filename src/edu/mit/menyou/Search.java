package edu.mit.menyou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
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
	    
		
		String location = "42.3598,-71.0921";
        String radius = "200";
        LocuTask task = new LocuTask();
        task.execute(location, radius);		
		
		//Listening to button event
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	search_history.add(search_input.getText().toString());
            	Toast.makeText(Search.this, "searching", Toast.LENGTH_SHORT).show();
            	adapter.notifyDataSetChanged();
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
	
	
	
	class LocuTask extends AsyncTask<String, Integer, JSONObject> {
		
		private static final String BASE_URL = "http://api.locu.com/v1_0/venue/search/?api_key=1a75b559bec4e3c7b00f0cc06d17356705599303";
		private String request_url;
		private JSONObject jsonData;
		
		@Override
		protected JSONObject doInBackground(String... params) {
			JSONObject json = null;
			request_url = BASE_URL + "&location=" + params[0] + "&radius=" + params[1];
			try {
			URL url = new URL(request_url);
	          System.out.println(url.toString());
	          BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream())); // read the server's output
	          String serverOutput;
	          String output = "";
	          while ((serverOutput = in.readLine()) != null) {
	              output = serverOutput;
	          }
	          json = new JSONObject(output);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			jsonData = result;
			JSONArray restaurants;
			try {
				restaurants = jsonData.getJSONArray("objects");
				for (int i=0; i<restaurants.length() ;i++) {
					JSONObject rest = restaurants.getJSONObject(i);
					System.out.println(rest.get("name"));
					search_history.add(rest.getString("name"));
					
					
//					search_history.add(search_input.getText().toString());
//	            	Toast.makeText(Search.this, "searching", Toast.LENGTH_SHORT).show();
//	            	adapter.notifyDataSetChanged();
//	            	adapter1.notifyDataSetChanged();
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
