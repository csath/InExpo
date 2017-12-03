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
import android.widget.TextView;
import appcontroller.AppController;

import com.android.volley.toolbox.ImageLoader;
import com.example.inexpo.R;

public class CustomStallListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Stalls> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
 
    public CustomStallListAdapter(Activity activity, List<Stalls> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }
 
    @Override
    public int getCount() {
        return movieItems.size();
    }
 
    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
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
            convertView = inflater.inflate(R.layout.stall_list_row, null);
        
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
 
        com.android.volley.toolbox.NetworkImageView thumbNail = (com.android.volley.toolbox.NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
        
      
 
        // getting movie data for the row
        Stalls m = movieItems.get(position);
 
        
       thumbNail.setImageUrl(m.getImageProfile(), imageLoader);
       
        // title
        title.setText(m.getStallName());
         
        // rating
        rating.setText("Rating: " + String.valueOf(m.getRating()));
         
        genre.setText(m.getTags());
         
        year.setText(String.valueOf(m.getReviewCount())+" views");
 
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