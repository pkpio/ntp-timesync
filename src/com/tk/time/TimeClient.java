package com.tk.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class TimeClient {
	private static String hostUrl = "127.0.0.1";
	private static int PORT = 27780;
	private Double minD;
	private NTPRequest minNTPrequest;
	private Socket socket;

	public TimeClient() {

		try {

			minNTPrequest = new NTPRequest();

			System.out.println("=================================");
			System.out.println("o\t\t\td");
			System.out.println("=================================");

			for (int i = 0; i < 10; i++) {

				socket = new Socket(InetAddress.getByName(hostUrl), PORT);
				sendNTPRequest(minNTPrequest);

				socket.close();
				this.threadSleep(300);

				minNTPrequest.calculateOandD();

			}

			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendNTPRequest(NTPRequest request) {
		/**
		 * set T1
		 */
		request.setT1(System.currentTimeMillis());

		// send request object
		OutputStream os;
		try {
			os = socket.getOutputStream();
			ObjectOutputStream oOs = new ObjectOutputStream(os);
			oOs.writeObject(request);
			oOs.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// receive response

		/**
		 * set t4
		 */
		request.setT4(System.currentTimeMillis());

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
