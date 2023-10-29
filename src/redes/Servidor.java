package redes;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

import jogoDaVelha.Jogo;
public class Servidor {
    

    public static void main(String args[]  ) throws Exception {
        
        int port = 8724;
        Socket socketC1 = null;
        Socket socketC2 = null;
       
        ServerSocket ss = new ServerSocket(port); // abre o socket
        System.out.println("TCP Server is starting up, listening at port " + port + ".");
        int IDCliente = -1;
        Socket jogadores[] = new Socket[2];
        String nomes[] = new String[2];	
        String[][] matriz = new String[3][3];
        boolean sair = false;	         
        String simbolo = "X";
        Jogo j = new Jogo();

       while(true){
                
               socketC1 = ss.accept();
               IDCliente = 0;
               jogadores[0] = socketC1;
               socketC2 = ss.accept();
               jogadores[1] = socketC2;
                
               
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
					//System.out.println("[TCPServer - Thread] enviando resposta [" + responseToClientC1 + "] para o cliente " + contadorCliente + ".");
					if(IDCliente % 2 == 0)
					System.out.println("[TCPServer - Thread] enviando resposta [" + responseToClientC1 + "] para o cliente X .");
					else
					System.out.println("[TCPServer - Thread] enviando resposta [" + responseToClientC1 + "] para o cliente O .");
                    
					outputStream.writeUTF(responseToClientC1); //mandando de volta uma resposta para o cliente
            
               // Thread clienteThread1 = new ThreadSockets(socketC1, 0);
                //clienteThread1.start();
                //Thread clienteThread2 = new ThreadSockets(socketC2, 1);
                //clienteThread2.start();
                
                
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
						
					}else if(responseToClientC1.equals("JOGAR")){

							
							
							Scanner input = new Scanner (System.in);
							
							String opc = "S";
							
							int posicao_y = 0;
							int posicao_x = 0;
							
				//for(int N = 0; N<2 ; N++){
							
					do{
							if(IDCliente == 0){

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
							
							  }else if(IDCliente == 1){

								
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
							
						}while(IDCliente == 1);
							
					  
								
	                    }			
                        
                j.validacao(matriz, sair, simbolo);
                    

        }catch (IOException e){
							
							System.out.println(" ### PROBLEMAS NO BLOCO DE TAGS NO SERVIDOR, POR FAVOR REVEJA!!!! ### ");
							e.printStackTrace();
						}
        } 
    }
}