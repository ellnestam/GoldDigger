package com.agical.golddigger;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.GoldField;
import com.agical.golddigger.model.Position;
import com.agical.jambda.Functions.Fn1;


public class TestMovesWithinBounds {

    private GoldField goldField;
    private Digger digger;

    @Before
    public void initateGolddigger() throws Exception {
        goldField = new GoldField(3,3);
        digger = new Digger("Diggers name", "secretName");
        digger.newGame(goldField);
        digger.move(Position.SOUTH);
        digger.move(Position.EAST);
        assertEquals( "b..\n...\n...\n", digger.getView());
    }
    
    @Test
    public void canMoveInAllDirections() throws Exception {
        moveAndAssert(Position.NORTH, "www\nb..\n...\n");
        moveAndAssert(Position.EAST, "www\n..w\n..w\n");
        moveAndAssert(Position.SOUTH, "..w\n..w\n..w\n");
        moveAndAssert(Position.WEST, "b..\n...\n...\n");
    }

    @Test
    public void wontMoveOutsideNorthBounds() throws Exception {
        moveAndAssert(Position.NORTH, "www\nb..\n...\n");
        moveAndAssert(Position.NORTH, "www\nb..\n...\n");
    }
    @Test
    public void wontMoveOutsideSouthBounds() throws Exception {
        moveAndAssert(Position.SOUTH, "...\n...\nwww\n");
        moveAndAssert(Position.SOUTH, "...\n...\nwww\n");
    }
    @Test
    public void wontMoveOutsideEastBounds() throws Exception {
        moveAndAssert(Position.EAST, "..w\n..w\n..w\n");
        moveAndAssert(Position.EAST, "..w\n..w\n..w\n");
    }
    @Test
    public void wontMoveOutsideWestBounds() throws Exception {
        moveAndAssert(Position.WEST, "wb.\nw..\nw..\n");
        moveAndAssert(Position.WEST, "wb.\nw..\nw..\n");
    }
    @Test(expected=RuntimeException.class)
    public void wontMoveOutside() throws Exception {
        moveAndAssert(Position.WEST, "wb.\nw..\nw..\n");
        moveAndAssert(Position.SOUTH, "w..\nw..\nwww\n");
        moveAndAssert(Position.SOUTH, "w..\nw..\nwww\n");
        digger.setPosition(new Position(4,1));
    }
    
    @Test
    public void fullField() throws Exception {
        assertEquals( "b..\n...\n...\n", goldField.getField(digger));
    }
    
    private void moveAndAssert(Fn1<Position,Position> move, String expected) {
        digger.move(move);
        assertEquals( expected, digger.getView());
    }
    
}
