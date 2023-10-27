package redes;

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
    private static int contadorThreads;

    //ArrayList<Socket> listaClientes = new ArrayList();

    BufferedReader readert = null;
    PrintStream outputStreamT = null;

    private Socket cliente1, cliente2;
	private int contThreads; 
	private String nomes [] = new String[2];
	
	public ThreadSockets(Socket s, int contadorThreads) {
		this.cliente = s; // Encapsular!!
        this.contadorThreads = contadorThreads;
        //listaClientes.add(cliente);
	}


    public ThreadSockets(Socket cliente1, Socket cliente2, String nome1, String nome2){
		this.cliente1 = cliente1;
		this.cliente2 = cliente2;
		this.nomes[0] = nome1;
		this.nomes[1] = nome2;
	}



    // toServer = new PrintStream(cliente.getOutputStream());
	//int port = 8722;
    Socket socket = null;
    BufferedReader reader = null; // Leitor local do cliente
    PrintStream outputStream = null; // Fluxo de saída para o cliente
    String nomeCliente = "";
    String vencedor = "";
    //List <String> listaNomes = new ArrayList<String>();

    String clientRequest = "";
    String responseToClient = "";
    //ServerSocket ss = new ServerSocket(port);

    String matriz[][] = new String[3][3];
    int matrizPosicoes[][] = new int[3][3]; 
	
	
	public void run() {
		try {

            //System.out.println("Cliente " + cliente.getInetAddress().getHostAddress() + " conectado!");
            if(nomes[0] != null && nomes[1] != null){
                System.out.println("NOMes na thread " + nomes[0] + ", " + nomes[1]);
            }

            // FOR 

            System.out.println(cliente1);
            System.out.println(cliente2);

          

            //PrintStream saida1 = new PrintStream(cliente1.getOutputStream());
            //PrintStream saida2 = new PrintStream(cliente2.getOutputStream());
            //outputStream = new PrintStream(socket.getOutputStream());

            //ObjectOutputStream saida1 = new ObjectOutputStream(cliente1.getOutputStream());
            //ObjectOutputStream saida2 = new ObjectInputStream(cliente2.getOutputStream());
            //saida1.writeUTF("TESTE DE JOGO!!");
            //saida2.println("TESTE DE JOGO da velha");
            
            



            


            
            /*System.out.println("Nome do Thread -> " + this.getName());
            //Thread-0
            //Thread-1
            */

            /* 
            System.out.println();
            System.out.println("THREAD -> " + this.activeCount());
            System.out.println();
            */

           

			
            
            /*System.out.println("Id da Threads -> " + this.getId());
            if(this.getId() == 15){
                this.setName("t1");
                //thread = x
            }else if(this.getId() == 17) {
                this.setName("t2");
                //thread = o
            }*/


            // TAGS:

            /* */



             
                     


                }

                


            

                
            //}
			
	catch(Exception ioe) {
			System.out.println("Erro: " + ioe.toString());
		}
	}

}
