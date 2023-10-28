package Teste;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.ArrayList;



public class ThreadSockets extends Thread{
	
	private Socket cliente; // Socket recebido pelo servidor

    //ArrayList<Socket> listaClientes = new ArrayList();

    BufferedReader readert = null;
    PrintStream outputStreamT = null;
	

    public ThreadSockets(Socket s){
        this.cliente = s;
    }



    BufferedReader reader = null; // Leitor local do cliente
        PrintStream outputStream = null; // Fluxo de saída para o cliente
        String nomeCliente = "";

        String clientRequest = "";
        String responseToClient = "";
        //ServerSocket ss = new ServerSocket(port);

     
	
	
	public void run() {
		try {
            System.out.println("ENTREI NA THREAD");

            reader = new BufferedReader(new InputStreamReader(cliente.getInputStream())); // CONSIDERAR TRATAMENTO DE EXÇÃO!!!!
            clientRequest = reader.readLine();// Mensagem do cliente
            //String novaMensagem = clientRequest.substring(clientRequest.indexOf("#")+1, clientRequest.indexOf("/#")); // ANALISAR
            String novaMensagem = clientRequest.toUpperCase();
            System.out.println("[TCPServer] Get request [" + novaMensagem + "] from Client.");


            // Enviando resposta ao cliente
            outputStream = new PrintStream(cliente.getOutputStream());
            responseToClient = novaMensagem;
            
            outputStream.println(responseToClient); // AQUI
            System.out.println("[TCPServer] Send out response [" + responseToClient + "] to Client.");

            

            // TAGS

            if(responseToClient.equals("LOGIN")){

                System.out.println("ENTREI NO LOGIN");
                
                

            }else if(responseToClient.equals("JOGAR")){
                System.out.println("ENTREI EM JOGAR");
                
                
            }

            
        
        }catch(Exception ioe) {
			System.out.println("Erro: " + ioe.toString());
		}
	}

}
