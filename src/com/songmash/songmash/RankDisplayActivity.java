package com.songmash.songmash;

import java.util.List;

import com.songmash.songmash.battlemanager.BattleManager;
import com.songmash.songmash.datastructure.Song;
import com.songmash.songmash.filesystem.NoMp3sFoundException;
import com.songmash.songmash.util.PrintUtil;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class RankDisplayActivity extends Activity {
	
	private boolean showHeader = false; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank_display);
		try {
			List<Song> rankedSongs = BattleManager.getInstance().getRankedSongs();
		
			StringBuffer rankedSongsBuffer = new StringBuffer();
			for (Song song : rankedSongs) {
				rankedSongsBuffer.append(song.getTitle() + " : " + PrintUtil.DOUBLE_FORMAT.format(song.getRating()) + "\n\n");
			}
			TextView textView = (TextView)findViewById(R.id.textView1);
			textView.setText(rankedSongsBuffer.toString());
			
			TextView textView2 = (TextView)findViewById(R.id.textView2);
			if(showHeader) {
				textView2.setVisibility(View.VISIBLE);
			}else {
				textView2.setVisibility(View.INVISIBLE);
			}
			
		} catch (NoMp3sFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	public void setShowHeader(boolean showHeader) {
		this.showHeader = showHeader;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rank_display, menu);
		return true;
	}

}
