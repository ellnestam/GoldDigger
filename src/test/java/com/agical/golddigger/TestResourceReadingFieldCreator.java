package com.agical.golddigger;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.agical.golddigger.model.Square;
import com.agical.golddigger.model.fieldcreator.FieldCreator;
import com.agical.golddigger.model.fieldcreator.ResourceFieldCreator;


public class TestResourceReadingFieldCreator {

    @Test
    public void readFilesInSequence() throws Exception {
        FieldCreator fieldCreator = new ResourceFieldCreator(getClass());
        
        Square[][] createField1 = fieldCreator.createField();
        String field1 = Square.getField(createField1);
        assertEquals("1", createField1[2][2].getStringRepresentation());
        assertEquals(".", createField1[2][1].getStringRepresentation());
        assertEquals(3, fieldCreator.getMaxLatitude());
        assertEquals(2, fieldCreator.getMaxLongitude());
        
        Square[][] createField2 = fieldCreator.createField();
        assertEquals(".", createField2[2][2].getStringRepresentation());
        assertEquals("1", createField2[2][1].getStringRepresentation());
        assertEquals("2", createField2[1][3].getStringRepresentation());
        assertEquals(2, fieldCreator.getMaxLatitude());
        assertEquals(3, fieldCreator.getMaxLongitude());

        fieldCreator.createField();
        Square[][] createField = fieldCreator.createField();
        String field1InLoop = Square.getField(createField);
        assertEquals(field1, field1InLoop);
    }

    
}
