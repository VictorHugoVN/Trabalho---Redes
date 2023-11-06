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
                System.out.println("[#askNome/#] Digite o seu nome: ");
                System.out.flush();
                System.out.println("[#GetNome/#]");
                toServer.writeUTF(inFromUser.readLine()); //envia para o servidor o nome do cliente
                System.out.println("[#Espera/#] Aguarde um parceiro de jogo ");
                    ObjectInputStream fromServer2 = new ObjectInputStream(socket.getInputStream());  
                    nomes = (String[]) fromServer2.readObject();
                    
                    System.out.println("        #Lista_jogadores/# ");
                    System.out.println("___________________________");
                    System.out.println("|         Jogadores       |");
                    System.out.println("---------------------------");
                    System.out.println("#SetSimbolo/# Jogador X -> "+nomes[0]);
                    System.out.println("#SetSimbolo/# Jogador O -> "+nomes[1]);
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
                    
                    System.out.println("[#askPosicao_horizontal/#] digite a posição orizontal ");
                    System.out.println("[#getPosição_hotizontal/#]");
                    int posY = posicao.nextInt();
                    if(posY>3 || posY<1){
                        System.out.println("[#erroPosicao/#] Posição inválida!! Por favor digite a posição num range de 1 a 3");
                        System.out.println();
                        System.out.println("[#askPosicao_vertical/#] digite a posição orizontal ");
                        posY = posicao.nextInt();
                    }
                    
                    System.out.println("[#askPosicao_Vertical/#] digite a posição Vertical ");
                    System.out.println("[#getPosição_vertical/#]");
                    int posX = posicao.nextInt();
                    if(posX>3 || posX<1){
                        System.out.println("[#erroPosicao/#] Posição inválida!! Por favor digite a posição num range de 1 a 3");
                        System.out.println();
                        System.out.println("[ #askPosicao_vertical/#] digite a posição Vertical ");
                        System.out.println("[#getPosição_vertical/#]");
                         posX = posicao.nextInt();
                    }

                    if(matriz[posY-1][posX-1] == "X" || matriz[posY-1][posX-1] == "O"){
                        System.out.println();
                        System.out.println("[#erroPosicao/#] posições ja marcadas no tabuleiro, escolha outras posições!!");
                        System.out.println();
                        System.out.println("[#askPosicao_horizontal/#] digite a posição orizontal ");
                        System.out.println("[#getPosição_hotizontal/#]");
                        posY = posicao.nextInt();
                        System.out.println();
                        System.out.println("[#askPosicao_Vertical/#] digite a posição Vertical ");
                        System.out.println("[#getPosição_vertical/#]");
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
                else if(responseFromServer.equals(("HISTORIA"))){
                    System.out.println();
                    System.out.println("Tem registros dele desde o século 14, lá no Egito. Só que foi mais pra frente, " +
                    "já no século 19, na Inglaterra, que o jogo ficou popular mesmo." +
                    "Era a brincadeira favorita da hora do chá das mulheres idosas, " + 
                    "que adoravam praticar esse passatempo pra relaxar, " + 
                    "e é daí que vem o nome: jogo da velha! " +
                    "Quando as mulheres inglesas se reuniam na hora do chá para bordar tinha aquelas mais " +
                    "velhas que não conseguiam mais fazer este ofício." +
                    "Muito dessas senhoras já apresentavam problemas de vista e não enxergavam o suficiente para conseguir bordar. " +
                    "A solução para conseguirem um novo passatempo foi jogar o jogo de velha.");
                    System.out.println();
    
                }
                else if(responseFromServer.equals("REGRAS")){
                    System.out.println();
                    System.out.println("Dois jogadores escolhem dois símbolo com que querem jogar. Normalmente"+
                    "é usado as letras X e O. O material do jogo é um tabuleiro, que pode ser desenhado, "+
                    "com três linhas e três colunas. Os espaços em branco dessas linhas e colunas serão " +
                    "preenchidos com os símbolos escolhidos." +
                    "O objetivo do jogo é preencher ou as linhas diagonais ou as horizontais ou "+
                    "as verticais com um mesmo símbolo (X ou O) e impedir que seu adversário faço isso "+
                    "primeiro que você.");
                    System.out.println();
                }
                else if(responseFromServer.equals("VERSAO")){
                         System.out.println();
                          System.out.println("1.0");
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
   