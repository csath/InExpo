package com.example.inexpo;

import model.EventDetail;
import model.Sponsers;
import model.Stalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.webservice.ServiceHandler;

import static com.example.inexpo.CommonUtilities.SPONSER_DETAI_URL;
import static com.example.inexpo.CommonUtilities.STALL_DETAIL_URL;
import static com.example.inexpo.CommonUtilities.EVENT_DETAIL_URL;
import database.DBHelper;

public class StartLog extends Activity {

	public final String urlStallData = "http://www.inexpo.hostingsiteforfree.com/dummydata/Test.php";
	public final String urlSponserData = "http://www.inexpo.hostingsiteforfree.com/dummydata/sponsers.php";
	DBHelper dbHandler= new DBHelper(this);
	
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.startlog);
/////////////////////////////////
//Window window = this.getWindow();
//window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//window.setStatusBarColor(this.getResources().getColor(R.color.accent));
///////////////////////////////
	
		
			//getting stall details
			
			if (new ConnectionDetector(this).isConnectingToInternet()) {
				new GetAllDetails().execute();
			} 
					
				

		/* start up the splash screen and main menu in a time delayed thread */

		new Handler().postDelayed(new Thread() {
			@Override
			public void run() {
				Intent mainMenu = new Intent(StartLog.this, MainActivity.class);
				StartLog.this.startActivity(mainMenu);
				StartLog.this.finish();
				overridePendingTransition(R.drawable.fadein, R.drawable.fadeout);

			}
		}, 1000);
	}

	private class GetAllDetails extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
		
			refreshDatabase();	
			return null;
		}
		
		
		private boolean refreshDatabase(){
			ServiceHandler sh = new ServiceHandler();
			
			if (dbHandler.getAllStallDetails().size()==0){
			
			String jsonStr = sh.makeServiceCall(STALL_DETAIL_URL, ServiceHandler.GET);
			try {
				JSONArray response=new JSONArray(jsonStr);
				for (int i = 0; i < response.length(); i++) {					
						JSONObject obj = response
								.getJSONObject(i);						
						Stalls stall = new Stalls();
						stall.setStallId(obj.getInt("id"));
						stall.setStallName(obj.getString("name"));
						stall.setUrl(obj.getString("url"));
						stall.setRating(((Number) obj.get("rating")).doubleValue());
						stall.setDesctiption(obj.getString("description"));
						stall.setImageCover(obj.getString("imagecover"));
						stall.setImageProfile(obj.getString("imageprofile"));
						stall.setCategory(obj.getString("category"));
						stall.setStallEmail(obj.getString("stallemail"));
						stall.setStallContactNo(obj.getString("telephone"));
						stall.setTags(obj.getString("tags"));
						stall.setReviewCount(obj.getInt("noOfreviews"));
						
						if(!dbHandler.isStallAvailable(stall.getStallId())){
							dbHandler.insertStall(stall);
						}else{
							dbHandler.updateStall(stall);
						}
				}			
			} catch (JSONException e) {				
				e.printStackTrace();
			}
			}
			
			if(!dbHandler.isSponserDetailAvailable()){
			
			String jsonSponser = sh.makeServiceCall(SPONSER_DETAI_URL, ServiceHandler.GET);
			try {
				JSONArray response=new JSONArray(jsonSponser);
				for (int i = 0; i < response.length(); i++) {					
						JSONObject obj = response
								.getJSONObject(i);						
						Sponsers item = new Sponsers();
						item.setId(obj.getInt("id"));
						item.setName(obj.getString("name"));
						item.setDesctiption(obj.getString("description"));
						item.setCategory( obj.getString("category"));
						item.setImage(obj.getString("image"));					
							dbHandler.insertSponser(item);
						
				}			
			} catch (JSONException e) {				
				e.printStackTrace();
			}
		}
			if(!dbHandler.isEventDetailAvailable()){
			String jsonEvent = sh.makeServiceCall(EVENT_DETAIL_URL, ServiceHandler.GET);
			try {
						JSONObject obj = new JSONObject(jsonEvent);					
						EventDetail item = new EventDetail();
					
						item.setName(obj.getString("name"));
						item.setStartDate(obj.getString("startdate"));
						item.setEndDate(obj.getString("enddate"));
						item.setContactEmail( obj.getString("contact-email"));
						item.setContactNo( obj.getString("contact_no"));
						item.setVenue(obj.getString("venue"));	
						item.setEndTime(obj.getString("endtime"));
						item.setStartTime(obj.getString("starttime"));
						item.setDescription(obj.getString("description"));
						
						dbHandler.insertEvent(item);						
							
			} catch (JSONException e) {				
				e.printStackTrace();
			}
			}
			
			return true;
		}
	}
}