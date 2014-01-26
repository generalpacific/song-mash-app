package com.songmash.songmash;

import java.util.List;

import com.songmash.songmash.battlemanager.BattleManager;
import com.songmash.songmash.datastructure.Song;
import com.songmash.songmash.filesystem.NoMp3sFoundException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class RankDisplayActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank_display);
		try {
			List<Song> rankedSongs = BattleManager.getInstance().getRankedSongs();
		
			StringBuffer rankedSongsBuffer = new StringBuffer();
			for (Song song : rankedSongs) {
				rankedSongsBuffer.append(song.getTitle() + ":" +song.getRating() + "\n");
			}
			TextView textView = (TextView)findViewById(R.id.textView1);
			textView.setText(rankedSongsBuffer.toString());
		} catch (NoMp3sFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rank_display, menu);
		return true;
	}

}
