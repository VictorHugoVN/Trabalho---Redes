package jogoDaVelha;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Jogo implements Serializable{
		// serializable servirá para que eu possa passar esses metodos apos instaciar a classo como objeto
		//private String jogador1;
		//private String jogador2;

		public Jogo(){

		}

		//public Jogo(String nome1, String nome2){
			//this.jogador1 = nome1;
			//this.jogador2 = nome2;
		//}
		

		//public String getJogador1() {
			//return jogador1;
		//}

		//public void setJogador1(String jogador1) {
			//this.jogador1 = jogador1;
		//}

		//public String getJogador2() {
			//return jogador2;
	//	}

		//public void setJogador2(String jogador2) {
		//	this.jogador2 = jogador2;
		//}

		
	
	public void game(String jogador1, String jogador2, int posicao_y, int posicao_x, int posicao_y1, int posicao_X1){

		Scanner input = new Scanner (System.in);
		String[][] matriz = new String[3][3];
        String opc;
        
        
        String simbolo = "X";
        boolean sair = false;

			 //jogador1 = getJogador1();
         System.out.println("Jogador X: " + jogador1);
       		//jogador2 = getJogador2();
         System.out.println("Jogador O: " + jogador2);
        

         try {
         	do {    
        		 for (int cont = 0; cont < 1; cont++) { 
        			 System.out.println("horizontal  = " + posicao_y);
					 System.out.println("Vertical  = " + posicao_x);

					matriz[posicao_y - 1][posicao_x - 1] = simbolo;
					matriz[posicao_y1-1][posicao_X1 - 1] = simbolo;
					//questions(simbolo, jogador1, jogador2, cont, posicao_y, posicao_x, contCliente);
					//sair = validacao(matriz, sair, simbolo);

	         }
	
	           opc = winner(sair, simbolo, input, matriz);
			   
         	} while (opc.toUpperCase().equals("S"));
        } catch (InputMismatchException e) {
   			System.out.println("Você digitou o valor errado!");
   			//game(null);
   		} catch (Exception e) {
   			System.out.println("ERRO!");
   			e.printStackTrace();
   		} 
         	
        System.out.println("Obrigado por testar o programa!");	
     	input.close();
	}



	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String questions(String simbolo, String jogador1, String jogador2, int cont, int posicao_y, int posicao_x, int contCliente) {
		Scanner input = new Scanner (System.in);
		 String matriz[][] = new String[3][3];
		
		do {
						if(contCliente == 1 ){
						
	                     System.out.println( jogador2 +" entrou com a linha: " + posicao_y);
	                     //posicao_y = input.nextInt();
	                     System.out.println( jogador2 + " entrou com a coluna: " + posicao_x);
	                    // posicao_x = input.nextInt();
						 
						}else if (contCliente == 2){
						 System.out.println(jogador1 +" entrou com a linha: " + posicao_y);
	                     //posicao_y = input.nextInt();
	                     System.out.println(jogador1 +" entrou com a coluna: " + posicao_x);
	                     //posicao_x = input.nextInt();
						}
	                 } while (posicao_y <= 0 || posicao_y > 3 ||
	                          posicao_x <= 0 || posicao_x > 3 ||
	                          matriz[posicao_y - 1][posicao_x - 1] != null); 
					
							 return matriz[posicao_y - 1][posicao_x - 1] = simbolo;
					
	}

	/**
	 * @param matriz
	 * @param sair
	 * @param simbolo
	 */
	public void validacao(String matriz[][], boolean sair, String simbolo){

		 for (int y = 0; y < 3; y++) {
	                     for (int x = 0; x < 3; x++) {
	                    	 if ((matriz[y][0] == "X" && matriz[y][1] == "X" && matriz[y][2] == "X") ||
		                             (matriz[y][0] == "O" && matriz[y][1] == "O" && matriz[y][2] == "O") ||
		                             (matriz[0][x] == "X" && matriz[1][x] == "X" && matriz[2][x] == "X") ||
		                             (matriz[0][x] == "O" && matriz[1][x] == "O" && matriz[2][x] == "O") ||
		                             (matriz[0][0] == "X" && matriz[1][1] == "X" && matriz[2][2] == "X") ||
		                             (matriz[0][0] == "O" && matriz[1][1] == "O" && matriz[2][2] == "O") ||
		                             (matriz[0][2] == "X" && matriz[1][1] == "X" && matriz[2][0] == "X") ||
		                             (matriz[0][2] == "O" && matriz[1][1] == "O" && matriz[2][0] == "O")) {
		                             sair = true;
		                              break;
		                     }
	                    	 
	                         if (matriz[y][x] == null)
	                             System.out.print("[_]");
	                         else
	                             System.out.print("[" + matriz[y][x] + "]");
								 simbolo = matriz[y][x] ;
	                     }
	
	                     if (sair == true)
	                          break;
						 
	                     System.out.println();
	                 }	

					  //if (sair == true) 
	                  //break; 
					 // return sair;
	
	                 //if (simbolo == "X") {
	                     //simbolo = "O";
	                // } else {
	                     //simbolo = "X";
	                // }
					winner(sair, simbolo, null, matriz);
	}

public String winner(boolean sair, String simbolo,Scanner input, String[][] matriz){
		String opc = "fim";
			 if (sair == true) {
	                 if(simbolo == "X")
	                     System.out.println("X ganhou!!");
	                 else
	                     System.out.println(" O ganhou!!");
	             }
	
	            // System.out.print("Deseja começar novamente [S] ou [N]? ");
	            // opc = input.next();
	
	             //sair = false;
	             //simbolo = "X";
	            // for (int y = 0; y < 3; y++) {
	              //   for (int x = 0; x < 3; x++)
	                   //  matriz[y][x] = null;
	            // }
				 return opc;
	}

}
