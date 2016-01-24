package com.tk.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TimeClient {
	private static String hostUrl = "127.0.0.1";
	private static int PORT = 27780;
	private Double minD=new Double(1200);
	private NTPRequest minNTPrequest;
	private Socket socket;

	public TimeClient() {

		try {

			minNTPrequest = new NTPRequest();

			System.out.println("=================");
			System.out.println("  o\t  d");
			System.out.println("=================");

			for (int i = 0; i < 10; i++) {

				socket = new Socket(InetAddress.getByName(hostUrl), PORT);
				sendNTPRequest();
				/**
				 *  add 300ms of gap between 2 measurements
				 */
				this.threadSleep(300);

				minNTPrequest.calculateOandD();

			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendNTPRequest() {
		/**
		 * set T1
		 */
		minNTPrequest.setT1(System.currentTimeMillis());

		/**
		 * send request object
		 */

		try {

			ObjectOutputStream oOs = new ObjectOutputStream(socket.getOutputStream());
			oOs.writeObject(minNTPrequest);

			/**
			 * wait for server's response
			 */
			ObjectInputStream oIs = new ObjectInputStream(socket.getInputStream());
			minNTPrequest= (NTPRequest) oIs.readObject();
			//System.out.println("T2 "+minNTPrequest.getT2());
			oOs.close();
			oIs.close();

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		/**
		 * set t4 add offset 1200 ms
		 */
		minNTPrequest.setT4((long)(System.currentTimeMillis()+minD));

	}

	private void threadSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TimeClient();
	}

}
