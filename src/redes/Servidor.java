package redes;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;
 
class Servidor {
    public static void main(String args[]) throws Exception {
        int port = 8722;
        Socket socket = null;
        BufferedReader reader = null; // Leitor local do cliente
        PrintStream outputStream = null; // Fluxo de saída para o cliente
        String nomeCliente = "";
        //List <String> listaNomes = new ArrayList<String>();
        String nomes[] = new String[2];

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
            String novaMensagem = clientRequest;
            System.out.println("[TCPServer] Get request [" + novaMensagem + "] from Client.");


            // Enviando resposta ao cliente
            outputStream = new PrintStream(socket.getOutputStream());
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
        }
    }


}