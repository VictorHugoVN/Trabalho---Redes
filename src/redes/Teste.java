package redes;

class Teste{
    public static void main(String[] args){

        String matriz[][] = new String[3][3];
        matriz[1][0] = "oi";

        for(int l = 0; l <= 2; l++){
            for(int c = 0; c <= 2; c++){
                if(matriz[l][c] != null){
                    System.out.println("Elemento -> " + matriz[l][c]);
                }
                
            }
        }

        System.out.println(matriz[1][0].equals("oi"));

    }
}