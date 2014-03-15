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
    
    List<Map<String, String>> planetsList = new ArrayList<Map<String,String>>();
   	 
    private void initList() {
    // We populate the planets
    planetsList.add(createPlanet("planet", "Mercury"));
    planetsList.add(createPlanet("planet", "Venus"));
    planetsList.add(createPlanet("planet", "Mars"));
    planetsList.add(createPlanet("planet", "Jupiter"));
    planetsList.add(createPlanet("planet", "Saturn"));
    planetsList.add(createPlanet("planet", "Uranus"));
    planetsList.add(createPlanet("planet", "Neptune"));
    planetsList.add(createPlanet("planet", "Mercury"));
    planetsList.add(createPlanet("planet", "Venus"));
    planetsList.add(createPlanet("planet", "Mars"));
    planetsList.add(createPlanet("planet", "Jupiter"));
    planetsList.add(createPlanet("planet", "Saturn"));
    planetsList.add(createPlanet("planet", "Uranus"));
    planetsList.add(createPlanet("planet", "Neptune"));
    	   }
     
  	private HashMap<String, String> createPlanet(String key, String name) {
    	    HashMap<String, String> planet = new HashMap<String, String>();
    	    planet.put(key, name);
    	    return planet;
   	}
  	
  	SimpleAdapter simpleAdpt = new SimpleAdapter(this, planetsList, android.R.layout.simple_list_item_1, new String[] {"planet"}, new int[] {android.R.id.text1});
	
  	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		initList();
		ListView lv = (ListView) findViewById(R.id.listView);
		SimpleAdapter simpleAdpt = new SimpleAdapter(this, planetsList, android.R.layout.simple_list_item_1, new String[] {"planet"}, new int[] {android.R.id.text1});
		lv.setAdapter(simpleAdpt);
		
		final AutoCompleteTextView search_input = (AutoCompleteTextView) findViewById(R.id.search_input);
		search_input.setText("search for your restaurant", TextView.BufferType.EDITABLE);
		final ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
		final ImageButton gpsButton = (ImageButton) findViewById(R.id.gps_button);
		
		final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, search_history);
	    AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.search_input);
	    textView.setAdapter(adapter1);
	    
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
     // React to user clicks on item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
     	     public void onItemClick(AdapterView<?> parentAdapter, View view, int position,long id) {
     	    	 // We know the View is a TextView so we can cast it
     	         TextView clickedView = (TextView) view;
     	         Toast.makeText(Search.this, "Item with id ["+id+"] - Position ["+position+"] - Planet ["+clickedView.getText()+"]", Toast.LENGTH_SHORT).show();
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
	   public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	 	      super.onCreateContextMenu(menu, v, menuInfo);
	  	      AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
		      // We know that each row in the adapter is a Map
	  	      HashMap map =  (HashMap) simpleAdpt.getItem(aInfo.position);
	  	 
	  	      menu.setHeaderTitle("Options for " + map.get("planet"));
	  	      menu.add(1, 1, 1, "Details");
	  	      menu.add(1, 2, 2, "Delete");
	  	 	  }
}
