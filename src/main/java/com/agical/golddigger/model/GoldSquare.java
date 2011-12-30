package com.agical.golddigger.model;

public class GoldSquare extends Square {

    private int nrOfGoldPieces;
    private static final int SQUARE_CAPACITY = 9;

    public GoldSquare(int nrOfGoldPieces) {
        this.nrOfGoldPieces = nrOfGoldPieces;
    }
    public void grabBy(Digger digger) {
        nrOfGoldPieces = digger.addPendingGold(nrOfGoldPieces);
    }
    public void dropBy(Digger digger) {
        int capacity = SQUARE_CAPACITY-nrOfGoldPieces;
        if(capacity>digger.getCarriedGold()) {
            nrOfGoldPieces += digger.getCarriedGold();
            digger.addPendingGold(-1*digger.getCarriedGold());
        } else {
            nrOfGoldPieces += capacity;
            digger.addPendingGold(-1*(capacity));
        }
    }
    
    @Override
    public String getStringRepresentation() {
        return nrOfGoldPieces==0?".":nrOfGoldPieces+"";
    }
    @Override
    public boolean isEmpty() {
        return nrOfGoldPieces==0;
    }
}
