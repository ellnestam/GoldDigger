package com.agical.golddigger;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.Diggers;
import com.agical.golddigger.model.Position;
import com.agical.golddigger.model.fieldcreator.FieldCreator;
import com.agical.golddigger.model.fieldcreator.ResourceFieldCreator;
import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;


public class TestMidFieldWalls {

    private Digger digger;

    @Before
    public void initateGolddigger() throws Exception {
        final ResourceFieldCreator fieldCreator = new ResourceFieldCreator(getClass());
        fieldCreator.createField();
        fieldCreator.createField();
        Diggers diggers = new Diggers(new Fn0<FieldCreator>() {
			@Override
			public FieldCreator apply() {
				return fieldCreator;
			}});
        digger = diggers.createDigger("Diggers name", "secretName");
        diggers.newGame(digger);
        assertEquals( "www\nwbw\nw1.\n", digger.getView());
    }
    
    @Test
    public void wontMoveToMidFieldWall() throws Exception {
        moveAndAssert(Position.EAST, "www\nwbw\nw1.\n");
    }
    
    private void moveAndAssert(Fn1<Position,Position> move, String expected) {
        digger.move(move);
        assertEquals( expected, digger.getView());
    }
    
}
