package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

class ClientThread extends Thread {

	private BufferedReader input = null;
	private PrintStream output = null;
	private Socket clientSocket = null;
	private final ClientThread[] clientThreads;
	private int maxPlayersOnServer;

	public ClientThread(Socket clientSocket, ClientThread[] threads) {
		this.clientSocket = clientSocket;
		this.clientThreads = threads;
		maxPlayersOnServer = threads.length;
	}

	public void run() {
		int maxPlayersOnServer = this.maxPlayersOnServer;
		ClientThread[] clientThreads = this.clientThreads;

		try {
			// Initialize input/output streams for this client
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			output = new PrintStream(clientSocket.getOutputStream());
			output.println("Enter your username: ");
			String name = input.readLine().trim();
			output.println("Welcome " + name + "! To exit game, type 'exit'");
			
			for (int i = 0; i < maxPlayersOnServer; i++) {
				if (clientThreads[i] != null && clientThreads[i] != this) {
					clientThreads[i].output.println(name + " entered the game");
				}
			}
			
			// If the user does not exit, display the messages on screen
			while (true) {
				
				String line = input.readLine();
				if (line.startsWith("exit")) {
					break;
				}

				for (int i = 0; i < maxPlayersOnServer; i++) {
					if (clientThreads[i] != null) {
						clientThreads[i].output.println(name + ": " + line);
					}
				}
			}
			for (int i = 0; i < maxPlayersOnServer; i++) {
				if (clientThreads[i] != null && clientThreads[i] != this) {
					clientThreads[i].output.println(name + " left the game!");
				}
			}
			output.println("Goodbye " + name + "!");

			// Clean up this thread to open up a space for a new player on the server
			for (int i = 0; i < maxPlayersOnServer; i++) {
				if (clientThreads[i] == this) {
					clientThreads[i] = null;
				}
			}

			// Close the socket and i/o streams
			input.close();
			output.close();
			clientSocket.close();
			
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
}

