package com.comsats.restaurantandroid.activity;

import com.comsats.restaurantandroid.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Favourite extends ApplicationMenu{
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favourite);
		
		RelativeLayout backBtn = (RelativeLayout) findViewById(R.id.backBtn);
        ImageView homeBtn = (ImageView) findViewById(R.id.homeBtn);
        TextView headerTxt = (TextView) findViewById(R.id.headertext); 
        
        ImageView favBtn = (ImageView) findViewById(R.id.fav);
        ImageView mostOrder = (ImageView) findViewById(R.id.mostOrder);
         
        headerTxt.setText("My Choice");
        

        backBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Favourite.this, Home.class);
        		Favourite.this.startActivity(ourIntent);
			}
		});
        homeBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Favourite.this, Home.class);
				Favourite.this.startActivity(ourIntent);
			}
		});
	  
		favBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread timer = new Thread()
        		{
        			public void run(){
        					try{
        						Intent ourIntent = new Intent(Favourite.this, Loading.class);
        		        		Favourite.this.startActivity(ourIntent);
        		        		sleep(1000);
        						
        					}catch (Exception e) {
								// TODO: handle exception
							}finally{
								finish();
								Intent ourIntent = new Intent(Favourite.this,
										FavouriteDishesList.class);
								Favourite.this.startActivity(ourIntent); 
							}
        			}
        		};
        		
        	timer.start();
        	
			}
		});
		
		mostOrder.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread timer = new Thread()
        		{
        			public void run(){
        					try{
        						Intent ourIntent = new Intent(Favourite.this, Loading.class);
        		        		Favourite.this.startActivity(ourIntent);
        		        		sleep(1000);
        						
        					}catch (Exception e) {
								// TODO: handle exception
							}finally{
								finish();
								Intent ourIntent = new Intent(Favourite.this,MostOrderedDishesList.class);
								Favourite.this.startActivity(ourIntent); 
							}
        			}
        		};
        		
        	timer.start();
			}
		});
	}
	  
	  @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		  finish();
			Intent ourIntent = new Intent(Favourite.this, Home.class);
  		Favourite.this.startActivity(ourIntent);
	}


	}
	  
	 
