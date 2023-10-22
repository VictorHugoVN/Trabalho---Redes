package redes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class ThreadSockets extends Thread{
	
	private Socket cliente;
	
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
