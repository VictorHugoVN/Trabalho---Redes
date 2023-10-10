package redes;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ThreadSockets extends Thread{
	
	private Socket cliente;
	PrintStream toServer = null; 
    BufferedReader fromServer = null;
	private int contThreads;
    //private String nomes [] = new String [2];


	/*public ThreadSockets(Socket s, String[] nomes) {
        this.nomes [0] = nomes[0];
		this.nomes [1] = nomes[1];
		this.cliente = s; // Encapsular!!
	}*/
	
	public ThreadSockets(Socket s, int contadorThreads){
		this.cliente = s;
		this.contThreads = contadorThreads;
	}
	
	@Override
	public void run() {
		try {

			BufferedReader reader = null; // Leitor local do cliente
			PrintStream outputStream = null; // Fluxo de saída para o cliente
			String nomeCliente = "";
			String nomes[] = new String[2];
			
	
			String clientRequest = "";
			String responseToClient = "";

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
			   
	
				   
				try{
					if(responseToClient.equals("LOGIN")){
						// Servirá para contar as treads abertas e armazenar os nomes com o indice na thread que foi aberta, ou seja o primeiro jogador que se conectar terá o seu nome na posiçaão 0)
					
						// Lê o nome do cliente e aguarda o outro fazer o loguin para mandar iniciar o jogo.
						nomeCliente = reader.readLine();
						System.out.println("Nome do jogador " + this.contThreads + " -> "  + nomeCliente);  // *** um problema aqui é que eu n da pra passar essa lista para a thread executar o jogo antes do outro cliente se conectar
						nomes[this.contThreads] = nomeCliente;// colocando no arrey de nomes para que eu possa passar para a thread
					
	
	
					}else if(responseToClient.equals("JOGAR")){
						
						System.out.println("Nova Thread inicializada, cuidará do cliente " + nomes[this.contThreads]);
						outputStream.println(nomes);
						JogoDaVelha c = new JogoDaVelha();
						c.game(nomes[0], nomes[1]);
	
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
