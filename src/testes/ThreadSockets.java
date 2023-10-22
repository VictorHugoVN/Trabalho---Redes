package testes;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;



public class ThreadSockets extends Thread{
	
	private Socket cliente; // Socket recebido pelo servidor
	
	public ThreadSockets(Socket s) {
		this.cliente = s; // Encapsular!!
	}

	//int port = 8722;
    Socket socket = null;
    BufferedReader reader = null; // Leitor local do cliente
    PrintStream outputStream = null; // Fluxo de saída para o cliente
    String nomeCliente = "";
    //List <String> listaNomes = new ArrayList<String>();
    String nomes[] = new String[2];

    String clientRequest = "";
    String responseToClient = "";
    //ServerSocket ss = new ServerSocket(port);
	
	
	public void run() {
		try {

            System.out.println("Cliente " + cliente.getInetAddress().getHostAddress() + " conectado!");

			reader = new BufferedReader(new InputStreamReader(cliente.getInputStream())); // CONSIDERAR TRATAMENTO DE EXÇÃO!!!!
            clientRequest = reader.readLine();// Mensagem do cliente
            //String novaMensagem = clientRequest.substring(clientRequest.indexOf("#")+1, clientRequest.indexOf("/#")); // ANALISAR
            String novaMensagem = clientRequest;
            System.out.println("[TCPServer] Get request [" + novaMensagem + "] from Client.");


            // Enviando resposta ao cliente
            outputStream = new PrintStream(cliente.getOutputStream());
            responseToClient = novaMensagem.toUpperCase();
            
            outputStream.println(responseToClient); // AQUI
            System.out.println("[TCPServer] Send out response [" + responseToClient + "] to Client.");
            
            if(responseToClient.equals("LOGIN")){
                
                //RETORNAR UM CÓDIGO PARA O CLIENTE, PARA ENTÃO PEGAR O NOME?
                nomeCliente = reader.readLine();
                System.out.println("Nome do cliente -> " + nomeCliente);
                //listaNomes.add(nomeCliente);
                nomes[0] = nomeCliente;

            }else if(responseToClient.equals("JOGAR")){
                outputStream.println(nomes[0]);
                //outputStream.println(listaNomes);
                // PASSAR TAMBÉM LISTA COM POSIÇÕES JÁ USADAS
            }
			
		}catch(IOException ioe) {
			System.out.println("Erro: " + ioe.toString());
		}
	}

}
