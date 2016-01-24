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
			System.out.println("waiting for connection");

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

				Socket clientSocket = serverSocket.accept();

				NTPRequestHandler client = new NTPRequestHandler(clientSocket);
				Thread clientThread = new Thread(client);
				clientThread.start();

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

			InputStream is;
			try {
				is = client.getInputStream();
				ObjectInputStream oIs = new ObjectInputStream(is);
				request = (NTPRequest) oIs.readObject();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// System.out.println("T1 "+request.getT1());
			/**
			 * set T2 value
			 */
			request.setT2(System.currentTimeMillis());
			/**
			 * add random delay between 10 to 100 ms
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
				ObjectOutputStream oOs = new ObjectOutputStream(client.getOutputStream());
				oOs.flush();
				oOs.writeObject(request);
				/**
				 * 
				 * close socket to client after a sleep
				 */
				threadSleep(300);
				oOs.close();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// TODO

			try {
				client.close();
			} catch (Exception e) {
				System.out.println("failed to close socket");
				e.printStackTrace();
			}
		}

	}

}
