package testes;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;
 
class Servidor {
    public static void main(String args[]) throws Exception {
        Socket socket = null;
        int port = 8722;
        /*BufferedReader reader = null; // Leitor local do cliente
        PrintStream outputStream = null; // Fluxo de saída para o cliente
        String nomeCliente = "";
        //List <String> listaNomes = new ArrayList<String>();
        String nomes[] = new String[2];

        String clientRequest = "";
        String responseToClient = "";*/
        ServerSocket ss = new ServerSocket(port);

        System.out.println("TCP Server is starting up, listening at port " + port + ".");
 
        while (true) {
            // Recebendo solicitação do cliente
            socket = ss.accept();
            //System.out.println("Cliente " + ss.getInetAddress().getHostAddress() + " conectado!");
            
            Thread thread = new ThreadSockets(socket);
            //new Thread(thread).start();
            thread.start();
        }
    }

    


}