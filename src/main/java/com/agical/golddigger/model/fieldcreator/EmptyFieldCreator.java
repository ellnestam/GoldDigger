/**
 * 
 */
package com.agical.golddigger.model.fieldcreator;

import com.agical.golddigger.model.BankSquare;
import com.agical.golddigger.model.Square;


public class EmptyFieldCreator extends FieldCreator {
    private int maxLatitude; 
    private int maxLongitude;
    
    public EmptyFieldCreator(int maxLatitude, int maxLongitude) {
        this.maxLatitude = maxLatitude;
        this.maxLongitude = maxLongitude;
    }
    public Square[][] createField() {
        Square[][] fields = new Square[maxLatitude+2][maxLongitude+2];
        for(int lat=0;lat<maxLatitude+2;lat++) {
            for(int lon=0;lon<maxLongitude+2;lon++) {
                if(lon==0 || lat==0 || lat==maxLatitude+1 || lon==maxLongitude+1) {
                    fields[lat][lon] = Square.wall();
                } else {
                    fields[lat][lon] = Square.empty();
                }
            }
        }
        fields[1][1] = new BankSquare();
        return fields;
    }
    public int getMaxLatitude() {
        return maxLatitude;
    }
    public int getMaxLongitude() {
        return maxLongitude;
    }
}