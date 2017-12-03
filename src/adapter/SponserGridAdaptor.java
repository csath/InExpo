package adapter;

import java.util.List;

import model.Sponsers;
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

public class SponserGridAdaptor extends BaseAdapter{

	private Activity activity;
    private LayoutInflater inflater;
    private List<Sponsers> sponserItems;
   // ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	 public SponserGridAdaptor(Activity activity, List<Sponsers> sponserItems) {
	        this.activity = activity;
	        this.sponserItems = sponserItems;
	        
	      
	    }
	
	
	@Override
	public int getCount() {
		return sponserItems.size();
	}

	@Override
	public Object getItem(int position) {
		return sponserItems.get(position);
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
	            convertView = inflater.inflate(R.layout.sponser_grid_view, null);
	        
	       
	        
	        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
	        TextView name = (TextView) convertView.findViewById(R.id.sponserType);
	        TextView sponserType = (TextView) convertView.findViewById(R.id.name);
	        TextView description = (TextView) convertView.findViewById(R.id.description);
	        
	        name.setText(sponserItems.get(position).getName());
	        sponserType.setText(sponserItems.get(position).getCategory());
	        description.setText(sponserItems.get(position).getDesctiption());
	        icon.setImageBitmap(StringToBitMap(sponserItems.get(position).getImage()));
	        
	        
	        
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
