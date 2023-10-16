package redes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import jogoDaVelha.Jogo;

public class ThreadSockets extends Thread{
	
	private Socket cliente;
	private int contThreads; 
	private String nomes [] = new String[2];

	PrintStream toServer = null; 
    BufferedReader fromServer = null;
	ObjectInputStream  reader = null; // Leitor local do cliente
	ObjectOutputStream  outputStream = null; // Fluxo de saída para o cliente
	String clientRequest = "";
	String responseToClient = "";
    

	public ThreadSockets(Socket s, int contadorThreads){
		this.cliente = s;
		this.contThreads = contadorThreads;
	}
	public ThreadSockets(Socket s, String nomes[]){
		this.cliente = s;
		this.nomes[0] = nomes[0];
		this.nomes[1] = nomes[1];
		//this.nomes[1] = nomes[1];
	}
	
	@Override
	public void run() {
		try {

			

			while (true) {
				// Recebendo solicitação do cliente
				reader = new ObjectInputStream (cliente.getInputStream()); // CONSIDERAR TRATAMENTO DE EXÇÃO!!!!
				clientRequest = (String) reader.readObject();// Mensagem do cliente
				String novaMensagem = clientRequest;
				System.out.println("[TCPServer] pegou solicitação [" + novaMensagem + "] do cliente.");
	
	
				// Enviando resposta ao cliente
				outputStream = new ObjectOutputStream (cliente.getOutputStream());
				responseToClient = novaMensagem.toUpperCase();
	
	
					System.out.println("[TCPServer] enviando resposta [" + responseToClient + "] para o cliente.");
					
				
				   
				try{
					 if(responseToClient.equals("JOGAR")){

						
							System.out.println("novo jogo enviado do servidor");
							outputStream.writeObject("nomes[0]");
							outputStream.writeObject("nomes[1]");
							outputStream.flush();

					}
						}catch (IOException e){
							
							System.out.println(" ### PROBLEMAS NO BLOCO DE TAGS NO SERVIDOR, POR FAVOR REVEJA!!!! ### ");
							e.printStackTrace();
						}
						
					outputStream.writeObject(responseToClient); //mandando de volta uma resposta para o cliente	

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
