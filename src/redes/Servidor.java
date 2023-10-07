package redes;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
 
class Servidor {
        private static int contadorThreads = -1;

    public static void main(String args[]) throws Exception {
        
        int port = 8724;
        Socket socket = null;
        BufferedReader reader = null; // Leitor local do cliente
        PrintStream outputStream = null; // Fluxo de saída para o cliente
        String nomeCliente = "";
        String nomes[] = new String[2];


        String clientRequest = "";
        String responseToClient = "";
        ServerSocket ss = new ServerSocket(port); // abre o socket
        System.out.println("TCP Server is starting up, listening at port " + port + ".");
 
        while (true) {
            // Recebendo solicitação do cliente
            socket = ss.accept();// aceita conexão
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // CONSIDERAR TRATAMENTO DE EXÇÃO!!!!
            clientRequest = reader.readLine();// Mensagem do cliente
            String novaMensagem = clientRequest;
            System.out.println("[TCPServer] Get request [" + novaMensagem + "] from Client.");


            // Enviando resposta ao cliente
            outputStream = new PrintStream(socket.getOutputStream());
            responseToClient = novaMensagem.toUpperCase();


            if(responseToClient != "JOGAR"){ // caso for diferente da tag jogar volta para o cliente que tem mais dados para tratar na classe cliente, caso for jogar, não volte para a classe cliente
                
                System.out.println("[TCPServer] Send out response [" + responseToClient + "] to Client.");
                outputStream.println(responseToClient); //
           
            }

            try{
            if(responseToClient.equals("LOGIN")){
                contadorThreads++;// Servirá para contar as treads abertas e armazenar os nomes com o indice na thread que foi aberta, ou seja o primeiro jogador que se conectar terá o seu nome na posiçaão 0)
             
                
                // Lê o nome do cliente e aguarda o outro fazer o loguin para mandar iniciar o jogo.
                nomeCliente = reader.readLine();
                System.out.println("Nome do jogador" + contadorThreads + "->"  + nomeCliente);  // *** um problema aqui é que eu n da pra passar essa lista para a thread executar o jogo antes do outro cliente se conectar
                nomes[contadorThreads] = nomeCliente;// colocando no arrey de nomes para que eu possa passar para a thread
                System.out.println("TESTE DE CHEGADA da tag login");
                //while(contadorThreads != 1){ // serve para verificar se a quantidade de jogador conectado é suficiente para jogar
                   // System.out.println("!!!! POR FAVOR AGUARDE O PRÓXIMO JOGADOR SE CONECTAR");
               // }

            }else if(responseToClient.equals("JOGAR")){
                
                Thread clienteThread = new ThreadSockets(socket, nomes);
                clienteThread.start();
                System.out.println("Nova Thread inicializada, cuidará do cliente " + nomes[contadorThreads]);
                outputStream.println(nomes);

            }
             } catch (IOException e){
                
                System.out.println(" ### PROBLEMAS NO BLOCO DE TAGS NO SERVIDOR, POR FAVOR REVEJA!!!! ### ");
                e.printStackTrace();
             }
        }
    }
}