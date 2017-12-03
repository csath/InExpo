package database;

import static com.example.inexpo.CommonUtilities.EVENT_DETAIL_URL;
import static com.example.inexpo.CommonUtilities.SPONSER_DETAI_URL;
import static com.example.inexpo.CommonUtilities.STALL_DETAIL_URL;
import model.EventDetail;
import model.Sponsers;
import model.Stalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.webservice.ServiceHandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

public class UpdateHelper {
	
	private Context context;
	
	DBHelper dbHandler= new DBHelper(context);
	
	public UpdateHelper(Context context){
		this.context=context;
	}
	
	public void executeUpdate(int operationCode){
		if(operationCode==6){
			new updateStalls().execute();
		}else if(operationCode==7){
			new updateEvents().execute();
		}else if(operationCode==8){
			new updateSponsers().execute();
		}
			
	}
	public void updateAll(){
		new updateStalls().execute();
		new updateEvents().execute();
		new updateSponsers().execute();
	}
	
	private class updateStalls extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
		
			updateStallTable();	
			return null;
		}
		
		
		private boolean updateStallTable(){
			ServiceHandler sh = new ServiceHandler();	
			
			
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
			return true;			
			}
						
		}
	public Bitmap StringToBitMap(String encodedString){
	     try{
	       byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
	       Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
	       return bitmap;
	     }catch(Exception e){
	       e.getMessage();
	       return null;
	     }
	      }
	
	private class updateEvents extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
		
			updateEventTable();	
			return null;
		}
		
		
		private boolean updateEventTable(){
			ServiceHandler sh = new ServiceHandler();
			
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
							
							dbHandler.removeEvent();
							dbHandler.insertEvent(item);						
								
				} catch (JSONException e) {				
					e.printStackTrace();
				}
				return true;
			
		}
	}
	private class updateSponsers extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
		
			updateSponserTable();	
			return null;
		}
		
		
		private boolean updateSponserTable(){
			ServiceHandler sh = new ServiceHandler();
			
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
						
						if(!dbHandler.isSponserAvailable(item.getId())){
							dbHandler.insertSponser(item);
						}else{
							dbHandler.updateSponser(item);
						}
						
						
				}			
			} catch (JSONException e) {				
				e.printStackTrace();
			}
			return true;
			
		}
		}
}
