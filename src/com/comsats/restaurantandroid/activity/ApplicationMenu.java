package com.comsats.restaurantandroid.activity;

import com.comsats.restaurantandroid.R;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ApplicationMenu extends Activity{
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {  
	   switch (item.getItemId()) {
	   case R.id.menuProfile:
		      doProfile();
		      return true;
	   case R.id.menuAbout:
	      doAbout();
	      return true;
	   case R.id.menuExit:
		      doExit();
		      return true;
	   }
	     
	   return true;
	}

	private void doProfile() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(), PrefsActivity.class);
		startActivity(intent);
	}

	private void doExit() {
		// TODO Auto-generated method stub
		finish();
		  Intent intent = new Intent(getApplicationContext(), Home.class);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		    intent.putExtra("EXIT", true);
		    startActivity(intent);
		
	}

	private void doAbout(){
	   //Show the about screen
		finish();
		Intent intent = new Intent(getApplicationContext(), About.class);
		startActivity(intent);
	}
	
}
