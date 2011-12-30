package com.agical.golddigger;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;

import com.agical.golddigger.model.Square;
import com.agical.golddigger.model.fieldcreator.FieldCreator;
import com.agical.golddigger.model.fieldcreator.RandomFieldCreator;
import com.agical.golddigger.model.fieldcreator.StringFieldCreator;


public class TestStringFieldCreator {

    @Test
    @Ignore
    public void testname() throws Exception {
        int maxLatitude = 50;
        int maxLongitude = 100;
        RandomFieldCreator randomFieldCreator = new RandomFieldCreator(maxLatitude,maxLongitude,400);
        Square[][] createField = randomFieldCreator.createField();
        String result = Square.getField(createField);
        System.out.println(result);
        FieldCreator fieldCreator = new StringFieldCreator(result);
        Square[][] recreatedField = fieldCreator.createField();
        assertEquals(result, Square.getField(recreatedField));
        assertEquals(maxLatitude, fieldCreator.getMaxLatitude());
        assertEquals(maxLongitude, fieldCreator.getMaxLongitude());
    }
    
}
