package appcontroller;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
	 
    public static final String TAG = AppController.class
            .getSimpleName();
 
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
 
    private static AppController mInstance;
 
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
 
    public static synchronized AppController getInstance() {
        return mInstance;
    }
 
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
 
        return mRequestQueue;
    }
 
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }
 
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
 
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
 
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    @Override
    public Intent registerReceiver(BroadcastReceiver receiver,
    		IntentFilter filter) {
    	// TODO Auto-generated method stub
    	return super.registerReceiver(receiver, filter);
    }@Override
    public Intent registerReceiver(BroadcastReceiver receiver,
    		IntentFilter filter, String broadcastPermission, Handler scheduler) {
    	// TODO Auto-generated method stub
    	return super.registerReceiver(receiver, filter, broadcastPermission, scheduler);
    }@Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
    	// TODO Auto-generated method stub
    	super.unregisterReceiver(receiver);
    }
   @Override
    public void sendBroadcast(Intent intent) {
    	// TODO Auto-generated method stub
    	super.sendBroadcast(intent);
    }@Override
    public void sendBroadcast(Intent intent, String receiverPermission) {
    	// TODO Auto-generated method stub
    	super.sendBroadcast(intent, receiverPermission);
    }
    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
    	// TODO Auto-generated method stub
    	return super.bindService(service, conn, flags);
    }@Override
    public Context createPackageContext(String packageName, int flags)
    		throws NameNotFoundException {
    	// TODO Auto-generated method stub
    	return super.createPackageContext(packageName, flags);
    }
}
