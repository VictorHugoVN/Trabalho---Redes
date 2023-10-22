package redes;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.ArrayList;



public class ThreadSockets extends Thread{
	
	private Socket cliente; // Socket recebido pelo servidor
    private static int contadorThreads;

    //ArrayList<Socket> listaClientes = new ArrayList();

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
                System.out.println("NOMes na thread " + nomes[0] + nomes[1]);
            }
            

            /*
            while(true){
                pergunto n1, armazeno
                pergunto n2, armazeno
                verificação
                renderização, enviando posições
            }
            */




            
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



            if(responseToClient.equals("JOGAR")){
                //contadorThreads -= 1;
                //System.out.println("Total de Threads active -> " + this.activeCount());
                //contadorThreads -= 1;
                //System.out.println("Total de Threads ativas -> " + contadorThreads);
                outputStream.println(this.getName());

                for(int cont = 0; cont < 9; cont++){


                    String p1 = reader.readLine();
                    String p2 = reader.readLine();
                    System.out.println("P1 -> " + p1);
                    System.out.println("P2 -> "+ p2);
                    String simbolo = reader.readLine();
                    System.out.println(simbolo); 

                    int pos1 = Integer.parseInt(p1);
                    int pos2 = Integer.parseInt(p2);
                    matriz[pos1 - 1][pos2 - 1] = simbolo;// Até aqui ok
                   
                    
                    boolean sair = false;
                    for (int y = 0; y < 3; y++) { //VERIFICAÇÃO DE VENCEDOR(A)
                        for (int x = 0; x < 3; x++) {   
                            if ((matriz[y][0] == "X" && matriz[y][1] == "X" && matriz[y][2] == "X") ||
                                (matriz[0][x] == "X" && matriz[1][x] == "X" && matriz[2][x] == "X") ||
                                (matriz[0][0] == "X" && matriz[1][1] == "X" && matriz[2][2] == "X") ||                                  
                                (matriz[0][2] == "X" && matriz[1][1] == "X" && matriz[2][0] == "X")
                                ) 
                            {
                                    //Thread-1 Venceu
                                    outputStream.println("Thread-1 Venceu!");
                                    sair = true;
                                    break;
                            }else if(
                                (matriz[y][0] == "O" && matriz[y][1] == "O" && matriz[y][2] == "O") ||
                                (matriz[0][x] == "O" && matriz[1][x] == "O" && matriz[2][x] == "O") ||
                                (matriz[0][0] == "O" && matriz[1][1] == "O" && matriz[2][2] == "O") ||
                                (matriz[0][2] == "O" && matriz[1][1] == "O" && matriz[2][0] == "O")
                            ){
                                //Thread-3 Venceu
                                outputStream.println("Thread-3 venceu");
                                sair = true;
                                break;
                            }
                            
                            ///*if (matriz[y][x] == null) //Imprimindo Matriz
                                //System.out.print("[_]");
                            //else
                                //System.out.print("[" + matriz[y][x] + "]");
                                //*/
                        }
   
                        if (sair == true)
                            break;
   
                        System.out.println();
                    } // Fim verificação

                    //Enviar novamente posições já usadas, strings
                    for(int i = 0; i <= 2; i++){ // Até aqui, aparentemente, tudo ok
                        for(int x = 0; x <= 2; x++){
                            ///*if(matriz[i][x].equals("X") || matriz[i][x].equals("O")){
                                //outputStream.println(matriz[i][x]);
                            //}else{
                                //outputStream.println("");
                            //}*/
                            String s = matriz[i][x];
                            System.out.println("ESTOU ENVIANDO ISSO -> " + s);
                            outputStream.println(s);
                            
                        }
                    }

                    
                    if (sair == true){
                        break;
                    }


                }

                


            

                
            }
			
		}catch(IOException ioe) {
			System.out.println("Erro: " + ioe.toString());
		}
	}

}
