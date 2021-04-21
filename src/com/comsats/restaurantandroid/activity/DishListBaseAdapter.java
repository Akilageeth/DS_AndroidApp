package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

import com.comsats.restaurantandroid.DishDetails;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.store.OrderStore;
import com.comsats.restaurantandroid.util.ImageLoader;
import com.comsats.restaurantandroid.util.URLConnectionReader;

public class DishListBaseAdapter extends BaseAdapter {
	private ArrayList<DishDetails> itemDetailsArrayList = null;;
	private ArrayList<DishDetails> itemDetailsArrayOriginal = null;
	
	 public ImageLoader imageLoader; 
	private LayoutInflater l_Inflater;
	private String activityType;
	private String catName;
	private String catID;

	public DishListBaseAdapter(Context context, ArrayList<DishDetails> results, String type) {
		itemDetailsArrayList = results;
		l_Inflater = LayoutInflater.from(context);
		activityType = type;
		this.itemDetailsArrayOriginal = new ArrayList<DishDetails>();
        this.itemDetailsArrayOriginal.addAll(results);
        imageLoader=new ImageLoader(context);
	}
	public DishListBaseAdapter(Context context, ArrayList<DishDetails> results, String type, String name, String id) {
		itemDetailsArrayList = results;
		l_Inflater = LayoutInflater.from(context);
		activityType = type;
		catName = name;
		catID = id;
		this.itemDetailsArrayOriginal = new ArrayList<DishDetails>();
        this.itemDetailsArrayOriginal.addAll(results);
        imageLoader=new ImageLoader(context);
	}
	
	public int getCount() {
		return itemDetailsArrayList.size();
	}

	public Object getItem(int position) {
		return itemDetailsArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.dish, null);
			holder = new ViewHolder();
			holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
			holder.txt_itemPrice = (TextView) convertView.findViewById(R.id.price);
			holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);
			//holder.quickAdd = (ImageView) convertView.findViewById(R.id.quickAdd);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		final Integer dish_id = itemDetailsArrayList.get(position).getDishID();
		final DishDetails obj = itemDetailsArrayList.get(position);

		holder.txt_itemName.setText(itemDetailsArrayList.get(position).getDishName());
		holder.txt_itemPrice.setText(itemDetailsArrayList.get(position).getDishPrice() + " $");
		String thumburl = URLConnectionReader.getMediaIP()+"uploads/dish/" + itemDetailsArrayList.get(position).getDishImage();
		imageLoader.DisplayImage(thumburl, holder.itemImage);

		holder.itemImage.setScaleType(ScaleType.FIT_XY);

				return convertView;
	}

	static class ViewHolder {
		TextView txt_itemName;
		TextView txt_itemPrice;
		ImageView itemImage;
		ImageView quickAdd;
		
	}

	   public void filter(String charText) {
	        charText = charText.toLowerCase();
	        itemDetailsArrayList.clear();
	        if (charText.length() == 0) {
	            itemDetailsArrayList.addAll(itemDetailsArrayOriginal);
	        } else {
	            for (DishDetails var : itemDetailsArrayOriginal) {
	                if (var.getDishName().toLowerCase().contains(charText)) {
	                	itemDetailsArrayList.add(var);
	                }
	            }
	        }
	        notifyDataSetChanged();
	    }
}
