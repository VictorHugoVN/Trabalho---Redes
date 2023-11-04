package redes;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Scanner;

import jogoDaVelha.Jogo;



public class Cliente {

    public static void main(String args[]) throws Exception {

        String serverName = "localhost";
        int port = 8724; // Same port number with the server
        Socket socket = null;
        DataOutputStream toServer = null; 
        DataInputStream  fromServer = null;
        System.out.println("Cliente TCP iniciado, usando servidor: " + serverName + ", Porta: " + port);
        Jogo j = new Jogo();
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        String mensagemS;
        ObjectInputStream fromServer1;
        String[][] matriz = new String[3][3];

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
             
             if (userInput.equalsIgnoreCase("QUIT")) {
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
               
                
                     int cont = 0;
                     Scanner posicao = new Scanner(System.in);
                    //String matriz[][] = new String[3][3];
                    String simbolo = "X";
                   
                    boolean sair = false;
                    System.out.println("[#EntradaPos1/#] digite a posição orizontal ");
                    cont++;
                    int posY = posicao.nextInt();
                    if(posY>3 || posY<1){
                        System.out.println("Posição inválida!! Por favor digite a posição num range de 1 a 3");
                        System.out.println();
                        System.out.println("[#EntradaPos1/#] digite a posição orizontal ");
                        posY = posicao.nextInt();
                    }
                    toServer.writeInt(posY);
                    System.out.println("[#EntradaPos2/#] digite a posição Vertical ");
                    cont++;
                    int posX = posicao.nextInt();
                    if(posX>3 || posX<1){
                        System.out.println("Posição inválida!! Por favor digite a posição num range de 1 a 3");
                        System.out.println();
                        System.out.println("[#EntradaPos2/#] digite a posição Vertical ");
                    
                         posX = posicao.nextInt();
                    }
                    toServer.writeInt(posX);

                    fromServer1 = new ObjectInputStream(socket.getInputStream());      
                    ArrayList<Integer> listaPos = (ArrayList<Integer>) fromServer1.readObject();
                    matriz[listaPos.get(0)-1][listaPos.get(1)-1] = "X";
                    matriz[listaPos.get(2)-1][listaPos.get(3)-1] = "O";

                  Object j1 =  fromServer1.readObject();
                  if (j1 instanceof Jogo) {
                    Jogo instancia = (Jogo) j1;
                    sair = instancia.validacao(matriz, sair, simbolo);
                  
                }

                if (sair == true) {
                        if(simbolo == "X"){
                            System.out.println("X ganhou!!");
                            break;
                        }else{
                        System.out.println("O ganhou!!");
                        break;
                        }
                       
                       
                        
                    
                }
    
            }  else if(responseFromServer.equals("JOGAR1")){
               
                
                     int cont = 0;
                     Scanner posicao = new Scanner(System.in);
                    //String matriz[][] = new String[3][3];
                   String simbolo = "X";
                   
                    boolean sair = false;
                    System.out.println("[#EntradaPos1/#] digite a posição orizontal ");
                    cont++;
                    int posY = posicao.nextInt();
                    toServer.writeInt(posY);
                    System.out.println("[#EntradaPos2/#] digite a posição Vertical ");
                    cont++;
                    int posX = posicao.nextInt();
                    toServer.writeInt(posX);
                
                    fromServer1 = new ObjectInputStream(socket.getInputStream());      
                    ArrayList<Integer> listaPos = (ArrayList<Integer>) fromServer1.readObject();
                    
                    matriz[listaPos.get(0)-1][listaPos.get(1)-1] = "X";
                    matriz[listaPos.get(2)-1][listaPos.get(3)-1] = "O";
                    //matriz[listaPos.get(4)-1][listaPos.get(5)-1] = "X";
					//matriz[listaPos.get(6)-1][listaPos.get(7)-1] = "O";

                  Object j1 =  fromServer1.readObject();
                  if (j1 instanceof Jogo) {
                    Jogo instancia = (Jogo) j1;
                    instancia.validacao(matriz, sair, simbolo);
                }
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
   