package com.agical.golddigger.view;

import static com.agical.golddigger.view.PeekTools.stringFrom;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.GoldField;
import com.agical.golddigger.model.Position;
import com.agical.golddigger.model.fieldcreator.StringFieldCreator;

public class TestFieldPeek {
	private Digger digger;
	private FieldPeek fieldPeek;

	@Before
	public void setupField() {
		String field =
			"wwwwwwwwwwwwwwwwwwww\n"+
			"w..................w\n"+
			"w...b..............w\n"+
			"w..................w\n"+
			"w..................w\n"+
			"w..................w\n"+
			"w..................w\n"+
			"w..................w\n"+
			"w..................w\n"+
			"w.......www........w\n"+
			"w..................w\n"+
			"w.........www......w\n"+
			"w..................w\n"+
			"w..................w\n"+
			"w..................w\n"+
			"w..................w\n"+
			"w..................w\n"+
			"w..............1...w\n"+
			"w..................w\n"+
			"wwwwwwwwwwwwwwwwwwww\n";
		GoldField goldField = new GoldField(new StringFieldCreator(field));
		digger = new Digger("digger", "pigger");
		digger.newGame(goldField);
		fieldPeek = new FieldPeek(digger, 5, 3);
	}
	
	@Test
	public void viewUpperLeftCorner() {
		digger.setPosition(new Position(1, 1));
		String expectedField =
			"wwwww\n"+
			"w....\n"+
			"w...b\n";
		
		assertThat(stringFrom(fieldPeek.getPeek()), is(equalTo(expectedField)));
	}
	
	@Test
	public void viewUpperLeftCornerPlus2East() {
		digger.setPosition(new Position(1, 3));
		String expectedField =
			"wwwww\n"+
			".....\n"+
			"...b.\n";
		
		assertThat(stringFrom(fieldPeek.getPeek()), is(equalTo(expectedField)));
	}
	
	@Test
	public void viewLowerRightCorner() {
		digger.setPosition(new Position(18, 18));
		String expectedField =
			"1...w\n"+
			"....w\n"+
			"wwwww\n";
		
		assertThat(stringFrom(fieldPeek.getPeek()), is(equalTo(expectedField)));
	}
	
	@Test
	public void viewMiddle() {
		digger.setPosition(new Position(10, 10));
		String expectedField =
			"www..\n"+
			".....\n"+
			"..www\n";
		
		assertThat(stringFrom(fieldPeek.getPeek()), is(equalTo(expectedField)));
	}

	
}
