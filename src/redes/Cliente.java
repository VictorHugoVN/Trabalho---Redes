package redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
 
class Cliente {
    public static void main(String args[]) throws Exception {

        String serverName = "localhost";
        int port = 8724; // Same port number with the server
        Socket socket = null;
        PrintStream toServer = null; 
        BufferedReader fromServer = null;

        System.out.println("Cliente TCP iniciado, usando servidor: " + serverName + ", Porta: " + port);
 
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";

         do {
            // Abrindo uma nova conexão de soquete para o servidor com o número de porta especificado
            socket = new Socket(serverName, port);// tentando conexão com o servidor

            //mecanismo que faz a leitura das tags e separa as strings
            System.out.print("Digite uma tag agora, (quit) para finalizar: ");
            System.out.flush();
            userInput = inFromUser.readLine().replace(" ", "");// aqui pega a string !!!!
            userInput = userInput.substring(userInput.indexOf("#")+1, userInput.indexOf("/#"));

            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }
 
            
            // Enviando entrada do usuário para o servidor
            toServer = new PrintStream(socket.getOutputStream()); 
            toServer.println(userInput);
            System.out.println("[TCPClient] Enviar entrada do usuário [" + userInput + "] ao Servidor.");
 
            // Recebendo resposta do servidor, recebende de volta as tags.
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseFromServer = fromServer.readLine();
            System.out.println("[TCPClient] Obtenha resposta [" + responseFromServer + "] do servidor.");

            try{
            if(responseFromServer.equals("LOGIN")){
                // pegando nome
                System.out.println("Digite o seu nome: ");
                System.out.flush();
                toServer.println(inFromUser.readLine()); //envia para o servidor o nome do cliente
            }
            if(responseFromServer.equals("JOGAR")){
                String nomeJogador = fromServer.readLine();
                for(int i =0 ; i < 2; i++)
                System.out.println("oioi");
                
                //userInput = "quit";
            }
            
        }catch(IOException e){
           
            System.out.println(" ### PROBLEMAS NO BLOCO DE TAGS NO CLIENTE, POR FAVOR REVEJA!!!! ### ");
            e.printStackTrace();    
        }
        } while (!userInput.equals("quit")); // Encerre o cliente se 'quit' for uma entrada
 
        // Close connection
        if (socket != null) {
            socket.close();
        }
    }
}
   