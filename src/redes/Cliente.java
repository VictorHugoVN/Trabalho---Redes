package redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
 
public class Cliente {
    public static void main(String args[]) throws Exception {

        String serverName = "localhost";
        int port = 8724; // Same port number with the server
        Socket socket = null;
        PrintStream toServer = null; 
        BufferedReader fromServer = null;
        String meuNome ="";

        System.out.println("Cliente TCP iniciado, usando servidor: " + serverName + ", Porta: " + port);
 
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";

         do {
            // Abrindo uma nova conexão de soquete para o servidor com o número de porta especificado
            socket = new Socket(serverName, port);// tentando conexão com o servidor

            //mecanismo que faz a leitura das tags e separa as strings
            System.out.print("Digite uma tag agora, (#quit/#) para finalizar: ");
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
                meuNome = inFromUser.readLine();
                toServer.println(meuNome); //envia para o servidor o nome do cliente
                /// o que posso fazer aqui é o servidor pegar o nome dos clientes e retornar para os 2 clientes a lista de nomes para que a thread possa
            }
            if(responseFromServer.equals("JOGAR")){

                System.out.println("Inicializando jogo" + meuNome);
                int contador = 0;


            
                
                //Laço
                String matrizString[][] = new String[3][3];
                String sair = "";

                Cliente c = new Cliente();
                //c.renderizar(matrizString);

                while(true){

                    /*for(int l = 0; l < 3; l++){ // Lendo matriz do servidor
                        for(int col = 0; col < 3; col++){
                            matrizString[l][col] = fromServer.readLine();
                        }
                    }*/

                    

                    int posicao_y = 0;
                    int posicao_x = 0;
                    Scanner input = new Scanner (System.in);

                    if(contador % 2 == 0){ // Vez do jogador
                        System.out.print("Digite a posição horizontal: ");
                        posicao_y = input.nextInt();
                        System.out.print("Digite a posição vertical: ");
                        posicao_x = input.nextInt();
                    }

                    // Convertendo para String
                    String y = "";
                    String x = "";
                    String valorAtual = "";
                    y += posicao_y; // linha
                    x += posicao_x; // coluna
                    valorAtual += contador; // serve para indicar de quem é a vez

                    // Enviando ao servidor
                    toServer.println(y);
                    toServer.println(x);
                    toServer.println(valorAtual);


                    // Receber o contador novamente
                    String atualCont = "";
                    atualCont = fromServer.readLine();
                    contador = Integer.parseInt(atualCont);

                    // Recebendo símbolo do vencedor, caso tenha
                    String simb = fromServer.readLine();
                    if(simb.equals("X")){
                        System.out.println(meuNome + " Venceu!!");
                    }else if(simb.equals("O")){
                        System.out.println("Computador Venceu!!");
                    }

                    // Recebendo a matriz preenchida do servidor
                    for(int l = 0; l < 3; l++){
                        for(int co = 0; co < 3; co++){
                            matrizString[l][co] = fromServer.readLine();
                        }
                    }

                    c.renderizar(matrizString);
                    if(contador == 9){
                        if(simb.equals("")){
                            System.out.println("Deu velha!!");
                        }
                        break;
                    }
                    System.out.println();
                    System.out.println("Abaixo, a jogada do computador!");
                    contador++;




                    
                }

                



                /*Cliente jogo = new Cliente();
                jogo.game(meuNome, socket);*/
                
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


    public void renderizar(String matriz[][]){
        for(int l = 0; l < 3; l++){
            for(int c = 0; c < 3; c++){
                System.out.print("[" + matriz[l][c] + "] ");
            }
            System.out.println();
        }
    }









}
   