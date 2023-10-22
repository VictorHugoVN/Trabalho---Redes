package redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import jogoDaVelha.Jogo; 

public class Cliente {

    public static void main(String args[]) throws Exception {

        String serverName = "localhost";
        int port = 8724; // Same port number with the server
        Socket socket = null;
        ObjectOutputStream toServer = null; 
        ObjectInputStream  fromServer = null;
        //String nome[] = new String[2];
        Jogo c ;
         String[] nome = new String[3];

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
            toServer = new ObjectOutputStream(socket.getOutputStream()); 
            toServer.writeObject(userInput);
            System.out.println("[TCPClient] Enviar entrada do usuário [" + userInput + "] ao Servidor.");
 
            // Recebendo resposta do servidor, recebende de volta as tags.
            fromServer = new ObjectInputStream (socket.getInputStream());
            String responseFromServer = (String) fromServer.readObject();
            System.out.println("[TCPClient] resposta: [" + responseFromServer + "], do servidor.");

            try{
            if(responseFromServer.equals("LOGIN")){
                // pegando nome
                System.out.println("Digite o seu nome: ");
                System.out.flush();
                toServer.writeObject(inFromUser.readLine()); //envia para o servidor o nome do cliente
                /// o que posso fazer aqui é o servidor pegar o nome dos clientes e retornar para os 2 clientes a lista de nomes para que a thread possa
            }
            if(responseFromServer.equals("JOGAR")){

               toServer.writeObject("JOGAR");
               System.out.println("Inicializando jogo Multiplayer");
              
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
   