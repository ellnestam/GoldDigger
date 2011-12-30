package com.agical.golddigger.fieldcreator;

import static org.junit.Assert.*;
import static com.agical.golddigger.model.fieldcreator.FieldCreator.*;

import org.junit.Ignore;
import org.junit.Test;

import com.agical.golddigger.model.Square;
import com.agical.golddigger.model.fieldcreator.FieldCreator;
import com.agical.golddigger.model.fieldcreator.RandomFieldCreator;


public class TestRandomFieldGenerator {

    @Test
    public void generateFieldWithWalls() throws Exception {
        RandomFieldCreator creator = new RandomFieldCreator(20,20,50,50);
        Square[][] createField = creator.createField();
        System.out.println(Square.getField(createField));
        assertEquals(50, getNrOfInternalWalls(createField));
        assertEquals(50, getAmountOfGold(createField));
    }       
    
    private int getAmountOfGold(Square[][] createField) {
        int amountOfGold = 0;
        for (int i = 1; i < createField.length-1; i++) {
            Square[] squares = createField[i];
            for (int j = 1; j < squares.length-1; j++) {
                Square square = squares[j];
                String digit = square.getStringRepresentation();
                if(Character.isDigit(digit.charAt(0))) amountOfGold += Integer.parseInt(digit);
            }
        }
        return amountOfGold;
    }

    private int getNrOfInternalWalls(Square[][] createField) {
        int nrOfWalls = 0;
        for (int i = 1; i < createField.length-1; i++) {
            Square[] squares = createField[i];
            for (int j = 1; j < squares.length-1; j++) {
                Square square = squares[j];
                if(!square.isTreadable()) nrOfWalls++;
            }
        }
        return nrOfWalls;
    }

    @Test
    public void identifyFieldWithUnreachableSquare() throws Exception {
        Square[][] field = new Square[][]{
                {w(),w(),w(),w(),w()},
                {w(),b(),w(),e(),w()},
                {w(),w(),w(),w(),w()}};
        assertFalse(FieldCreator.isValid(field));
    }
    @Test
    public void identifyFieldWithAllSquaresReachable() throws Exception {
        Square[][] field = new Square[][]{
                {w(),w(),w(),w(),w()},
                {w(),b(),e(),e(),w()},
                {w(),w(),w(),w(),w()}};
        assertTrue(FieldCreator.isValid(field));
    }
    
    @Test
    public void trickySuccedingField() throws Exception {
        Square[][] field = new Square[][]{
                {w(),w(),w(),w(),w(),w()},
                {w(),b(),w(),e(),e(),w()},
                {w(),e(),e(),w(),e(),w()},
                {w(),e(),e(),e(),e(),w()},
                {w(),w(),w(),w(),w(),w()}};
        assertTrue(FieldCreator.isValid(field));
    }

    @Test
    public void trickyFailingField() throws Exception {
        Square[][] field = new Square[][]{
                {w(),w(),w(),w(),w(),w()},
                {w(),b(),w(),e(),e(),w()},
                {w(),e(),e(),w(),e(),w()},
                {w(),e(),w(),e(),e(),w()},
                {w(),w(),w(),w(),w(),w()}};
        assertFalse(FieldCreator.isValid(field));
    }
}
