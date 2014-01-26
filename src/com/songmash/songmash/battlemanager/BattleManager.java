package com.songmash.songmash.battlemanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;

import com.songmash.songmash.constants.RatingConstants;
import com.songmash.songmash.datastructure.Battle;
import com.songmash.songmash.datastructure.Song;
import com.songmash.songmash.filesystem.MP3Reader;
import com.songmash.songmash.filesystem.NoMp3sFoundException;
import com.songmash.songmash.util.RandomUtil;

/**
 * A singleton manager class for managing the battles between the songs.
 * The class provides different methods for battles between classes and updating the 
 * rating based on the winner of the class.
 * @author prashant
 *
 */

@SuppressLint("UseSparseArrays")
public final class BattleManager {
	private static BattleManager instance;
	private List<String> songsTitles = null;
	private List<Song> songs = null;
	private Map<Integer,Song> songCache = null;
	private Set<Integer> isBattleDoneMap = null;
	private Map<Integer, Battle> battles = null;
	private int numOfSongs = 0;
	private int numOfBattles = 0;
	
	private BattleManager() throws NoMp3sFoundException {
		try {
			songsTitles = MP3Reader.getMp3();
			songs = new ArrayList<Song>();
			songCache = new HashMap<Integer, Song>();
			Integer i = 0;
			for (String songTitle : songsTitles) {
				Song song = new Song(songTitle);
				songs.add(song);
				songCache.put(i, song);
				i = i + 1;
			}
			numOfSongs = i;
			numOfBattles = (numOfSongs * (numOfSongs - 1)) / 2;
			battles = new HashMap<Integer, Battle>();
			int k = 0;
			for(i = 0; i < numOfSongs; ++i) {
				for(int j = i + 1; j < numOfSongs; ++j) {
					battles.put(k, new Battle(i, j));
					++k;
				}
			}
			isBattleDoneMap = new HashSet<Integer>();
		} catch (NoMp3sFoundException e) {
			throw e;
		}
	}
	
	public static BattleManager getInstance() throws NoMp3sFoundException {
		// Double checking for thread safe implementation
		if(instance == null) {
			synchronized (BattleManager.class) {
				if(instance == null ){
					instance = new BattleManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * Returns the next random battle.
	 * Flow is if the randomly battle is already taken place try next random battle.
	 * If after 5 tries no battle is decided the next not completed battle is returned.
	 * @return null if all battles are done.
	 */
	public Battle nextBattle() {
		int nextRandom = RandomUtil.getInstance().nextRandom(numOfBattles);
		for(int i = 0; i < 5; ++i) {
			if(isBattleDoneMap.contains(Integer.valueOf(nextRandom))) {
				nextRandom = RandomUtil.getInstance().nextRandom(numOfBattles);
				continue;
			}
			isBattleDoneMap.add(nextRandom);
			return battles.get(nextRandom);
		}
		// Random was not able to return the battle. Return next remaining battle.
		for(int i = 0; i < numOfBattles; ++i) {
			if(isBattleDoneMap.contains(Integer.valueOf(i))) {
				continue;
			}
			isBattleDoneMap.add(i);
			return battles.get(i); 
		}
		return null;
	}
	
	/**
	 * Returns song for given index.
	 * @param index
	 * @return
	 */
	public Song getSong(int index) {
		if(songCache.containsKey(index)) {
			return songCache.get(index);
		}
		throw new IndexOutOfBoundsException();
	}
	
	/**
	 * Updates the ratings of song1 and song2 based on score1 and score2.
	 * Elo ranking is used to update the ratings of the songs.
	 */
	public void updateRating(int song1Id, int song2Id, int score1, int score2){
		Song song1 = getSong(song1Id);
		Song song2 = getSong(song2Id);
		double rating1 = song1.getRating();
		double rating2 = song2.getRating();
		
		double E1 = 1 / (1 + 10 * (rating2 - rating1) / RatingConstants.BASE_RATING);
		double E2 = 1 / (1 + 10 * (rating1 - rating2) / RatingConstants.BASE_RATING);
		
		song1.setRating(rating1 + RatingConstants.C1 * (score1 - E1));
		song2.setRating(rating2 + RatingConstants.C1 * (score2 - E2));
	}
	
	/**
	 * Returns the list of songs sorted by the descending order of their ratings.
	 */
	public List<Song> getRankedSongs() {
		List<Song> rankedSongs = new ArrayList<Song>(songs);
		Collections.sort(rankedSongs, new Comparator<Song>() {

			@Override
			public int compare(Song lhsSong, Song rhsSong) {
				if(lhsSong.getRating() > rhsSong.getRating()) {
					return -1;
				}
				return 1;
			}
		});
		return rankedSongs;
	}
} 
