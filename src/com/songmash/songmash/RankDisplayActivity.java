package com.songmash.songmash;

import java.util.List;

import com.songmash.songmash.battlemanager.BattleManager;
import com.songmash.songmash.constants.StringConstants;
import com.songmash.songmash.datastructure.Song;
import com.songmash.songmash.filesystem.NoMp3sFoundException;
import com.songmash.songmash.util.PrintUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class RankDisplayActivity extends Activity {
	
	private boolean areBattlesDone = false; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank_display);
		
		Intent intent = getIntent();
		areBattlesDone = intent.getBooleanExtra(StringConstants.ARE_BATTLES_DONE, false);
		
		try {
			List<Song> rankedSongs = BattleManager.getInstance().getRankedSongs();
		
			StringBuffer rankedSongsBuffer = new StringBuffer();
			for (Song song : rankedSongs) {
				rankedSongsBuffer.append(song.getTitle() + " : " + PrintUtil.DOUBLE_FORMAT.format(song.getRating()) + "\n\n");
			}
			TextView textView = (TextView)findViewById(R.id.textView1);
			textView.setText(rankedSongsBuffer.toString());
			
			TextView textView2 = (TextView)findViewById(R.id.textView2);
			if(areBattlesDone) {
				textView2.setText("All the battles are done. This is the Final Ranking. Restart the app to reset ratings.");
			}else {
				textView2.setText("Present Rankings");
			}
			
		} catch (NoMp3sFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	@Override
	public void onBackPressed() {
		// if all battles are not done allow the user go back to next battle.
	    if(!areBattlesDone) {
	    	super.onBackPressed();
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rank_display, menu);
		return true;
	}

}
