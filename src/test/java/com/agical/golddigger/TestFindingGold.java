package com.agical.golddigger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.GoldField;
import com.agical.golddigger.model.Position;
import com.agical.golddigger.model.Square;
import com.agical.golddigger.model.fieldcreator.FieldCreator;
import com.agical.jambda.Option;


public class TestFindingGold {
    private FieldCreator fieldCreator;
    private GoldField goldField;
    private Digger digger;

    private final class FieldCreatorImplementation extends FieldCreator {
        public Square[][] createField() {
            return new Square[][]{
                                {w(),w(),w(),w(),w()},
                                {w(),b(),g(),g(),w()},
                                {w(),g9(),g(),g(),w()},
                                {w(),w(),w(),w(),w()}};
        }
        public int getMaxLatitude() {
            return 2;
        }
        public int getMaxLongitude() {
            return 3;
        }
    }

    @Before
    public void before() throws Exception {
        fieldCreator = new FieldCreatorImplementation();
        goldField = new GoldField(fieldCreator);
    
    }
    
    @Test
    public void diggerMustBringGoldToBankBaseBeforeCashingIn() throws Exception {
       digger = new Digger("Diggers name", "secretName");
       digger.newGame(goldField);
       digger.move(Position.EAST);
       digger.grab();
       assertEquals(1, digger.getCarriedGold());
       assertEquals(".",goldField.fieldAt(1,2));
       digger.move(Position.WEST);
       digger.drop();
       assertEquals(1, digger.getGoldInTheBank());
       assertEquals(0, digger.getCarriedGold());
    }

    @Test
    public void diggerCannotDropMoreThanNineInOneSquare() throws Exception {
       digger = new Digger("Diggers name", "secretName");
       digger.newGame(goldField);
       digger.move(Position.EAST);
       digger.grab();
       assertEquals(1, digger.getCarriedGold());
       assertEquals(".",goldField.getSquare(digger.getPosition()).getStringRepresentation());
       digger.move(Position.WEST);
       digger.move(Position.SOUTH);
       digger.drop();
       assertEquals("9", goldField.getSquare(digger.getPosition()).getStringRepresentation());
       assertEquals(1, digger.getCarriedGold());
    }

    @Test
    public void diggerCanDropGold() throws Exception {
       digger = new Digger("Diggers name", "secretName");
       digger.newGame(goldField);
       digger.move(Position.EAST);
       digger.grab();
       assertEquals(1, digger.getCarriedGold());
       assertEquals(".",goldField.fieldAt(1,2));
       digger.move(Position.SOUTH);
       digger.drop();
       assertEquals("2",goldField.fieldAt(2,2));
       assertEquals(0, digger.getCarriedGold());
    }

    @Test
    public void diggerCannotCarryMoreThanThreeGoldPieces() throws Exception {
       digger = new Digger("Diggers name", "secretName");
       digger.newGame(goldField);

       digger.move(Position.EAST);
       digger.grab();
       assertEquals(1, digger.getCarriedGold());
       assertEquals(".",goldField.getSquare(digger.getPosition()).getStringRepresentation());

       digger.move(Position.EAST);
       digger.grab();
       assertEquals(2, digger.getCarriedGold());
       assertEquals(".",goldField.getSquare(digger.getPosition()).getStringRepresentation());
       
       digger.move(Position.SOUTH);
       digger.grab();
       assertEquals(3, digger.getCarriedGold());
       assertEquals(".",goldField.getSquare(digger.getPosition()).getStringRepresentation());

       digger.move(Position.WEST);
       digger.grab();
       assertEquals(3, digger.getCarriedGold());
       assertEquals("1",goldField.getSquare(digger.getPosition()).getStringRepresentation());

    }

    @Test
    public void diggerKnowsViewedFields() throws Exception {
       digger = new Digger("Diggers name", "secretName");
       digger.newGame(goldField);
       Square[][] squares = digger.getGoldField().getSquares();
       Option<Square> unviewed = squares[0][0].hasBeenViewed();
       assertTrue(unviewed.isEmpty());
       digger.getView();
       Option<Square> viewed = squares[0][0].hasBeenViewed();
       assertTrue(viewed.isSome());
    }

}
