package com.example.inexpo;

import java.util.List;

import model.Stalls;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import appcontroller.AppController;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class FeedbackActivity extends Activity{
	private Toolbar toolbar;
	private TextView textview;
	private  int position;
	private  int max;
	private  List<? extends Parcelable> movieList;
	private RatingBar rbar;
	private float userRate;
	private NetworkImageView profilepic ;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
//	overridePendingTransition(R.layout.pull_in_right,
//			R.layout.push_out_left);
	setContentView(R.layout.one_stall_view);
	
//	movieList = getIntent().getParcelableArrayListExtra("stall_data"); 
//	position=getIntent().getIntExtra("pos",0);
//	max=movieList.size()-1;
//	
//		rbar=(RatingBar)findViewById(R.id.ratingBar1);
//		textview=(TextView)findViewById(R.id.textView1);
//		profilepic = (NetworkImageView) this.findViewById(R.id.profilepic);
//		
//		
//		toolbar = (Toolbar) findViewById(R.id.toolbaronestallview);
//		toolbar.inflateMenu(R.menu.onestallviewmenu);
//		toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//		
//			@Override
//			public void onClick(View v) {
//				onBackPressed();
//			}
//		});
//		toolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//
//			@Override
//			public boolean onMenuItemClick(MenuItem item) {
//				switch (item.getItemId()) {
//
//				case R.id.stallbackward:
//					if(position>0){
//						position=position-1;
//						LoadStallDetail((Stalls)movieList.get(position));
//					}break;
//					
//				case R.id.stallforward:
//					if(position<max){
//						++position;
//						LoadStallDetail((Stalls)movieList.get(position));
//					
//				}break;
//					}
//				return false;
//			}
//		});
//		
//		
//		
//		rbar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
//			public void onRatingChanged(RatingBar ratingBar, float rating,
//					boolean fromUser) {
//		 
//					userRate=rating;
//					//textview.setText(new Float(userRate).toString());
//				}
//			});
//		
//		LoadStallDetail((Stalls)movieList.get(position));
//		
//	}
//		
//	
//	private void LoadStallDetail(Stalls stall){
//		textview.setText(stall.getTitle());
//		rbar.setRating((float)stall.getRating()/2);
//		profilepic.setImageUrl(stall.getThumbnailUrl(), imageLoader);
	}
}
