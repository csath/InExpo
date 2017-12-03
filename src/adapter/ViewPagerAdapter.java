package adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter  {

	

		  private List<Fragment> fragments;


		  public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {

		    super(fm);

		    this.fragments = fragments;

		  }

		  @Override 

		  public Fragment getItem(int position) {

		    return this.fragments.get(position);

		  }


		  @Override

		  public int getCount() {

		    return this.fragments.size();

		  }
		 @Override
		public float getPageWidth(int position) {
			  return 1f;
		}
		
}
