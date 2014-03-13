package util;

import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class TCPServer {

  // Initialize the server and client sockets
  private static ServerSocket serverSocket = null;
  private static Socket clientSocket = null;

  // This game server can accept up to maxPlayersOnServer players
  private static final int maxPlayersOnServer = 10;
  private static final ClientThread[] clientThreads = new ClientThread[maxPlayersOnServer];

  public static void main(String args[]) {
    
    System.out.println("Server running using port " + Properties.PORT_NUMBER);

    // Open a server socket
    try {
      serverSocket = new ServerSocket(Properties.PORT_NUMBER);
    } catch (IOException e) {
      System.out.println(e);
    }

    // Open a client socket for each connection and pass it to a new thread
    while (true) {
      try {
        clientSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxPlayersOnServer; i++) {
          if (clientThreads[i] == null) {
            clientThreads[i] = new ClientThread(clientSocket, clientThreads);
            (clientThreads[i]).start();
            break;
          }
        }
        if (i == maxPlayersOnServer) {
          PrintStream os = new PrintStream(clientSocket.getOutputStream());
          os.println("Server too busy. Try later.");
          os.close();
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}