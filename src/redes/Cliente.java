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
        String nomes[] = new String[2];

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
                System.out.println(" Aguarde um parceiro de jogo ");
                    ObjectInputStream fromServer2 = new ObjectInputStream(socket.getInputStream());  
                    nomes = (String[]) fromServer2.readObject();
                    
                    System.out.println("___________________________");
                    System.out.println("|         Jogadores       | ");
                    System.out.println("---------------------------");
                    System.out.println("    Jogador X -> "+nomes[0]);
                    System.out.println("    Jogador O -> "+nomes[1]);
                    System.out.println("____________________________");
                    System.err.println();
                    System.out.println("digite #jogar/# para iniciar a proxima rodada");
                    System.err.println();

                        
                        
            }
            else if(responseFromServer.equals("JOGAR")){
               
                
                    Scanner posicao = new Scanner(System.in);
                    String simbolo = "X";
                    boolean sair = false;
                    String opc;
                    System.out.println("[#EntradaPos1/#] digite a posição orizontal ");
                    int posY = posicao.nextInt();
                    if(posY>3 || posY<1){
                        System.out.println("Posição inválida!! Por favor digite a posição num range de 1 a 3");
                        System.out.println();
                        System.out.println("[#EntradaPos1/#] digite a posição orizontal ");
                        posY = posicao.nextInt();
                    }
                    
                    System.out.println("[#EntradaPos2/#] digite a posição Vertical ");
                    int posX = posicao.nextInt();
                    if(posX>3 || posX<1){
                        System.out.println("Posição inválida!! Por favor digite a posição num range de 1 a 3");
                        System.out.println();
                        System.out.println("[#EntradaPos2/#] digite a posição Vertical ");
                    
                         posX = posicao.nextInt();
                    }

                    if(matriz[posY-1][posX-1] == "X" || matriz[posY-1][posX-1] == "O"){
                        System.out.println();
                        System.out.println("posições ja marcadas no tabuleiro, escolha outras posições!!");
                        System.out.println();
                        System.out.println("[#EntradaPos1/#] digite a posição orizontal ");
                        posY = posicao.nextInt();
                        System.out.println();
                        System.out.println("[#EntradaPos2/#] digite a posição Vertical ");
                        posX = posicao.nextInt();
                    }
                    toServer.writeInt(posY);
                    toServer.writeInt(posX);

                    fromServer1 = new ObjectInputStream(socket.getInputStream());      
                    ArrayList<Integer> listaPos = new ArrayList<>(10);
                    listaPos = (ArrayList<Integer>) fromServer1.readObject();
                   System.out.println("elementos da lista: " + listaPos);
                    
                    if(listaPos.size() == 2){
                        matriz[listaPos.get(0) - 1][listaPos.get(1) - 1] = "X";
                    }else if(listaPos.size() == 4){
                        matriz[listaPos.get(0)-1][listaPos.get(1)-1] = "X";
                        matriz[listaPos.get(2)-1][listaPos.get(3)-1] = "O";
                    }

                  Object j1 =  fromServer1.readObject();
                  if (j1 instanceof Jogo) {
                    Jogo instancia = (Jogo) j1;
                    instancia.validacao(matriz, sair, simbolo);
                  
                }

                /*if (sair == true) {
                        if(matriz == "X"){
                            System.out.println("X ganhou!!");
                            break;
                        }else{
                        System.out.println("O ganhou!!");
                        break;
                        }*/

                }
    
            } 
        
            
        catch(IOException e){
           
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
   