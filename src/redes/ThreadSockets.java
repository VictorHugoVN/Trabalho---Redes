package redes;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import jogoDaVelha.Jogo;


public class ThreadSockets extends Thread{
	
	private Socket cliente = null;
	private int contadorCliente = 0; 
	private static String nomes [] = new String[3];
	private String nomeJogador = "";
	private String nomeJogador2 = "";
	private static Socket jogadores[] = new Socket[9];

	public ThreadSockets(Socket s){
		this.cliente = s;
	}

	public ThreadSockets(Socket socketC, int contadorCliente){
		this.cliente = socketC;
		this.contadorCliente = contadorCliente;
	}
	
	public String[] getNomes() {
		return nomes;
	}

	public void setNomes(String[] nomes) {

		this.nomes = nomes;
	}

	public ThreadSockets(Socket s, String nomes[]){
		this.cliente = s;
		this.nomes[0] = nomes[0];
		this.nomes[1] = nomes[1];
		//this.nomes[1] = nomes[1];
	}
	
	public String getNomeJogador() {
		return nomeJogador;
	}

	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}

	public String getNomeJogador2() {
		return nomeJogador2;
	}

	public void setNomeJogador2(String nomeJogador2) {
		this.nomeJogador2 = nomeJogador2;
	}
	
	public Socket getCliente() {
		return cliente;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}
	
	
	@Override
	public void run() {
		try {


			while (true){
			
			String responseToClientC1 = "";
       		String nomeCliente1 = "";
       		DataOutputStream outputStream = null;// Fluxo de saída para o cliente
				
			//if(contadorCliente % 2 == 0){
				
					//contadorCliente = 0;
			//}else{
					//contadorCliente = 1;
			//}

                    jogadores[contadorCliente] = getCliente();
					/////// TRATA AS INFORMAÇÕES DO  CLIENTE  QUE SE CONECTA //////////
					DataInputStream readerC1 = new DataInputStream (jogadores[contadorCliente].getInputStream()); // serve para que eu consiga ler o que foi enviado pelo cliente
					String clientRequestC1 = (String) readerC1.readUTF();// Mensagem do cliente
					String novaMensagemC1 = clientRequestC1;
					if(contadorCliente % 2 == 0)
					System.out.println("[TCPServer - Thread] pegou solicitação [" + novaMensagemC1 + "] do cliente X .");
					else
					System.out.println("[TCPServer - Thread] pegou solicitação [" + novaMensagemC1 + "] do cliente O .");

					// Enviando resposta ao cliente
					outputStream = new DataOutputStream (jogadores[contadorCliente].getOutputStream());
					responseToClientC1 = novaMensagemC1.toUpperCase();
					//System.out.println("[TCPServer - Thread] enviando resposta [" + responseToClientC1 + "] para o cliente " + contadorCliente + ".");
					if(contadorCliente % 2 == 0)
					System.out.println("[TCPServer - Thread] enviando resposta [" + responseToClientC1 + "] para o cliente X .");
					else
					System.out.println("[TCPServer - Thread] enviando resposta [" + responseToClientC1 + "] para o cliente O .");
					outputStream.writeUTF(responseToClientC1); //mandando de volta uma resposta para o cliente
	
				try{
					
					if(responseToClientC1.equals("LOGIN")){
						
						
						
						// Lê o nome do cliente e aguarda o outro fazer o login para mandar iniciar o jogo.
						nomeCliente1 = (String) readerC1.readUTF();
						if(contadorCliente % 2 == 0){
						System.out.println("[#PegaJogadorX/#] Nome do jogador X é  -> "  + nomeCliente1);  // *** um problema aqui é que eu n da pra passar essa lista para a thread executar o jogo antes do outro cliente se conectar
						}else if(contadorCliente % 2 != 0){
						System.out.println("[#PegaJogadorO/#] Nome do jogador O é  -> "  + nomeCliente1);  // *** um problema aqui é que eu n da pra passar essa lista para a thread executar o jogo antes do outro cliente se conectar
						}
						
						nomes[contadorCliente] = nomeCliente1;// colocando no arrey de nomes para que eu possa passar para a thread
						
					}/*else if(responseToClientC1.equals("JOGAR")){

							
							Jogo j = new Jogo();
							Scanner input = new Scanner (System.in);
							String[][] matriz = new String[3][3];
							String opc = "S";
							String simbolo = "X";
							boolean sair = false;	         
							int posicao_y = 0;
							int posicao_x = 0;
							
				//for(int N = 0; N<2 ; N++){
							
					do{
							if(contadorCliente == 0){

							outputStream.writeUTF("JOGAR");
							outputStream = new DataOutputStream(jogadores[0].getOutputStream());
							System.out.println("teste de chegada C1");
							//outputStream.writeUTF("[#EntradaPos1/#] digite a posição orizontal");
							posicao_y = (int) readerC1.readInt();
							System.out.println(posicao_y);
							//outputStream.writeUTF("[#EntradaPos2/#] digite a posição vertical");
							posicao_x = (int) readerC1.readInt();
							System.out.println(posicao_x);
							matriz[posicao_y - 1][posicao_x - 1] = "X";
							sair = j.validacao(matriz, sair, simbolo);
							
							  }else if(contadorCliente == 1){

								
							outputStream = new DataOutputStream(jogadores[1].getOutputStream());
							System.out.println("teste de chegada C2");
							outputStream.writeUTF("JOGAR");
							//outputStream.writeUTF("[#EntradaPos1/#] digite a posição orizontal");
							posicao_y = (int) readerC1.readInt();
							System.out.println(posicao_y);
							//outputStream.writeUTF("[#EntradaPos2/#] digite a posição vertical");
							posicao_x = (int) readerC1.readInt();
							System.out.println(posicao_x);
							//simbolo = "O";
							matriz[posicao_y - 1][posicao_x - 1] = "O";
							sair = j.validacao(matriz, sair, simbolo);
							}
							
						}while(contadorCliente == 1);
							
					//matriz[posicao_y - 1][posicao_x - 1] = simbolo;
					
						
	        
			
						
	}	*/		

		 
			}catch (IOException e){
							
							System.out.println(" ### PROBLEMAS NO BLOCO DE TAGS NO SERVIDOR, POR FAVOR REVEJA!!!! ### ");
							e.printStackTrace();
						}
					
					//System.out.println("[TCPServer - thread] enviando resposta [" + responseToClient + "] para o cliente.");
					//outputStream.writeObject(responseToClient); //mandando de volta uma resposta para o cliente	

		              //Lembrar que tenho que fechar essa thread em algum lugar 

				}

			}catch(IOException ioe) {
				Thread id = new Thread(); 
				System.out.println("Erro: Threads cuja o ID é: " + id.getId() + " " + ioe.toString());
			}
		}

	
		
	}
