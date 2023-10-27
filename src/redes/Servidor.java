package redes;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;


// TESTE DE RAMIFICAÇÃO


 
class Servidor {
    private static int contadorThreads = -1;
    private static int contadorAtivacao = -1;
    public static void main(String args[]) throws Exception {
        Socket socket = null;
        int port = 8722;
        BufferedReader reader = null; // Leitor local do cliente
        PrintStream outputStream = null; // Fluxo de saída para o cliente
        String nomeCliente = "";
        String nomes[] = new String[2];
        Socket clientes[] = new Socket[2];

        int vetorAtivacao[] = new int[]{0, 0};
        String simbolos[] = new String[]{"X", "O"};

        String clientRequest = "";
        String responseToClient = "";
        ServerSocket ss = new ServerSocket(port);

        System.out.println("TCP Server is starting up, listening at port " + port + ".");
 
        while (true) {
            // Recebendo solicitação do cliente
            socket = ss.accept();
            System.out.println("Cliente " + ss.getInetAddress().getHostAddress() + " conectado!");

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // CONSIDERAR TRATAMENTO DE EXÇÃO!!!!
            clientRequest = reader.readLine();// Mensagem do cliente
            //String novaMensagem = clientRequest.substring(clientRequest.indexOf("#")+1, clientRequest.indexOf("/#")); // ANALISAR
            String novaMensagem = clientRequest.toUpperCase();
            System.out.println("[TCPServer] Get request [" + novaMensagem + "] from Client.");


            // Enviando resposta ao cliente
            outputStream = new PrintStream(socket.getOutputStream());
            responseToClient = novaMensagem;
            
            outputStream.println(responseToClient); // AQUI
            System.out.println("[TCPServer] Send out response [" + responseToClient + "] to Client.");

            

            // TAGS

            if(responseToClient.equals("LOGIN")){

                contadorThreads++;                

                nomes[contadorThreads] = reader.readLine();
                clientes[contadorThreads] = socket;
                String simb = simbolos[contadorThreads];
                outputStream.println(simb);
                
                System.out.println("Nome do jogador " + contadorThreads + " é  -> "  + nomes[contadorThreads]);
                
                

            }else if(responseToClient.equals("JOGAR")){

                contadorAtivacao++;
                vetorAtivacao[contadorAtivacao] = 1;

                
                
            
                if(vetorAtivacao[0] == 1 && vetorAtivacao[1] == 1){
                    
                    //outputStream.println("ok"); //
                    System.out.println("Thread será startada!");
                    //outputStream.println(nomes[0]);
                    //outputStream.println(nomes[1]);
                    Thread clienteThread = new ThreadSockets(clientes[0], clientes[1], nomes[0], nomes[1]);
                    //clienteThread.start();

                    DataOutputStream saida1 = new DataOutputStream(clientes[0].getOutputStream());
                    //ObjectOutputStream saida2 = new ObjectInputStream(cliente2.getOutputStream());
                    saida1.writeUTF("TESTE DE JOGO!!");
                    System.out.println("Enviado!!!");


                }else{
                    System.out.println("Não temos dois clientes ainda!");
                    //outputStream.println("aguarde"); //
                    
                }    
                
            }

            

            


        }
    }

    


}


