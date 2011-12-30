package com.agical.golddigger;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.GoldField;
import com.agical.golddigger.model.Position;
import com.agical.golddigger.model.fieldcreator.FieldCreator;
import com.agical.golddigger.model.fieldcreator.RandomFieldCreator;


public class TestRandomFieldCreator {

    @Test
    public void goldPieceField() throws Exception {
        FieldCreator randomFieldCreator = new RandomFieldCreator(3,3,3);
        GoldField goldField = new GoldField(randomFieldCreator);
        Digger digger = new Digger("Diggers name", "secretName");
        digger.newGame(goldField);
        moveEastAndPickUp(digger);
        moveEastAndPickUp(digger);
        moveSouthAndPickUp(digger);
        moveWestAndPickUp(digger);
        moveWestAndPickUp(digger);
        moveSouthAndPickUp(digger);
        moveEastAndPickUp(digger);
        moveEastAndPickUp(digger);
        moveNorthAndPickUp(digger);
        moveNorthAndPickUp(digger);
        moveWestAndPickUp(digger);
        moveWestAndPickUp(digger);
        digger.drop();
        assertEquals(3, digger.getGoldInTheBank());
    }

    private void moveEastAndPickUp(Digger digger) {
        digger.move(Position.EAST);
        digger.grab();
    }

    private void moveWestAndPickUp(Digger digger) {
        digger.move(Position.WEST);
        digger.grab();
    }

    private void moveNorthAndPickUp(Digger digger) {
        digger.move(Position.NORTH);
        digger.grab();
    }
    
    private void moveSouthAndPickUp(Digger digger) {
        digger.move(Position.SOUTH);
        digger.grab();
    }
}
