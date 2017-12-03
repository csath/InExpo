package com.example.inexpo;

import java.util.ArrayList;
import java.util.List;
import model.Notifications;
import adapter.CustomNotificationListAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import database.DBHelper;

public class NotificationActivity extends Activity{

	private Toolbar toolbar;
	private ListView listView;
	private SwipeRefreshLayout swipeView;
	public static CustomNotificationListAdapter adapter;
	private List<Notifications> notificationList = new ArrayList<Notifications>();
	private  DBHelper dbHandler= new DBHelper(this);
	
	@SuppressLint("NewApi") @SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		overridePendingTransition(R.layout.pull_in_right,
//				R.layout.push_out_left);
		setContentView(R.layout.activity_notification);
/////////////////////////////////
Window window = this.getWindow();
window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
window.setStatusBarColor(this.getResources().getColor(R.color.accent));
///////////////////////////////
				
		toolbar = (Toolbar) findViewById(R.id.toolbarnotification);
		toolbar.setTitle("Notification");
		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		toolbar.setTitleTextColor(getResources().getColor(R.color.textcolor));
		
		listView = (ListView) findViewById(R.id.notificationView);
		swipeView = (SwipeRefreshLayout) findViewById(R.id.swipenotifi);
		
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
						swipeView.setRefreshing(true);
						loadListView();
						(new Handler()).postDelayed(new Runnable() {
							@Override
							public void run() {
								swipeView.setRefreshing(false);
								}
						}, 1000);

					}
				});

		listView.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0 ) {
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
				View toolbar = view.findViewById(R.id.toolbarexpand);
				ImageView newNotify = (ImageView)view.findViewById(R.id.thumbnailnotifichecked);
				TextView notifymessage = (TextView)view.findViewById(R.id.messagenotifi);
						 // Creating the expand animation for the item
                ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);
                
              
				if(notificationList.get(position).getIsChecked()==0){
					newNotify.setVisibility(View.GONE);
					dbHandler.updateNotificationAsChecked(notificationList.get(position).getId());
//					 	LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				        View rowView = inflater.inflate(R.layout.notification_list_row, null);
//				        RelativeLayout bgcolor = (RelativeLayout)rowView.findViewById(R.id.relbackground);
//				        bgcolor.setBackgroundColor(getResources().getColor(R.color.notification_checked_background));
//				        loadListView();
					
				}				 
             // Start the animation on the toolbar
					
                toolbar.startAnimation(expandAni);
                if(notifymessage.getText()==""){
                	notifymessage.setText(notificationList.get(position).getMessage());
                }else{
                	notifymessage.setText("");
                	
                }
                
			}

		});
		
		adapter = new CustomNotificationListAdapter(NotificationActivity.this, notificationList);
		listView.setAdapter(adapter);
		loadListView();
	
	}
	
	public void loadListView() {

		notificationList.clear();	
		List<Notifications>  temp =  dbHandler.getAllNotifications();
		int count=0;
		while(count<temp.size()){
			notificationList.add(temp.get(temp.size()-1-count));
			count++;
		}
			adapter.notifyDataSetChanged();
		}
	@Override
	protected void onResume() {
		loadListView();
		super.onResume();
	}
}
