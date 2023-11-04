package redes;

import java.net.Socket;
import java.util.ArrayList;

public class Keep {

    private int posy = 0;
    private int posx = 0;
    private int posy2 = 0;
    private int posx2 = 0;
    private ArrayList<Integer> ListaPos = new ArrayList<>(10);
    private int posYX[] = new int[2];

    public Keep(int posy, int posx, int posy2, int posx2){
        this.posy = posy;   
        this.posx = posx;
        this.posy2 = posy2;
        this.posx2 = posx2;
        
    }
    
    public Keep(int posy, int posx){
        
        this.posy = posy;
        this.posx = posx;
        //ListaPos.add(posy);
       // ListaPos.add(posx);

    }
    
    public Keep(){

    }

     public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

     public int[] getPosYX() {
        return posYX;
    }

    public void setPosYX(int[] posYX) {
        this.posYX = posYX;
    }

     public int getPosy2() {
        return posy2;
    }

    public void setPosy2(int posy2) {
        this.posy2 = posy2;
    }

    public int getPosx2() {
        return posx2;
    }

    public void setPosx2(int posx2) {
        this.posx2 = posx2;
    }

   public int[] AddPos(){
        
        this.posYX[0] = getPosy();
        this.posYX[1] = getPosx();
        this.posYX[2] = getPosy2();
        this.posYX[3] = getPosx2();

        return posYX;
    }

    public ArrayList<Integer> AddPosYX(){
        ListaPos.add(getPosy());
        ListaPos.add(getPosx());
        System.out.println("elementos da lista de posições : " + ListaPos);
            return ListaPos;
    }

    
}
