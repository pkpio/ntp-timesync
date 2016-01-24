package com.tk.time;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TimeServer {
	private static int PORT = 27780;
	private ServerSocket serverSocket;

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
	
	void startServer(){
		while (true) {
	        try {
	            //wait for connection
	        	System.out.println("waiting for connection");
	            Socket clientSocket = serverSocket.accept();
	            NTPRequestHandler client = new NTPRequestHandler(clientSocket);
	            Thread clientThread = new Thread(client);
	            clientThread.start();
	        } catch(Exception e){
	        	//e.printStackTrace();
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
			// aad random delay between 10 to 100 ms
			this.threadSleep(ThreadLocalRandom.current().nextLong(10,100+1));
			
			// Read current system time
			Long currentTime=System.currentTimeMillis();
			System.out.println("time is "+currentTime);
			// send current system time to client
			

		}

		private void sendNTPAnswer(NTPRequest request) {
			// write into socket
			
			//close socket to client
			try {
				client.close();
			} catch (IOException e) {
				System.out.println("failed to close socket");
				e.printStackTrace();
			}
		}

	}

}
