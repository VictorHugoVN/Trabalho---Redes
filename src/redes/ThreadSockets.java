package redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import jogoDaVelha.Jogo;


public class ThreadSockets extends Thread{
	
	private Socket cliente = null;
	private int contadorCliente = 0; 
	private static String nomes [] = new String[3];
	private String nomeJogador = "";
	private String nomeJogador2 = "";


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

			/*if(contadorCliente != 1){
					System.out.println("cliente 2 conectado");
					contadorCliente++;
				}else{
					System.out.println("cliente 1 conectado");
				}*/

			while (true){
			
			String responseToClientC1 = "";
       		String nomeCliente1 = "";
       		ObjectOutputStream outputStream = null;// Fluxo de saída para o cliente
       	

					/////// TRATA AS INFORMAÇÕES DO  CLIENTE  QUE SE CONECTA //////////
					ObjectInputStream readerC1 = new ObjectInputStream (getCliente().getInputStream()); // serve para que eu consiga ler o que foi enviado pelo cliente
					String clientRequestC1 = (String) readerC1.readObject();// Mensagem do cliente
					String novaMensagemC1 = clientRequestC1;
					System.out.println("[TCPServer] pegou solicitação [" + novaMensagemC1 + "] do cliente.");

					// Enviando resposta ao cliente
					outputStream = new ObjectOutputStream (getCliente().getOutputStream());
					responseToClientC1 = novaMensagemC1.toUpperCase();
					System.out.println("[TCPServer] enviando resposta [" + responseToClientC1 + "] para o cliente.");
					outputStream.writeObject(responseToClientC1); //mandando de volta uma resposta para o cliente
	
				try{
					if(responseToClientC1.equals("LOGIN")){
						
						// Lê o nome do cliente e aguarda o outro fazer o login para mandar iniciar o jogo.
						nomeCliente1 = (String) readerC1.readObject();
						System.out.println("Nome do jogador " + contadorCliente + " é  -> "  + nomeCliente1);  // *** um problema aqui é que eu n da pra passar essa lista para a thread executar o jogo antes do outro cliente se conectar
						nomes[contadorCliente] = nomeCliente1;// colocando no arrey de nomes para que eu possa passar para a thread
					
					}else if(contadorCliente == 1){

						System.out.println(" Aguarde um parceiro de jogo !!! ");

					}else if(responseToClientC1.equals("JOGAR")){
						
						setNomeJogador(nomes[1]);
						setNomeJogador2(nomes[2]);
						Jogo j = new Jogo();
						j.game(getNomeJogador(), getNomeJogador2());
						
					}
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
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block //
				e1.printStackTrace();
			}
		}

	
		
	}
