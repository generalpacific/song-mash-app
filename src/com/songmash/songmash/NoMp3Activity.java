package com.songmash.songmash;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NoMp3Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_no_mp3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.no_mp3, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
	    // Do Nothing
	}

}
