package game;

public class Tabuleiro {
    
    private int[][] tabuleiro = new int [3][3];

    public Tabuleiro(){
        zerarTabuleiro();
    }

    public void mostrarTrabuleiro(){
        System.out.println(" imprimindo o trabuleiro ");
        System.out.println();
        for(int linha=0 ; linha<3 ; linha++){
        
            for(int coluna=0 ; coluna<3 ; coluna++){
                
                if(tabuleiro[linha][coluna]==-1){
                    System.out.print(" X ");
                }
                if(tabuleiro[linha][coluna]==1){
                    System.out.print(" O ");
                }
                if(tabuleiro[linha][coluna]==0){
                    System.out.print("   ");
                }
                
                if(coluna==0 || coluna==1)
                    System.out.print("|");
            }
            System.out.println();
        }
                
    }

    public void zerarTabuleiro(){

        for(int linha = 0 ; linha < 3 ; linha ++){
            for(int coluna = 0 ; coluna < 3; coluna ++){
                    this.tabuleiro [linha][coluna]= 0;

            }
        }
    }

    /*public int getPosicao(int[] vez){
        return vez;
    }

    public int setPosicao(int[] vez, int jogador){

    }

    public void checaLinha(){

    }

    public void ChecaColuna(){

    }

    public void checaLaterais(){
    }*/
}
