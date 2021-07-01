package edu.fiuba.algo3;

public class BlackBox {
    private int number=1;

    public void setNum( int n ){ number = n; }
    public int getNum( int n ){ return n*number; }

    public BlackBox(int n){
        number=n;
    }
    public BlackBox(){}

}
