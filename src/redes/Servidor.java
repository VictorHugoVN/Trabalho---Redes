package redes;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
 
public class Servidor {
        private static int contadorThreads = -1;
        

    public static void main(String args[]  ) throws Exception {
        
        int port = 8724;
        Socket socket = null;
       
        ServerSocket ss = new ServerSocket(port); // abre o socket
        System.out.println("TCP Server is starting up, listening at port " + port + ".");
        
        String responseToClient = "";
        String nomeCliente = "";
        String nomes []  = new String[2];
        ObjectOutputStream outputStream;
        while(true){
        socket = ss.accept();// aceita conexão
        
       
       
            ObjectInputStream reader = new ObjectInputStream (socket.getInputStream()); // serve para que eu consiga ler o que foi enviado pelo cliente
				String clientRequest = (String) reader.readObject();// Mensagem do cliente
				String novaMensagem = clientRequest;
				System.out.println("[TCPServer] pegou solicitação [" + novaMensagem + "] do cliente.");

				// Enviando resposta ao cliente
                outputStream = new ObjectOutputStream (socket.getOutputStream());
				responseToClient = novaMensagem.toUpperCase();

               
                System.out.println("[TCPServer] enviando resposta [" + responseToClient + "] para o cliente.");
                outputStream.writeObject(responseToClient); //mandando de volta uma resposta para o cliente

        if(responseToClient.equals("LOGIN")){
            // abre a thread
             contadorThreads++;// Servirá para contar as treads abertas e armazenar os nomes com o indice na thread que foi aberta, ou seja o primeiro jogador que se conectar terá o seu nome na posiçaão 0)
            
            // Lê o nome do cliente e aguarda o outro fazer o login para mandar iniciar o jogo.
            nomeCliente = (String) reader.readObject();
            System.out.println("Nome do jogador " + contadorThreads + " é  -> "  + nomeCliente);  // *** um problema aqui é que eu n da pra passar essa lista para a thread executar o jogo antes do outro cliente se conectar
            nomes[contadorThreads] = nomeCliente;// colocando no arrey de nomes para que eu possa passar para a thread
        
        }else{

                Thread clienteThread = new ThreadSockets(socket, nomes);
                    clienteThread.start();
        }

        
 
            }
        }
    }
