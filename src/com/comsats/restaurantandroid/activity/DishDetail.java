package com.comsats.restaurantandroid.activity;

import com.comsats.restaurantandroid.Customer;
import com.comsats.restaurantandroid.DishDetails;
import com.comsats.restaurantandroid.R;
import com.comsats.restaurantandroid.store.OrderStore;
import com.comsats.restaurantandroid.util.ImageLoader;
import com.comsats.restaurantandroid.util.URLConnectionReader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class DishDetail extends ApplicationMenu {

	final Context context = this;
	String dish_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dish_detail);
		// top order bar
		RelativeLayout top_bar = (RelativeLayout) findViewById(R.id.top_order_bar);
		TextView top_order_txt = (TextView) findViewById(R.id.text_order);

		top_bar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(DishDetail.this, Order.class);
				DishDetail.this.startActivity(ourIntent);
			}
		});

		if (OrderStore.getCart().isEmpty()) {

		} else {
			top_order_txt.setText(OrderStore.getCart().size()
					+ " item(s) added.");
			top_bar.setVisibility(0);
		}

		String ingredient_list = GetSearchResults();
		
		Bundle gotBasket = getIntent().getExtras();
		
		ImageView favBtn = null;
		ImageView removeFav = null;

		RelativeLayout backBtn = (RelativeLayout) findViewById(R.id.backBtn);
		ImageView homeBtn = (ImageView) findViewById(R.id.homeBtn);
		ImageView orderBtn = (ImageView) findViewById(R.id.add_order);
		
		if(gotBasket.getString("checkDish").equals("fav")){
		removeFav = (ImageView) findViewById(R.id.add_fav);
		removeFav.setImageResource(R.drawable.remove_fav);
		removeFav.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Bundle gotBasket = getIntent().getExtras();
				
				removeFavourite(Integer.parseInt(gotBasket.getString("dishID")));
				finish();
				Intent ourIntent = new Intent(DishDetail.this,
						FavouriteDishesList.class);
				DishDetail.this.startActivity(ourIntent);
			}
		}); // remove favourite button
		}else{
			favBtn = (ImageView) findViewById(R.id.add_fav);
			favBtn.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final Bundle gotBasket = getIntent().getExtras();
					
					AlertDialog.Builder alert = new AlertDialog.Builder(context);

					alert.setTitle("Favourite Dish");
					alert.setMessage("Add this to favourite?");
					
					alert.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String result = addToFavourite(Integer.parseInt(gotBasket.getString("dishID")));
									if(result.equals("success"))
									{
										Toast.makeText(
												context,
													"Dish added",
												Toast.LENGTH_LONG).show();
									}
									else if(result.equals("already"))
									{
										Toast.makeText(
												context,
													"Dish already added to favourites",
												Toast.LENGTH_LONG).show();
									}
									
										}
							});

					alert.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// Canceled.
									dialog.cancel();
								}
							});

					alert.show();
				}
			});
			

		} //else add/remove favourite button
		
		TextView headerTxt = (TextView) findViewById(R.id.headertext);
		headerTxt.setText("Detail");

		// set xml textview
		TextView dish_title = (TextView) findViewById(R.id.dish_title);
		TextView dish_price = (TextView) findViewById(R.id.dish_price);
		TextView dish_discount = (TextView) findViewById(R.id.dish_discount);
		TextView dish_calories = (TextView) findViewById(R.id.dish_calories);
		TextView dish_cook = (TextView) findViewById(R.id.dish_cook_time);
		TextView dish_ingredients = (TextView) findViewById(R.id.dish_ingredients);
		TextView dish_desc = (TextView) findViewById(R.id.dish_desc);
		ImageView dish_image = (ImageView) findViewById(R.id.dish_image);

		
		dish_title.setText(gotBasket.getString("dishName"));
		dish_price.setText("Price: " + gotBasket.getString("dishPrice") + "$");
		dish_discount.setText("Discount: "
				+ gotBasket.getString("dishDiscount") + "%");
		dish_calories.setText("Calories: "
				+ gotBasket.getString("dishCalories"));
		dish_cook.setText("Cooking Time: "
				+ gotBasket.getString("dishCookTime") + " minutes");
		if (ingredient_list == null) {
			dish_ingredients.setText("Ingredients: No Special Ingredient");
		} else {
			dish_ingredients.setText("Ingredients: " + ingredient_list);
		}

		dish_desc.setText(gotBasket.getString("dishDescription"));

		String thumburl = URLConnectionReader.getMediaIP()
				+ "uploads/dish/"
				+ gotBasket.getString("dishImage");
		ImageLoader imageLoader = new ImageLoader(this);
		imageLoader.DisplayImage(thumburl, dish_image);

		LayoutParams params = (LayoutParams) dish_image.getLayoutParams();
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
		dish_image.setLayoutParams(params);

		dish_image.setScaleType(ScaleType.FIT_XY);

		backBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle gotBasket = getIntent().getExtras();

				Bundle basket = new Bundle();
				basket.putString("id", gotBasket.getString("cat_id"));
				basket.putString("name", gotBasket.getString("name"));
				if (gotBasket.getString("checkDish").equals("normal")) {
					finish();
					Intent ourIntent = new Intent(DishDetail.this,
							DishesList.class);
					ourIntent.putExtras(basket);
					DishDetail.this.startActivity(ourIntent);
				} else if (gotBasket.getString("checkDish").equals("hot")) {
					finish();
					Intent ourIntent = new Intent(DishDetail.this,
							HotDishesList.class);
					DishDetail.this.startActivity(ourIntent);
				} else if (gotBasket.getString("checkDish").equals("fav")) {
					finish();
					Intent ourIntent = new Intent(DishDetail.this,
							FavouriteDishesList.class);
					DishDetail.this.startActivity(ourIntent);
				}else if (gotBasket.getString("checkDish").equals("mostOrder")) {
					finish();
					Intent ourIntent = new Intent(DishDetail.this,
							MostOrderedDishesList.class);
					DishDetail.this.startActivity(ourIntent);
				}
				
			}
		});
		homeBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent ourIntent = new Intent(DishDetail.this, Home.class);
				DishDetail.this.startActivity(ourIntent);
			}
		});
		orderBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Bundle gotBasket = getIntent().getExtras();
				final DishDetails obj = new DishDetails();
				obj.setDishID(Integer.parseInt(gotBasket.getString("dishID")));
				obj.setDishCategory(Integer.parseInt(gotBasket
						.getString("dishCategory")));
				obj.setDishName(gotBasket.getString("dishName"));
				obj.setDishDescription(gotBasket.getString("dishDescription"));
				obj.setDishPrice(gotBasket.getString("dishPrice"));
				obj.setDishCalories(gotBasket.getString("dishCalories"));
				obj.setDishDiscount(gotBasket.getString("dishDiscount"));
				obj.setDishCookTime(gotBasket.getString("dishCookTime"));
				obj.setDishImage(gotBasket.getString("dishImage"));
				obj.setDishUnit(gotBasket.getString("dishUnit"));
				obj.setDishStatus(gotBasket.getString("dishStatus"));

				AlertDialog.Builder alert = new AlertDialog.Builder(context);

				alert.setTitle("Quantity");
				alert.setMessage("How Many " + obj.getDishUnit());

				// Set an EditText view to get user input
				final EditText input = new EditText(context);
				input.setInputType(0x00002002);
				input.setText("1");
				alert.setView(input);

				alert.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Toast.makeText(l_Inflater.getContext(),
								// "Item "+input.getText()+" added to your order!",
								// Toast.LENGTH_LONG).show();
								
								if(input.getText().toString().equals("") || input.getText().toString() == null)
								{
									Toast.makeText(context, "Please set quantity again", Toast.LENGTH_LONG).show();
								}
								else
								{
								Float quantity = Float.parseFloat(input
										.getText().toString());
								obj.setQuantity(quantity);
								OrderStore.saveOrderItem(obj.getDishID(), obj);
								Toast.makeText(
										context,
										"Item " + obj.getDishName()
												+ " added to your order!",
										Toast.LENGTH_LONG).show();
								if (gotBasket.getString("checkDish").equals(
										"hot")) {
									Bundle basket = new Bundle();
									basket.putString("checkDish",
											gotBasket.getString("checkDish"));
									basket.putString("dishID",
											gotBasket.getString("dishID"));
									basket.putString("dishCategory",
											gotBasket.getString("dishCategory"));
									basket.putString("dishName",
											gotBasket.getString("dishName"));
									basket.putString(
											"dishDescription",
											gotBasket
													.getString("dishDescription"));
									basket.putString("dishPrice",
											gotBasket.getString("dishPrice"));
									basket.putString("dishCalories",
											gotBasket.getString("dishCalories"));
									basket.putString("dishDiscount",
											gotBasket.getString("dishDiscount"));
									basket.putString("dishCookTime",
											gotBasket.getString("dishCookTime"));
									basket.putString("dishImage",
											gotBasket.getString("dishImage"));
									basket.putString("dishUnit",
											gotBasket.getString("dishUnit"));
									basket.putString("dishStatus",
											gotBasket.getString("dishStatus"));
									finish();
									Intent myIntent = new Intent(
											DishDetail.this, DishDetail.class);
									myIntent.putExtras(basket);
									DishDetail.this.startActivity(myIntent);
								}else if (gotBasket.getString("checkDish").equals(
										"fav")) {
									Bundle basket = new Bundle();
									basket.putString("checkDish",
											gotBasket.getString("checkDish"));
									basket.putString("dishID",
											gotBasket.getString("dishID"));
									basket.putString("dishCategory",
											gotBasket.getString("dishCategory"));
									basket.putString("dishName",
											gotBasket.getString("dishName"));
									basket.putString(
											"dishDescription",
											gotBasket
													.getString("dishDescription"));
									basket.putString("dishPrice",
											gotBasket.getString("dishPrice"));
									basket.putString("dishCalories",
											gotBasket.getString("dishCalories"));
									basket.putString("dishDiscount",
											gotBasket.getString("dishDiscount"));
									basket.putString("dishCookTime",
											gotBasket.getString("dishCookTime"));
									basket.putString("dishImage",
											gotBasket.getString("dishImage"));
									basket.putString("dishUnit",
											gotBasket.getString("dishUnit"));
									basket.putString("dishStatus",
											gotBasket.getString("dishStatus"));
									finish();
									Intent myIntent = new Intent(
											DishDetail.this, DishDetail.class);
									myIntent.putExtras(basket);
									DishDetail.this.startActivity(myIntent);
								}else if (gotBasket.getString("checkDish").equals(
										"mostOrder")) {
									Bundle basket = new Bundle();
									basket.putString("checkDish",
											gotBasket.getString("checkDish"));
									basket.putString("dishID",
											gotBasket.getString("dishID"));
									basket.putString("dishCategory",
											gotBasket.getString("dishCategory"));
									basket.putString("dishName",
											gotBasket.getString("dishName"));
									basket.putString(
											"dishDescription",
											gotBasket
													.getString("dishDescription"));
									basket.putString("dishPrice",
											gotBasket.getString("dishPrice"));
									basket.putString("dishCalories",
											gotBasket.getString("dishCalories"));
									basket.putString("dishDiscount",
											gotBasket.getString("dishDiscount"));
									basket.putString("dishCookTime",
											gotBasket.getString("dishCookTime"));
									basket.putString("dishImage",
											gotBasket.getString("dishImage"));
									basket.putString("dishUnit",
											gotBasket.getString("dishUnit"));
									basket.putString("dishStatus",
											gotBasket.getString("dishStatus"));
									finish();
									Intent myIntent = new Intent(
											DishDetail.this, DishDetail.class);
									myIntent.putExtras(basket);
									DishDetail.this.startActivity(myIntent);
								}else if (gotBasket.getString("checkDish")
										.equals("normal")) {
									Bundle basket = new Bundle();
									basket.putString("id",
											gotBasket.getString("cat_id"));
									basket.putString("cat_id",
											gotBasket.getString("cat_id"));
									basket.putString("name",
											gotBasket.getString("name"));
									basket.putString("checkDish",
											gotBasket.getString("checkDish"));
									basket.putString("dishID",
											gotBasket.getString("dishID"));
									basket.putString("dishCategory",
											gotBasket.getString("dishCategory"));
									basket.putString("dishName",
											gotBasket.getString("dishName"));
									basket.putString(
											"dishDescription",
											gotBasket
													.getString("dishDescription"));
									basket.putString("dishPrice",
											gotBasket.getString("dishPrice"));
									basket.putString("dishCalories",
											gotBasket.getString("dishCalories"));
									basket.putString("dishDiscount",
											gotBasket.getString("dishDiscount"));
									basket.putString("dishCookTime",
											gotBasket.getString("dishCookTime"));
									basket.putString("dishImage",
											gotBasket.getString("dishImage"));
									basket.putString("dishUnit",
											gotBasket.getString("dishUnit"));
									basket.putString("dishStatus",
											gotBasket.getString("dishStatus"));
									finish();
									Intent myIntent = new Intent(
											DishDetail.this, DishDetail.class);
									myIntent.putExtras(basket);
									DishDetail.this.startActivity(myIntent);
								}
								}
							}
						});

				alert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
								dialog.cancel();
							}
						});

				alert.show();
			}
		});
		
		
	} // On Create Bundle

	public String GetSearchResults() {
		String result = null;
		Bundle gotBasket = getIntent().getExtras();
		dish_id = gotBasket.getString("dishID");

		String content = null;
		try {
			content = URLConnectionReader.getText(URLConnectionReader.getIP()
					+ "get_all_ingredients.jsp?id="
					+ dish_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (content.length() > 0) {
						result = content;
		} else {
			
		}
		return result;
	}
	
	public String addToFavourite(int id) {
		String result = null;
		String content = null;
		try {
			content = URLConnectionReader.getText(URLConnectionReader.getIP()
					+ "add_to_favourite.jsp?id="
					+ id+"&customerID="+Customer.getUuid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

					result = content;
		return result;
	}
	
	public String removeFavourite(int id) {
		String result = null;
		String content = null;
		try {
			content = URLConnectionReader.getText(URLConnectionReader.getIP()
					+ "remove_favourite.jsp?id="
					+ id+"&customerID="+Customer.getUuid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

					result = content;
		return result;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Bundle gotBasket = getIntent().getExtras();

		Bundle basket = new Bundle();
		basket.putString("id", gotBasket.getString("cat_id"));
		basket.putString("name", gotBasket.getString("name"));
		if (gotBasket.getString("checkDish").equals("normal")) {
			finish();
			Intent ourIntent = new Intent(DishDetail.this,
					DishesList.class);
			ourIntent.putExtras(basket);
			DishDetail.this.startActivity(ourIntent);
		} else if (gotBasket.getString("checkDish").equals("hot")) {
			finish();
			Intent ourIntent = new Intent(DishDetail.this,
					HotDishesList.class);
			DishDetail.this.startActivity(ourIntent);
		} else if (gotBasket.getString("checkDish").equals("fav")) {
			finish();
			Intent ourIntent = new Intent(DishDetail.this,
					FavouriteDishesList.class);
			DishDetail.this.startActivity(ourIntent);
		}else if (gotBasket.getString("checkDish").equals("mostOrder")) {
			finish();
			Intent ourIntent = new Intent(DishDetail.this,
					MostOrderedDishesList.class);
			DishDetail.this.startActivity(ourIntent);
		}
	}

}
