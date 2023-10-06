package redes;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ThreadSockets extends Thread{
	
	private Socket cliente;
	PrintStream toServer = null; 
    BufferedReader fromServer = null;


	public ThreadSockets(Socket s) {
		this.cliente = s; // Encapsular!!
	}
	
	
	@Override
	public void run() {
		try {

			// 1 - Definir stream de saída de dados do servidor
			DataInputStream entrada = new DataInputStream(cliente.getInputStream());
			String mensagem = entrada.readUTF();
			String novaMensagem = mensagem.toUpperCase();
			
			// 2 - Definir stream de saída de dados do servidor
			DataOutputStream saida = new DataOutputStream(cliente.getOutputStream());
			saida.writeUTF(novaMensagem);


        // Read from user input
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        do {
            System.out.print("Digite qualquer string agora, (quit) para finalizar: ");
            System.out.flush();
            userInput = inFromUser.readLine().replace(" ", "");// aqui pega a string !!!!
            userInput = userInput.substring(userInput.indexOf("#")+1, userInput.indexOf("/#"));
            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }
 
            // Enviando entrada do usuário para o servidor
            toServer = new PrintStream(cliente.getOutputStream()); 
            toServer.println(userInput); // AQUI
            
            
            // Temos que enviar de volta ao servidor !!!!
            System.out.println("[TCPClient] Enviar entrada do usuário [" + userInput + "] ao Servidor.");
 
            // Recebendo resposta do servidor
            fromServer = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            String responseFromServer = fromServer.readLine(); // AQUI
            System.out.println("[TCPClient] Obtenha resposta [" + responseFromServer + "] do servidor.");
            if(responseFromServer.equals("LOGIN")){
                // pegando nome
                System.out.println("Digite o seu nome: ");
                System.out.flush();
                //String nome = inFromUser.readLine();
                // enviando nome
                toServer.println(inFromUser.readLine());
            }
            if(responseFromServer.equals("JOGAR")){
                
                String nomeJogador = fromServer.readLine();
                JogoDaVelha c = new JogoDaVelha(); // chama a classe do jogo 
                c.game(nomeJogador, nomeJogador);
                userInput = "quit";
            }

        } while (!userInput.equals("quit")); // Encerre o cliente se 'quit' for uma entrada
 
        // Close connection
        if (cliente != null) {
            cliente.close();
        }

			// 3 - Fechar streams de entrada e saída de dados
			entrada.close();
			saida.close();
			
			// 4 - Fechar socket de comunicação
			cliente.close();
			
		}catch(IOException ioe) {
			System.out.println("Erro: " + ioe.toString());
		}
	}
	
}