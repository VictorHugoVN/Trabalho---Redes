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
    private String nomes [] = new String [2];


	public ThreadSockets(Socket s, String nomes[]) {
        this.nomes [0]= nomes [0];
		this.nomes [1]= nomes [1];
		this.cliente = s; // Encapsular!!
	}
	
	
	@Override
	public void run() {
		try {

            JogoDaVelha c = new JogoDaVelha();
            c.game(nomes[0], nomes[1]);
 
            // Close connection
            if (cliente != null) {
                cliente.close();
            }
			
			// 4 - Fechar socket de comunicação
			cliente.close();
			
		}catch(IOException ioe) {
			System.out.println("Erro: Em uma das Threads  " + ioe.toString());
		}
	}
	
}