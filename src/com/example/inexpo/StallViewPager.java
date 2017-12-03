package com.example.inexpo;

import java.util.ArrayList;
import java.util.List;
import database.DBHelper;
import model.Stalls;
import adapter.ViewPagerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;



public class StallViewPager extends FragmentActivity    {

	ViewPagerAdapter pageAdapter;
	private  int position;
	private  int stallId;
	private  ArrayList<Integer> stallList;	
	
	DBHelper dbHandler= new DBHelper(this);
	  @SuppressLint("NewApi") @Override

	  public void onCreate(Bundle savedInstanceState) {

	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.viewpager);
	    
/////////////////////////////////
Window window = this.getWindow();
window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
window.setStatusBarColor(this.getResources().getColor(R.color.accent));
///////////////////////////////
	    
	    stallList =  getIntent().getIntegerArrayListExtra("stall_id"); 
	  //  movieList=StallActivity.movieList;
	    position=getIntent().getIntExtra("pos",0);
	  //  stallId=getIntent().getIntExtra("id",0);
	   
	    
	    List<Fragment> fragments = getFragments();

	    pageAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getFragments());

	    ViewPager pager = (ViewPager)findViewById(R.id.viewPager);

	    pager.setAdapter(pageAdapter);
	    pager.setCurrentItem(position);
	  //  pageAdapter.notifyDataSetChanged();
	  //  pager.setClipToPadding(true);
	  //  pager.setPageMargin(60);
	    
	

	  }
	  private List<Fragment> getFragments(){

		  List<Fragment> fList = new ArrayList<Fragment>();
		 
		 int temp=0;
		 
		 while(temp<stallList.size()){			 
			 fList.add(MyFragment.newInstance(stallList.get(temp),this));
			 temp++;
		 }
		  
		  return fList;

		}

}
