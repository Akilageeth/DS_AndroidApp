package com.comsats.restaurantandroid.activity;

import com.comsats.restaurantandroid.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Contact extends ApplicationMenu{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		
		RelativeLayout backBtn = (RelativeLayout) findViewById(R.id.backBtn);
        ImageView homeBtn = (ImageView) findViewById(R.id.homeBtn);
        TextView headerTxt = (TextView) findViewById(R.id.headertext); 
        
        headerTxt.setText("Contact us");
        
        backBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Contact.this, Home.class);
        		Contact.this.startActivity(ourIntent);
			}
		});
        homeBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Contact.this, Home.class);
				Contact.this.startActivity(ourIntent);
			}
		});
	}

	public void onBackPressed() {
		finish();
		Intent ourIntent = new Intent(Contact.this, Home.class);
		Contact.this.startActivity(ourIntent);
	}
}
