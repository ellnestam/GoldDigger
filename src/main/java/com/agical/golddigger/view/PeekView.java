package com.agical.golddigger.view;

public interface PeekView {

	void drawEmpty(int x, int y);

	void drawSolidWall(int x, int y);

	void drawCenterWall(int x, int y);

	void drawNorthWall(int x, int y);

	void drawNorthEastWall(int x, int y);

	void drawEastWall(int x, int y);

	void drawSouthEastWall(int x, int y);

	void drawSouthWall(int x, int y);

	void drawSouthWestWall(int x, int y);

	void drawWestWall(int x, int y);

	void drawNorthWestWall(int x, int y);

	void drawInvertedNorthEastWall(int x, int y);

	void drawInvertedNorthWestWall(int x, int y);

	void drawInvertedSouthEastWall(int x, int y);

	void drawInvertedSouthWestWall(int x, int y);

	void drawDigger(int x, int y);

	void drawGold(int x, int y, int count);

	void drawBank(int x, int y);

        void drawShadow(int x, int y);
}
