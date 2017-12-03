package adapter;

import java.util.List;

import model.Stalls;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import appcontroller.AppController;

import com.android.volley.toolbox.ImageLoader;
import com.example.inexpo.R;

public class CustomGridViewAdapter extends BaseAdapter{

	private Activity activity;
    private LayoutInflater inflater;
    private List<Stalls> stallItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	 public CustomGridViewAdapter(Activity activity, List<Stalls> stallItems) {
	        this.activity = activity;
	        this.stallItems = stallItems;
	        
	      
	    }
	
	
	@Override
	public int getCount() {
		return stallItems.size();
	}

	@Override
	public Object getItem(int position) {
		return stallItems.get(position);
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
	            convertView = inflater.inflate(R.layout.stall_grid_view, null);
	        
	        if (imageLoader == null)
	            imageLoader = AppController.getInstance().getImageLoader();
	        
	        com.android.volley.toolbox.NetworkImageView gridThumbNail = (com.android.volley.toolbox.NetworkImageView) convertView.findViewById(R.id.gridcover);
	        TextView gridTitle = (TextView) convertView.findViewById(R.id.gridtext);
	        
	        gridTitle.setText(stallItems.get(position).getStallName());
	        gridThumbNail.setImageUrl(stallItems.get(position).getImageProfile(),imageLoader);
	        
	        
		return convertView;
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

}
