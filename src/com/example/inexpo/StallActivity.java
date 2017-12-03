package com.example.inexpo;

import static com.example.inexpo.CommonUtilities.STALL_DETAIL_URL;

import java.util.ArrayList;
import java.util.List;

import model.Stalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapter.CustomGridViewAdapter;
import adapter.CustomStallListAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webservice.ServiceHandler;

import database.DBHelper;

public class StallActivity extends FragmentActivity {
	private Toolbar toolbar;
	private ListView listView;
	private GridView gridView;
	private SwipeRefreshLayout swipeView;
	private TextView refreshing;
	private EditText searchsuggestion;
	private ImageView exitbtn;
	private RelativeLayout searchlayout;
	private RelativeLayout extralayout;
	private RelativeLayout listviewlayout;
	private TextView nomatchfound;
	private String searchKeyword="";

	DBHelper dbHandler= new DBHelper(this);
	
	

	private static CustomStallListAdapter stallListAdapter;
	private static CustomGridViewAdapter stallGridAdapter;
	public  List<Stalls> stallList = new ArrayList<Stalls>();
	private List<Stalls>  AllList;
	
	@SuppressLint("NewApi") @SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
//		overridePendingTransition(R.layout.pull_in_right,
//				R.layout.push_out_left);
		setContentView(R.layout.activity_stall);
/////////////////////////////////
Window window = this.getWindow();
window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
window.setStatusBarColor(this.getResources().getColor(R.color.accent));
///////////////////////////////
		final InputMethodManager imm = (InputMethodManager)getSystemService(
  		      Context.INPUT_METHOD_SERVICE);
		
		listView = (ListView) findViewById(R.id.stallView);
		gridView = (GridView) findViewById(R.id.gridView);
		toolbar = (Toolbar) findViewById(R.id.toolbarstall);
		refreshing = (TextView) findViewById(R.id.swipetorefresh);
		searchsuggestion = (EditText) findViewById(R.id.searchsuggestion);
		swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
		exitbtn = (ImageView) findViewById(R.id.exitbutton);
		searchlayout = (RelativeLayout) findViewById(R.id.searchlayout);
		extralayout = (RelativeLayout) findViewById(R.id.extrarel);
		listviewlayout = (RelativeLayout) findViewById(R.id.listviewlayout);
		nomatchfound = (TextView) findViewById(R.id.nomatchfound);
	
		
		toolbar.setTitleTextColor(getResources().getColor(R.color.textcolor));
				
		searchsuggestion.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		        	searchForStalls();
		        	imm.hideSoftInputFromWindow(searchsuggestion.getWindowToken(), 0);
		            return true;
		        }
		        return false;
		    }
		});
		
		toolbar.inflateMenu(R.menu.stallmenu);
		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//onBackPressed();
				finish();
			}
		});

		toolbar.setTitle("Stalls");
		toolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {

				case R.id.search:
					toolbar.setVisibility(View.GONE);
					searchlayout.setVisibility(View.VISIBLE);
					listviewlayout.setVisibility(View.VISIBLE);
				//	pager.setVisibility(View.GONE);
					imm.toggleSoftInputFromWindow(searchsuggestion.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
					searchsuggestion.requestFocus();
					break;
					
				case R.id.viewselectorgrid:
						//stallGridAdapter.notifyDataSetChanged();
						listView.setVisibility(View.GONE);
						gridView.setVisibility(View.VISIBLE);
				
						break;
						
				case R.id.viewselectorlist:
						//stallListAdapter.notifyDataSetChanged();
						gridView.setVisibility(View.GONE);
						listView.setVisibility(View.VISIBLE);
						
								
					
					break;
				}
							
				return false;
			}
		});
		// /////////////////// Toolbar ends //////////////////////

		exitbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				imm.hideSoftInputFromWindow(searchsuggestion.getWindowToken(), 0);
				toolbar.setVisibility(View.VISIBLE);
				searchlayout.setVisibility(View.GONE);
				searchsuggestion.setText("");
				searchForStalls();
			}
		});

		swipeView.setEnabled(false);
		swipeView.setColorScheme(android.R.color.holo_blue_dark,
				android.R.color.holo_blue_dark,
				android.R.color.holo_blue_dark,
				android.R.color.holo_blue_dark,
				android.R.color.holo_blue_light);
		swipeView
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						new RefreshDatabaseAction().execute();
						//						AllList.clear();
//						 AllList =  dbHandler.getAllStallDetails();
//							int count=0;
//						stallList.clear();
//							while(count<AllList.size()){
//								stallList.add(AllList.get(count));
//								count++;
//							}
//							
//							stallGridAdapter.notifyDataSetChanged();
//							stallListAdapter.notifyDataSetChanged();
							
						swipeView.setRefreshing(true);
						extralayout.setVisibility(View.VISIBLE);
						toolbar.setVisibility(View.GONE);
						listView.setAlpha(0.75f);						
						searchForStalls();
						(new Handler()).postDelayed(new Runnable() {
							@Override
							public void run() {
								extralayout.setVisibility(View.GONE);
								toolbar.setVisibility(View.VISIBLE);
								listView.setAlpha(1);
								swipeView.setRefreshing(false);
								if(!new ConnectionDetector(StallActivity.this).isConnectingToInternet()){
									Toast.makeText(getApplication(), "No Internet Connection",
											Toast.LENGTH_SHORT).show();
								}
							}
						}, 2000);

					}
				});

		listView.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0 && !searchlayout.isShown()) {
					swipeView.setEnabled(true);
				} else {
					swipeView.setEnabled(false);
				}
			}

		});
		gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (firstVisibleItem == 0 && !searchlayout.isShown()) {
					swipeView.setEnabled(true);
				} else {
					swipeView.setEnabled(false);
				}
				
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				imm.hideSoftInputFromWindow(searchsuggestion.getWindowToken(), 0);
//				  pageAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getFragments());
//  				    pager.setAdapter(pageAdapter);
//  				    
//  				    pager.setOffscreenPageLimit(3);
//  				    pager.setClipToPadding(false);
//  				    pager.setPageMargin(5);
//  				     
//  				    pager.setCurrentItem(position);
//				    pager.setVisibility(View.VISIBLE);
//				    searchlayout.setVisibility(View.GONE);
//				    listviewlayout.setVisibility(View.GONE);
//				    extralayout.setVisibility(View.GONE);
//					toolbar.setVisibility(View.GONE);
				ArrayList<Integer> temp = new ArrayList<Integer>();
				
				for(int i=0;i<stallList.size();i++){
					temp.add(stallList.get(i).getStallId());
				}
				Intent parse=new Intent(StallActivity.this,StallViewPager.class);
				parse.putExtra("pos",position);
			//	parse.putExtra("id",stallList.get(position).getStallId());
				//parse.putParcelableArrayListExtra("stall_data", (ArrayList<? extends Parcelable>) stallList);
				parse.putIntegerArrayListExtra("stall_id",  temp);
				startActivity(parse);
			}

		});		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				imm.hideSoftInputFromWindow(searchsuggestion.getWindowToken(), 0);
//				  pageAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getFragments());
//  				    pager.setAdapter(pageAdapter);
//  				    
//  				    pager.setOffscreenPageLimit(3);
//  				    pager.setClipToPadding(false);
//  				    pager.setPageMargin(5);
//  				     
//  				    pager.setCurrentItem(position);
//				    pager.setVisibility(View.VISIBLE);
//				    searchlayout.setVisibility(View.GONE);
//				    listviewlayout.setVisibility(View.GONE);
//				    extralayout.setVisibility(View.GONE);
//					toolbar.setVisibility(View.GONE);
				ArrayList<Integer> temp = new ArrayList<Integer>();
				
				for(int i=0;i<stallList.size();i++){
					temp.add(stallList.get(i).getStallId());
				}
				
				Intent parse=new Intent(StallActivity.this,StallViewPager.class);
				parse.putExtra("pos",position);
				//parse.putExtra("id",stallList.get(position).getStallId());
				parse.putIntegerArrayListExtra("stall_id",  temp);
				startActivity(parse);
			}

		});		

		searchsuggestion.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				searchForStalls();
			}
		});
		
		//////////////// database access //////////////////
		  AllList =  dbHandler.getAllStallDetails();
			int count=0;
			stallList.clear();
			while(count<AllList.size()){
				stallList.add(AllList.get(count));
				count++;
			}
			if(AllList.size()==0){
				
					new RefreshDatabaseAction().execute();
				
			}
			///////////////////////////////////////////////
		stallListAdapter = new CustomStallListAdapter(this, stallList);
		stallGridAdapter = new CustomGridViewAdapter(this, stallList);
		gridView.setAdapter(stallGridAdapter);
		listView.setAdapter(stallListAdapter);
		stallGridAdapter.notifyDataSetChanged();
		stallListAdapter.notifyDataSetChanged();
		
	
	}

	private void searchForStalls() {

		stallList.clear();
		nomatchfound.setVisibility(View.GONE);
		searchKeyword=searchsuggestion.getText().toString().toLowerCase().replaceAll("\\s+","");
	
			for (int i = 0; i < AllList.size(); i++) {					
				if(AllList.get(i).getStallName().toLowerCase().replaceAll("\\s+","").matches("(.*)"+searchKeyword+"(.*)")||AllList.get(i).getTags().toLowerCase().replaceAll("\\s+","").matches("(.*)"+searchKeyword+"(.*)")){
						stallList.add(AllList.get(i));
				}			
			}
			stallGridAdapter.notifyDataSetChanged();
			stallListAdapter.notifyDataSetChanged();
			if ( stallList.size()==0) {
				nomatchfound.setVisibility(View.VISIBLE);
			}
	}

	private boolean refreshDatabase(){
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


	private class RefreshDatabaseAction extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			if(new ConnectionDetector(StallActivity.this).isConnectingToInternet()){
				refreshDatabase();	
				
			}
			return null;
		}
	}
}
