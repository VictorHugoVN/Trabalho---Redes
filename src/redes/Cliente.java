package redes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
 
class Cliente {
    public static void main(String args[]) throws Exception {
        String serverName = "localhost";
        int port = 8722; // Same port number with the server
        Socket socket = null;
        PrintStream toServer = null; 
        BufferedReader fromServer = null;
        ThreadSockets novaThread;

        
        System.out.println("Cliente TCP iniciado, usando servidor: " + serverName + ", Porta: " + port);
 
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";

        do {

            
            socket = new Socket(serverName, port);
            novaThread = new ThreadSockets(socket);
            novaThread.start();
            //novaThread.run();

        } while (!userInput.equals("quit")); 
 
        // Close connection
        if (socket != null) {
            socket.close();
        }
    }
}
   