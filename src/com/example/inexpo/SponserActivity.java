package com.example.inexpo;

import static com.example.inexpo.CommonUtilities.SPONSER_DETAI_URL;

import java.util.List;

import model.Sponsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.SponserGridAdaptor;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.Toast;

import com.example.webservice.ServiceHandler;

import database.DBHelper;

public class SponserActivity extends Activity{
	
	private GridView gridView;
	private static SponserGridAdaptor adapter;
	DBHelper dbHandler= new DBHelper(this);
	private static List<Sponsers>  SponserList;
	private Toolbar toolbar;
	
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_sponser);
				
				toolbar = (Toolbar) findViewById(R.id.toolbarsponser);
				toolbar.setTitleTextColor(getResources().getColor(R.color.textcolor));
				toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
				toolbar.setNavigationOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						//onBackPressed();
						finish();
					}
				});

				toolbar.setTitle("Sponsers");
				
		/////////////////////////////////
		Window window = this.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		window.setStatusBarColor(this.getResources().getColor(R.color.accent));
		///////////////////////////////
		
		if(!dbHandler.isSponserDetailAvailable()){
			if (new ConnectionDetector(this).isConnectingToInternet()){
				new GetSponserDetails().execute();
								
			}else{
				Toast.makeText(this,"No Internet connection", Toast.LENGTH_SHORT).show();
			}
		}
		
				
		
		
		SponserList=dbHandler.getSponserDetails();		
		
		gridView = (GridView) findViewById(R.id.gridView);
		adapter=new SponserGridAdaptor(this, SponserList);
		gridView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
			}
private class GetSponserDetails extends AsyncTask<Void, Void, Void> {

	@Override
	protected Void doInBackground(Void... params) {
	
		refreshDatabase();	
		
		return null;
	}
	
	
	private boolean refreshDatabase(){
		ServiceHandler sh = new ServiceHandler();
		String jsonStr = sh.makeServiceCall(SPONSER_DETAI_URL, ServiceHandler.GET);
		try {
			JSONArray response=new JSONArray(jsonStr);
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
		return true;
	}
}
}