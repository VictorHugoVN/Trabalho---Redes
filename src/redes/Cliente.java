package redes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
 
class Cliente {
    public static void main(String args[]) throws Exception {
        String serverName = "localhost";
        int port = 8722; // Same port number with the server
        Socket socket = null;
        PrintStream toServer = null;
        BufferedReader fromServer = null;
        String nomeCliente = "";
 
        System.out.println("Cliente TCP iniciado, usando servidor: " + serverName + ", Porta: " + port);
 
        // Read from user input
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        do {
            System.out.print("Digite qualquer string agora: ( #quit/# para finalizar ) ");
            System.out.flush();
            userInput = inFromUser.readLine().replace(" ", "");// aqui pega a string !!!!
            userInput = userInput.substring(userInput.indexOf("#")+1, userInput.indexOf("/#"));
            //System.out.println("USER INPUT -> " + userInput);
            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }
 
            // Abra uma nova conexão de soquete para o servidor com o número de porta especificado
            socket = new Socket(serverName, port);

        
 
            // Enviando entrada do usuário para o servidor
            toServer = new PrintStream(socket.getOutputStream()); 
            toServer.println(userInput); // AQUI
            
            
            // Temos que enviar de volta ao servidor !!!!
            System.out.println("[TCPClient] Enviar entrada do usuário [" + userInput + "] ao Servidor.");
 
            // Recebendo resposta do servidor
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String responseFromServer = fromServer.readLine(); // AQUI
            //toServer.println(responseFrom)
            System.out.println("[TCPClient] Obtenha resposta [" + responseFromServer + "] do servidor.");
            if(responseFromServer.equals("LOGIN")){
                // pegando nome
                System.out.println("Digite o seu nome: ");
                System.out.flush();
                nomeCliente = inFromUser.readLine();
                // enviando nome
                toServer.println(nomeCliente);
            }
            if(responseFromServer.equals("JOGAR")){

                String msgServer = fromServer.readLine();

                if(msgServer.equals("aguarde")){
                    System.out.println("Ainda não temos dois jogadores, aguarde!");
                }else if(msgServer.equals("ok")){
                    String n1 = fromServer.readLine();
                    String n2 = fromServer.readLine();
                    if(n1 != null && n2 != null){
                        Cliente c = new Cliente();
                        if(nomeCliente.equals(n1)){ // Posso fazer esperar aqui?????
                            c.jogoDaVelha(n1, n2, toServer, fromServer, "X");
                        }else if(nomeCliente.equals(n2)){
                            c.jogoDaVelha(n1, n2, toServer, fromServer, "O");
                        }
                        
                    }
                    userInput = "#quit/#";
                }

                
                /*String nomeJogador = fromServer.readLine();
                String simb = null;
                if(nomeJogador.equals("Thread-1")){
                    simb = "X";
                }else if(nomeJogador.equals("Thread-3")){
                    simb = "O";
                }
                Cliente c = new Cliente();
                c.jogoDaVelha(nomeJogador, toServer, simb, fromServer);
                userInput = "quit";*/
            }

        } while (!userInput.equals("quit")); // Encerre o cliente se 'quit' for uma entrada
 
        // Close connection
        if (socket != null) {
            socket.close();
        }
    }

    // simb = x ou o
    public void jogoDaVelha(String nome1, String nome2, PrintStream toServer, BufferedReader fromServer, String simb){
        Scanner input = new Scanner (System.in);
		String[][] matriz = new String[3][3];
        String opc;
        String pos_y;
        String pos_x;
        int posicao_x;
        int posicao_y;
        PrintStream toServerr = toServer;
        BufferedReader fromServerr = fromServer;



        String jogador1 = nome1;
        String jogador2 = "Segundo Jogador";
        String simbolo = simb;
        boolean sair = false;

         //System.out.print("Jogador X: ");
         //jogador1 = input.next();
         //System.out.print("Jogador O: ");
         //jogador2 = input.next();

         try {
         	do {    
        		 for (int cont = 0; cont < 9; cont++) { 
        			 do {
                            
	                        System.out.print("Digite a posição horizontal: ");
	                        pos_y = input.nextLine();
                            toServerr.println(pos_y); // Enviando posição da linha
	                        
                            System.out.print("Digite a posição vertical: ");
	                        pos_x = input.nextLine();                            
                            toServerr.println(pos_x); // Enviando posição da coluna
                            toServerr.println(simb);

                            posicao_x = Integer.parseInt(pos_x);
                            posicao_y = Integer.parseInt(pos_y);

	                 } while (posicao_y <= 0 || posicao_y > 3 ||
	                          posicao_x <= 0 || posicao_x > 3 ||
	                          matriz[posicao_y - 1][posicao_x - 1] != null);
	
                    //Não precisaremos
	                 matriz[posicao_y - 1][posicao_x - 1] = simbolo;
	
                     /*
	                 for (int y = 0; y < 3; y++) { //VERIFICAÇÃO DE VENCEDOR(A), levar para o sevidor!
	                     for (int x = 0; x < 3; x++) {//Verifica se alguém venceu
	                    	 if ((matriz[y][0] == "X" && matriz[y][1] == "X" && matriz[y][2] == "X") ||
		                             (matriz[y][0] == "O" && matriz[y][1] == "O" && matriz[y][2] == "O") ||
		                             (matriz[0][x] == "X" && matriz[1][x] == "X" && matriz[2][x] == "X") ||
		                             (matriz[0][x] == "O" && matriz[1][x] == "O" && matriz[2][x] == "O") ||
		                             (matriz[0][0] == "X" && matriz[1][1] == "X" && matriz[2][2] == "X") ||
		                             (matriz[0][0] == "O" && matriz[1][1] == "O" && matriz[2][2] == "O") ||
		                             (matriz[0][2] == "X" && matriz[1][1] == "X" && matriz[2][0] == "X") ||
		                             (matriz[0][2] == "O" && matriz[1][1] == "O" && matriz[2][0] == "O")) {
		                             sair = true;
		                             break;
		                     }
	                    	 
	                         if (matriz[y][x] == null) //Imprimindo Matriz
	                             System.out.print("[_]");
	                         else
	                             System.out.print("[" + matriz[y][x] + "]");
	                     }
	
	                     if (sair == true)
	                         break;
	
	                     System.out.println();
	                 } // Fim verificação*/


                     //Receber posições já usadas
                     for (int y = 0; y < 3; y++) { 
	                     for (int x = 0; x < 3; x++) {

                            String s = fromServerr.readLine();
                            matriz[y][x] = s;

                            if (matriz[y][x].equals(null)) //Imprimindo Matriz
                                System.out.print("[_]");
                            else
                                System.out.print("[" + matriz[y][x] + "]");
                         }
                         System.out.println();
                    }
	
	                 if (sair == true) 
	                     break;
	
	                 if (simbolo == "X") {
	                     simbolo = "O";
	                 } else {
	                     simbolo = "X";
	                 }
	             }
	
	             if (sair == true) {
	                 if(simbolo == "X")
	                     System.out.println(jogador1 + " ganhou!!");
	                 else
	                     System.out.println(jogador2 + " ganhou!!");
	             }
	
	             System.out.print("Deseja começar novamente [S] ou [N]? ");
	             opc = input.next().toUpperCase();
                 if(opc.equals("S")){
                    // QUIT
                 }
	
	             sair = false;
	             simbolo = "X";
	             for (int y = 0; y < 3; y++) {
	                 for (int x = 0; x < 3; x++)
	                     matriz[y][x] = null;
	             }
         	} while (opc.toUpperCase().equals("S"));
        } catch (InputMismatchException e) {
   			System.out.println("Você digitou o valor errado!");
   			//main(null);
   		} catch (Exception e) {
   			System.out.println("ERRO!");
   			e.printStackTrace();
   		} 
         	
        System.out.println("Obrigado por testar o programa!");	
     	input.close();
	
    }


}