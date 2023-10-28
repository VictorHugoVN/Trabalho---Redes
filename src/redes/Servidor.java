package redes;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
 
class Servidor {
        private static int contadorThreads = -1;

    public static void main(String args[]) throws Exception {
        
        int port = 8724;
        Socket socket = null;
       
        ServerSocket ss = new ServerSocket(port); // abre o socket
        System.out.println("TCP Server is starting up, listening at port " + port + ".");
        
        while(true){
            socket = ss.accept();// aceita conexão
        
        
            contadorThreads++;
            Thread clienteThread = new ThreadSockets(socket, contadorThreads);
            clienteThread.start();
 
        }
    }
}