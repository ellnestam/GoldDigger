package com.agical.golddigger.view;

import static com.agical.golddigger.view.PeekTools.peekFrom;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.Test;

import com.agical.golddigger.model.Position;
import com.agical.golddigger.model.Rectangle;
import com.agical.golddigger.model.Square;

public class TestPeek {

	@Test
	public void drawsSimpleField() throws IOException {
		String map = "...\n"+
		 			 ".w.\n"+
		 			 "...\n";

		Peek peek = peekFrom(map);
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares("0,0,.", "1,0,.", "2,0,.",
				                                      "0,1,.", "1,1,C", "2,1,.",
				                                      "0,2,.", "1,2,.", "2,2,.",
				                                      "0,0,D")));
	}
	
	
	@Test
	public void drawsNorthWall() throws IOException {
		
		String map = "www\n"+
					 "www\n"+
					 "...\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,X", "1,0,X", "2,0,X", 
				"0,1,N", "1,1,N", "2,1,N", 
				"0,2,.", "1,2,.", "2,2,.",
				"0,0,D")));
	}
	
	@Test
	public void drawsEastWall() throws IOException {
		String map = ".ww\n"+
		".ww\n"+
		".ww\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,.", "1,0,E", "2,0,X",
				"0,1,.", "1,1,E", "2,1,X", 
				"0,2,.", "1,2,E", "2,2,X",
				"0,0,D")));
	}

	@Test
	public void drawsNorthEastWall() throws IOException {
		
		String map = "www\n"+
					 "www\n"+
					 ".ww\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,X", "1,0,X", "2,0,X", 
				"0,1,N", "1,1,NE", "2,1,X", 
				"0,2,.", "1,2,E", "2,2,X",
				"0,0,D")));
	}

	
	@Test
	public void drawsSouthWall() throws IOException {
		String map = "...\n"+
					 "www\n"+
					 "www\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,.", "1,0,.", "2,0,.",
				"0,1,S", "1,1,S", "2,1,S", 
				"0,2,X", "1,2,X", "2,2,X",
				"0,0,D")));
	}
	
	@Test
	public void drawsSouthEastWall() throws IOException {
		
		String map = ".ww\n"+
					 "www\n"+
					 "www\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,.", "1,0,E", "2,0,X", 
				"0,1,S", "1,1,SE", "2,1,X", 
				"0,2,X", "1,2,X", "2,2,X",
				"0,0,D")));
	}
	
	@Test
	public void drawsWestWall() throws IOException {
		String map = "ww.\n"+
					 "ww.\n"+
					 "ww.\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,X", "1,0,W", "2,0,.",
				"0,1,X", "1,1,W", "2,1,.", 
				"0,2,X", "1,2,W", "2,2,.",
				"0,0,D")));
	}
	
	@Test
	public void drawsSouthWestWall() throws IOException {
		
		String map = "ww.\n"+
					 "www\n"+
					 "www\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,X", "1,0,W", "2,0,.", 
				"0,1,X", "1,1,SW", "2,1,S", 
				"0,2,X", "1,2,X", "2,2,X",
				"0,0,D")));
	}
	
	@Test
	public void drawsNorthWestWall() throws IOException {
		
		String map = "www\n"+
					 "www\n"+
					 "ww.\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,X", "1,0,X", "2,0,X", 
				"0,1,X", "1,1,NW", "2,1,N", 
				"0,2,X", "1,2,W", "2,2,.",
				"0,0,D")));
	}
	
	@Test
	public void drawsInvertedNorthEastWall() throws IOException {
		
		String map = "...\n"+
					 "ww.\n"+
					 "ww.\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,.", "1,0,.", "2,0,.", 
				"0,1,S", "1,1,NEI", "2,1,.", 
				"0,2,X", "1,2,W", "2,2,.",
				"0,0,D")));
	}
	
	@Test
	public void drawsInvertedNorthWestWall() throws IOException {
		
		String map = "...\n"+
					 ".ww\n"+
					 ".ww\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,.", "1,0,.", "2,0,.", 
				"0,1,.", "1,1,NWI", "2,1,S", 
				"0,2,.", "1,2,E", "2,2,X",
				"0,0,D")));
	}
	
	@Test
	public void drawsInvertedSouthEastWall() throws IOException {
		
		String map = "ww.\n"+
					 "ww.\n"+
					 "...\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,X", "1,0,W", "2,0,.", 
				"0,1,N", "1,1,SEI", "2,1,.", 
				"0,2,.", "1,2,.", "2,2,.",
				"0,0,D")));
	}
	
	
	@Test
	public void drawsInvertedSouthWestWall() throws IOException {
		
		String map = ".ww\n"+
					 ".ww\n"+
					 "...\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,.", "1,0,E", "2,0,X", 
				"0,1,.", "1,1,SWI", "2,1,N", 
				"0,2,.", "1,2,.", "2,2,.",
				"0,0,D")));
	}
	
	@Test
	public void drawsBankSquare() throws IOException {
		
		String map = "...\n"+
					 ".b.\n"+
					 "...\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,.", "1,0,.", "2,0,.", 
				"0,1,.", "1,1,B", "2,1,.", 
				"0,2,.", "1,2,.", "2,2,.",
				"0,0,D")));
	}
	
	
	@Test
	public void drawsAllEdgeWalls() throws IOException {
		String map = "wwwwwwww\n"+
					 "wwwwwwww\n"+
					 "ww....ww\n"+
					 "ww....ww\n"+
					 "ww....ww\n"+
					 "wwwwwwww\n"+
					 "wwwwwwww\n";
		
		Peek peek = peekFrom(map);
		
		PeekViewStub peekView  = new PeekViewStub();
		peek.drawTo(peekView);
		
		assertThat(peekView.getLog(), equalTo(squares(
				"0,0,X", "1,0,X", "2,0,X", "3,0,X", "4,0,X", "5,0,X", "6,0,X", "7,0,X",
				"0,1,X", "1,1,NW", "2,1,N", "3,1,N", "4,1,N", "5,1,N", "6,1,NE", "7,1,X",
				"0,2,X", "1,2,W", "2,2,.", "3,2,.", "4,2,.", "5,2,.", "6,2,E", "7,2,X",
				"0,3,X", "1,3,W", "2,3,.", "3,3,.", "4,3,.", "5,3,.", "6,3,E", "7,3,X",
				"0,4,X", "1,4,W", "2,4,.", "3,4,.", "4,4,.", "5,4,.", "6,4,E", "7,4,X",
				"0,5,X", "1,5,SW", "2,5,S", "3,5,S", "4,5,S", "5,5,S", "6,5,SE", "7,5,X",
				"0,6,X", "1,6,X", "2,6,X", "3,6,X", "4,6,X", "5,6,X", "6,6,X", "7,6,X",
				"0,0,D")));
	}

	private String squares(String... cells) throws IOException {
		StringWriter sw = new StringWriter();
		for (String cell : cells) {
			sw.write('(');
			sw.write(cell);
			sw.write(')');
			sw.write("\n");
		}
		sw.close();
		return sw.toString();
	}
}
