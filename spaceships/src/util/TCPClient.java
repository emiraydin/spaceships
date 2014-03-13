package util;

import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient implements Runnable {

	// Initialize sockets and input/output streams
	private static Socket clientSocket = null;
	private static PrintStream output = null;
	private static BufferedReader input = null;

	private static BufferedReader inputLine = null;
	private static boolean connectionClosed = false;

	public static void main(String[] args) {

		System.out.println("Client is now running on " + Properties.HOST_NAME + " port " + Properties.PORT_NUMBER);

		// Open a socket on given host name and port number, and initialize input/output streams
		try {
			clientSocket = new Socket(Properties.HOST_NAME, Properties.PORT_NUMBER);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			output = new PrintStream(clientSocket.getOutputStream());
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + Properties.HOST_NAME);
		} catch (IOException e) {
			System.err.println("I/O connection to " + Properties.HOST_NAME + "cannot be established!");
		}

		// Write the data to the recently opened socket, given everything is set up
		if (clientSocket != null && output != null && input != null) {
			
			try {
				// Create new thread to read from the server
				new Thread(new TCPClient()).start();
				while (!connectionClosed) {
					output.println(inputLine.readLine().trim());
				}
				// Close streams and sockets
				output.close();
				input.close();
				clientSocket.close();
			} catch (IOException e) {
				System.err.println(e);
			}
			
		}
		
	}

	// Run the thread that will be created to read from the server
	public void run() {
		// Continue reading from the socket until the Goodbye signal is sent from the server
		String responseLine;
		try {
			while ((responseLine = input.readLine()) != null) {
				System.out.println(responseLine);
				if (responseLine.indexOf("Goodbye") != -1)
					break;
			}
			connectionClosed = true;
			// Exit the client application
			System.exit(0);
			
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}