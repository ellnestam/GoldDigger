package com.agical.golddigger.view;

import java.io.StringWriter;

import com.agical.golddigger.model.Position;
import com.agical.golddigger.model.Rectangle;
import com.agical.golddigger.model.Square;
import com.agical.golddigger.model.fieldcreator.StringFieldCreator;

public class PeekTools {
	public static String stringFrom(Peek peek) {
		StringWriter writer = new StringWriter();
		for (Square[] row : peek.getPiece()) {
			for (Square square : row) {
				writer.append(square == null ? "*" : square.getStringRepresentation());
			}
			writer.write('\n');
		}
		return writer.toString();
	}
	
	public static Peek peekFrom(String map) {
		StringFieldCreator stringFieldCreator = new StringFieldCreator(map);
		Square[][] field = stringFieldCreator.createField();
		for (Square[] squares : field) {
			for (Square square : squares) {
				square.viewed();
			}
		}
		return new Peek(field, new Position(0,0), new Rectangle(0,0, field[0].length, field.length));
	}
}
