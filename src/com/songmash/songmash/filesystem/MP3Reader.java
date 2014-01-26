package com.songmash.songmash.filesystem;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public final class MP3Reader {
	private MP3Reader(){
		// Cannot instantiate this class.
	}
	
	private static List<String> songs = new ArrayList<String>();

	public static List<String> getMp3() throws NoMp3sFoundException {
		// if songs is already updated return else populate songs.
		if(!songs.isEmpty()) {
			return songs;
		}
		populateMp3s(Environment.getExternalStorageDirectory());
		if(songs.isEmpty()) {
			throw new NoMp3sFoundException("Cannot read filesystem");
		}
		return songs;
	}
	
	/**
	 * Populate mp3 files in songs list.
	 * @param file
	 */
	private static void populateMp3s(File file) {
		if (file == null) {
			return;
		}
		String[] files = file.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				if (filename != null && filename.trim().endsWith(".mp3")) {
					return true;
				}
				return false;
			}
		});

		if (files != null) {
			for (String filename : files) {
				songs.add(filename);
			}
		}

		File[] listFiles = file.listFiles();
		if (listFiles == null) {
			return;
		}
		for (File currentFile : listFiles) {
			if (currentFile.isDirectory()) {
				populateMp3s(currentFile);
			}
		}
	}

}
