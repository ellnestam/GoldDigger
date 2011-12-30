/**
 * 
 */
package com.agical.golddigger.model;

import com.agical.jambda.Option;

public abstract class Square {
	public static String getField(Square[][] createField) {
		String result = "";
		for (Square[] squares : createField) {
			for (Square square : squares) {
				result += square.getStringRepresentation();
			}
			result += "\n";
		}
		return result;
	}
	public static Square createFromChar(char squareChar) {
		if(squareChar == 'w') return Square.wall(); 
		if(squareChar == 'b') return new BankSquare();
		if(squareChar >= ASCII_NR_START && squareChar <= ASCII_NR_END) return new GoldSquare(squareChar-ASCII_NR_START);
		return new GoldSquare(0);
	}
    public static final Square empty() {
        return new GoldSquare(0);
    }
    public static Square wall() {
        return new WallSquare();
    }
        private static final int ASCII_NR_START = 48;
        private static final int ASCII_NR_END = 59;
        private Option<Square> viewed = Option.none();
        
        public Square() {
            super();
        }
        protected void doNothing() {}

        public abstract String getStringRepresentation();
        
        public final String toString() {
            return getStringRepresentation();
        }
        public void grabBy(Digger digger) {
            doNothing();
        }
        public void dropBy(Digger digger) {
            doNothing();
        }
        public boolean isTreadable() {
            return true;
        }
        public Option<Square> hasBeenViewed() {
            return viewed;
        }
        public void viewed() {
            viewed = Option.some(this);
        }
        public boolean isEmpty() {
            return true;
        }
    }