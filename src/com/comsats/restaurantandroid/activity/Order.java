package com.comsats.restaurantandroid.activity;

import com.comsats.restaurantandroid.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class Order extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order);
		
		
		
		RelativeLayout backBtn = (RelativeLayout) findViewById(R.id.backBtn);
        ImageView homeBtn = (ImageView) findViewById(R.id.homeBtn);
        TextView headerTxt = (TextView) findViewById(R.id.headertext); 
        
        
        headerTxt.setText("My Order");
        
       
        
        backBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Order.this, Home.class);
        		Order.this.startActivity(ourIntent);
			}
		});
        homeBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Order.this, Home.class);
				Order.this.startActivity(ourIntent);
			}
		});
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost(); // The activity TabHost
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, OrderBasket.class);
        spec = tabHost.newTabSpec("orderBasket")
        .setIndicator("My Order", res.getDrawable(R.drawable.my_order))
        .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs

        intent = new Intent().setClass(this, RunningOrder.class);
        spec = tabHost.newTabSpec("runningOrder")
        .setIndicator("Running Order", res.getDrawable(R.drawable.running_order))
        .setContent(intent);
        tabHost.addTab(spec);


        intent = new Intent().setClass(this, OrderHistoryTab.class);
        spec = tabHost
        .newTabSpec("orderHistory")
        .setIndicator("Order History",
        res.getDrawable(R.drawable.history_icon))
        .setContent(intent);
        tabHost.addTab(spec);

        //set tab which one you want open first time 0 or 1 or 2
        tabHost.setCurrentTab(0);


	}
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.menu, menu);
	        return true;
	    }
	 
	
	
}
