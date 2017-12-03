package com.example.inexpo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

@SuppressLint("NewApi") public class BrowserActivity extends Activity {
	
	private Toolbar toolbar;
	private static  String url = "https://google.lk/";
	private WebView browser;
	private TextView errortext1;
	private TextView errortext2;
	private RelativeLayout rel1;
	private ImageView image;
	GoogleAnalyticsTracker tracker;
	private static ProgressDialog csprogress;
	

protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
//	overridePendingTransition(R.layout.pull_in_right,
//			R.layout.push_out_left);
	setContentView(R.layout.activity_map);
	
	url=getIntent().getStringExtra("url");
	
	
///////////////////////////////
Window window = this.getWindow();
window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
window.setStatusBarColor(this.getResources().getColor(R.color.accent));
/////////////////////////////
	
	csprogress =new ProgressDialog(BrowserActivity.this);
	csprogress.setMessage("Loading...");
	toolbar = (Toolbar) findViewById(R.id.toolbarmap);
	errortext1=(TextView)findViewById(R.id.errortext1);
	errortext2=(TextView)findViewById(R.id.errortext2);
	rel1=(RelativeLayout)findViewById(R.id.rel1);
	image=(ImageView)findViewById(R.id.image);
	
	browser = (WebView) findViewById(R.id.webView1);
	browser.setWebViewClient(new MyBrowser());
	browser.getSettings().setLoadsImagesAutomatically(true);
	browser.getSettings().setJavaScriptEnabled(true);
	browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
	
	toolbar.setTitleTextColor(getResources().getColor(R.color.textcolor));
	
	final Activity activity = this;
    tracker = GoogleAnalyticsTracker.getInstance();
    // Start the tracker, updating google every 20 seconds
    
    tracker.start((String) getText(R.string.analyticsID), 20, this);

    browser.setWebChromeClient(new WebChromeClient() { 
      public void onProgressChanged(WebView view, int progress) { 
        activity.setProgress(progress * 100 ); 
      } 
    }); 

    browser.setWebViewClient(new WebViewClient() { 
    	public void onLoadResource(WebView view, String url) {
    		  browser.setVisibility(View.VISIBLE);
              errortext1.setVisibility(View.GONE);
              errortext2.setVisibility(View.GONE);
              image.setVisibility(View.GONE);
              
    	};
    	
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
           
            browser.setVisibility(View.GONE);
            errortext1.setVisibility(View.VISIBLE);
            errortext2.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            
            rel1.setOnClickListener(new  View.OnClickListener(){
             
				@Override
				public void onClick(View v) {
					browser.loadUrl(url);
									}

            });
            //setContentView(R.layout.activity_map_load_fail);
        }
    });
	
    
     
	toolbar.setTitle("Navigation Map");
	toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
	toolbar.setNavigationOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			onBackPressed();
		}
	});
	
	browser.loadUrl(url);

}
private class MyBrowser extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
	}
}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
if(browser.canGoBack()){
        browser.goBack();
}else{
	onBackPressed();
}
        return true;
    }
    return super.onKeyDown(keyCode, event);
}

@Override
public void onResume() {
  //  tracker.trackPageView("ArticlesActivity");
    super.onResume();
}
@Override
protected void onDestroy() {
  super.onDestroy();
  // Stop the tracker when it is no longer needed.
  tracker.stop();
}


}
