package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;

import com.comsats.restaurantandroid.ItemDetails;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.store.OrderStore;
import com.comsats.restaurantandroid.util.URLConnectionReader;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CategoriesList extends ApplicationMenu implements TextWatcher {
	/** Called when the activity is first created. */
	EditText searchCategory;
	ListView lv1;
	CategoriesListBaseAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ArrayList<ItemDetails> image_details = GetSearchResults();
        
        RelativeLayout backBtn = (RelativeLayout) findViewById(R.id.backBtn);
        ImageView homeBtn = (ImageView) findViewById(R.id.homeBtn);
        TextView headerTxt = (TextView) findViewById(R.id.headertext); 
        
        headerTxt.setText("Dish Categories");
        //  top order bar
        RelativeLayout top_bar = (RelativeLayout) findViewById(R.id.top_order_bar);
        TextView top_order_txt = (TextView) findViewById(R.id.text_order);
        
        top_bar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(CategoriesList.this, Order.class);
        		CategoriesList.this.startActivity(ourIntent);
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
        

        
        backBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(CategoriesList.this, Home.class);
        		CategoriesList.this.startActivity(ourIntent);
			}
		});
        homeBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(CategoriesList.this, Home.class);
        		CategoriesList.this.startActivity(ourIntent);
			}
		});
        
        lv1 = (ListView) findViewById(R.id.listV_main);
        adapter = new CategoriesListBaseAdapter(this, image_details);
        if(image_details.size() > 0)
        {
        	lv1.setAdapter(adapter);
        }
        else
        {
        	Toast.makeText(getApplicationContext(), "No Category Found!", Toast.LENGTH_LONG).show();
        }
        
        
        
        lv1.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) {
        		Object o = lv1.getItemAtPosition(position);
            	ItemDetails obj_itemDetails = (ItemDetails)o;
            	final Bundle basket = new Bundle();
    			basket.putString("id", String.valueOf(obj_itemDetails.getCatId()));
    			basket.putString("name", obj_itemDetails.getCatName());
        		
        		Thread timer = new Thread()
        		{
        			public void run(){
        					try{
        						Intent ourIntent = new Intent(CategoriesList.this, Loading.class);
        		        		CategoriesList.this.startActivity(ourIntent);
        		        		sleep(1000);
        						
        					}catch (Exception e) {
								// TODO: handle exception
							}finally{
								try {
									@SuppressWarnings("rawtypes")
									Class ourClass = Class.forName("com.comsats.restaurantandroid.activity.DishesList");
									finish();
									Intent ourIntent = new Intent(CategoriesList.this, ourClass);
									ourIntent.putExtras(basket);
									CategoriesList.this.startActivity(ourIntent);
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
        
        searchCategory = (EditText) findViewById(R.id.search_category);
        searchCategory.addTextChangedListener(this);
    }
    
	public void onDestroy()
    {
        lv1.setAdapter(null);
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
        String text = searchCategory.getText().toString().toLowerCase();
        adapter.filter(text);
    }
    
    private ArrayList<ItemDetails> GetSearchResults(){
    	ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();
    	
    	
    	String content = null;
		try {
			content = URLConnectionReader.getText(URLConnectionReader.getIP()+"get_all_categories.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(content.length() > 0)
		{
		String [] allURLs = content.split(";");
		ItemDetails item_details;
        for (int i=0; i<allURLs.length; i++) {
        	item_details = new ItemDetails();
        	String [] detail = allURLs[i].split("---");
        	item_details.setCatId(Integer.parseInt(detail[0]));
        	item_details.setCatName(detail[1]);
        	item_details.setCatImage(detail[2]);
        	item_details.setCatStatus(detail[3]);
        	results.add(item_details);
        }
		}
		else
		{
			
		}
    	return results;
    }
    @Override
	public void onBackPressed() {
    	finish();
		Intent ourIntent = new Intent(CategoriesList.this, Home.class);
		CategoriesList.this.startActivity(ourIntent);
	}
}