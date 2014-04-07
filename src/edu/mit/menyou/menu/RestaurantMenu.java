package edu.mit.menyou.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.mit.menyou.Profile;
import edu.mit.menyou.R;
import edu.mit.menyou.R.id;
import edu.mit.menyou.R.layout;
import edu.mit.menyou.R.menu;
import edu.mit.menyou.home.Home;
import edu.mit.menyou.orderedDish.OrderedDish;
import edu.mit.menyou.search.Search;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantMenu extends Activity {
	private RestaurantMenuAdapter adpt;
	private ListView lView;
	private TextView RestaurantName;
	private String restID;
	private String restName;
	private String selectedDish;
	final Context context = this;
	private String[] allergies;
	private SharedPreferences prefs;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	getActionBar().setDisplayShowTitleEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        
    	prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		String allergiesKey = "edu.mit.menyou.allergies";
		String allergiesString = prefs.getString(allergiesKey, null);
		allergies = allergiesString.split("\\s+");
        
        adpt  = new RestaurantMenuAdapter(new ArrayList<RestaurantMenuItem>(), this);
        lView = (ListView) findViewById(R.id.menuListView);
        lView.setAdapter(adpt);
        registerForContextMenu(lView); //register for the contextmenu
        
        RestaurantName = (TextView) findViewById(R.id.restName);
        restName = getIntent().getExtras().getString("restName");
        restID = getIntent().getExtras().getString("restID");
        RestaurantName.setText(restName);
        
        
        String restID = getIntent().getExtras().getString("restID");
        new AsyncListViewLoader().execute(restID);
        
    }

    private class AsyncListViewLoader extends AsyncTask<String, Void, List<RestaurantMenuItem>> {
    	private final ProgressDialog dialog = new ProgressDialog(RestaurantMenu.this);
    	private final String BASE_URL = "http://api.locu.com/v1_0/venue/";
    	private final String END_URL = "/?api_key=1a75b559bec4e3c7b00f0cc06d17356705599303";
		private String request_url;
    	
		@Override
		protected void onPostExecute(List<RestaurantMenuItem> result) {			
			super.onPostExecute(result);
			dialog.dismiss();
			adpt.setItemList(result);
			adpt.notifyDataSetChanged();
			if (result.size() == 0) {				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					alertDialogBuilder.setTitle("Ooops...... :(");
					alertDialogBuilder
						.setMessage("We are very sorry but we do not have the menu for " + restName + " in our records.")
						.setCancelable(false)
						.setPositiveButton("OK",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								RestaurantMenu.this.finish();
							}
						  });
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
			}
		}

		@Override
		protected void onPreExecute() {		
			super.onPreExecute();
			dialog.setMessage("Retrieving menu...");
			dialog.show();			
		}

		@Override
		protected List<RestaurantMenuItem> doInBackground(String... params) {
			JSONObject json = null;
			List<RestaurantMenuItem> result = new ArrayList<RestaurantMenuItem>();
			request_url = BASE_URL + params[0] + END_URL;
			
			

			try {
				URL u = new URL(request_url);

				// read the server's output
				BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
		        String serverOutput;
		        String output = "";
		        while ((serverOutput = in.readLine()) != null) {
		        	output = serverOutput;
		        }
		        
		        // unpack the json from server
		        json = new JSONObject(output);
		        JSONObject objs = json.getJSONArray("objects").getJSONObject(0);
				JSONArray menus = objs.getJSONArray("menus");
								
				for (int men=0; men < menus.length(); men++) {
					JSONObject menu = menus.getJSONObject(men);
					JSONArray sections = menu.getJSONArray("sections");
					
					for (int sec = 0; sec < sections.length(); sec++) {
						JSONObject section = sections.getJSONObject(sec);
						JSONArray subsections = section.getJSONArray("subsections");
						
						for (int subsec = 0; subsec < subsections.length(); subsec ++) {
							JSONObject subsection = subsections.getJSONObject(subsec);
							JSONArray dishes = subsection.getJSONArray("contents");
							
							for (int d = 0; d < dishes.length(); d ++) {
								JSONObject dish = dishes.getJSONObject(d);
								// add dish to the menu list
								try {
									result.add(convertDish(dish));
								} catch(JSONException e) {
									// catches anything thats not formatted like a standard dish
									// i.e. any dish with options
									// don't do anything --> these options don't show up in the menu...
								}
								
							}
						}
					}
				}
				return result;
			}
			catch(Throwable t) {
				t.printStackTrace();
			}
			
			Algorithm alg = new Algorithm(result,allergies);
		
			result=alg.calculate();
			
			return null;
		}
		
		private RestaurantMenuItem convertDish(JSONObject obj) throws JSONException {
			String name = obj.getString("name");
			String description = obj.getString("description");
			return new RestaurantMenuItem(name, description);
		}
    	
    }
    
    // We want to create a context Menu when the user long click on an item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    	if (v.getId() == R.id.menuListView) {
    		ListView lv = (ListView) v;
    		AdapterView.AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) menuInfo;
    		RestaurantMenuItem obj = (RestaurantMenuItem) lv.getItemAtPosition(acmi.position);

    		selectedDish = obj.getName();
    		menu.setHeaderTitle(selectedDish);
    		menu.add(1, 1, 1, "Details");
    		menu.add(1, 2, 1, "Friends Recommend");
    		menu.add(1, 3, 2, "Ordered This!");

    	}
    }
    
  //the correct callback name starts with o and not O
    @Override
    public boolean onContextItemSelected(MenuItem item) {
       switch (item.getItemId()) {
       case 1:
           //first ContextMenu option I picked this to start the  new activity
       break; 
       case 2:
          //stuff for option 2 of the ContextMenu
       break;
       case 3:
           //stuff for option 2 of the ContextMenu
    	   Intent intent = new Intent(RestaurantMenu.this, OrderedDish.class);
    	   intent.putExtra("restID", restID);
    	   intent.putExtra("restName", restName);
    	   intent.putExtra("dishName", selectedDish);
           startActivity(intent);
       break;
       }
       return super.onContextItemSelected(item);
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
	        	Intent nextScreen1 = new Intent(getApplicationContext(), Search.class);
                startActivity(nextScreen1);
	            return true;
	        case R.id.action_profile:
	        	Intent nextScreen2 = new Intent(getApplicationContext(), Profile.class);
                startActivity(nextScreen2);	           
                return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
