package redes;


import java.net.*;

 
public class Servidor {
    

    public static void main(String args[]  ) throws Exception {
        
        int port = 8724;
        Socket socketC1 = null;
        
       
        ServerSocket ss = new ServerSocket(port); // abre o socket
        System.out.println("TCP Server is starting up, listening at port " + port + ".");
        int IDCliente = -1;
        Socket jogadores[] = new Socket[2];
        
       while(true){
                
               socketC1 = ss.accept();
                IDCliente++;
                if(IDCliente == 0 ){
                    jogadores[IDCliente] = socketC1;

                }else if(IDCliente == 1){
                    jogadores[IDCliente] = socketC1;
                }else{
                    IDCliente = 0;
                }
        
                
                Thread clienteThread1 = new Thread(new ThreadSockets(socketC1, IDCliente));
                clienteThread1.start();
                
        }
    } 
}
