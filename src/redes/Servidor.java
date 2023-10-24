package redes;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
 
public class Servidor {
    //private static int contadorThreads = -1;
        

    public static void main(String args[]  ) throws Exception {
        
        int port = 8724;
        Socket socketC1 = null;
        Socket socketC2 = null;

        ServerSocket ss = new ServerSocket(port); // abre o socket
        System.out.println("TCP Server is starting up, listening at port " + port + ".");

        while(true){
          
        socketC1 = ss.accept();// aceita conexão Cliente 1;
        socketC2 = ss.accept();// aceita conexão do cliente 2;
       

       
        Thread clienteThread = new Thread(new ThreadSockets(socketC2, 2));
         clienteThread.start();
        Thread ClienteThread2 = new Thread(new ThreadSockets(socketC1, 1));
        ClienteThread2.start();
        
        System.out.println("O Servidor acessou a thread (passou as credenciais do socket parta a thread). ");
        

       
                


            /*/////// TRATA AS INFORMAÇÕES DO SEGUNDO CLIENTE  QUE SE CONECTA //////////
            
                ObjectInputStream readerC2 = new ObjectInputStream (socketC2.getInputStream()); // serve para que eu consiga ler o que foi enviado pelo cliente
				String clientRequestC2 = (String) readerC2.readObject();// Mensagem do cliente
				String novaMensagemC2 = clientRequestC2;
				System.out.println("[TCPServer] pegou solicitação [" + novaMensagemC2 + "] do cliente.");

				// Enviando resposta ao cliente
                outputStreamC2 = new ObjectOutputStream (socketC2.getOutputStream());
				responseToClientC2 = novaMensagemC2.toUpperCase();
                System.out.println("[TCPServer] enviando resposta [" + responseToClientC2 + "] para o cliente.");
                outputStreamC2.writeObject(responseToClientC2); //mandando de volta uma resposta para o cliente

             
               
        if(responseToClientC1.equals("LOGIN")){
            // abre a thread
             contadorThreads++;// Servirá para contar as treads abertas e armazenar os nomes com o indice na thread que foi aberta, ou seja o primeiro jogador que se conectar terá o seu nome na posiçaão 0)
            // Lê o nome do cliente e aguarda o outro fazer o login para mandar iniciar o jogo.
            nomeCliente1 = (String) readerC1.readObject();
            System.out.println("Nome do jogador " + contadorThreads + " é  -> "  + nomeCliente1);  // *** um problema aqui é que eu n da pra passar essa lista para a thread executar o jogo antes do outro cliente se conectar
            //nomes[contadorThreads] = nomeCliente1;// colocando no arrey de nomes para que eu possa passar para a thread
        
        }else if(responseToClientC1.equals("JOGAR")){

                outputStream.writeObject("Aguardando O segundo Cliente se Conectar");
                //System.out.println("Aguardando O segundo Cliente se Conectar");
               

        }
        
        else  if(responseToClientC2.equals("LOGIN")){
            // abre a thread
             contadorThreads++;// Servirá para contar as treads abertas e armazenar os nomes com o indice na thread que foi aberta, ou seja o primeiro jogador que se conectar terá o seu nome na posiçaão 0)
            // Lê o nome do cliente e aguarda o outro fazer o login para mandar iniciar o jogo.
            nomeCliente2 = (String) readerC2.readObject();
            System.out.println("Nome do jogador " + contadorThreads + " é  -> "  + nomeCliente2);  // *** um problema aqui é que eu n da pra passar essa lista para a thread executar o jogo antes do outro cliente se conectar
            //nomes[contadorThreads] = nomeCliente1;// colocando no arrey de nomes para que eu possa passar para a thread
        
        }else if(responseToClientC2.equals("JOGAR")){

                
               

        }*/

        
 
            }
        }
    }
