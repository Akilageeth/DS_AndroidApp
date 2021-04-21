package com.comsats.restaurantandroid.activity;

import java.util.ArrayList;

import com.comsats.restaurantandroid.ItemDetails;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.util.ImageLoader;
import com.comsats.restaurantandroid.util.URLConnectionReader;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class CategoriesListBaseAdapter extends BaseAdapter {
	private static ArrayList<ItemDetails> itemDetailsArrayList;
    private ArrayList<ItemDetails> itemDetailsArrayOriginal;
	
    public ImageLoader imageLoader; 
    
	private LayoutInflater l_Inflater;

	public CategoriesListBaseAdapter(Context context, ArrayList<ItemDetails> results) {
		itemDetailsArrayList = results;
		l_Inflater = LayoutInflater.from(context);
		this.itemDetailsArrayOriginal = new ArrayList<ItemDetails>();
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

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.item_details_view, null);
			holder = new ViewHolder();
			holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
			holder.itemImage = (ImageView) convertView.findViewById(R.id.photo);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_itemName.setText(itemDetailsArrayList.get(position).getCatName());
		String thumburl = URLConnectionReader.getMediaIP()+"uploads/category/" + itemDetailsArrayList.get(position).getCatImage();
		imageLoader.DisplayImage(thumburl, holder.itemImage);
		

		
		holder.itemImage.setScaleType(ScaleType.FIT_XY);

		
		return convertView;
	}

	static class ViewHolder {
		TextView txt_itemName;
		ImageView itemImage;
	}

    public void filter(String charText) {
        charText = charText.toLowerCase();
        itemDetailsArrayList.clear();
        if (charText.length() == 0) {
            itemDetailsArrayList.addAll(itemDetailsArrayOriginal);
        } else {
            for (ItemDetails var : itemDetailsArrayOriginal) {
                if (var.getCatName().toLowerCase().contains(charText)) {
                	itemDetailsArrayList.add(var);
                }
            }
        }
        notifyDataSetChanged();
    }

	
}
