package com.agical.golddigger.model;

public class WallSquare extends Square {

    public boolean isTreadable() {
        return false;
    }

    public String getStringRepresentation() {
        return "w";
    }
}
