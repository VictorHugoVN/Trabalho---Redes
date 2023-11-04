package redes;


import java.net.*;
import java.util.ArrayList;
public class Servidor {
    
	
    public static void main(String args[]) throws Exception {
        
        int port = 8724;
        Socket socketC1 = null;
      	int IDCliente = 0;
       
        ServerSocket ss = new ServerSocket(port); // abre o socket
        System.out.println("TCP Server is starting up, listening at port " + port + ".");
		ArrayList<Socket> clientes = new ArrayList<>(10);
		int cont = 0;
		
       while(true){
					
					socketC1 = ss.accept();
					cont ++;
					clientes.add(socketC1);
					System.out.println("tamanho das lista é " + clientes.size());
					System.out.println(clientes);
					
					
					
					if(clientes.size() > 1){	
							
							Thread novathread = new ThreadSockets(clientes.get(0), clientes.get(1));
							novathread.start();
							System.out.println("thread start");
							clientes.clear();

					}
						
					
					
		
        } 
    }
}