package com.songmash.songmash.datastructure;

/**
 * A data class to store the participants in the battle.
 * @author prashant
 *
 */
public class Battle {
	private int song1;
	private int song2;
	
	public Battle(int song1, int song2) {
		this.song1 = song1;
		this.song2 = song2;
	}

	public int getSong1() {
		return song1;
	}

	public void setSong1(int song1) {
		this.song1 = song1;
	}

	public int getSong2() {
		return song2;
	}

	public void setSong2(int song2) {
		this.song2 = song2;
	}
	
}
