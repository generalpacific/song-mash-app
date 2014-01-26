package com.songmash.songmash.filesystem;

public class NoMp3sFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NoMp3sFoundException(String message){
		super(message);
	}

}
