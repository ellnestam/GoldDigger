package com.agical.golddigger.view;

import org.junit.Before;
import org.junit.Test;

import com.agical.golddigger.gui.FieldView;
import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.GoldField;
import com.agical.golddigger.model.Position;
import com.agical.golddigger.model.fieldcreator.StringFieldCreator;

import static com.agical.golddigger.view.PeekTools.stringFrom;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestFieldPeekNarrowField {
	private FieldPeek fieldPeek;
	private Digger digger;

	@Before
	public void setUpField() {
		String field =
			"wwwwww\n"+
			"w....w\n"+
			"w...bw\n"+
			"w....w\n"+
			"w....w\n"+
			"w....w\n"+
			"wwwwww\n";
		
		GoldField goldField = new GoldField(new StringFieldCreator(field));
		digger = new Digger("dig", "dug");
		digger.newGame(goldField);
		digger.setPosition(new Position(4,4));
		fieldPeek = new FieldPeek(digger, 10, 9);
	}

	@Test
	public void centersAndFillsEdgesWithWalls() {
		String expected = 
			"wwwwwwwwww\n"+
			"wwwwwwwwww\n"+
			"www....www\n"+
			"www...bwww\n"+
			"www....www\n"+
			"www....www\n"+
			"www....www\n"+
			"wwwwwwwwww\n"+
			"wwwwwwwwww\n";
		assertThat(stringFrom(fieldPeek.getPeek()), is(expected));
	}
	
	@Test
	public void positionTakesAddedWallsIntoAccount() {
		int expectedLat = digger.getPosition().getLatitude()+1;
		int expectedLong = digger.getPosition().getLongitude()+2;
		assertThat(fieldPeek.getPeek().getPosition(), is(new Position(expectedLat,expectedLong)));
	}
	
}
