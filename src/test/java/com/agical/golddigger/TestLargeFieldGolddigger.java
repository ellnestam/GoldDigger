package com.agical.golddigger;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.GoldField;


public class TestLargeFieldGolddigger {

    private GoldField goldField;
    private Digger digger;

    @Before
    public void initateGolddigger() throws Exception {
        goldField = new GoldField(4,5);
        digger = new Digger("Diggers name", "secretName");
        digger.newGame(goldField);
    }
    
    @Test
    public void initialDiggerView() throws Exception {
        assertEquals( "www\nwb.\nw..\n", digger.getView());
    }

    @Test
    public void fullField() throws Exception {
        assertEquals( "b....\n.....\n.....\n.....\n", goldField.getField(digger));
    }
    
}
