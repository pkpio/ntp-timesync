package com.tk.time;
import java.io.Serializable;


public class NTPRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public long t1;
	public long t2;
	public long t3;
	public long t4;
	public double o;
	public double d;

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
	
	public void calculateOandD() {
		////
	}
	
}
