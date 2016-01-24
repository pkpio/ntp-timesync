package com.tk.time;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TimeServer {
	private static int PORT = 27780;
	private ServerSocket serverSocket;
	NTPRequest request;

	public TimeServer() {
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server started on port: " + PORT);

		} catch (IOException e) {
			e.printStackTrace();
			try {
				serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	void startServer() {
		while (true) {
			try {
				/**
				 * wait for connection
				 */
				System.out.println("waiting for connection");
				Socket clientSocket = serverSocket.accept();
				InputStream is = clientSocket.getInputStream();
				ObjectInputStream oIs = new ObjectInputStream(is);
				request = (NTPRequest) oIs.readObject();
				if (request != null) {
					NTPRequestHandler client = new NTPRequestHandler(clientSocket);
					Thread clientThread = new Thread(client);
					clientThread.start();
				}
				is.close();
				oIs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		new TimeServer().startServer();
	}

	private class NTPRequestHandler implements Runnable {
		private Socket client;

		public NTPRequestHandler(Socket client) {
			this.client = client;

		}

		void threadSleep(long millis) {

			try {
				Thread.sleep(millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {

			/**
			 * set T2 value
			 */
			request.setT2(System.currentTimeMillis());
			/**
			 * aad random delay between 10 to 100 ms
			 */
			this.threadSleep(ThreadLocalRandom.current().nextLong(10, 100 + 1));

			/**
			 * set T3 value
			 */
			request.setT3(System.currentTimeMillis());

			/**
			 * send current system time to client
			 */
			sendNTPAnswer(request);

		}

		private void sendNTPAnswer(NTPRequest request) {
			/**
			 * write into socket
			 */
			
		    try {
		    	ObjectOutputStream oOs=new ObjectOutputStream(client.getOutputStream());
				oOs.writeObject(request);
				oOs.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/**
			 * close socket to client after a sleep
			 */
		    threadSleep(300);
			try {
				//client.close();
			} catch (Exception e) {
				System.out.println("failed to close socket");
				e.printStackTrace();
			}
		}

	}

}
