package com.agical.golddigger.view;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.Position;
import com.agical.golddigger.model.Rectangle;
import com.agical.golddigger.model.Square;

public class FieldPeek {

	private final Digger digger;
	private final int width;
	private final int height;
	private Rectangle peekBounds;


	public FieldPeek(Digger digger, int width, int height) {
		this.digger = digger;
		this.width = width;
		this.height = height;
		peekBounds = new Rectangle(0, 0, width, height);
	}

	public Peek getPeek() {
		Position position = digger.getPosition();
		Square[][] allSquares = digger.getGoldField().getSquares();
		Rectangle bounds = createBounds(position, allSquares[0].length, allSquares.length);
		
		Square[][] result = new Square[peekBounds.getHeight()][peekBounds.getWidth()];
		
		int deltaX = (bounds.getWidth() - peekBounds.getWidth()) / 2;
		int deltaY = (bounds.getHeight() - peekBounds.getHeight()) / 2;
		
		for(int y = peekBounds.getY1(); y < peekBounds.getHeight(); y++) {
			for(int x = peekBounds.getX1(); x < peekBounds.getWidth(); x++) {
				int readX = x + bounds.getX1()+deltaX;
				int readY = y + bounds.getY1()+deltaY;
				if(!bounds.contains(readX,readY)) {
					result[y][x] = Square.wall();
				}
				else {
					result[y][x] = allSquares[readY][readX];
				}
			}
		}
		
		return new Peek(result, new Position(position.getLatitude()-deltaY, position.getLongitude()-deltaX), bounds);
	}

	private Rectangle createBounds(Position position, int fieldWith,
			int fieldHeight) {
		int minX = Math.max(0, position.getLongitude() + 1 - Math.round(width/2f));
		int minY = Math.max(0, position.getLatitude() + 1 - Math.round(height/2f));
		int maxX = Math.min(fieldWith, minX + width);
		int maxY = Math.min(fieldHeight, minY + height); 
		Rectangle bounds = new Rectangle(
				      adjustMinimum(minX, maxX, width),
				      adjustMinimum(minY, maxY, height),
					  maxX,
					  maxY);
		return bounds;
	}

	private int adjustMinimum(int min, int max, int desiredSize) {
		int deltaX = max - min;
		if (deltaX < desiredSize) min = Math.max(min -= (desiredSize-deltaX), 0);
		return min;
	}

}
