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
			holder.quickAdd = (ImageView) convertView.findViewById(R.id.quickAdd);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		final Integer dish_id = itemDetailsArrayList.get(position).getDishID();
		final DishDetails obj = itemDetailsArrayList.get(position);
		holder.quickAdd.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final AlertDialog.Builder alert = new AlertDialog.Builder(l_Inflater.getContext());

				alert.setTitle("Quantity");
				alert.setMessage("How Many " + itemDetailsArrayList.get(position).getDishUnit());

				// Set an EditText view to get user input 
				final EditText input = new EditText(l_Inflater.getContext());
				input.setInputType(0x00002002);
				
				input.setText("1");
				alert.setView(input);

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					//Toast.makeText(l_Inflater.getContext(), "Item "+input.getText()+" added to your order!", Toast.LENGTH_LONG).show();
					if(input.getText().toString().equals("") || input.getText().toString() == null)
					{
						Toast.makeText(l_Inflater.getContext(), "Please set quantity again", Toast.LENGTH_LONG).show();
					}
					else
					{
						
					
					Float quantity = Float.parseFloat(input.getText().toString());
					obj.setQuantity(quantity);
					OrderStore.saveOrderItem(dish_id, obj);
					Toast.makeText(l_Inflater.getContext(), "Item "+obj.getDishName()+" added to your order!", Toast.LENGTH_LONG).show();

					if(activityType.equals("hot"))
					{
						((Activity)l_Inflater.getContext()).finish();
					Intent myIntent = new Intent(l_Inflater.getContext(), HotDishesList.class);

					l_Inflater.getContext().startActivity(myIntent);
					}else if(activityType.equals("fav"))
					{
						((Activity)l_Inflater.getContext()).finish();
						Intent myIntent = new Intent(l_Inflater.getContext(), FavouriteDishesList.class);

						l_Inflater.getContext().startActivity(myIntent);

					}else if(activityType.equals("mostOrder"))
					{
						((Activity)l_Inflater.getContext()).finish();
						Intent myIntent = new Intent(l_Inflater.getContext(), MostOrderedDishesList.class);

						l_Inflater.getContext().startActivity(myIntent);

					}else if(activityType.equals("normal"))
					{
						((Activity)l_Inflater.getContext()).finish();
						Bundle basket = new Bundle();
						basket.putString("id", catID);
						basket.putString("name", catName);
						Intent myIntent = new Intent(l_Inflater.getContext(), DishesList.class);
						myIntent.putExtras(basket);
						l_Inflater.getContext().startActivity(myIntent);
					} 
					
					}
				  }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface dialog, int whichButton) {
				    // Canceled.
					  dialog.cancel();
				  }
				});

				alert.show();
				
				//OrderStore.saveOrderItem(dish_id, obj);
				//Toast.makeText(l_Inflater.getContext(), "Item "+obj.getDishName()+" added to your order!", Toast.LENGTH_LONG).show();

				//Intent myIntent = new Intent(l_Inflater.getContext(), HotDishesList.class);

				//l_Inflater.getContext().startActivity(myIntent);
				
								}
		});
		
		holder.txt_itemName.setText(itemDetailsArrayList.get(position).getDishName());
		holder.txt_itemPrice.setText(itemDetailsArrayList.get(position).getDishPrice() + " $");
		String thumburl = URLConnectionReader.getMediaIP()+"uploads/dish/" + itemDetailsArrayList.get(position).getDishImage();
		imageLoader.DisplayImage(thumburl, holder.itemImage);
		//LayoutParams params = (LayoutParams) holder.itemImage.getLayoutParams();
	//	params.width = 150;
	//	params.height = 98;
		
		// existing height is ok as is, no need to edit it
	//	holder.itemImage.setLayoutParams(params);
		
		holder.itemImage.setScaleType(ScaleType.FIT_XY);



		
		/*try {
			String thumburl = "http://"+URLConnectionReader.getIP()+":8080/RestAutomationAdmin/uploads/dish/" + itemDetailsArrayList.get(position).getDishImage();
			Drawable drawable = LoadImageFromWebOperations(thumburl);
			
			holder.itemImage.setImageDrawable(drawable);
			
			
			LayoutParams params = (LayoutParams) holder.itemImage.getLayoutParams();
			params.width = 150;
			params.height = 98;
			
			// existing height is ok as is, no need to edit it
			holder.itemImage.setLayoutParams(params);
			
			holder.itemImage.setScaleType(ScaleType.FIT_XY);



		} catch (OutOfMemoryError e) {
		    System.gc();
		    String thumburl = "http://"+URLConnectionReader.getIP()+":8080/RestAutomationAdmin/uploads/dish/" + itemDetailsArrayList.get(position).getDishImage();
			Drawable drawable = LoadImageFromWebOperations(thumburl);
			
			holder.itemImage.setImageDrawable(drawable);
			
			
			LayoutParams params = (LayoutParams) holder.itemImage.getLayoutParams();
			params.width = 150;
			params.height = 98;
			
			// existing height is ok as is, no need to edit it
			holder.itemImage.setLayoutParams(params);
			
			holder.itemImage.setScaleType(ScaleType.FIT_XY);



		}*/
				return convertView;
	}

	static class ViewHolder {
		TextView txt_itemName;
		TextView txt_itemPrice;
		ImageView itemImage;
		ImageView quickAdd;
		
	}
	/**
     * Filter
     * @author 9Android.net
     *
     */
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
