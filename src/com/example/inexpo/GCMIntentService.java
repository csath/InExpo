package com.example.inexpo;

import static com.example.inexpo.CommonUtilities.SENDER_ID;
import static com.example.inexpo.CommonUtilities.displayMessage;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import database.DBHelper;
import database.UpdateHelper;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";
	private static long notificationId;
	
	
	
    public GCMIntentService() {
        super(SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
       // displayMessage(context, "Your device registred with GCM");
      //  Log.d("NAME", MainActivity.name);
        ServerUtilities.register(context, "test_name", "test_email", registrationId);
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
       // displayMessage(context, getString(R.string.gcm_unregistered));
        ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String messageRecieved = intent.getExtras().getString("price");
        
    	if(messageRecieved.equals("")){
			return;
		}
          // notifies user
     
      DBHelper dbHandler= new DBHelper(context);
      long when = System.currentTimeMillis();
	 
      try{
		
    	  String encr = messageRecieved.substring(0,1);
    	  String message = messageRecieved.substring(2);			
		
		int type= Integer.parseInt(encr);
		
		
		if(type>=0 && type <=4){
			dbHandler.insertNotification(message,0,type,when);
			 displayMessage(context, messageRecieved);
		    
			 if(!MainActivity.isNotificationOff){
		    	generateNotification(context, message); 		    	
		      }
			
		}else if(type>=6 && type<=8){
					new UpdateHelper(context).executeUpdate(type);				   							
		}
      }catch(Exception e){
    	  Log.e("my merror","error parse on message recieved");
      }
    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String messageRecieved = getString(R.string.gcm_deleted, total);
      //s  displayMessage(context, messageRecieved);
      
     //   generateNotification(context, message);
        
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
      //  displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
      //  displayMessage(context, getString(R.string.gcm_recoverable_error,
      //          errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
    	notificationId=System.currentTimeMillis();
    	int icon = R.drawable.ic_logo;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        
        String title = context.getString(R.string.app_name);
        
        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, (int)notificationId, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify((int)notificationId, notification);      

    }

}
