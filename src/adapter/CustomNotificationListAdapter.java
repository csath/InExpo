package adapter;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Notifications;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.inexpo.R;
import com.readystatesoftware.viewbadger.BadgeView;

public class CustomNotificationListAdapter extends BaseAdapter {

	private Activity activity;
    private LayoutInflater inflater;
    private List<Notifications> notificationItems;
    private int NOTIFICATION_TYPE_ONE=1; //alert
    private int NOTIFICATION_TYPE_TWO=2; //event
    private int NOTIFICATION_TYPE_THREE=3; //promotion
    private int NOTIFICATION_TYPE_FOUR=4; //news
    private int NOTIFICATION_TYPE_DEFAULT=0;
    private Bitmap BMP_TYPE_ONE ;
    private Bitmap BMP_TYPE_TWO ;
    private Bitmap BMP_TYPE_THREE ;
    private Bitmap BMP_TYPE_DEFAULT ;
    private Bitmap BMP_TYPE_FOUR ;
    public static BadgeView notificationBadgetemp;
     
   
    public CustomNotificationListAdapter(Activity activity, List<Notifications> movieItems) {
        this.activity = activity;
        this.notificationItems = movieItems;
        BMP_TYPE_ONE = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_communities);
 		BMP_TYPE_TWO = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_people);
 		BMP_TYPE_THREE = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_home);
 		BMP_TYPE_DEFAULT = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_logo);
 		BMP_TYPE_FOUR = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_logo);
         
      
    }
    
    @Override
    public int getCount() {
        return notificationItems.size();
    }
 
    @Override
    public Object getItem(int location) {
        return notificationItems.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.notification_list_row, null);
 
        ImageView thumbNail = (ImageView) convertView.findViewById(R.id.thumbnailnotifi);
        TextView title = (TextView) convertView.findViewById(R.id.titlenotifi);
        TextView message = (TextView) convertView.findViewById(R.id.messagenotifi);
        TextView time = (TextView) convertView.findViewById(R.id.timenotifi);
        RelativeLayout background = (RelativeLayout) convertView.findViewById(R.id.relbackground);
        TextView textexpand = (TextView)convertView.findViewById(R.id.timenotifiexpand);
        ImageView newNotify = (ImageView)convertView.findViewById(R.id.thumbnailnotifichecked);
        // getting movie data for the row
        Notifications m = notificationItems.get(position);
 
        if(m.getType()==NOTIFICATION_TYPE_ONE){
        	thumbNail.setImageBitmap(BMP_TYPE_ONE);
        	title.setText("Alert");
        }else if(m.getType()==NOTIFICATION_TYPE_TWO){
        	thumbNail.setImageBitmap(BMP_TYPE_TWO);
        	title.setText("Event");
        }else if(m.getType()==NOTIFICATION_TYPE_THREE){
        	thumbNail.setImageBitmap(BMP_TYPE_THREE);
        	title.setText("Promotion");
        }else if(m.getType()==NOTIFICATION_TYPE_DEFAULT){
        	thumbNail.setImageBitmap(BMP_TYPE_DEFAULT);
        	title.setText("Inexpo");
        }else if(m.getType()==NOTIFICATION_TYPE_FOUR){
        	thumbNail.setImageBitmap(BMP_TYPE_FOUR);
        	title.setText("News");
        }
         
        if(m.getIsChecked()==0){
        	newNotify.setVisibility(View.VISIBLE);
        	//	background.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.nitification_unchecked));
        }else{
        	newNotify.setVisibility(View.GONE);
        	//background.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.list_row_selector));
        }
        
        // message
        message.setText( String.valueOf(m.getMessage()));
        textexpand.setText( String.valueOf(m.getMessage()));
        // time
        
        long when = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String calculatedTime = formatter.format(m.getTime());
        Date newDate=null;
        Date oldDate=null;
        
        try{
        newDate =  formatter.parse(formatter.format(when));
        oldDate =  formatter.parse(formatter.format(m.getTime()));
        
        long diff = newDate.getTime() - oldDate.getTime();
        
		
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		if(diffDays>1){
        	calculatedTime=diffDays+ " days ago";
        }else if(diffDays==1 ){
        	calculatedTime="yesterday";
        }else if(diffHours>1){
        	calculatedTime=diffHours+" hours ago";
        }else if(diffHours==1){
        	calculatedTime="an hour ago";
        }else if(diffHours<1 && diffMinutes>0){
        	calculatedTime=diffMinutes+" mins ago";
        }else{
        	calculatedTime="now";
        }
        }catch(Exception e){
        	
        }finally{

  		  time.setText(calculatedTime);
        }
     
        View toolbar = convertView.findViewById(R.id.toolbarexpand);
        ((RelativeLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -200;
        toolbar.setVisibility(View.GONE);
      
 
        return convertView;

}}
