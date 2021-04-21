package com.comsats.restaurantandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comsats.restaurantandroid.DishDetails;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.store.OrderStore;
import com.comsats.restaurantandroid.util.URLConnectionReader;

import java.util.ArrayList;


public class Hotels extends ApplicationMenu implements TextWatcher {

	public String cat_id,name;
	EditText searchDish;
	ListView dishesList;
	DishListBaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotels);


		RelativeLayout backBtn = (RelativeLayout) findViewById(R.id.backBtn);
		ImageView homeBtn = (ImageView) findViewById(R.id.homeBtn);
		TextView headerTxt = (TextView) findViewById(R.id.headertext);

		//  top order bar
		TextView top_hotel_txt = (TextView) findViewById(R.id.text_order);

		backBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Hotels.this, CategoriesList.class);
				Hotels.this.startActivity(ourIntent);
			}
		});
		homeBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(Hotels.this, Home.class);
				Hotels.this.startActivity(ourIntent);
			}
		});

	}

	@Override
	public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	}

	@Override
	public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	}

	@Override
	public void afterTextChanged(Editable editable) {

	}
}
