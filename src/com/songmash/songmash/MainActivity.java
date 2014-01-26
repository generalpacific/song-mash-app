package com.songmash.songmash;

import com.songmash.songmash.battlemanager.BattleManager;
import com.songmash.songmash.datastructure.Battle;
import com.songmash.songmash.filesystem.NoMp3sFoundException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static BattleManager battleManager = null;
	private Battle currentBattle = null;
	private ProgressDialog pd;
	private Activity currentActivity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Async task to initialize battlemanager which involves reading all the mp3 files from the SD card. 
		AsyncTask<Void, Void, Void> battleManagerInitializerTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected void onPreExecute() {
				pd = new ProgressDialog(currentActivity);
				pd.setTitle("Reading MP3 files from SD card..");
				pd.setMessage("Please wait.");
				pd.setCancelable(false);
				pd.setIndeterminate(true);
				pd.show();
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				try {
					battleManager = BattleManager.getInstance();
				} catch (NoMp3sFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				if (pd!=null) {
					pd.dismiss();
				}
			}
			
		};
		
		battleManagerInitializerTask.execute((Void[])null);
		
		final Button button3 = (Button)findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Start rankDisplayActivity
				Intent intent = new Intent(getApplicationContext(), RankDisplayActivity.class);
			    startActivity(intent);

				
			}
		});
		
		final Button button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(currentBattle != null) {
					battleManager.updateRating(currentBattle.getSong1(), currentBattle.getSong2(), 100, 0);
				}
				nextBattle();
			}
		});
		
		final Button button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(currentBattle != null) {
					battleManager.updateRating(currentBattle.getSong1(), currentBattle.getSong2(), 0, 100);
				}
				nextBattle();
			}
		});
	}

	private void nextBattle() {
		Battle battle = battleManager.nextBattle();
		if(battle == null) {
			// TODO handle null
			return;
		}
		currentBattle = battle;
		TextView song1View = (TextView)findViewById(R.id.Song1);
		TextView song2View = (TextView)findViewById(R.id.Song2);
		song1View.setText(battleManager.getSong(battle.getSong1()).getTitle());
		song2View.setText(battleManager.getSong(battle.getSong2()).getTitle());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
