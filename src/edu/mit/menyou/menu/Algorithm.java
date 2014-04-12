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
	private List<String> likesList;
	private List<String> dislikesList;
	private List<RestaurantMenuItem> listOfDishes2;
	private List<RestaurantMenuItem> listOfDishesBad;
	private List<RestaurantMenuItem> listOfDishesFinal;
	
	private int costInt;
	private int spiceInt;
	private int denseInt;
	private int discoverInt;
	private int healthInt;
	
	private int likesPlus;
	private int dislikesDrop=-2;
	
	
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

		
		List<RestaurantMenuItem> listOfDishesBad = new ArrayList<RestaurantMenuItem>();
		List<RestaurantMenuItem> listOfDishes2 = new ArrayList<RestaurantMenuItem>();

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
		if(discoverInt<8){likesPlus=3;}
		if(discoverInt>7 && discoverInt<16){likesPlus=2;}
		if(discoverInt>15 && discoverInt<24){likesPlus=1;}
		if(discoverInt>23 && discoverInt<32){likesPlus=0;}
		if(discoverInt>31 && discoverInt<40){likesPlus=-1;}
		
		
			for (int k=0;k<listOfDishes1.size();k++){
				RestaurantMenuItem obj = listOfDishes1.get(k);
				obj.setRank(100);
				String dish = obj.getName();
				String desc = obj.getDescription();
				Double price = Double.parseDouble(obj.getPrice());
				
				for (int j=0;j<likesList.size();j++){
					String food = likesList.get(j);
					
					if(dish.toLowerCase(Locale.ENGLISH).contains(food)){
						int rank = obj.getRank();
						obj.setRank(rank+likesPlus);
						//listOfDishes1.get(k).changeRank(likesPlus);
					}
					if(desc.toLowerCase(Locale.ENGLISH).contains(food)){
						int rank = obj.getRank();
						obj.setRank(rank+likesPlus);
					}
				}
				for (int j=0;j<dislikesList.size();j++){
					String food = dislikesList.get(j);
					
					if(dish.contains(food) || desc.toLowerCase(Locale.ENGLISH).contains(food)){
						int rank = obj.getRank();
						obj.setRank(rank+dislikesDrop);
						//listOfDishes1.get(k).changeRank(likesPlus);
					}
					if(desc.toLowerCase(Locale.ENGLISH).contains(food)){
						int rank = obj.getRank();
						obj.setRank(rank+dislikesDrop);
					}
				}
				if(costInt<8){
					int rank = obj.getRank();
					if(price>13 && price<=16){
					obj.setRank(rank-2);
					}
					if(price>16){
						obj.setRank(rank-4);
					}
				}
				if(costInt>7 && costInt<16){
					int rank = obj.getRank();
					if(price>13 && price<=16){
					obj.setRank(rank-1);
					}
					if(price>16){
						obj.setRank(rank-3);
					}
				}
				if(costInt>15 && costInt<24){
					int rank = obj.getRank();
					if(price>16){
						obj.setRank(rank-1);
					}
				}
				if(costInt>31){
					int rank = obj.getRank();
					if(price>16 && price<20){
						obj.setRank(rank+2);
					}
					if(price>=20){
						obj.setRank(rank+3);
					}
				}
			}
		

		List<RestaurantMenuItem> listOfDishesFinal = new ArrayList<RestaurantMenuItem>();
		listOfDishesFinal.clear();
		
		//Collections.sort(listOfDishes1, new MenuItemComparator());
		
		Collections.sort(listOfDishes1);
		
		listOfDishesFinal.addAll(listOfDishes1);
		if(!listOfDishesBad.isEmpty()){
		listOfDishesFinal.add(new RestaurantMenuItem("-----Allergies-----\n-------------------","you would need to make sure the chef knows about your allergy in order to enjoy the following",""));
		listOfDishesFinal.addAll(listOfDishesBad);
		}

		//listOfDishesFinal.addAll(listOfDishesBad);

		return listOfDishesFinal;
		//return listOfDishes2; 
	}
}
