package com.comsats.restaurantandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.util.ImageLoader;
import com.comsats.restaurantandroid.util.URLConnectionReader;

public class Greeting extends ApplicationMenu{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.greeting);
		
		Bundle gotBasket = getIntent().getExtras();
		
		RelativeLayout backBtn = (RelativeLayout) findViewById(R.id.backBtn);
        ImageView homeBtn = (ImageView) findViewById(R.id.homeBtn);
        TextView headerTxt = (TextView) findViewById(R.id.headertext);
        
        ImageView greetingBanner = (ImageView) findViewById(R.id.greetingBanner);
        TextView greetingDescription = (TextView) findViewById(R.id.descriptionTextView);
        
        
        headerTxt.setText(gotBasket.getString("title"));
        greetingDescription.setText(gotBasket.getString("description"));
        
        String thumburl = URLConnectionReader.getMediaIP()
				+ "uploads/greeting/"
				+ gotBasket.getString("banner");
		
        ImageLoader imageLoader = new ImageLoader(this);
        imageLoader.DisplayImage(thumburl, greetingBanner);
      //  Drawable drawable = LoadImageFromWebOperations(thumburl);
		
	//	greetingBanner.setImageDrawable(drawable);
		LayoutParams params = (LayoutParams) greetingBanner.getLayoutParams();
		Display mDisplay= this.getWindowManager().getDefaultDisplay();
		//params.width = 480;
		params.width = mDisplay.getWidth();
		//params.height = 300;
		if(mDisplay.getWidth() > 480)
		{
		params.height = 400;
		}else
		{
			params.height = 300;
		}

		// existing height is ok as is, no need to edit it
		greetingBanner.setLayoutParams(params);

		greetingBanner.setScaleType(ScaleType.FIT_XY);

        

        backBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Greeting.this, Home.class);
        		Greeting.this.startActivity(ourIntent);
			}
		});
        homeBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Greeting.this, Home.class);
				Greeting.this.startActivity(ourIntent);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		Intent ourIntent = new Intent(Greeting.this, Home.class);
		Greeting.this.startActivity(ourIntent);
	}
}
