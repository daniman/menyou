package edu.mit.menyou.menu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.content.res.AssetManager;

public class  Algorithm {
	List<RestaurantMenuItem> listOfDishes1;
	//String[] allergiesList;
	private List<String> allergiesList;
	private List<String> likesList = null;
	private List<String> dislikesList = null;
	private List<RestaurantMenuItem> listOfDishes2;
	private List<RestaurantMenuItem> listOfDishesBad;
	private List<RestaurantMenuItem> listOfDishesFinal;
	
	private int costInt;
	private int spiceInt;
	private int denseInt;
	private int discoverInt;
	private int healthInt;
	
	private int likesPlus;
	private int dislikesDrop=-3;
	private int spicePoints;
	private int healthPoints;
	private int densePoints;
	
	
	public  Algorithm(List<RestaurantMenuItem> LOD, List<String> AL,List<String> L,List<String> D, int cost, int spice, int dense, int discover, int health){
		listOfDishes1=LOD;
		allergiesList=AL;
		likesList=L;
		dislikesList=D;
		costInt=cost;
		spiceInt=spice;
		denseInt=dense;
		discoverInt=discover;
		healthInt=health;
	}
	
	public void  reset(){
		/// Reset all food Rank to 100 each time ///
		
				for (int k=0;k<listOfDishes1.size();k++){
					listOfDishes1.get(k).setRank(100);
				}
		
	}
	
	
	public List<RestaurantMenuItem> calculate(){
		
		for (int k=0;k<listOfDishes1.size();k++){
			listOfDishes1.get(k).setRank(100);
		}
		//do stuff with the first 2 lists to make the final one

		/*
		if(allergiesList.length==0){
			return listOfDishes1;
		}
		*/
		if(listOfDishes1.isEmpty()){
			return listOfDishes1;
		}
		
		
		////////////////////  Allergies  //////////////////////
		List<String> eggAllergy = Arrays.asList("egg","cake","eggnog","mayo");
		List<String> fishAllergy = Arrays.asList("fish","salmon","anchov","haddock","mahi","tilapia","tuna");
		List<String> milkAllergy = Arrays.asList("milk","butter","cheese","pudding","yogurt");
		List<String> peanutsAllergy = Arrays.asList("peanut","ground nuts","mixed nuts");
		List<String> shellfishAllergy = Arrays.asList("crab","lobster","crayfish","shrimp");
		List<String> soyAllergy = Arrays.asList("miso","soy","tofu");
		List<String> treeNutsAllergy = Arrays.asList("almond","cashew","coconut","macadamia","pecan","pistachio","walnut");
		List<String> wheatAllergy = Arrays.asList("wheat","pasta");
		
		//////////////////// keywords /////////////////////////////
		List<String> heavy = Arrays.asList("pasta","bread","cream","fried","risotto","gnocchi","ragu","spaghetti","steak","stew","bisque","chowder");
		List<String> light = Arrays.asList("salad","soup","fresh","raw","fish","ceviche","tartare");
		List<String> healthy = Arrays.asList("soup","salad","soup","fish","fresh","steamed","boiled","baked","roasted","chicken","turkey","poached");
		List<String> unhealthy = Arrays.asList("fried","cream","sauteed","stir fry","steak","burger","fries","pizza","wing","bbq");
		List<String> spicy = Arrays.asList("chili","jalepeno","spicy","hot","cayenne","pepper");
	

		
		List<RestaurantMenuItem> listOfDishesBad = new ArrayList<RestaurantMenuItem>();

		if (allergiesList.contains("egg")){
			
			for (int k=0;k<listOfDishes1.size();k++){
				for (int j=0;j<eggAllergy.size();j++){
					if(listOfDishes1.get(k).getName().toLowerCase(Locale.ENGLISH).contains(eggAllergy.get(j))){
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
					if(listOfDishes1.get(k).getDescription().toLowerCase().contains(eggAllergy.get(j))){
						
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
				}
			}
		} 
		if (allergiesList.contains("fish")){
			for (int k=0;k<listOfDishes1.size();k++){
				for (int j=0;j<eggAllergy.size();j++){
					if(listOfDishes1.get(k).getName().toLowerCase().contains(fishAllergy.get(j))){
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
					if(listOfDishes1.get(k).getDescription().toLowerCase().contains(fishAllergy.get(j))){
						
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
				}
			}
		} 

		if (allergiesList.contains("milk")){
			for (int k=0;k<listOfDishes1.size();k++){
				for (int j=0;j<milkAllergy.size();j++){
					if(listOfDishes1.get(k).getName().toLowerCase().contains(milkAllergy.get(j))){
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
					if(listOfDishes1.get(k).getDescription().toLowerCase().contains(milkAllergy.get(j))){
						
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
				}
			}
		} 
		if (allergiesList.contains("peanuts")){
			for (int k=0;k<listOfDishes1.size();k++){
				for (int j=0;j<peanutsAllergy.size();j++){
					if(listOfDishes1.get(k).getName().toLowerCase().contains(peanutsAllergy.get(j))){
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
					if(listOfDishes1.get(k).getDescription().toLowerCase().contains(peanutsAllergy.get(j))){
						
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
				}
			}
		} 
		if (allergiesList.contains("shellfish")){
			for (int k=0;k<listOfDishes1.size();k++){
				for (int j=0;j<shellfishAllergy.size();j++){
					if(listOfDishes1.get(k).getName().toLowerCase().contains(shellfishAllergy.get(j))){
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
					if(listOfDishes1.get(k).getDescription().toLowerCase().contains(shellfishAllergy.get(j))){
						
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
				}
			}
		} 
		if (allergiesList.contains("soy")){
			for (int k=0;k<listOfDishes1.size();k++){
				for (int j=0;j<soyAllergy.size();j++){
					if(listOfDishes1.get(k).getName().toLowerCase().contains(soyAllergy.get(j))){
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
					if(listOfDishes1.get(k).getDescription().toLowerCase().contains(soyAllergy.get(j))){
						
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
				}
			}
		} 
		if (allergiesList.contains("tree nuts")){
			for (int k=0;k<listOfDishes1.size();k++){
				for (int j=0;j<treeNutsAllergy.size();j++){
					if(listOfDishes1.get(k).getName().toLowerCase().contains(treeNutsAllergy.get(j))){
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
					if(listOfDishes1.get(k).getDescription().toLowerCase().contains(treeNutsAllergy.get(j))){
						
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
				}
			}
		} 
		if (allergiesList.contains("wheat")){
			for (int k=0;k<listOfDishes1.size();k++){
				for (int j=0;j<wheatAllergy.size();j++){
					if(listOfDishes1.get(k).getName().toLowerCase().contains(wheatAllergy.get(j))){
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
					if(listOfDishes1.get(k).getDescription().toLowerCase().contains(wheatAllergy.get(j))){
						
						listOfDishesBad.add(listOfDishes1.get(k));
						listOfDishes1.remove(k);
					}
				}
			}
		}
		
		// Discover Mood // 
		//changes amount added to a dish based off if user prefers familiar or new
		if(discoverInt<8){likesPlus=4;}
		if(discoverInt>7 && discoverInt<16){likesPlus=3;}
		if(discoverInt>15 && discoverInt<24){likesPlus=1;}
		if(discoverInt>23 && discoverInt<32){likesPlus=0;}
		if(discoverInt>31){likesPlus=-1;}	
		
			for (int k=0;k<listOfDishes1.size();k++){
				RestaurantMenuItem obj = listOfDishes1.get(k);
				obj.setRank(100);
				String dish = obj.getName();
				String desc = obj.getDescription();
				Double price = Double.parseDouble(obj.getPrice());
				
				// add points if likes match up //
				for (int j=0;j<likesList.size();j++){
					String food = likesList.get(j);
					if(dish.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(likesPlus);
					}
					if(desc.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(likesPlus);
					}
				}
				
				// subtract points if dislikes match up //
				for (int j=0;j<dislikesList.size();j++){
					String food = dislikesList.get(j);
					if(dish.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(dislikesDrop);
					}
					if(desc.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(dislikesDrop);
					}
				} 
				
				/// cost sensitivity ////
				if(costInt<8){
					if(price>13 && price<=18){
						obj.changeRank(-2);
					}
					if(price>18){
						obj.changeRank(-4);
					}
				}
				if(costInt>7 && costInt<16){
					if(price>13 && price<=18){
						obj.changeRank(-1);
					}
					if(price>18){
						obj.changeRank(-3);
					}
				}
				if(costInt>15 && costInt<24){
					//Neutral to cost
				}
				if(costInt>23 && costInt<32){
					if(price>16){
						obj.changeRank(1);
					}
				}
				if(costInt>31){
					if(price>16 && price<22){
						obj.changeRank(2);
					}
					if(price>=22){
						obj.changeRank(3);
					}
				}
				
				// Spicy  Mood // 
				//changes amount added to a dish for spicy keywords
				if(spiceInt<8){spicePoints=-5;}
				if(spiceInt>7 && spiceInt<16){spicePoints=-3;}
				if(spiceInt>15 && spiceInt<24){spicePoints=0;}
				if(spiceInt>23 && spiceInt<32){spicePoints=3;}
				if(spiceInt>31){spicePoints=5;}	
				
				// spiciness rank //
				for (int j=0;j<spicy.size();j++){
					String food = spicy.get(j);
					if(dish.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(spicePoints);
					}
					if(desc.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(spicePoints);
					}
				}
				
				// Healthy and Unhealthy // 
				//changes amount added to a dish for healthy keywords
				if(healthInt<8){healthPoints=-3;}
				if(healthInt>7 && healthInt<16){healthPoints=-1;}
				if(healthInt>15 && healthInt<24){healthPoints=1;}
				if(healthInt>23 && healthInt<32){healthPoints=2;}
				if(healthInt>31){healthPoints=4;}	
				
				// healthiness rank //
				for (int j=0;j<healthy.size();j++){
					String food = healthy.get(j);
					if(dish.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(healthPoints);
					}
					if(desc.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(healthPoints);
					}
				}
				for (int j=0;j<unhealthy.size();j++){
					String food = unhealthy.get(j);
					if(dish.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(-healthPoints);
					}
					if(desc.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(-healthPoints);
					}
				}
				
				// Density  Mood // 
				//changes amount added to a dish for density keywords
				if(denseInt<8){densePoints=-3;}
				if(denseInt>7 && denseInt<16){densePoints=-1;}
				if(denseInt>15 && denseInt<24){densePoints=0;}
				if(denseInt>23 && denseInt<32){densePoints=1;}
				if(denseInt>31){densePoints=3;}	
				
				// lightness rank //
				for (int j=0;j<light.size();j++){
					String food = light.get(j);
					if(dish.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(-densePoints);
					}
					if(desc.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(-densePoints);
					}
				}
				// heavy rank //
				for (int j=0;j<heavy.size();j++){
					String food = heavy.get(j);
					if(dish.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(densePoints);
					}
					if(desc.toLowerCase(Locale.ENGLISH).contains(food)){
						obj.changeRank(densePoints);
					}
				}

			}
		

		List<RestaurantMenuItem> listOfDishesFinal = new ArrayList<RestaurantMenuItem>();
		listOfDishesFinal.clear();
		
		//Collections.sort(listOfDishes1, new MenuItemComparator());
		
		Collections.sort(listOfDishes1);
		
		for(int i =0; i<listOfDishesBad.size();i++){
			listOfDishesBad.get(i).setRank(-99);
		}
		
		listOfDishesFinal.addAll(listOfDishes1);
		
		if(!listOfDishesBad.isEmpty()){
		listOfDishesFinal.add(new RestaurantMenuItem("-----Allergies-----","you would need to make sure the chef knows about your allergy in order to enjoy the following",""));
		listOfDishesFinal.get(listOfDishesFinal.size()-1).setRank(-99);
		
		listOfDishesFinal.addAll(listOfDishesBad);
		}

		//listOfDishesFinal.addAll(listOfDishesBad);

		return listOfDishesFinal;
		//return listOfDishes2; 
	}
}
