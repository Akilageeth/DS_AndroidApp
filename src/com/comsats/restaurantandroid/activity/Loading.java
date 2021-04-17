package com.comsats.restaurantandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import com.comsats.restaurantandroid.R;


public class Loading extends Activity{
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.loading);
	       	      
	    }
	 
	    
	    @Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			finish();
		}
}
