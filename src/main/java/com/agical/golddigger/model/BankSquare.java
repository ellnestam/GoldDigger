package com.agical.golddigger.model;

public class BankSquare extends Square {
    
    public void dropBy(Digger digger) {
        digger.cashGold();
    }

    public String getStringRepresentation() {
        return "b";
    }
}
