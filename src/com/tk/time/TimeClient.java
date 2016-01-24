package com.tk.time;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class TimeClient {
	private static String hostUrl = "localhost";
	private static int PORT = 27780;
	private Double minD;
	private NTPRequest minNTPrequest;
	private Socket socket;

	public TimeClient() {

		try {

			for (int i = 0; i < 10; i++) {
				socket = new Socket(InetAddress.getLocalHost(), PORT);
				this.threadSleep(300);
				socket.close();
				
			}

			
			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private void sendNTPRequest(NTPRequest request) {
		//

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
