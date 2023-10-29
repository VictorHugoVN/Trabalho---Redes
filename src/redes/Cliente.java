package redes;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;



public class Cliente {

    public static void main(String args[]) throws Exception {

        String serverName = "localhost";
        int port = 8724; // Same port number with the server
        Socket socket = null;
        DataOutputStream toServer = null; 
        DataInputStream  fromServer = null;
        System.out.println("Cliente TCP iniciado, usando servidor: " + serverName + ", Porta: " + port);
 
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        String mensagemS;
         do {

            // Abrindo uma nova conexão de soquete para o servidor com o número de porta especificado
            socket = new Socket(serverName, port);// tentando conexão com o servidor

           // DataInputStream readoServer = new DataInputStream(socket.getInputStream());
           // mensagemS = readoServer.readUTF();

          
            //mecanismo que faz a leitura das tags e separa as strings

            System.out.print("Digite uma tag agora, (quit) para finalizar: ");
            System.out.flush();
            userInput = inFromUser.readLine().replace(" ", "");// aqui pega a string !!!!
            userInput = userInput.substring(userInput.indexOf("#")+1, userInput.indexOf("/#"));

            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }
 
            
            // Enviando entrada do usuário para o servidor
            toServer = new DataOutputStream(socket.getOutputStream()); 
            toServer.writeUTF(userInput);
            System.out.println("[TCPClient] Enviar entrada do usuário [" + userInput + "] ao Servidor.");
 
            // Recebendo resposta do servidor, recebende de volta as tags.
            fromServer = new DataInputStream (socket.getInputStream());
            String responseFromServer = (String) fromServer.readUTF();
            System.out.println("[TCPClient] resposta: [" + responseFromServer + "], do servidor.");

            try{
            if(responseFromServer.equals("LOGIN")){
                // pegando nome
                System.out.println("[#EntradaNome/#] Digite o seu nome: ");
                System.out.flush();
                toServer.writeUTF(inFromUser.readLine()); //envia para o servidor o nome do cliente
                /// o que posso fazer aqui é o servidor pegar o nome dos clientes e retornar para os 2 clientes a lista de nomes para que a thread possa
            }
            else if(responseFromServer.equals("JOGAR")){
                    String mensagem;
                    Scanner posicao = new Scanner(System.in);
                    String matriz[][] = new String[3][3];
                    boolean sair = false;
                    
                    //mensagem = fromServer.readUTF();
                    //System.out.println(mensagem);
                    System.out.println("[#EntradaPos1/#] digite a posição orizontal ");
                   int posY = posicao.nextInt();
                    toServer.writeInt(posY);
                    //System.out.println("[#EntradaPos1/#] Digite uma posição vertical");
                  // mensagem = fromServer.readUTF();
                    System.out.println("[#EntradaPos2/#] digite a posição Vertical ");
                    int posX = posicao.nextInt();
                    toServer.writeInt(posX);
                    fromServer.readUTF();
                   
                   
   
                  
            }
            else if(responseFromServer.equals("JOGAR1")){
                String mensagem;
                do{

                    mensagem = fromServer.readUTF();
                    System.out.println(mensagem);
                    Scanner posicao = new Scanner(System.in);
                   int posY = posicao.nextInt();
                    toServer.writeInt(posY);

                    System.out.println("Digite uma posição vertical");
                   mensagem = fromServer.readUTF();
                    int posX = posicao.nextInt();
                    toServer.writeInt(posX);
                    fromServer.readUTF();
                   
                   }while(fromServer.readUTF() == "JOGAR1");
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
   