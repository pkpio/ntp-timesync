package com.tk.time;

import java.io.Serializable;


public class NTPRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long t1; //time at client
	private long t2; //time at server when client request was received
	private long t3; //time at server after adding random delay
	private long t4; //time at client when server response was received
	private double o;
	private double d;

	public NTPRequest() {
	
	}
	
	public long getT1() {
		return t1;
	}
	public void setT1(long t1) {
		this.t1 = t1;
	}
	public long getT2() {
		return t2;
	}
	public void setT2(long t2) {
		this.t2 = t2;
	}
	public long getT3() {
		return t3;
	}
	public void setT3(long t3) {
		this.t3 = t3;
	}
	public long getT4() {
		return t4;
	}
	public void setT4(long t4) {
		this.t4 = t4;
	}
	public double getD() {
		return d;
	}
	public double getO() {
		return o;
	}
	
	
	/**
	 * Calculates D and O values
	 */
	public void calculateOandD() {
		/**
		 * Delay evaluation formula :
		 * 		d = t + t’ = T(i-2) - T(i-3) + T (i) - T(i-1)
		 *  
		 *  Our mapping is:
		 *  T(i-3) -> T1
		 *  T(i-2) -> T2
		 *  T(i-1) -> T3
		 *  T(i)   -> T4
		 *  
		 * Revised formula for our notation :
		 *  	d = T2 - T1 + T4 - T3
		 */
		d = (t2 - t1) + (t4 - t3);
		
		/**
		 * Offset formula :
		 * 		o = 1/2 * (T(i-2) - T(i-3) + T(i-1) - T(i))
		 * 
		 * Revised formula for our notation :
		 * 		o = 1/2 * (T2 - T1 + T3 - T4)
		 */
		o = 0.5 * (t2 - t1 + t3 - t4);
		
		// Print these values
		System.out.println(o+"\t"+d);
	}

	/**
	 * Calculates the min accuracy of this measurement
	 * @return min accuracy
	 */
	public double getAccuracyMin(){
		return o - (d / 2);
	}
	
	/**
	 * Calculates the max accuracy of this measurement
	 * @return max accuracy
	 */
	public double getAccuracyMax(){
		return o + (d / 2);
	}
}
