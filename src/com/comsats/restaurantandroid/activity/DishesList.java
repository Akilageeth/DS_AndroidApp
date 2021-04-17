package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;

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


public class DishesList extends ApplicationMenu implements TextWatcher {

	public String cat_id,name;
	EditText searchDish;
	ListView dishesList;
	DishListBaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dishes_main);
		
        //  top order bar
        RelativeLayout top_bar = (RelativeLayout) findViewById(R.id.top_order_bar);
        TextView top_order_txt = (TextView) findViewById(R.id.text_order);
        
        top_bar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(DishesList.this, Order.class);
        		DishesList.this.startActivity(ourIntent);
			}
		});
        
        if(OrderStore.getCart().isEmpty())
        {
        	
        }
        else
        {
        	top_order_txt.setText(OrderStore.getCart().size()+" item(s) added.");
        	top_bar.setVisibility(0);
        }
        

		
		ArrayList<DishDetails> dish_details = GetSearchResults();
		
		Bundle gotBasket = getIntent().getExtras();
		name = gotBasket.getString("name");
		
		RelativeLayout backBtn = (RelativeLayout) findViewById(R.id.backBtn);
        ImageView homeBtn = (ImageView) findViewById(R.id.homeBtn);
        TextView headerTxt = (TextView) findViewById(R.id.headertext); 
        
        dishesList = (ListView) findViewById(R.id.dishesList);
        adapter = new DishListBaseAdapter(this, dish_details,"normal",gotBasket.getString("name"),gotBasket.getString("id")); 
        
        headerTxt.setText(name);
        
        backBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(DishesList.this, CategoriesList.class);
        		DishesList.this.startActivity(ourIntent);
			}
		});
        homeBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(DishesList.this, Home.class);
				DishesList.this.startActivity(ourIntent);
			}
		});
        
        if(dish_details.size() > 0)
        {
        	  
              dishesList.setAdapter(adapter);
        }
        else
        {
        	Toast.makeText(getApplicationContext(), "No Dishes Found!", Toast.LENGTH_LONG).show();
        }
      
        
        dishesList.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
        		Object o = dishesList.getItemAtPosition(position);
				DishDetails obj_itemDetails = (DishDetails)o;
            	final Bundle basket = new Bundle();
            	basket.putString("checkDish", "normal");
    			basket.putString("cat_id", cat_id);
    			basket.putString("name", name);
    			basket.putString("dishID", String.valueOf(obj_itemDetails.getDishID()));
    			basket.putString("dishCategory", String.valueOf(obj_itemDetails.getDishCategory()));
    			basket.putString("dishName", obj_itemDetails.getDishName());
    			basket.putString("dishDescription", obj_itemDetails.getDishDescription());
    			basket.putString("dishPrice", obj_itemDetails.getDishPrice());
    			basket.putString("dishCalories", obj_itemDetails.getDishCalories());
    			basket.putString("dishDiscount", obj_itemDetails.getDishDiscount());
    			basket.putString("dishCookTime", obj_itemDetails.getDishCookTime());
    			basket.putString("dishImage", obj_itemDetails.getDishImage());
    			basket.putString("dishUnit", obj_itemDetails.getDishUnit());
    			basket.putString("dishStatus", obj_itemDetails.getDishStatus());
    			
        		Thread timer = new Thread()
        		{
        			public void run(){
        					try{
        						Intent ourIntent = new Intent(DishesList.this, Loading.class);
        		        		DishesList.this.startActivity(ourIntent);
        		        		sleep(1000);
        						
        					}catch (Exception e) {
								// TODO: handle exception
							}finally{
								try {
									@SuppressWarnings("rawtypes")
									Class ourClass = Class.forName("com.comsats.restaurantandroid.activity.DishDetail");
									finish();
									Intent ourIntent = new Intent(DishesList.this, ourClass);
									ourIntent.putExtras(basket);
									DishesList.this.startActivity(ourIntent);
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
							}
        			}
        		};
        	timer.start();
        		
        	}
        }); 
		
        searchDish = (EditText) findViewById(R.id.search_dish);
        searchDish.addTextChangedListener(this);
	}
	
	public void onDestroy()
    {
        dishesList.setAdapter(null);
        super.onDestroy();
    }
	
	public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
        // TODO Auto-generated method stub
 
    }
 
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
 
    }
 
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
        String text = searchDish.getText().toString().toLowerCase();
        adapter.filter(text);
    }

	public ArrayList<DishDetails> GetSearchResults(){
    	ArrayList<DishDetails> results = new ArrayList<DishDetails>();
    	Bundle gotBasket = getIntent().getExtras();
		cat_id = gotBasket.getString("id");
    	
    	String content = null;
		try {
			content = URLConnectionReader.getText(URLConnectionReader.getIP()+"get_all_dishes.jsp?id="+cat_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(content.length() > 0)
		{
			String [] allURLs = content.split(";");	
			DishDetails item_details;
	        for (int i=0; i<allURLs.length; i++) {
	        	item_details = new DishDetails();
	        	String [] detail = allURLs[i].split("---");
	        	item_details.setDishID(Integer.parseInt(detail[0]));
	        	item_details.setDishCategory(Integer.parseInt(detail[1]));
	        	item_details.setDishName(detail[2]);
	        	item_details.setDishDescription(detail[3]);
	        	item_details.setDishPrice(detail[4]);
	        	item_details.setDishCalories(detail[5]);
	        	item_details.setDishDiscount(detail[6]);
	        	item_details.setDishCookTime(detail[7]);
	        	item_details.setDishImage(detail[8]);
	        	item_details.setDishStatus(detail[9]);
	        	item_details.setDishUnit(detail[10]);
	        	
	        	results.add(item_details);
	        
			}	
		}
		else
		{
		// Toast.makeText(getApplicationContext(), "nothing", Toast.LENGTH_LONG).show();
		}
    	return results;
    }
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		Intent ourIntent = new Intent(DishesList.this, CategoriesList.class);
		DishesList.this.startActivity(ourIntent);
	}
        
        
        }
