/**
 * 
 */
package com.agical.golddigger.model.fieldcreator;

import java.util.HashSet;
import java.util.Set;

import com.agical.golddigger.model.BankSquare;
import com.agical.golddigger.model.GoldSquare;
import com.agical.golddigger.model.Square;


public abstract class FieldCreator {
    public abstract Square[][] createField();
    public abstract int getMaxLatitude();
    public abstract int getMaxLongitude();
    public static final Square b() {
        return new BankSquare();
    }
    public static final Square g() {
        return new GoldSquare(1);
    }
    public static final Square g9() {
        return new GoldSquare(9);
    }
    public static final Square e() {
        return Square.empty();
    }
    public static final Square w() {
        return Square.wall();
    }
    public static boolean isValid(Square[][] field) {
        Set<String> treadableFields = getAllTreadableFields(field);
        Set<String> reachableFields = new HashSet<String>();
        findReachableFields(reachableFields, field, 1, 1);
        return treadableFields.equals(reachableFields);
    }
    private static void findReachableFields(Set<String> reachableFields, Square[][] field, int i, int j) {
        String squareIndex = i + "," + j;
        if(field[i][j].isTreadable()&&!reachableFields.contains(squareIndex)) {
            reachableFields.add(squareIndex);
            findReachableFields(reachableFields, field, i+1, j);
            findReachableFields(reachableFields, field, i, j+1);
            findReachableFields(reachableFields, field, i-1, j);
            findReachableFields(reachableFields, field, i, j-1);
        }
    }
    private static Set<String> getAllTreadableFields(Square[][] field) {
        Set<String> treadableFields = new HashSet<String>();
        for (int i = 0; i < field.length; i++) {
            Square[] squares = field[i];
            for (int j = 0; j < squares.length; j++) {
                Square square = squares[j];
                if(square.isTreadable()) {
                    treadableFields.add(i + "," + j);
                }
            }
        }
        return treadableFields;
    }
}