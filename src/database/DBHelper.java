package database;

import java.util.ArrayList;
import java.util.HashMap;

import model.EventDetail;
import model.Notifications;
import model.Sponsers;
import model.Stalls;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper  {

	 public static final String DATABASE_NAME = "Inexpo.db";
	 
	   public static final String NOTIFICATION_TABLE_NAME = "notification";
	   public static final String NOTIFICATION_COLUMN_ID = "id";
	   public static final String NOTIFICATION_COLUMN_MESAGE = "message";
	   public static final String NOTIFICATION_COLUMN_ISCHECKED = "ischecked";
	   public static final String NOTIFICATION_COLUMN_TYPE = "type";
	   public static final String NOTIFICATION_COLUMN_TIME = "time";
	 
	   public static final String EVENT_TABLE_NAME = "eventdetail";
	   public static final String EVENT_COLUMN_START_DATE = "startday";
	   public static final String EVENT_COLUMN_END_DATE = "endday";
	   public static final String EVENT_COLUMN_VENUE = "venue";
	   public static final String EVENT_COLUMN_ID = "id";
	   public static final String EVENT_COLUMN_START_TIME = "starttime";
	   public static final String EVENT_COLUMN_END_TIME = "endtime";
	   public static final String EVENT_COLUMN_CONTACTNO = "contactno";
	   public static final String EVENT_COLUMN_EMAIL = "email";
	   public static final String EVENT_COLUMN_NAME = "name";
	   public static final String EVENT_COLUMN_DESCRIPTION = "description";
	   
	   public static final String SPONSER_TABLE_NAME = "sponser";
	   public static final String SPONSER_COLUMN_CATEGORY = "type";
	   public static final String SPONSER_COLUMN_NAME = "name";
	   public static final String SPONSER_COLUMN_DESCRIPTION = "description";
	   public static final String SPONSER_COLUMN_IMAGE = "image";
	   public static final String SPONSER_COLUMN_ID = "id";	   
	   
	   public static final String STALL_TABLE_NAME = "stalldetail";
	   public static final String STALL_COLUMN_ID = "id";
	   public static final String STALL_COLUMN_NAME = "name";
	   public static final String STALL_COLUMN_CATEGORY = "category";
	   public static final String STALL_COLUMN_RATING = "rating";
	   public static final String STALL_COLUMN_IMAGE_PROFILE = "imageprofile";
	   public static final String STALL_COLUMN_IMAGE_COVER = "imagecover";
	   public static final String STALL_COLUMN_DESCRIPTION = "description";
	   public static final String STALL_COLUMN_URL = "url";
	   public static final String STALL_COLUMN_TAGS = "tag";
	   public static final String STALL_COLUMN_EMAIL = "email";
	   public static final String STALL_COLUMN_CONTACT_NO = "telephone";
	   public static final String STALL_COLUMN_REVIEW_COUNT = "noofreviews";
	   public static final String STALL_COLUMN_USER_FEEDBACK = "userfeedback";
	   public static final String STALL_COLUMN_USER_RATING = "userrating";
	   

	   private HashMap hp;
	   private Context context;

	   public DBHelper(Context context)
	   {		   
	      super(context, DATABASE_NAME , null, 1);
	      this.context=context;
	   }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(
			      "create table "+ NOTIFICATION_TABLE_NAME +
			      "("+NOTIFICATION_COLUMN_ID +" integer primary key AUTOINCREMENT,"+ NOTIFICATION_COLUMN_MESAGE +" text,"+NOTIFICATION_COLUMN_ISCHECKED+" integer,"+NOTIFICATION_COLUMN_TYPE+" integer,"+NOTIFICATION_COLUMN_TIME+" real)"
			      );
		db.execSQL(
			      "create table "+ STALL_TABLE_NAME +
			      "("+STALL_COLUMN_ID +" integer primary key,"+ STALL_COLUMN_REVIEW_COUNT +" int,"+ STALL_COLUMN_EMAIL +" text,"+ STALL_COLUMN_CONTACT_NO +" text,"+ STALL_COLUMN_TAGS +" text,"+ STALL_COLUMN_NAME +" text,"+STALL_COLUMN_IMAGE_PROFILE+" text,"+STALL_COLUMN_IMAGE_COVER+" text,"+STALL_COLUMN_DESCRIPTION+" text,"+STALL_COLUMN_CATEGORY+" text,"+STALL_COLUMN_URL+" text,"+STALL_COLUMN_USER_FEEDBACK+" text,"+STALL_COLUMN_USER_RATING+" double,"+STALL_COLUMN_RATING+" double)"
			      );
		db.execSQL(
			      "create table "+ EVENT_TABLE_NAME +
			      "("+EVENT_COLUMN_START_DATE +" date,"+ EVENT_COLUMN_ID +" integer,"+ EVENT_COLUMN_DESCRIPTION +" text,"+ EVENT_COLUMN_NAME +" text,"+ EVENT_COLUMN_END_DATE +" date,"+EVENT_COLUMN_VENUE+" text,"+EVENT_COLUMN_CONTACTNO+" text,"+EVENT_COLUMN_EMAIL+" text,"+EVENT_COLUMN_END_TIME+" text,"+EVENT_COLUMN_START_TIME+" text)"
			      );
		db.execSQL(
			      "create table "+ SPONSER_TABLE_NAME +
			      "("+SPONSER_COLUMN_ID +" integer primary key ,"+ SPONSER_COLUMN_NAME +" text,"+SPONSER_COLUMN_DESCRIPTION+" text,"+SPONSER_COLUMN_IMAGE+" text,"+SPONSER_COLUMN_CATEGORY+" text)"
			      );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	public boolean insertNotification (String message,int isChecked,int type,long time)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();

	      contentValues.put(NOTIFICATION_COLUMN_MESAGE, message);
	      contentValues.put(NOTIFICATION_COLUMN_ISCHECKED, isChecked);
	      contentValues.put(NOTIFICATION_COLUMN_TYPE, type);
	      contentValues.put(NOTIFICATION_COLUMN_TIME, time);
	     
	      db.insert(NOTIFICATION_TABLE_NAME, null, contentValues);
	      db.close();
	      return true;
	   }
	
	 public ArrayList<Notifications> getAllNotifications()
	   {
	      ArrayList<Notifications> array_list = new ArrayList<Notifications>();
	    
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from "+ NOTIFICATION_TABLE_NAME, null );
	      res.moveToFirst();
	      while(res.isAfterLast() == false){
	    	  
	    	  Notifications temp=new Notifications();
	    	  temp.setId( res.getInt(res.getColumnIndex(NOTIFICATION_COLUMN_ID)));
	    	  temp.setMessage( res.getString(res.getColumnIndex(NOTIFICATION_COLUMN_MESAGE)));
	    	  temp.setIsChecked( res.getInt(res.getColumnIndex(NOTIFICATION_COLUMN_ISCHECKED)));
	    	  temp.setType( res.getInt(res.getColumnIndex(NOTIFICATION_COLUMN_TYPE)));
	    	  temp.setTime( res.getLong(res.getColumnIndex(NOTIFICATION_COLUMN_TIME)));
	    	  array_list.add(temp);
	    	  
	      res.moveToNext();
	      }
	      db.close();
	      return array_list;
	   }
	 
	 public boolean updateNotificationAsChecked(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues args = new ContentValues();
		args.put(NOTIFICATION_COLUMN_ISCHECKED, 1);
		db.update(NOTIFICATION_TABLE_NAME, args,NOTIFICATION_COLUMN_ID +"="+id, null);
		 db.close();
		 return true;
		 
	 }
	 public int getUnreadNotificationCount(){
			int count=0;
			 
			SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from "+ NOTIFICATION_TABLE_NAME+" where "+NOTIFICATION_COLUMN_ISCHECKED+"= 0", null );
		      res.moveToFirst();
		      while(res.isAfterLast() == false){
		    	  count++;
		    	  res.moveToNext();
		      }
		      db.close();
			 return count;
			 
		 }
	 public ArrayList<Stalls> getAllStallDetails()
	   {
	      ArrayList<Stalls> array_list = new ArrayList<Stalls>();
	    
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from "+ STALL_TABLE_NAME, null );
	      res.moveToFirst();
	      while(res.isAfterLast() == false){
	    	  
	    	  Stalls temp=new Stalls();
	    	  temp.setStallId( res.getInt(res.getColumnIndex(STALL_COLUMN_ID)));
	    	  temp.setStallName( res.getString(res.getColumnIndex(STALL_COLUMN_NAME)));
	    	  temp.setImageProfile( res.getString(res.getColumnIndex(STALL_COLUMN_IMAGE_PROFILE)));
	    	//  temp.setImageCover( res.getString(res.getColumnIndex(STALL_COLUMN_IMAGE_COVER)));
	    	//  temp.setDesctiption( res.getString(res.getColumnIndex(STALL_COLUMN_DESCRIPTION)));
	    	  	temp.setRating( res.getLong(res.getColumnIndex(STALL_COLUMN_RATING)));
	    	 // temp.setCategory( res.getString(res.getColumnIndex(STALL_COLUMN_CATEGORY)));
	    	//  temp.setUrl( res.getString(res.getColumnIndex(STALL_COLUMN_URL)));
	    	//  temp.setStallEmail( res.getString(res.getColumnIndex(STALL_COLUMN_EMAIL)));
	    	//  temp.setStallContactNo( res.getString(res.getColumnIndex(STALL_COLUMN_CONTACT_NO)));
	    	  temp.setTags( res.getString(res.getColumnIndex(STALL_COLUMN_TAGS)));
	    	  temp.setReviewCount( res.getInt(res.getColumnIndex(STALL_COLUMN_REVIEW_COUNT)));
	    	  
//	    	 if(!res.isNull(res.getColumnIndex(STALL_COLUMN_USER_RATING))){
//	    	  temp.setUserRationg( res.getLong(res.getColumnIndex(STALL_COLUMN_USER_RATING)));
//	    	 }else{
//	    		 temp.setUserRationg(0);
//	    	 }
//	    	if(!res.isNull(res.getColumnIndex(STALL_COLUMN_USER_FEEDBACK))){
//	    		 temp.setUserFeedback( res.getString(res.getColumnIndex(STALL_COLUMN_USER_FEEDBACK)));
//		    	 }else{
//		    		 temp.setUserFeedback("");
//		   	 }
	    	 
	    	  array_list.add(temp);
	    	  
	      res.moveToNext();
	      }
	     db.close();
	      return array_list;
	   }
	 public boolean insertStall (Stalls stall)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();

	      contentValues.put(STALL_COLUMN_ID, stall.getStallId());
	      contentValues.put(STALL_COLUMN_NAME, stall.getStallName());
	      contentValues.put(STALL_COLUMN_IMAGE_PROFILE, stall.getImageProfile());
	      contentValues.put(STALL_COLUMN_IMAGE_COVER, stall.getImageCover());
	      contentValues.put(STALL_COLUMN_CATEGORY, stall.getCategory());
	      contentValues.put(STALL_COLUMN_DESCRIPTION, stall.getDesctiption());
	      contentValues.put(STALL_COLUMN_RATING, stall.getRating());
	      contentValues.put(STALL_COLUMN_URL, stall.getUrl());
	      contentValues.put(STALL_COLUMN_EMAIL, stall.getStallEmail());
	      contentValues.put(STALL_COLUMN_CONTACT_NO, stall.getStallContactNo());
	      contentValues.put(STALL_COLUMN_TAGS, stall.getTags());
	      contentValues.put(STALL_COLUMN_REVIEW_COUNT, stall.getReviewCount());
	     
	      db.insert(STALL_TABLE_NAME, null, contentValues);
	      db.close();
	      return true;
	   }
	 public boolean removeStall (int id)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      db.delete(STALL_TABLE_NAME, STALL_COLUMN_ID + " = "+ id, null);
	      db.close();
	      return true;
	   }
	 public boolean removeAllStalls ()
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      db.delete(STALL_TABLE_NAME, null, null);
	      db.close();
	      return true;
	   }
	 public boolean isStallAvailable (int id)
	   {
		  int count=0;
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor res =  db.rawQuery( "select id from "+ STALL_TABLE_NAME+" where id = "+id, null );
	      res.moveToFirst();
	      while(res.isAfterLast() == false){
	    	  count++;
	    	  res.moveToNext();
	      }
		 if(count ==0){
			 db.close();
			 return false;
		 }else{	  
			 db.close();
	         return true;
		 }
	   }
	 public boolean updateStall (Stalls stall)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();

	      contentValues.put(STALL_COLUMN_NAME, stall.getStallName());
	      contentValues.put(STALL_COLUMN_IMAGE_PROFILE, stall.getImageProfile());
	      contentValues.put(STALL_COLUMN_IMAGE_COVER, stall.getImageCover());
	      contentValues.put(STALL_COLUMN_CATEGORY, stall.getCategory());
	      contentValues.put(STALL_COLUMN_DESCRIPTION, stall.getDesctiption());
	      contentValues.put(STALL_COLUMN_RATING, stall.getRating());
	      contentValues.put(STALL_COLUMN_URL, stall.getUrl());
	      contentValues.put(STALL_COLUMN_EMAIL, stall.getStallEmail());
	      contentValues.put(STALL_COLUMN_CONTACT_NO, stall.getStallContactNo());
	      contentValues.put(STALL_COLUMN_TAGS, stall.getTags());
	      contentValues.put(STALL_COLUMN_REVIEW_COUNT, stall.getReviewCount());
	      
	      db.update(STALL_TABLE_NAME,contentValues,STALL_COLUMN_ID +" = "+stall.getStallId() , null);
	      db.close();
	      return true;
	   }
	 public boolean updateFeedback (int id,String feedback,double userRating)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();

	      contentValues.put(STALL_COLUMN_ID, id);
	      contentValues.put(STALL_COLUMN_USER_FEEDBACK, feedback);
	      contentValues.put(STALL_COLUMN_USER_RATING, userRating);
	      
	      db.update(STALL_TABLE_NAME,contentValues,STALL_COLUMN_ID +" = "+id , null);
	  
	      db.close();   
	      return true;
	   }
	 public Stalls getStallDetail(int id)
	   {
	      Stalls temp = new Stalls();
	    
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from "+ STALL_TABLE_NAME+ " where "+STALL_COLUMN_ID+" = "+id, null );
	      res.moveToFirst();
	     
	      temp.setStallId( res.getInt(res.getColumnIndex(STALL_COLUMN_ID)));
    	  temp.setStallName( res.getString(res.getColumnIndex(STALL_COLUMN_NAME)));
    	  temp.setImageProfile( res.getString(res.getColumnIndex(STALL_COLUMN_IMAGE_PROFILE)));
    	  temp.setImageCover( res.getString(res.getColumnIndex(STALL_COLUMN_IMAGE_COVER)));
    	  temp.setDesctiption( res.getString(res.getColumnIndex(STALL_COLUMN_DESCRIPTION)));
    	  temp.setRating( res.getLong(res.getColumnIndex(STALL_COLUMN_RATING)));
    	  temp.setCategory( res.getString(res.getColumnIndex(STALL_COLUMN_CATEGORY)));
    	  temp.setUrl( res.getString(res.getColumnIndex(STALL_COLUMN_URL)));
    	  temp.setStallEmail( res.getString(res.getColumnIndex(STALL_COLUMN_EMAIL)));
    	  temp.setStallContactNo( res.getString(res.getColumnIndex(STALL_COLUMN_CONTACT_NO)));
    	  temp.setTags( res.getString(res.getColumnIndex(STALL_COLUMN_TAGS)));
    	  temp.setReviewCount( res.getInt(res.getColumnIndex(STALL_COLUMN_REVIEW_COUNT)));
	    	 
	    	  if(!res.isNull(res.getColumnIndex(STALL_COLUMN_USER_RATING))){
	    	  temp.setUserRationg( res.getLong(res.getColumnIndex(STALL_COLUMN_USER_RATING)));
	    	 }else{
	    		 temp.setUserRationg(0); 		  
	    	 }
	    	  if(!res.isNull(res.getColumnIndex(STALL_COLUMN_USER_FEEDBACK))){
	    		 temp.setUserFeedback( res.getString(res.getColumnIndex(STALL_COLUMN_USER_FEEDBACK)));
		    	 }else{
		    		 temp.setUserFeedback("");
		  
		    }
	    	  db.close();
	     
	      return temp;
	   }
	 public ArrayList<Sponsers> getSponserDetails()
	   {
		 ArrayList<Sponsers> array_list = new ArrayList<Sponsers>();
		 
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from "+ SPONSER_TABLE_NAME, null );
	      res.moveToFirst();
	      while(res.isAfterLast() == false){	    	  
	    	  Sponsers temp=new Sponsers();
	    	  temp.setId(( res.getInt(res.getColumnIndex(SPONSER_COLUMN_ID))));
	    	  temp.setName(( res.getString(res.getColumnIndex(SPONSER_COLUMN_NAME))));
	    	  temp.setDesctiption( res.getString(res.getColumnIndex(SPONSER_COLUMN_DESCRIPTION)));	    	
	    	  temp.setImage( res.getString(res.getColumnIndex(SPONSER_COLUMN_IMAGE)));
	    	  temp.setCategory( res.getString(res.getColumnIndex(SPONSER_COLUMN_CATEGORY)));	    
	    	 
	    	  array_list.add(temp);
	    	  
	      res.moveToNext();
	      }
	      db.close();
	      return array_list;
	   }
	 public boolean insertSponser (Sponsers item)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();

	      contentValues.put(SPONSER_COLUMN_ID, item.getId());
	      contentValues.put(SPONSER_COLUMN_NAME, item.getName());
	      contentValues.put(SPONSER_COLUMN_DESCRIPTION, item.getDesctiption());
	      contentValues.put(SPONSER_COLUMN_CATEGORY, item.getCategory());
	      contentValues.put(SPONSER_COLUMN_IMAGE, item.getImage());
	     
	      db.insert(SPONSER_TABLE_NAME, null, contentValues);
	      db.close();
	      return true;
	   }
	 public boolean isSponserDetailAvailable(){
			int count=0;
			SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from "+ SPONSER_TABLE_NAME, null );
		      res.moveToFirst();
		      while(res.isAfterLast() == false){
		      count++;
		      res.moveToNext();
		      }	   
		      if(count==0){
		    	  db.close();
			      return false;
		      }else{
		    	  db.close();
		    	  return true;
		      }
			    
		 }
	 public boolean isEventDetailAvailable(){
		 int count=0;
			SQLiteDatabase db = this.getReadableDatabase();
		      Cursor res =  db.rawQuery( "select * from "+ EVENT_TABLE_NAME, null );
		      res.moveToFirst();
		      while(res.isAfterLast() == false){
		      count++;
		      res.moveToNext();
		      }	   
		      if(count==0){
		    	  db.close();
			      return false;
		      }else{
		    	  db.close();
		    	  return true;
		      }
		 }
	 public boolean insertEvent (EventDetail item)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();

	      contentValues.put(EVENT_COLUMN_ID, 1);
	      contentValues.put(EVENT_COLUMN_NAME, item.getName());
	      contentValues.put(EVENT_COLUMN_CONTACTNO, item.getContactNo());
	      contentValues.put(EVENT_COLUMN_EMAIL, item.getContactEmail());
	      contentValues.put(EVENT_COLUMN_START_DATE, item.getStartDate());
	      contentValues.put(EVENT_COLUMN_END_DATE, item.getEndDate());
	      contentValues.put(EVENT_COLUMN_START_TIME, item.getStartTime());
	      contentValues.put(EVENT_COLUMN_END_TIME, item.getEndTime());
	      contentValues.put(EVENT_COLUMN_DESCRIPTION, item.getDescription());
	     
	      db.insert(EVENT_TABLE_NAME, null, contentValues);
	      db.close();
	      return true;
	   }
	 
	 public EventDetail getEventDetail()
	   {
		 		 
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from "+ EVENT_TABLE_NAME+" where "+EVENT_COLUMN_ID+" = 1", null );
	      res.moveToFirst();
	      EventDetail temp=new EventDetail();
	            	  
	    	
	    	  temp.setName(( res.getString(res.getColumnIndex(EVENT_COLUMN_NAME))));
	    	  temp.setDescription( res.getString(res.getColumnIndex(EVENT_COLUMN_DESCRIPTION)));	    	
	    	  temp.setStartDate( res.getString(res.getColumnIndex(EVENT_COLUMN_START_DATE)));
	    	  temp.setEndDate( res.getString(res.getColumnIndex(EVENT_COLUMN_END_DATE)));
	    	  temp.setStartTime( res.getString(res.getColumnIndex(EVENT_COLUMN_START_TIME)));
	    	  temp.setEndTime( res.getString(res.getColumnIndex(EVENT_COLUMN_END_TIME)));
	    	  temp.setVenue( res.getString(res.getColumnIndex(EVENT_COLUMN_VENUE)));
	    	  temp.setContactNo( res.getString(res.getColumnIndex(EVENT_COLUMN_CONTACTNO)));
	    	  temp.setContactEmail( res.getString(res.getColumnIndex(EVENT_COLUMN_EMAIL)));
	    	  
	    	  db.close();
	      return temp;
	   }
	 public boolean removeEvent ()
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      db.delete(EVENT_TABLE_NAME, null, null);
	      db.close();
	      return true;
	   }
	 public boolean isSponserAvailable (int id)
	   {
		  int count=0;
	      SQLiteDatabase db = this.getWritableDatabase();
	      Cursor res =  db.rawQuery( "select id from "+ SPONSER_TABLE_NAME+" where id = "+id, null );
	      res.moveToFirst();
	      while(res.isAfterLast() == false){
	    	  count++;
	    	  res.moveToNext();
	      }
		 if(count ==0){
			 db.close();
			 return false;
		 }else{	  
			 db.close();
	         return true;
		 }
	   }
	 public boolean updateSponser (Sponsers item)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();

	      contentValues.put(SPONSER_COLUMN_NAME, item.getName());
	      contentValues.put(SPONSER_COLUMN_DESCRIPTION, item.getDesctiption());
	      contentValues.put(SPONSER_COLUMN_CATEGORY, item.getCategory());
	      contentValues.put(SPONSER_COLUMN_IMAGE, item.getImage());
	     
	      db.update(SPONSER_TABLE_NAME,contentValues,SPONSER_COLUMN_ID +" = "+item.getId() , null);
	      db.close();
	      return true;
	   }
}
