package com.tk.time;

import java.util.concurrent.ThreadLocalRandom;

public class Util {
	
	/**
	 * Induced offset between server and client clocks (client is lagging)
	 */
	public static final long SERVER_OFFSET = 1200;
	
	/**
	 * Network IP address
	 */
	public static String HOST_ADDR = "127.0.0.1";
	

	/**
	 * Network port
	 */
	public static int HOST_PORT = 27780;
	
	/**
	 * Get a random delay between 10 - 100 ms
	 * @return
	 */
	public static long getRandomDelay(){
		return ThreadLocalRandom.current().nextLong(10, 100 + 1);
	}
	
	/**
	 * Attempts to sleep the calling thread for given duration
	 * @param duration
	 */
	public static void sleepThread(long duration){
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
