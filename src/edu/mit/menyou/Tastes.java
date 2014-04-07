package edu.mit.menyou;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;


import java.util.Set;

import edu.mit.menyou.menu.RestaurantMenu;
import edu.mit.menyou.menu.RestaurantMenuItem;
import edu.mit.menyou.orderedDish.OrderedDish;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class Tastes extends FragmentActivity {
	
	private static final List<String> dislikes_list = new ArrayList<String>();
	private static final List<String> likes_list = new ArrayList<String>();
	private static final List<String> allergies_full = new ArrayList<String>();
	private static final List<String> allergies_list = new ArrayList<String>();
	private static final List<String> likes_food_list = new ArrayList<String>();	
	private static final List<String> dislikes_food_list = new ArrayList<String>();

	final static String firstTime = "edu.mit.menyou.firstTime";
	final static String allergiesKey = "edu.mit.menyou.allergies";
	final static String likesKey = "edu.mit.menyou.likes";
	final static String dislikesKey = "edu.mit.menyou.dislikes";
	
	
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrollable_stuff);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		dislikes_list.clear();
		likes_list.clear();
		allergies_full.clear();
		allergies_list.clear();
		likes_food_list.clear();
		dislikes_food_list.clear();
		
		
		try {
			AssetManager assetManager = getResources().getAssets();
			InputStream is = assetManager.open("foodList");
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			String line;
			if ( is != null) {
				while ((line = r.readLine()) != null) {
					likes_food_list.add(line);
					dislikes_food_list.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			AssetManager assetManager = getResources().getAssets();
			InputStream is = assetManager.open("allergiesList");
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			String line;
			if ( is != null) {
				while ((line = r.readLine()) != null) {
					allergies_full.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		final Button SetupButton = (Button) findViewById(R.id.setup_button);
		
		// Create the adapter that will return a fragment for each of the four
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
	    public Fragment getItem(int position) {
	        // getItem is called to instantiate the fragment for the given page.
	        // Return a DummySectionFragment (defined as a static inner class
	        // below) with the page number as its lone argument.
	    switch (position) {
	        case 0:
	            Fragment fragment = new DummySectionFragment();
	            Bundle args = new Bundle();
	            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
	            fragment.setArguments(args);
	            return fragment;

	          case 1:
	            Fragment fragment2 = new DummySectionFragment2();
	            Bundle args2 = new Bundle();
	            args2.putInt(DummySectionFragment2.ARG_SECTION_NUMBER, position + 2);
	            fragment2.setArguments(args2);
	            return fragment2;

	          case 2:

	             Fragment fragment3 = new DummySectionFragment3();
	             Bundle args3 = new Bundle();
	             args3.putInt(DummySectionFragment3.ARG_SECTION_NUMBER, position + 3);
	             fragment3.setArguments(args3);
	             return fragment3;
	         
	          case 3:

		             Fragment fragment4 = new DummySectionFragment4();
		             Bundle args4 = new Bundle();
		             args4.putInt(DummySectionFragment4.ARG_SECTION_NUMBER, position + 3);
		             fragment4.setArguments(args4);
		             return fragment4;
		          
	          case 4:

		             Fragment fragment5 = new DummySectionFragment5();
		             Bundle args5 = new Bundle();
		             args5.putInt(DummySectionFragment5.ARG_SECTION_NUMBER, position + 3);
		             fragment5.setArguments(args5);
		             return fragment5;

	        default:
	            return null;
	    }}

		@Override
		public int getCount() {
			// Show 5 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);	
			}
			return null;
		}}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_scrollable_stuff_dummy, container, false);
			rootView.findViewById(R.id.section_label);
			return rootView;
		}
	}
	public static class DummySectionFragment2 extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment2() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView2 = inflater.inflate(R.layout.fragment_scrollable_stuff_dummy2, container, false);

			final SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);						
			final TextView allergies = (TextView) rootView2.findViewById(R.id.allergies);
			final ListView lv = (ListView) rootView2.findViewById(R.id.listView2);
			final ArrayAdapter<String> adpt = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_list_item_1, allergies_full);
			lv.setAdapter(adpt);
			
			//allergies_list = prefs.getStringSet(allergiesKey, null);
			
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		 	     public void onItemClick(AdapterView<?> adapter, View view, int position,long id) { 
			         // We know the View is a TextView so we can cast it
				         TextView clickedView = (TextView) view;
				         String newAllergy = clickedView.getText().toString();
					     allergies.setText(allergies.getText().toString()+" "+newAllergy);
					     allergies_full.remove(position);	
					     allergies_list.add(newAllergy);
						 adpt.notifyDataSetChanged();
						 String allergiesTotal = prefs.getString(allergiesKey, null);
						 //allergies_list.add(newAllergy);
						 prefs.edit().putString(allergiesKey, allergiesTotal+" "+newAllergy).commit();
					     }
					});
			return rootView2;
			
		}
	}
	public static class DummySectionFragment3 extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		private ArrayAdapter<String> likesAdpt;
		private ArrayAdapter<String> adpt;
		private String clickedLike;

		public DummySectionFragment3() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView3 = inflater.inflate(R.layout.fragment_scrollable_stuff_dummy3, container, false);
						
			// Get a reference to the AutoCompleteTextView in the layout
			final AutoCompleteTextView likes_input = (AutoCompleteTextView) rootView3.findViewById(R.id.likes_input);
			
			
			final ListView likesLV = (ListView) rootView3.findViewById(R.id.likes);
			final ListView lv = (ListView) rootView3.findViewById(R.id.listView3);
			adpt = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_list_item_1, likes_food_list);
			likesAdpt = new ArrayAdapter<String>(getActivity().getBaseContext(),R.layout.likes_list_item, likes_list);

			
			likes_input.setAdapter(adpt);
			lv.setAdapter(adpt);
			likesLV.setAdapter(likesAdpt);
			registerForContextMenu(likesLV);  //register for contextmenu
			
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		 	     public void onItemClick(AdapterView<?> adapter, View view, int position,long id) { 
			         // We know the View is a TextView so we can cast it
		 	    	 TextView clickedView = (TextView) view;
					 likes_list.add(clickedView.getText().toString());
					 likes_food_list.remove(position);
					 likesAdpt.notifyDataSetChanged();
					 adpt.notifyDataSetChanged();
		 	     }
			});
			rootView3.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            	public void onClick(View view) {
            		String liked = likes_input.getText().toString();
            		likes_list.add(liked);
            		
            		if(likes_food_list.contains(liked)){
            			int index = likes_food_list.indexOf(liked);
            			likes_food_list.remove(index);
            		}
            		
            		likes_input.setText("");
            		likesAdpt.notifyDataSetChanged();
					adpt.notifyDataSetChanged();
            		
                }
            });
			
			return rootView3;
		}
		// We want to create a context Menu when the user long click on an item
	    @Override
	    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
	    	if (v.getId() == R.id.likes) {
	    		super.onCreateContextMenu(menu, v, menuInfo);
	    		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
			    clickedLike = likesAdpt.getItem(aInfo.position);
	    		
	    		menu.setHeaderTitle(clickedLike);
	    		menu.add(1, 1, 1, "remove from my likes");
	    		menu.add(1, 2, 1, "nevermind");
	    	}
	    }
	    @Override
	    public boolean onContextItemSelected(MenuItem item) {
	       switch (item.getItemId()) {
	       case 1:
	           //first ContextMenu option
	    	   likes_list.remove(clickedLike);
	    	   likes_food_list.add(clickedLike);
	    	   likesAdpt.notifyDataSetChanged();
	    	   adpt.notifyDataSetChanged();
	       break; 
	       case 2:
	          //stuff for option 2 of the ContextMenu
	    	  //nothing
	       break;
	       }
	       return super.onContextItemSelected(item);
	    }
	}
	public static class DummySectionFragment4 extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		private ArrayAdapter<String> dislikesAdpt;
		private ArrayAdapter<String> adpt2;
		private String clickedDislike;

		public DummySectionFragment4() {}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView4 = inflater.inflate(R.layout.fragment_scrollable_stuff_dummy4, container, false);
			
			final AutoCompleteTextView dislikes_input = (AutoCompleteTextView) rootView4.findViewById(R.id.dislikes_input);
			final ListView dislikesLV = (ListView) rootView4.findViewById(R.id.dislikes);
			final ListView lv = (ListView) rootView4.findViewById(R.id.listView4);
			adpt2 = new ArrayAdapter<String>(getActivity().getBaseContext(),android.R.layout.simple_list_item_1, dislikes_food_list);
			dislikesAdpt = new ArrayAdapter<String>(getActivity().getBaseContext(),R.layout.likes_list_item, dislikes_list);

			
			lv.setAdapter(adpt2);
			dislikes_input.setAdapter(adpt2);
			dislikesLV.setAdapter(dislikesAdpt);
			registerForContextMenu(dislikesLV);  //register for contextmenu
			
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		 	     public void onItemClick(AdapterView<?> adapter, View view, int position,long id) { 
			         // We know the View is a TextView so we can cast it
		 	    	 TextView clickedView = (TextView) view;
					 //dislikes.setText(dislikes.getText()+" "+clickedView.getText().toString());
		 	    	 String disliked= clickedView.getText().toString();
		 	    	 
		 	    	 if(!dislikes_list.contains(disliked)){
					 dislikes_list.add(disliked);
		 	    	 }
					 dislikes_food_list.remove(position);	
					 adpt2.notifyDataSetChanged();
					 dislikesAdpt.notifyDataSetChanged();
		 	    	 
		 	     }
			});
			
			rootView4.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            	public void onClick(View view) {
            		String disliked = dislikes_input.getText().toString();
            		
            		if(disliked!=""){
            		if(!dislikes_list.contains(disliked)){
            		dislikes_list.add(disliked);
            		}
            		if(dislikes_food_list.contains(disliked)){
            			int index = dislikes_food_list.indexOf(disliked);
            			dislikes_food_list.remove(index);
            		}
            		
            		dislikes_input.setText("");
            		adpt2.notifyDataSetChanged();
					dislikesAdpt.notifyDataSetChanged();
                }
            	}
            }); 
					
			return rootView4;
		}
		// We want to create a context Menu when the user long click on an item
	    @Override
	    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
	    	if (v.getId() == R.id.dislikes) {
	    		super.onCreateContextMenu(menu, v, menuInfo);
	    		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
			    clickedDislike = dislikesAdpt.getItem(aInfo.position);
	    		
	    		menu.setHeaderTitle(clickedDislike);
	    		menu.add(1, 1, 1, "remove from my likes");
	    		menu.add(1, 2, 1, "nevermind");

	    	}
	    }
	    
	  //the correct callback name starts with o and not O
	    @Override
	    public boolean onContextItemSelected(MenuItem item) {
	       switch (item.getItemId()) {
	       case 1:
	           //first ContextMenu option
	    	   dislikes_list.remove(clickedDislike);
	    	   dislikes_food_list.add(clickedDislike);
	    	   dislikesAdpt.notifyDataSetChanged();
	    	   adpt2.notifyDataSetChanged();
	    	   
	       break; 
	       case 2:
	          //stuff for option 2 of the ContextMenu
	    	  //nothing
	       break;
	       }
	       return super.onContextItemSelected(item);
	    }
		
	}
	public static class DummySectionFragment5 extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment5() {}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView5 = inflater.inflate(R.layout.fragment_scrollable_stuff_dummy5, container, false);
			
			final SharedPreferences prefs = getActivity().getBaseContext().getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);
		      
	        rootView5.findViewById(R.id.setup_button).setOnClickListener(new View.OnClickListener() {
	                    
	                	public void onClick(View view) {
	                		prefs.edit().putInt(firstTime, 1).commit();
	                		
	                        Intent intent = new Intent(getActivity(), Username.class);
	                        startActivity(intent);
	                    }
	                });
			return rootView5;
		}
	} 
	
}
