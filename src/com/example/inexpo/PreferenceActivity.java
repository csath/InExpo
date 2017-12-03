package com.example.inexpo;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class PreferenceActivity extends android.preference.PreferenceActivity{

	 private static final String TAG = PreferenceActivity.class.getSimpleName();

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        PreferenceManager.setDefaultValues(this, R.layout.notificationswitch, false);
	        addPreferencesFromResource(R.layout.notificationswitch);
	        findPreference("testKey").setOnPreferenceClickListener(mOnPreferenceClickListener);
	    }

	    @Override
	    protected void onStart() {
	        super.onStart();
	        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener);
	    }

	    @Override
	    protected void onStop() {
	        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener);
	        super.onStop();
	    }

	    private OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener = new OnSharedPreferenceChangeListener() {
	        @Override
	        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	            Toast.makeText(PreferenceActivity.this, String.valueOf(sharedPreferences.getBoolean(key, false)), Toast.LENGTH_SHORT).show();
	        }
	    };

	    private OnPreferenceClickListener mOnPreferenceClickListener = new OnPreferenceClickListener() {
	        @Override
	        public boolean onPreferenceClick(Preference preference) {
	            Log.d(TAG, "onPreferenceClick");
	            return true;
	        }
	    };
}
