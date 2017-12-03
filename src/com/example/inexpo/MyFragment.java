package com.example.inexpo;

import static com.example.inexpo.CommonUtilities.ADD_FEEDBACK_URL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import model.Stalls;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.android.volley.toolbox.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import appcontroller.AppController;
import database.DBHelper;

public class MyFragment extends Fragment {

	 public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
	//private static final Bitmap Bitmap = null;
	// ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	 private static Context c;
	 private  DBHelper dbHandler= new DBHelper( c);
	 ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	 
	  TextView feedback;
	  TextView introduction;
	  TextView offers;
	  TextView review;
	  TextView rateus;
	    EditText getfeedback;
	    RatingBar rbaruser;	
	    Button btnsubmit;
	    RelativeLayout viewProfile;
	    RelativeLayout coverImage;
	 
	 public static final MyFragment newInstance(int stall ,Context context)

	 {	c=context;

	   MyFragment f = new MyFragment();

	   Bundle bdl = new Bundle(1);
	  // bdl.putSerializable(EXTRA_MESSAGE,  stall);
	   bdl.putInt("id", stall);
	   f.setArguments(bdl);

	   return f;

	 }
 

	 

	 public View onCreateView(LayoutInflater inflater, ViewGroup container,

	   Bundle savedInstanceState) {

	//  Stalls stallrecieved = (Stalls) getArguments().getSerializable(EXTRA_MESSAGE);
	//  final Stalls stall=dbHandler.getStallDetail(stallrecieved.getStallId());
		 
		   if (imageLoader == null)
	            imageLoader = AppController.getInstance().getImageLoader();

		 final Stalls stall=dbHandler.getStallDetail(getArguments().getInt("id"));
		 
	   View v = inflater.inflate(R.layout.one_stall_view, container, false);

	   TextView messageTextView = (TextView)v.findViewById(R.id.textView1);
	   RatingBar rbar=(RatingBar)v.findViewById(R.id.ratingBar1);		
	   
	   com.android.volley.toolbox.NetworkImageView profilepic1 = (com.android.volley.toolbox.NetworkImageView) v.findViewById(R.id.profilepic);
	   coverImage=(RelativeLayout)v.findViewById(R.id.toprel);
	 
	   
	   messageTextView.setText(stall.getStallName());
	   rbar.setRating((float)stall.getRating()/2);
	   profilepic1.setImageUrl(stall.getImageProfile(),imageLoader);
	  // coverImage.setBackgroundDrawable(new BitmapDrawable(StringToBitMap(stall.getImageCover())));
	  // profilepic1.setima
	  
	   messageTextView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Toast.makeText(getActivity(), "kkkskksks", Toast.LENGTH_SHORT).show();
		}
	});

	  ////////////////////////////////////////////
	   final View toolbarl = v.findViewById(R.id.introductionrexpandlay);
       ((RelativeLayout.LayoutParams) toolbarl.getLayoutParams()).bottomMargin = -200;
       toolbarl.setVisibility(View.GONE);
       
	
	   
	   RelativeLayout r=(RelativeLayout)v.findViewById(R.id.rel2);
	   r.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 ExpandAnimation expandAni = new ExpandAnimation(toolbarl, 500);
			 toolbarl.startAnimation(expandAni);
		}
	});
	   
	   final View offerbar = v.findViewById(R.id.offerrexpandlay);
       ((RelativeLayout.LayoutParams) offerbar.getLayoutParams()).bottomMargin = -200;
       offerbar.setVisibility(View.GONE);
       
	   
	   RelativeLayout r3=(RelativeLayout)v.findViewById(R.id.rel3);
	   r3.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 ExpandAnimation expandAni = new ExpandAnimation(offerbar, 500);
			 offerbar.startAnimation(expandAni);
		}
	});
	   
	    feedback=(TextView)v.findViewById(R.id.userfeedbackview);
	    
	    getfeedback=(EditText)v.findViewById(R.id.userfeedback);
	    rbaruser=(RatingBar)v.findViewById(R.id.ratingBaruser);	
	    btnsubmit=(Button)v.findViewById(R.id.submitbutton);
	    viewProfile=(RelativeLayout)v.findViewById(R.id.rel4);
	    rateus=(TextView)v.findViewById(R.id.rateus);
	    
	    introduction=(TextView)v.findViewById(R.id.introductionexpand);
	    offers=(TextView)v.findViewById(R.id.offerexpand);
	    review=(TextView)v.findViewById(R.id.textViewy);
	    
	    
	    introduction.setText(stall.getDesctiption());
	    review.setText(stall.getCategory()+" | "+stall.getReviewCount()+" reviews");
	   
	   viewProfile.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent parse=new Intent(c,BrowserActivity.class);
			parse.putExtra("url",stall.getUrl());			
			startActivity(parse);
			
		}
	});
	   
	   if(!stall.getUserFeedback().equals("")){
		   getfeedback.setVisibility(View.GONE);
		   feedback.setText(stall.getUserFeedback());
		   feedback.setVisibility(View.VISIBLE);
		   btnsubmit.setText("Edit");
		   rateus.setText("You have rated us");
		   rbaruser.setIsIndicator(true);
	   }
	  
	   btnsubmit.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Toast.makeText(getActivity(), getfeedback.getText().toString(), Toast.LENGTH_SHORT).show();
			if(!btnsubmit.getText().equals("Edit")){
			
			if(!getfeedback.getText().toString().trim().equals("")){
				
				if(new ConnectionDetector(c).isConnectingToInternet()){
				feedback.setText("You reviewed as "+getfeedback.getText().toString());
				feedback.setVisibility(View.VISIBLE);
				getfeedback.setVisibility(View.GONE);
				rbaruser.setIsIndicator(true);
				btnsubmit.setText("Edit");
				
				new RefreshDatabaseAction().execute();
				
								
				dbHandler.updateFeedback(stall.getStallId(),getfeedback.getText().toString(),(double)rbaruser.getRating());
				getfeedback.setText(null);
				}else{
					Toast.makeText(getActivity(), "No Internet connection, please try later", Toast.LENGTH_SHORT).show();
				}
				//dbHandler. getUnreadNotificationCount();
			}else{
				Toast.makeText(getActivity(), "Please add a comment to submit", Toast.LENGTH_SHORT).show();
			}
			}else{
				feedback.setVisibility(View.GONE);
				getfeedback.setVisibility(View.VISIBLE);
				rbaruser.setIsIndicator(false);
				btnsubmit.setText("Add");
				
			}
			
		}
	});
	  
	   return v;

	 }
	 private void updateFeedback(){
		////////////////////////////////
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(ADD_FEEDBACK_URL);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("rating", new Float(rbaruser.getRating()).toString()));
		pairs.add(new BasicNameValuePair("review", getfeedback.getText().toString()));
					
		try {
		post.setEntity(new UrlEncodedFormEntity(pairs));
		HttpResponse response = client.execute(post);
		
		} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

///////////////////////////////
		 
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
	 
	 private class RefreshDatabaseAction extends AsyncTask<Void, Void, Void> {

			@Override
			protected Void doInBackground(Void... params) {
				if(new ConnectionDetector(c).isConnectingToInternet()){
					updateFeedback();	
					
				}
				return null;
			}
		}

	}
