package redes;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import jogoDaVelha.Jogo;


public class ThreadSockets extends Thread{
	
	private Socket cliente = null;
	private int IDCliente = 0;
	//private ArrayList <Socket> contadorClienteL  = new ArrayList(2);
	private static String nomes [] = new String[3];
	private String nomeJogador = "";
	private String nomeJogador2 = "";
	private Socket jogadores[] = new Socket[2];
	Keep k;
	ArrayList<Integer> listaPos = new ArrayList<>(10);
	

	public ThreadSockets(Socket s){
		this.cliente = s;
	}

	public ThreadSockets(Socket socketC, int IDCliente){
		this.cliente = socketC;
		this.IDCliente = IDCliente;
	}
	public ThreadSockets(Socket x, Socket o){
		this.jogadores[0] = x;
		this.jogadores[1] = o;
		
	}
	/*public ThreadSockets(Socket socketC, ArrayList<Socket> IDCliente){
		this.cliente = socketC;
		this.IDClienteL = IDCliente;
	}*/
	public String[] getNomes() {
		return nomes;
	}

	public void setNomes(String[] nomes) {

		this.nomes = nomes;
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
			String[][] matriz = new String[3][3];
			String responseToClientC1 = "";
       		String nomeCliente1 = "";
       		DataOutputStream outputStream = null;// Fluxo de saída para o cliente


                 
					/////// TRATA AS INFORMAÇÕES DO  CLIENTE  QUE SE CONECTA //////////
					DataInputStream readerC1 = new DataInputStream (jogadores[IDCliente].getInputStream()); // serve para que eu consiga ler o que foi enviado pelo cliente
					String clientRequestC1 = (String) readerC1.readUTF();// Mensagem do cliente
					String novaMensagemC1 = clientRequestC1;
					if(IDCliente % 2 == 0)
					System.out.println("[TCPServer - Thread] pegou solicitação [" + novaMensagemC1 + "] do cliente X .");
					else
					System.out.println("[TCPServer - Thread] pegou solicitação [" + novaMensagemC1 + "] do cliente O .");

					// Enviando resposta ao cliente
					outputStream = new DataOutputStream (jogadores[IDCliente].getOutputStream());
					responseToClientC1 = novaMensagemC1.toUpperCase();

					if(IDCliente % 2 == 0)
					System.out.println("[TCPServer - Thread] enviando resposta [" + responseToClientC1 + "] para o cliente X .");
					else
					System.out.println("[TCPServer - Thread] enviando resposta [" + responseToClientC1 + "] para o cliente O .");
					outputStream.writeUTF(responseToClientC1); //mandando de volta uma resposta para o cliente
	
				try{
					
					if(responseToClientC1.equals("LOGIN")){
						
						
						
						// Lê o nome do cliente e aguarda o outro fazer o login para mandar iniciar o jogo.
						nomeCliente1 = (String) readerC1.readUTF();
						if(IDCliente % 2 == 0){
						System.out.println("[#PegaJogadorX/#] Nome do jogador X é  -> "  + nomeCliente1);  // *** um problema aqui é que eu n da pra passar essa lista para a thread executar o jogo antes do outro cliente se conectar
						}else if(IDCliente % 2 != 0){
						System.out.println("[#PegaJogadorO/#] Nome do jogador O é  -> "  + nomeCliente1);  // *** um problema aqui é que eu n da pra passar essa lista para a thread executar o jogo antes do outro cliente se conectar
						}
						nomes[IDCliente] = nomeCliente1;// colocando no arrey de nomes para que eu possa passar para a thread
						//IDCliente=1;
						
					}
					
					if(responseToClientC1.equals("JOGAR")){

							
							Jogo j = new Jogo();
							Scanner input = new Scanner (System.in);
							
							String simbolo = "X";
							boolean sair = false;	         
							int posicao_y1 = 0;
							int posicao_x1 = 0;
							int posicao_y2 = 0;
							int posicao_x2 = 0;
							int posC1C2[] = new int[4];
		
							
						if(IDCliente == 0){
							
								posicao_y1 = (int) readerC1.readInt();
								System.out.println(posicao_y1);
								posicao_x1 = (int) readerC1.readInt();
								System.out.println(posicao_x1);
								//k = new Keep(posicao_y1, posicao_x1);
								//k.AddPosYX();
								listaPos.add(posicao_y1);
								listaPos.add(posicao_x1);
								//System.out.println(Thread.currentThread());;
						

						}else if(IDCliente == 1){

								posicao_y1 = (int) readerC1.readInt();
								System.out.println(posicao_y1);
								posicao_x1 = (int) readerC1.readInt();
								System.out.println(posicao_x1);
								listaPos.add(posicao_y1);
								listaPos.add(posicao_x1);
								System.out.println(" os elementos da lista : " + listaPos);
								matriz[listaPos.get(0)-1][listaPos.get(1)-1] = "X";
								matriz[listaPos.get(2)-1][listaPos.get(3)-1] = "O";

								ObjectOutputStream outputStream1 = new ObjectOutputStream(jogadores[0].getOutputStream());
								ObjectOutputStream outputStream2 = new ObjectOutputStream(jogadores[1].getOutputStream());
								outputStream1.writeObject(listaPos);
								outputStream2.writeObject(listaPos);
								j.validacao(matriz, sair, simbolo);
								//j.game(nomes[0], nomes [1], listaPos.get(0), listaPos.get(1), listaPos.get(2), listaPos.get(3));
								outputStream1.writeObject(j);
								outputStream2.writeObject(j);
								
						}	
					}	
					if(responseToClientC1.equals("JOGAR1")){

							
							Jogo j = new Jogo();
							Scanner input = new Scanner (System.in);
							
							String simbolo = "X";
							boolean sair = false;	         
							int posicao_y1 = 0;
							int posicao_x1 = 0;
							int posicao_y2 = 0;
							int posicao_x2 = 0;
							int posC1C2[] = new int[4];
		
							
						if(IDCliente == 0){
							
								posicao_y1 = (int) readerC1.readInt();
								System.out.println(posicao_y1);
								posicao_x1 = (int) readerC1.readInt();
								System.out.println(posicao_x1);
								//k = new Keep(posicao_y1, posicao_x1);
								//k.AddPosYX();
								listaPos.add(posicao_y1);
								listaPos.add(posicao_x1);
								//System.out.println(Thread.currentThread());;
								matriz[listaPos.get(0)-1][listaPos.get(1)-1] = "X";
								//matriz[listaPos.get(4)-1][listaPos.get(5)-1] = "X";
								//matriz[listaPos.get(6)-1][listaPos.get(7)-1] = "O";
								ObjectOutputStream outputStream1 = new ObjectOutputStream(jogadores[0].getOutputStream());
								ObjectOutputStream outputStream2 = new ObjectOutputStream(jogadores[1].getOutputStream());
								outputStream1.writeObject(listaPos);
								outputStream2.writeObject(listaPos);
								j.validacao(matriz, sair, simbolo);
								//j.game(nomes[0], nomes [1], listaPos.get(0), listaPos.get(1), listaPos.get(2), listaPos.get(3));
								outputStream1.writeObject(j);
								outputStream2.writeObject(j);
						

						}else if(IDCliente == 1){
								posicao_y1 = (int) readerC1.readInt();
								System.out.println(posicao_y1);
								posicao_x1 = (int) readerC1.readInt();
								System.out.println(posicao_x1);
								listaPos.add(posicao_y1);
								listaPos.add(posicao_x1);
								System.out.println(" os elementos da lista : " + listaPos);
								matriz[listaPos.get(0)-1][listaPos.get(1)-1] = "X";
								matriz[listaPos.get(2)-1][listaPos.get(3)-1] = "O";
								//matriz[listaPos.get(4)-1][listaPos.get(5)-1] = "X";
								//matriz[listaPos.get(6)-1][listaPos.get(7)-1] = "O";
								
								ObjectOutputStream outputStream1 = new ObjectOutputStream(jogadores[0].getOutputStream());
								ObjectOutputStream outputStream2 = new ObjectOutputStream(jogadores[1].getOutputStream());
								outputStream1.writeObject(listaPos);
								outputStream2.writeObject(listaPos);
								j.validacao(matriz, sair, simbolo);
								//j.game(nomes[0], nomes [1], listaPos.get(0), listaPos.get(1), listaPos.get(2), listaPos.get(3));
								outputStream1.writeObject(j);
								outputStream2.writeObject(j);
								
						}	
					}

			}catch (IOException e){
							
							System.out.println(" ### PROBLEMAS NO BLOCO DE TAGS NO SERVIDOR, POR FAVOR REVEJA!!!! ### ");
							e.printStackTrace();
						}
					
					//System.out.println("[TCPServer - thread] enviando resposta [" + responseToClient + "] para o cliente.");
					//outputStream.writeObject(responseToClient); //mandando de volta uma resposta para o cliente	

		              //Lembrar que tenho que fechar essa thread em algum lugar 

					  if(IDCliente ==  0){
							 IDCliente = 1;
						System.out.println(" vez do cliente [o]");
					  }else if(IDCliente == 1){
							IDCliente = 0;
							System.out.println(" vez do cliente [x]");
					  }
					 
					  
				}

			}catch(IOException ioe) {
				Thread id = new Thread(); 
				System.out.println("Erro: Threads cuja o ID é: " + id.getId() + " " + ioe.toString());
			}
		}

	
		
	}
