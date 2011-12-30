package com.agical.golddigger.view;

import com.agical.golddigger.model.Position;
import com.agical.golddigger.model.Rectangle;
import com.agical.golddigger.model.Square;
import com.agical.golddigger.model.WallSquare;
import com.agical.jambda.Unit;
import com.agical.jambda.Functions.Fn1;
import static com.agical.jambda.Booleans.*;


public class Peek {

	private final Square[][] piece;
	private final Position position;
	private final Rectangle bounds;

	public Peek(Square[][] piece, Position position, Rectangle bounds) {
		this.piece = piece;
		this.position = position;
		this.bounds = bounds;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public Square[][] getPiece() {
		return piece;
	}

	public void drawTo(PeekView peekView) {
		for(int y = 0; y < piece.length; y++) {
			for(int x = 0; x < piece[y].length;x++) {
				drawSquare(x, y, peekView);
			}
		}
		diggerPosition(peekView);
	}

	private void diggerPosition(PeekView peekView) {
		int x = position.getLongitude() - bounds.getX1();
		int y = position.getLatitude() - bounds.getY1();
		peekView.drawDigger(x,y);
	}

	private void drawSquare(int x, int y, PeekView peekView) {
		Square thisSquare = piece[y][x];
		String srep = thisSquare.getStringRepresentation();
		if(srep.equals(Square.wall().getStringRepresentation()))  {
			drawWall(x,y, peekView);
		}
		else if (srep.equals("b")) {
			peekView.drawBank(x, y);
		}
		else if (Character.isDigit(srep.charAt(0))) {
			char ch = srep.charAt(0);
			drawGold(x, y, ch-'0', peekView);
		}
		else {
			peekView.drawEmpty(x,y);
		}
		// orkade inte funktionalisera
		if(!thisSquare.hasBeenViewed().isSome()) {
		    peekView.drawShadow(x,y);
		}
	}

	private void drawGold(int x, int y, int i, PeekView peekView) {
		peekView.drawGold(x, y, i);
	}


	private void drawWall(int x, int y, PeekView peekView) {
		if(wallIs(x, y, SOLID))	peekView.drawSolidWall(x,y);
		else if(wallIs(x, y, SOUTHEAST_INV)) peekView.drawInvertedSouthEastWall(x,y);
		else if(wallIs(x, y, NORTHEAST_INV)) peekView.drawInvertedNorthEastWall(x,y);
		else if(wallIs(x, y, SOUTHWEST_INV)) peekView.drawInvertedSouthWestWall(x,y);
		else if(wallIs(x, y, NORTHWEST_INV)) peekView.drawInvertedNorthWestWall(x,y);
		else if(wallIs(x, y, NORTH)) peekView.drawNorthWall(x,y);
		else if(wallIs(x, y, NORTHEAST)) peekView.drawNorthEastWall(x,y);
		else if(wallIs(x, y, EAST))	peekView.drawEastWall(x,y);
		else if(wallIs(x, y, SOUTHEAST)) peekView.drawSouthEastWall(x,y);
		else if(wallIs(x, y, SOUTH)) peekView.drawSouthWall(x,y);
		else if(wallIs(x, y, SOUTHWEST)) peekView.drawSouthWestWall(x,y);
		else if(wallIs(x, y, WEST)) peekView.drawWestWall(x,y);
		else if(wallIs(x, y, NORTHWEST)) peekView.drawNorthWestWall(x,y);
		else peekView.drawCenterWall(x,y);
	}

	private boolean wallIs(int x, int y, Fn1<Square, Boolean>[][] pattern) {
		for (int dx = -1; dx <= 1; dx ++){
			for (int dy = -1; dy <= 1; dy ++){
				if (!pattern[dy+1][dx+1].apply(getSquare(x + dx, y + dy))) return false;
			}	
		}
		return true;
	}

	private Square getSquare(int x, int y) {
		if (x < 0 || x >= piece[0].length || y < 0 || y >= piece.length) return Square.wall();
		else return piece[y][x];
	}
	
	private static Fn1<Square, Boolean> WALL = new Fn1<Square, Boolean>(){
		@Override
		public Boolean apply(Square square) {
			return square instanceof WallSquare;
		}
	};
	
	private static Fn1<Square, Boolean> ANY = new Fn1<Square, Boolean>(){
		@Override
		public Boolean apply(Square square) {
			return true;
		}
	};
	
	private static Fn1<Square, Boolean> NOTW = WALL.compose(not);
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] SOLID = new Fn1[][] {
		{WALL, WALL, WALL},
		{WALL, WALL, WALL},
		{WALL, WALL, WALL},
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] NORTH = new Fn1[][] {
		{ANY,  WALL, ANY },
		{WALL, WALL, WALL},
		{ANY,  NOTW, ANY },
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] NORTHEAST = new Fn1[][] {
		{WALL, WALL, WALL},
		{WALL, WALL, WALL},
		{NOTW,  WALL, WALL},
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] NORTHEAST_INV = new Fn1[][] {
		{ANY, NOTW, ANY},
		{WALL, WALL, NOTW},
		{WALL, WALL, ANY},
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] EAST = new Fn1[][] {
		{ANY,  WALL, ANY },
		{NOTW, WALL, WALL},
		{ANY,  WALL, ANY },
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] SOUTHEAST = new Fn1[][] {
		{NOTW, WALL, WALL},
		{WALL, WALL, WALL},
		{WALL, WALL, WALL},
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] SOUTHEAST_INV = new Fn1[][] {
		{WALL, WALL, ANY},
		{WALL, WALL, NOTW},
		{ANY, NOTW, ANY },
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] SOUTH = new Fn1[][] {
		{ANY,  NOTW, ANY},
		{WALL, WALL, WALL},
		{ANY,  WALL, ANY},
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] SOUTHWEST = new Fn1[][] {
		{WALL, WALL, NOTW},
		{WALL, WALL, WALL},
		{WALL, WALL, WALL},
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] SOUTHWEST_INV = new Fn1[][] {
		{ANY, WALL, WALL },
		{NOTW, WALL, WALL },
		{ANY , NOTW, ANY },
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] WEST = new Fn1[][] {
		{ANY,  WALL, ANY },
		{WALL, WALL, NOTW},
		{ANY,  WALL, ANY },
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] NORTHWEST = new Fn1[][] {
		{WALL, WALL, WALL },
		{WALL, WALL, WALL },
		{WALL, WALL, NOTW },
	};
	
	@SuppressWarnings("unchecked")
	private static Fn1<Square, Boolean>[][] NORTHWEST_INV = new Fn1[][] {
		{ANY , NOTW, ANY },
		{NOTW, WALL, WALL },
		{ANY, WALL, WALL },
	};
}