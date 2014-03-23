package edu.mit.menyou;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;

import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Search extends Activity {
	private SimpleAdapter adpt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
		final AutoCompleteTextView search_input = (AutoCompleteTextView) findViewById(R.id.search_input);
		final ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
		final ImageButton gpsButton = (ImageButton) findViewById(R.id.gps_button);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        adpt  = new SimpleAdapter(new ArrayList<Restaurant>(), this);
        ListView lView = (ListView) findViewById(R.id.restaurantListView);
        
        lView.setAdapter(adpt);
        
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        String displayThis;
        if (!enabled) {
        	displayThis = "Please enable your GPS";
        	Toast.makeText(Search.this, displayThis, Toast.LENGTH_SHORT).show();
        } else {
        	// Exec async load task
            String location = "42.3598,-71.0921";
            (new AsyncListViewLoader()).execute(location);
        }
         
        
//		//Listening to button event
//      searchButton.setOnClickListener(new View.OnClickListener() {
//          public void onClick(View arg0) {
//          	search_history.add(search_input.getText().toString());
//          	Toast.makeText(Search.this, "searching", Toast.LENGTH_SHORT).show();
//          	adapter.notifyDataSetChanged();
//          	adapter1.notifyDataSetChanged();
//          }
//      });
      
//    //Listening to button event
//      gpsButton.setOnClickListener(new View.OnClickListener() {
//          public void onClick(View arg0) {
//          	
//          	LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
//              boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
//              String displayThis;
//              if (enabled) {
//              	displayThis = "GPS is enabled yayy";
//              } else {
//              	displayThis = "Please enable your GPS";
//              }
//          	Toast.makeText(Search.this, displayThis, Toast.LENGTH_SHORT).show();
//          }
//      });
    }


    private class AsyncListViewLoader extends AsyncTask<String, Void, List<Restaurant>> {
    	private final ProgressDialog dialog = new ProgressDialog(Search.this);
    	private final String BASE_URL = "http://api.locu.com/v1_0/venue/search/?api_key=1a75b559bec4e3c7b00f0cc06d17356705599303";
		private String request_url;
    	
		@Override
		protected void onPostExecute(List<Restaurant> result) {			
			super.onPostExecute(result);
			dialog.dismiss();
			adpt.setItemList(result);
			adpt.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {		
			super.onPreExecute();
			dialog.setMessage("Retrieving nearby restaurants...");
			dialog.show();			
		}

		@Override
		protected List<Restaurant> doInBackground(String... params) {
			JSONObject json = null;
			List<Restaurant> result = new ArrayList<Restaurant>();
			request_url = BASE_URL + "&location=" + params[0] + "&radius=200";

			try {
				URL u = new URL(request_url);
				System.out.println(u.toString());

				BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream())); // read the server's output
		        String serverOutput;
		        
		        String output = "";
		        while ((serverOutput = in.readLine()) != null) {
		        	output = serverOutput;
		        }
		        json = new JSONObject(output);
				JSONArray arr = json.getJSONArray("objects");
				for (int i=0; i < arr.length(); i++) {
					JSONObject rest = arr.getJSONObject(i);
					result.add(convertRestaurant(arr.getJSONObject(i)));
				}
				return result;
			}
			catch(Throwable t) {
				t.printStackTrace();
			}
			return null;
		}
		
		private Restaurant convertRestaurant(JSONObject obj) throws JSONException {
			String name = obj.getString("name");
			String description = obj.getString("street_address");
			return new Restaurant(name, description);
		}
    	
    }
    
}
