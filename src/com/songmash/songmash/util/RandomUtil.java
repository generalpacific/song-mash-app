package com.songmash.songmash.util;

import java.util.Random;

public final class RandomUtil {
	private static RandomUtil instance = null;
	private Random random = new Random();
	
	static {
		instance = new RandomUtil();
	}
	
	private RandomUtil() {
		// Private constructor;
	}
	
	public static RandomUtil getInstance() {
		return instance;
	}
	
	/**
	 * Returns next random between 0 and num
	 * @param num
	 * @return
	 */
	public int nextRandom(int num) {
		if(num < 0) {
			throw new RuntimeException("Num: " + num + " cannot be negative.");
		}
		int nextInt = random.nextInt();
		if(nextInt < 0) {
			nextInt *= -1;
		}
		return nextInt%num;
	}
}
