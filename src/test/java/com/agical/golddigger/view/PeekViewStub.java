package com.agical.golddigger.view;

public class PeekViewStub implements PeekView {
	private StringBuffer stringBuffer = new StringBuffer();
	
	
	public String getLog() {
		return stringBuffer.toString();
	}


	public void draw(int x, int y, String representation) {
		stringBuffer.append('(');
		stringBuffer.append(x);
		stringBuffer.append(',');
		stringBuffer.append(y);
		stringBuffer.append(',');
		stringBuffer.append(representation);
		stringBuffer.append(")\n");
	}


	@Override
	public void drawCenterWall(int x, int y) {
		draw(x, y, "C");
	}

	@Override
	public void drawSolidWall(int x, int y) {
		draw(x, y, "X");
	}

	@Override
	public void drawEmpty(int x, int y) {
		draw(x, y, ".");
	}


	@Override
	public void drawNorthWall(int x, int y) {
		draw(x, y, "N");
	}
	
	@Override
	public void drawNorthEastWall(int x, int y) {
		draw(x, y, "NE");
	}

	@Override
	public void drawEastWall(int x, int y) {
		draw(x, y, "E");		
	}

	@Override
	public void drawSouthEastWall(int x, int y) {
		draw(x, y, "SE");
	}
	
	@Override
	public void drawSouthWall(int x, int y) {
		draw(x, y, "S");		
	}

	@Override
	public void drawSouthWestWall(int x, int y) {
		draw(x, y, "SW");
	}

	@Override
	public void drawWestWall(int x, int y) {
		draw(x, y, "W");	
	}

	@Override
	public void drawNorthWestWall(int x, int y) {
		draw(x, y, "NW");
	}

	@Override
	public void drawInvertedNorthEastWall(int x, int y) {
		draw(x, y, "NEI");
	}

	@Override
	public void drawInvertedNorthWestWall(int x, int y) {
		draw(x, y, "NWI");
	}

	@Override
	public void drawInvertedSouthEastWall(int x, int y) {
		draw(x, y, "SEI");
	}

	@Override
	public void drawInvertedSouthWestWall(int x, int y) {
		draw(x, y, "SWI");
	}


	@Override
	public void drawDigger(int x, int y) {
		draw(x,y, "D");
	}


	@Override
	public void drawGold(int x, int y, int count) {
		draw(x, y, ""+count);		
	}


    @Override
    public void drawShadow(int x, int y) {
        draw(x,y,"V");
    }


	@Override
	public void drawBank(int x, int y) {
		draw(x, y, "B");
	}
}
