package redes;

import java.io.*;
import java.net.*;
 
class Servidor {
    public static void main(String args[]) throws Exception {
        int port = 8722;
        Socket socket = null;
        BufferedReader reader = null; // Local reader from the client
        PrintStream outputStream = null; // Output stream to the client
 
        String clientRequest = "";
        String responseToClient = "";
        ServerSocket ss = new ServerSocket(port);
        System.out.println("TCP Server is starting up, listening at port " + port + ".");
 
        while (true) {
            // Get request from client
            socket = ss.accept();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // CONSIDERAR TRATAMENTO DE EXÇÃO!!!!
            clientRequest = reader.readLine().replace(" ", "");// Mensagem do cliente
            String novaMensagem = clientRequest.substring(clientRequest.indexOf("#")+1, clientRequest.length()-1); // ANALISAR
            System.out.println("[TCPServer] Get request [" + novaMensagem + "] from Client.");
 
            // Send response to client
            outputStream = new PrintStream(socket.getOutputStream());
            responseToClient = clientRequest.toUpperCase();
            outputStream.println(responseToClient);
            System.out.println("[TCPServer] Send out response [" + responseToClient + "] to Client.");
        }
    }
}