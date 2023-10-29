package redes;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class ThreadSockets extends Thread{
	
	private Socket cliente;
	PrintStream toServer = null; 
    BufferedReader fromServer = null;
	private int contThreads;
	int matriz[][] = new int[3][3];
	String caracteres[][] = new String[3][3];



	/*public ThreadSockets(Socket s, String[] nomes) {
        this.nomes [0] = nomes[0];
		this.nomes [1] = nomes[1];
		this.cliente = s; // Encapsular!!
	}*/
	
	public ThreadSockets(Socket s, int contadorThreads){
		this.cliente = s;
		this.contThreads = contadorThreads;
	}

	BufferedReader reader = null; // Leitor local do cliente
			PrintStream outputStream = null; // Fluxo de saída para o cliente
			String nomeCliente = "";
			String nomes[] = new String[2];
			
	
			String clientRequest = "";
			String responseToClient = "";
	
	
	@Override
	public void run() {
		try {

			

			while (true) {
				// Recebendo solicitação do cliente
				reader = new BufferedReader(new InputStreamReader(cliente.getInputStream())); // CONSIDERAR TRATAMENTO DE EXÇÃO!!!!
				clientRequest = reader.readLine();// Mensagem do cliente
				String novaMensagem = clientRequest;
				System.out.println("[TCPServer] Get request [" + novaMensagem + "] from Client.");
	
	
				// Enviando resposta ao cliente
				outputStream = new PrintStream(cliente.getOutputStream());
				responseToClient = novaMensagem.toUpperCase();
	
	
			   
					
					System.out.println("[TCPServer] Send out response [" + responseToClient + "] to Client.");
					outputStream.println(responseToClient); //

					for(int l = 0; l < 3; l++){
							for(int c = 0; c < 3; c++){
								caracteres[l][c] = "_";
							}
						}
			   
	
				   
				try{
					if(responseToClient.equals("LOGIN")){
						// Servirá para contar as treads abertas e armazenar os nomes com o indice na thread que foi aberta, ou seja o primeiro jogador que se conectar terá o seu nome na posiçaão 0)
					
						// Lê o nome do cliente e aguarda o outro fazer o loguin para mandar iniciar o jogo.
						nomeCliente = reader.readLine();
						System.out.println("#SetSimbolo/# Jogador X -> "  + nomeCliente);  // *** um problema aqui é que eu n da pra passar essa lista para a thread executar o jogo antes do outro cliente se conectar
						//nomes[this.contThreads] = nomeCliente;// colocando no arrey de nomes para que eu possa passar para a thread
					
	
	
					}else if(responseToClient.equals("JOGAR")){
						
						System.out.println("Nova Thread inicializada, cuidará do cliente " + nomeCliente);
						//outputStream.println(nomeCliente);

						Boolean sair = false;
						int ativacao = 0;
						String vencedor = "";
						String simbolo = "";

						while(true){
							ativacao = 0;
							

							/*for(int l = 0; l < 3; l++){
								for(int c = 0; c < 3; c++){
									outputStream.println(caracteres[l][c]);
								}
							}*/

							// Recebendo linha, coluna e contador da vez, do cliente
							String y = reader.readLine();
							String x = reader.readLine();
							String contador = reader.readLine();

							// Convertendo para int
							int posY = Integer.parseInt(y);
							System.out.println("y " + posY);
							int posX = Integer.parseInt(x);
							System.out.println("x " + posX);
							int cont = Integer.parseInt(contador);

							if(cont % 2 == 0){ // Preenche para o jogador
								caracteres[posY-1][posX-1] = "X"; 
							}else{ // Vez do computador

								Random rand = new Random();
								int linha = rand.nextInt(3);
								int coluna = rand.nextInt(3);
								
								while(caracteres[linha][coluna] != "_"){
									linha = rand.nextInt(3);
									coluna = rand.nextInt(3);
								}
								caracteres[linha][coluna] = "O";

							}

							// Verificação de vencedor
							for (int yi = 0; yi < 3; yi++) {
								for (int xo = 0; xo < 3; xo++) {
									if (
											(caracteres[yi][0] == "O" && caracteres[yi][1] == "O" && caracteres[yi][2] == "O") ||											
											(caracteres[0][xo] == "O" && caracteres[1][xo] == "O" && caracteres[2][xo] == "O") ||											
											(caracteres[0][0] == "O" && caracteres[1][1] == "O" && caracteres[2][2] == "O") ||
											(caracteres[0][2] == "O" && caracteres[1][1] == "O" && caracteres[2][0] == "O") 
									){
										ativacao = 1;
										vencedor = "Computador";	
										cont = 9;
										simbolo = "O";
									}

									if((caracteres[yi][0] == "X" && caracteres[yi][1] == "X" && caracteres[yi][2] == "X") ||
										(caracteres[0][xo] == "X" && caracteres[1][xo] == "X" && caracteres[2][xo] == "X") ||
										(caracteres[0][0] == "X" && caracteres[1][1] == "X" && caracteres[2][2] == "X") ||
										(caracteres[0][2] == "X" && caracteres[1][1] == "X" && caracteres[2][0] == "X")
										){
											ativacao = 1;
											vencedor = "Jogador";
											cont = 9;
											simbolo = "X";
										}
								}
							}

							if(ativacao == 1){
								System.out.println("Vencedor" + vencedor);
							}
							
							// Enviar o contador, que servirá de condição de parada
							String contAtual = "";
							contAtual += cont;
							outputStream.println(contAtual);

							// Enviando símbolo do vencedor caso tenha algum, senão, envia espaço vazio
							outputStream.println(simbolo);


							for(int l = 0; l < 3; l++){ // Enviando a matriz devidamente preenchida para o jogador
								for(int c = 0; c < 3; c++){
									outputStream.println(caracteres[l][c]);
								}
							}

							if(cont == 9){
								break;
							}
						}

						

						
	
					}
						
				
				
				
				
				
				}catch (IOException e){
							
							System.out.println(" ### PROBLEMAS NO BLOCO DE TAGS NO SERVIDOR, POR FAVOR REVEJA!!!! ### ");
							e.printStackTrace();
						}

						
			
						// Close connection
						if (cliente != null) {
							cliente.close();
						}
						
						// 4 - Fechar socket de comunicação
						cliente.close();


				}
			}catch(IOException ioe) {
				Thread id = new Thread(); 
				System.out.println("Erro: Threads cuja o ID é: " + id.getId() + " " + ioe.toString());
			}
		}
		
	}