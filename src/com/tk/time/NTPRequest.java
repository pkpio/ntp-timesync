package com.tk.time;

import java.io.Serializable;


public class NTPRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public long t1; //time at client
	public long t2; //time at server when client request was received
	public long t3; //time at server after adding random delay
	public long t4; //time at client when server response was received
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
		// 
		// d=t+t'
		d=t2-t1+t4-t3;
		
		// o=oi+1/2(t'-t)
		o=0.5*d+0.5*(t4-t3-(t2-t1));
		System.out.println(o+"\t"+d);
		
	}
	
}
