package Teste;

import java.io.*;
import java.net.*;
import java.util.List;

import redes.ThreadSockets;

import java.util.ArrayList;


// TESTE DE RAMIFICAÇÃO


 
class Servidor {
    private static int contadorThreads = -1;
    private static int contadorAtivacao = -1;
    public static void main(String args[]) throws Exception {
        Socket socket = null;
        int port = 8722;
        
        ServerSocket ss = new ServerSocket(port);

        System.out.println("TCP Server is starting up, listening at port " + port + ".");
 
        while (true) {
            // Recebendo solicitação do cliente
            socket = ss.accept();
            System.out.println("Cliente " + ss.getInetAddress().getHostAddress() + " conectado!");

            ThreadSockets clienteThread = new ThreadSockets(socket, port);
            clienteThread.start();

            

            


        }
    }

    


}


