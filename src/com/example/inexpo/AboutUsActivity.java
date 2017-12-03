package com.example.inexpo;

import static com.example.inexpo.CommonUtilities.EVENT_DETAIL_URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.webservice.ServiceHandler;

import model.EventDetail;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import database.DBHelper;

public class AboutUsActivity extends Activity{
	
	DBHelper dbHandler= new DBHelper(this);
	private Toolbar toolbar;
	
	
@SuppressLint("NewApi") @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
//	overridePendingTransition(R.layout.pull_in_right,
//			R.layout.push_out_left);
	setContentView(R.layout.activity_aboutus);
	
	toolbar = (Toolbar) findViewById(R.id.toolbaraboutus);
	toolbar.setTitleTextColor(getResources().getColor(R.color.textcolor));
	toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
	toolbar.setNavigationOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			//onBackPressed();
			finish();
		}
	});

	toolbar.setTitle("About us");
	
/////////////////////////////////
Window window = this.getWindow();
window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
window.setStatusBarColor(this.getResources().getColor(R.color.accent));
///////////////////////////////


    	EventDetail e =new EventDetail();
    	if(dbHandler.isEventDetailAvailable()){
    		e=dbHandler.getEventDetail();
    	}else{
    		if(new ConnectionDetector(this).isConnectingToInternet()){
    			new updateSponsers().execute();
    		}
    	}
    	
    	TextView contactno=(TextView)findViewById(R.id.contactno);
    	TextView email=(TextView)findViewById(R.id.email);
    	TextView date=(TextView)findViewById(R.id.date);
    	TextView time=(TextView)findViewById(R.id.time);
    	TextView eventname=(TextView)findViewById(R.id.eventname);
    	
    	contactno.setText(e.getContactNo());
    	email.setText(e.getContactEmail());
    	eventname.setText(e.getName());
    	date.setText("Visit us on "+e.getStartDate()+" to "+e.getEndDate());
    	time.setText("from "+e.getStartTime()+" to "+e.getEndTime());
    	
}
private class updateSponsers extends AsyncTask<Void, Void, Void> {

	@Override
	protected Void doInBackground(Void... params) {
	
		updateEventTable();	
		return null;
	}
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
					
					dbHandler.insertEvent(item);						
						
		} catch (JSONException e) {				
			e.printStackTrace();
		}
		return true;
	
}
}
