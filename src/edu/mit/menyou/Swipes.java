package edu.mit.menyou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.parse.ParseObject;

import edu.mit.menyou.home.Home;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ViewFlipper;

public class Swipes extends Activity{
	
	private Animation mInFromRight;
    private Animation mOutToLeft;
    private Animation mInFromLeft;
    private Animation mOutToRight;
    private Animation inFromBelow;
    private Animation mOutAbove;
    private int i=-1;

	private List<String> foodList = new ArrayList<String>();	

    private ViewFlipper mViewFlipper;
    private Button btn;
    private TextView currentFood;
    private Context ctx = this;
    private ImageView overlay;
    private int count=0;
	private static SharedPreferences prefs;
	private static String[] likesArray;
	private static String[] dislikesArray;
	private static final List<String> dislikes_list = new ArrayList<String>();
	private static final List<String> likes_list = new ArrayList<String>();
	
	final static String likesKey = "edu.mit.menyou.likes";
	final static String dislikesKey = "edu.mit.menyou.dislikes";
	final static String allergiesKey = "edu.mit.menyou.allergies";

	private int COUNT_MAX=10;
    
	    private GestureDetector gestureDetector;
	    View.OnTouchListener gestureListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getActionBar().setDisplayShowTitleEnabled(false);



		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		foodList.clear();
		try {
			AssetManager assetManager = getResources().getAssets();
			InputStream is = assetManager.open("foodList");
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			String line;
			if ( is != null) {
				while ((line = r.readLine()) != null) {
					foodList.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long seed = System.nanoTime();
		Collections.shuffle(foodList, new Random(seed));
		
		prefs = this.getBaseContext().getSharedPreferences("edu.mit.menyou", Context.MODE_PRIVATE);

		
/////////////// likes //////////////////////
	String likesString = prefs.getString(likesKey, null);
	if(likesString!=null){
	likesArray = likesString.split(" ");
	int length = likesArray.length;
			
	
	for(int i=0;i<length;i++){
		String food=likesArray[i];
		food = food.replaceAll("_", " ");
		likes_list.add(food);
		if(foodList.contains(food)){
		foodList.remove(foodList.indexOf(food));
		}
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
		if(foodList.contains(food)){
		foodList.remove(foodList.indexOf(food));
		}
		}
	dislikes_list.remove(0);
	
	////////////////////////////////////////////////////
	
		 //logoController = (ImageView) findViewById(R.id.logoController);
		 //mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
	     //mViewFlipper.setDisplayedChild(0);

		 overlay = (ImageView) findViewById(R.id.overlay);
		 currentFood = (TextView) findViewById(R.id.currentfood);
		 //btn = (Button) findViewById(R.id.animate_button);
	     initAnimations();
	     
	     /*
	     btn.setOnClickListener(new OnClickListener() {
	    	 public void onClick(View arg0) {
	                	currentFood.setText("rawr");
	                	
	                	currentFood.startAnimation(AnimationUtils.loadAnimation(ctx, android.R.anim.slide_in_left));
	                	
	                
	            }
	        });
	        */
	     
		
		
		//TextView foodView = (TextView) findViewById(R.id.currentfood);
		// Do this for each view added to the grid
		//foodView.setOnClickListener(Game.this); 
		//foodView.setOnTouchListener(gestureListener);
		
		

        // Gesture detection
        gestureDetector = new GestureDetector(this, new MyGestureDetector() );
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        } 
        
	}
	
	   private void initAnimations() {
	        mInFromRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
	                +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f);
	        mInFromRight.setDuration(400);
	        AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
	        mInFromRight.setInterpolator(accelerateInterpolator);

	        mInFromLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
	                -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f);
	        mInFromLeft.setDuration(400);
	        mInFromLeft.setInterpolator(accelerateInterpolator);

	        mOutToRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
	                0.0f, Animation.RELATIVE_TO_PARENT, +1.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f);
	        mOutToRight.setDuration(600);
	        mOutToRight.setInterpolator(accelerateInterpolator);

	        mOutToLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, -1.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f);
	        mOutToLeft.setDuration(600);
	        mOutToLeft.setInterpolator(accelerateInterpolator);
	        
	        //custom from above
	        mOutAbove = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, -1.0f);
	        mOutAbove.setDuration(300);
	        mOutAbove.setInterpolator(accelerateInterpolator);
	        
	      //custom from above
	        inFromBelow = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f,
	                Animation.RELATIVE_TO_PARENT, +1.0f,
	                Animation.RELATIVE_TO_PARENT, 0.0f);
	        inFromBelow.setDuration(400);
	        inFromBelow.setInterpolator(accelerateInterpolator);

	        final GestureDetector gestureDetector;
	        gestureDetector = new GestureDetector(new MyGestureDetector());

	        currentFood.setOnTouchListener(new OnTouchListener() {

	            public boolean onTouch(View v, MotionEvent event) {
	                if (gestureDetector.onTouchEvent(event)) {
	                    return false;
	                } else {
	                    return true;
	                }
	            }
	        });
	        /*
	        mViewFlipper.setOnTouchListener(new OnTouchListener() {

	            public boolean onTouch(View v, MotionEvent event) {
	                if (gestureDetector.onTouchEvent(event)) {
	                    return false;
	                } else {
	                    return true;
	                }
	            }
	        });*/
	    }
	   
	   private class MyGestureDetector extends SimpleOnGestureListener {

	        private static final int SWIPE_MIN_DISTANCE = 120;
	        private static final int SWIPE_MAX_OFF_PATH = 250;
	        private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	        
	       

	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	                float velocityY) {
	        	
	        	if(foodList.size()<10){
		        	COUNT_MAX=foodList.size()+1;
		        }
	        	i=i+1;
	            System.out.println(" in onFling() :: ");
	            //if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
	                //return false;
	            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
	                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	            	count++;
	                //mViewFlipper.setInAnimation(mInFromRight);
	                //mViewFlipper.setOutAnimation(mOutToLeft);
	                //mViewFlipper.showNext();
	                //
	                
	                Animation outLeft = mOutToLeft;
	                overlay.setVisibility(View.VISIBLE);
	                overlay.setImageResource(R.drawable.logo_x);
	                currentFood.startAnimation(outLeft);
	                overlay.startAnimation(outLeft);
	                if(count>1){dislikes_list.add(currentFood.getText().toString());}
	                
	                currentFood.postDelayed(new Runnable() {
	                    @Override
	                    public void run() {
	                    	currentFood.setText(foodList.get(i));
	    	                currentFood.startAnimation(inFromBelow);
	    	                overlay.setVisibility(View.INVISIBLE);
	    	                if(count==COUNT_MAX){
	    	                	endSwipes();
	    	                }
	                    }
	                }, 600);

	            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
	                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	            	count++;
	                //mViewFlipper.setInAnimation(mInFromLeft);
	                //mViewFlipper.setOutAnimation(mOutToRight);
	                //mViewFlipper.showPrevious();
	                
	                Animation outRight = mOutToRight;
	                overlay.setVisibility(View.VISIBLE);
	                overlay.setImageResource(R.drawable.logo_check);
	                currentFood.startAnimation(outRight);
	                overlay.startAnimation(outRight);
	                if(count>1){likes_list.add(currentFood.getText().toString());}
	                
	                currentFood.postDelayed(new Runnable() {
	                    @Override
	                    public void run() {
	    	                currentFood.setText(foodList.get(i));
	    	                currentFood.startAnimation(inFromBelow);
	    	                overlay.setVisibility(View.INVISIBLE);
	    	                if(count==COUNT_MAX){
	    	                	endSwipes();
	    	                }

	                    }
	                }, 600);
	                
	                }
	            
	            else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
	                    && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
	            	count++;
	                //
	                
	                Animation outAbove = mOutAbove;
	                currentFood.startAnimation(outAbove);
	                
	                currentFood.postDelayed(new Runnable() {
	                    @Override
	                    public void run() {
	    	                currentFood.setText(foodList.get(i));
	    	                currentFood.startAnimation(inFromBelow);
	    	                overlay.setVisibility(View.INVISIBLE);
	    	                if(count>COUNT_MAX){
	    	                	endSwipes();
	    	                }
	                    }
	                }, 300);
	                
	                }
	            return super.onFling(e1, e2, velocityX, velocityY);
	        }
	    }

	   private void endSwipes(){
		   
		   String likes="";
	        String dislikes="";
	        
		   for(int i=0;i<likes_list.size();i++){
   			String food = likes_list.get(i);
   			food = food.replaceAll(" ", "_");
   			likes=likes+" "+food;
   		}
   		for(int i=0;i<dislikes_list.size();i++){
   			String food = dislikes_list.get(i);
   			food = food.replaceAll(" ", "_");
   			dislikes=dislikes+" "+food;
   		}
   		
   		prefs.edit().putString(likesKey, likes).commit();
   		prefs.edit().putString(dislikesKey, dislikes).commit();
   		
   		String first = "edu.mit.menyou.first";
		String last = "edu.mit.menyou.last";
		String number = "edu.mit.menyou.number";
		String firstname = prefs.getString(first, "Ben");
		String lastname = prefs.getString(last, "*no last name*");
		String username = firstname+" "+lastname;
		String mNumber = prefs.getString(number, "none");
		String allergiesString = prefs.getString(allergiesKey, null);
		
		String timeOfSwipe = "edu.mit.menyou.timeOfSwipe";
		String timeMillis = String.valueOf(System.currentTimeMillis());
		prefs.edit().putString(timeOfSwipe, timeMillis).commit();
		
		
		//user data on setup use//
		ParseObject UpdateTastes = new ParseObject("UpdateTastes");
		UpdateTastes.put("username", username);
		UpdateTastes.put("number", mNumber);
		UpdateTastes.put("allergies", allergiesString);
		UpdateTastes.put("likes", likes);
		UpdateTastes.put("dislikes", dislikes);
		UpdateTastes.put("fromSwipes", "swipe!");
		UpdateTastes.saveInBackground();
		
		Intent intent = new Intent(ctx, Home.class);
        startActivity(intent);
		   
	   }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first, menu);
		return true;
	}
	
}
