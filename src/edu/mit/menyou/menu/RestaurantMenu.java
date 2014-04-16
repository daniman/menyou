package edu.mit.menyou.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.mit.menyou.Profile;
import edu.mit.menyou.R;
import edu.mit.menyou.R.id;
import edu.mit.menyou.R.layout;
import edu.mit.menyou.R.menu;
import edu.mit.menyou.UpdateMood;
import edu.mit.menyou.home.Home;
import edu.mit.menyou.orderedDish.OrderedDish;
import edu.mit.menyou.search.Search;
import edu.mit.menyou.search.SearchRadius;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
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
import android.widget.AdapterView.OnItemClickListener;
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
	private Context ctx = this;
	private static String[] allergiesArray;
	private static String[] likesArray;
	private static String[] dislikesArray;
	private static final List<String> dislikes_list = new ArrayList<String>();
	private static final List<String> likes_list = new ArrayList<String>();
	private static final List<String> allergies_list = new ArrayList<String>();
	
	private int costInt;
	private int spiceInt;
	private int denseInt;
	private int discoverInt;
	private int healthInt;
	
	final static String costM = "edu.mit.menyou.cost";
	final static String spiceM = "edu.mit.menyou.spice";
	final static String healthM = "edu.mit.menyou.health";
	final static String denseM = "edu.mit.menyou.dense";
	final static String discoverM = "edu.mit.menyou.discover";
	
	private String restIDKey = "edu.mit.menyou.restID";
	private String restNameKey = "edu.mit.menyou.restName";
	

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	getActionBar().setDisplayShowTitleEnabled(false);
    	
    	prefs = this.getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		String allergiesKey = "edu.mit.menyou.allergies";
		String likesKey = "edu.mit.menyou.likes";
		String dislikesKey = "edu.mit.menyou.dislikes";
    	
		likes_list.clear();
		dislikes_list.clear();
		allergies_list.clear();
		
/////////////// allergies //////////////////
		
	String allergiesString = prefs.getString(allergiesKey, null);
	if(allergiesString!=null){
	allergiesArray = allergiesString.split(" ");
	int length = allergiesArray.length;
			
	
	for(int i=0;i<length;i++){
		String food=allergiesArray[i];
		food = food.replaceAll("-", " ");
		allergies_list.add(food);
		}
	allergies_list.remove(0);
	}
	
	/////////////// likes //////////////////////
	String likesString = prefs.getString(likesKey, null);
	if(likesString!=null){
	likesArray = likesString.split(" ");
	int length = likesArray.length;
			
	
	for(int i=0;i<length;i++){
		String food=likesArray[i];
		food = food.replaceAll("_", " ");
		likes_list.add(food);
		}
	likes_list.remove(0);
	}
	
	/////////////// dislikes //////////////////////
	String dislikesString = prefs.getString(dislikesKey, null);
	if(dislikesString!=null){
	dislikesArray = dislikesString.split(" ");
	int length = dislikesArray.length;
	
	
	for(int i=0;i<length;i++){
		String food=dislikesArray[i];
		food = food.replaceAll("_", " ");
		dislikes_list.add(food);
		}
	dislikes_list.remove(0);
	}
	
	////////////////////////////////////////////////////
	
	
	 super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_menu_list);
		
        adpt  = new RestaurantMenuAdapter(new ArrayList<RestaurantMenuItem>(), this);
        lView = (ListView) findViewById(R.id.menuListView);
        lView.setAdapter(adpt);
        lView.setTextFilterEnabled(true);
        registerForContextMenu(lView); //register for the contextmenu
        
        RestaurantName = (TextView) findViewById(R.id.restName);
        
        restName = prefs.getString(restNameKey, "");
        restID = prefs.getString(restIDKey, "");
        //restName = getIntent().getExtras().getString("restName");
        //restID = getIntent().getExtras().getString("restID");
        RestaurantName.setText(restName);
        
        
        
        new AsyncListViewLoader().execute(restID);

        lView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> a, View v, final int position, long id) {   
        		final int selectedPosition = position;
        		AlertDialog.Builder adb = new AlertDialog.Builder(RestaurantMenu.this); 
        		RestaurantMenuItem obj = (RestaurantMenuItem) lView.getItemAtPosition(position);
        		
        		selectedDish = obj.getName();
        		
        		adb.setTitle(obj.getName());
        		adb.setMessage(obj.getDescription()); 
        		adb.setPositiveButton("Cancel", null); 
        		adb.setNegativeButton("I Ordered This!", new DialogInterface.OnClickListener() {
        			public void onClick(DialogInterface dialog, int id) {
        				Intent intent = new Intent(RestaurantMenu.this, OrderedDish.class);
        				intent.putExtra("restID", restID);
        				intent.putExtra("restName", restName);
        				intent.putExtra("dishName", selectedDish);
        				intent.putExtra("position", position);
        				startActivity(intent);
        			}
        		});
        		adb.show();
                 
             }
         });
        
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
						.setMessage("We are very sorry but we do not have the menu for " + restName + " in our records at this time.")
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
			result.clear();
			request_url = BASE_URL + params[0] + END_URL;
			
			costInt=prefs.getInt(costM, 0);
			spiceInt=prefs.getInt(spiceM, 0);
			denseInt=prefs.getInt(denseM, 0);
			discoverInt=prefs.getInt(discoverM, 0);
			healthInt=prefs.getInt(healthM, 0);
			
			

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
				
				costInt=prefs.getInt(costM, 0);
				spiceInt=prefs.getInt(spiceM, 0);
				denseInt=prefs.getInt(denseM, 0);
				discoverInt=prefs.getInt(discoverM, 0);
				healthInt=prefs.getInt(healthM, 0);
				
				Thread.sleep(300);
				
				Algorithm alg = new Algorithm(result,allergies_list,likes_list,dislikes_list,costInt,spiceInt,denseInt,discoverInt,healthInt);
				alg.reset();
				result=alg.calculate();
				return result;
			}
			catch(Throwable t) {
				t.printStackTrace();
			}

			return null;
		}
		
		private RestaurantMenuItem convertDish(JSONObject obj) throws JSONException {
			String name = obj.getString("name");
			String description = obj.getString("description");
			String price = obj.getString("price");
			return new RestaurantMenuItem(name, description, price);

		}

		
    	
    }
    
//    // We want to create a context Menu when the user long click on an item
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//    	if (v.getId() == R.id.menuListView) {
//    		ListView lv = (ListView) v;
//    		AdapterView.AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) menuInfo;
//    		RestaurantMenuItem obj = (RestaurantMenuItem) lv.getItemAtPosition(acmi.position);
//
//    		selectedDish = obj.getName();
//    		menu.setHeaderTitle(selectedDish);
//    		menu.addSubMenu("title");
//    		menu.add(1, 1, 1, "Friends Recommend");
//    		menu.add(1, 2, 1, "Ordered This!");
//
//    	}
//    }
//    
//  //the correct callback name starts with o and not O
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//       switch (item.getItemId()) {
//       case 1:
//           //first ContextMenu option I picked this to start the  new activity
//       break;
//       case 2:
//           //stuff for option 2 of the ContextMenu
//    	   Intent intent = new Intent(RestaurantMenu.this, OrderedDish.class);
//    	   intent.putExtra("restID", restID);
//    	   intent.putExtra("restName", restName);
//    	   intent.putExtra("dishName", selectedDish);
//           startActivity(intent);
//       break;
//       }
//       return super.onContextItemSelected(item);
//    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_mood, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.update_mood:
	        	 Intent nextScreen2 = new Intent(getApplicationContext(), UpdateMood.class);
					startActivity(nextScreen2);
	            return true;
	        case R.id.menu_back:
	        	 Intent nextScreen = new Intent(getApplicationContext(), Search.class);
					startActivity(nextScreen);
	            return true;
	        case R.id.home:
	        	 Intent nextScreen3= new Intent(getApplicationContext(), Home.class);
					startActivity(nextScreen3);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	/*
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == RESULT_OK){      
		         String result=data.getStringExtra("result");          
		     }
		     if (resultCode == RESULT_CANCELED) { 
		    	 Intent nextScreen2 = new Intent(getApplicationContext(), RestaurantMenu.class);
					startActivity(nextScreen2);
					
					
		    	 
		         
		     }
		  }
		}
*/
}
