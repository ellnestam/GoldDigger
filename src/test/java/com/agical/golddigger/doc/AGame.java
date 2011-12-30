package com.agical.golddigger.doc;

import static com.agical.bumblebee.junit4.Storage.store;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.agical.golddigger.gui.GolddiggerGame;
import com.agical.golddigger.gui.GolddiggerGui;
import com.agical.golddigger.model.fieldcreator.fields.CompetitionFields;
import com.agical.golddigger.server.AdminWebController;
import com.agical.golddigger.server.GolddiggerServer;
import com.agical.golddigger.server.WebController;
import com.agical.jambda.Tuples.Tuple2;
import com.meterware.httpunit.WebResponse;

public class AGame {

    private static Tuple2<GolddiggerGui,GolddiggerServer> game;
    private WebController webController = new WebController("veryVerySecret", 8066, 0);

    @BeforeClass
    public static void startServer() throws Exception {
    	
        game = GolddiggerGame.startGameWithoutPlayback(8066, "target/documentationCalls.log", CompetitionFields.factory(6));
        AdminWebController adminWebController = new AdminWebController(8066);
        adminWebController.add("DaDiggas", "veryVerySecret");
    }
    
    @AfterClass
    public static void stopServer() throws Exception {
        game.getSecond().stop();
    }

    public void moveToOrigin() throws Exception {
        webController.moveNorth();
        webController.moveNorth();
        webController.moveNorth();
        webController.moveWest();
        webController.moveWest();
        webController.moveWest();
    }
    @Test
    public void viewYourNearestSurroundings() throws Exception {
        /*!
        To view your nearest surroundings, issue the following command to the server:
        >>>>
        #{url}
        <<<<
        The response text looks like this:
        >>>>
        #{response}
        <<<<
        This represents the 3-by-3 square surrounding the player, where the player stands on the center 
        position, the **b** in this case. 
        
        The **w** represents *walls*. Walls limits the area in which the player can move and they 
        cannot be treaded. The outside of the field is surrounded by walls, but walls can also be present 
        inside the field, where they are equally untreadable.
        
        The **b** represents the bank square. The bank square is always at the top left square 
        of the field, and this is also the initial position of the digger on all fields.
        
        The **.** (dot) represents an empty square.
        
        Numbers represents squares where there is **GOLD**! More about that later.
        */
        WebResponse view = webController.view();
        store("url", view.getURL());
        store("response", view.getText());
        assertEquals("www\nwb.\nw.1\n", view.getText());
    }
    
    @Test
    public void makeAMove() throws Exception {
        /*!
        The player can move in four directions:

        direction || url || response
        south | #{south} | #{southResponse}
        With the new view:
        >>>>
        #{viewS}
        <<<<
        direction || url || response
        east | #{east} | #{eastResponse}
        With the new view:
        >>>>
        #{viewE}
        <<<<
        direction || url || response
        north | #{north} | #{northResponse}
        With the new view:
        >>>>
        #{viewN}
        <<<<
        direction || url || response
        west | #{west} | #{westResponse}
        With the new view:
        >>>>
        #{viewW}
        <<<<
    
        If the move cannot be performed, like for instance you are positioned like this:
        >>>>
        #{view}
        <<<<
        ...moving north will not be possible and the response will be
        
        direction || url || response
        north | #{north2} | #{northResponse2}

        The view is unchanged
        
        */
        WebResponse south = webController.moveSouth();
        viewAndStore("viewS");
        WebResponse east = webController.moveEast();
        viewAndStore("viewE");
        WebResponse north = webController.moveNorth();
        viewAndStore("viewN");
        WebResponse west = webController.moveWest();
        viewAndStore("viewW");
        store("south", south.getURL());
        store("east", east.getURL());
        store("north", north.getURL());
        store("west", west.getURL());
        store("southResponse", south.getText());
        store("eastResponse", east.getText());
        store("northResponse", north.getText());
        store("westResponse", west.getText());
        String ok = "OK\n";
        assertEquals(ok, south.getText());
        assertEquals(ok, east.getText());
        assertEquals(ok, south.getText());
        assertEquals(ok, west.getText());
        viewAndStore("view");
        
        WebResponse north2 = webController.moveNorth();
        store("north2", north2.getURL());
        store("northResponse2", north2.getText());
        assertEquals("FAILED\n", north2.getText());
    
    }
    
    @Test
    public void grabTheGold() throws Exception {
        /*!
        To grab the gold on the spot you stand, issue the *grab* command. 
        This is the view before *grab* where we have centered the player above a 
        square with a number indicating gold:
        >>>>
        #{viewBefore}
        <<<<
        ...this is the grab command:
        action || url || response
        grab | #{grabUrl} | #{grabResponse}
        
        The response indicates that one piece of gold was grabbed. The view after lookes like this:
        >>>>
        #{viewAfter}
        <<<<
        The GoldDigger is greedy and will always try to grab as much as possible, but he cannot
        carry more than 3 pieces of gold at a single time. Here he is on a square with nine pieces,
        carrying one piece already:
        >>>>
        #{viewOnNine}
        <<<<
        When grabbing the gold on that spot, the response is
        action || response
        grab | #{grabResponse2}
        
        ...and the view looks like this
        >>>>
        #{viewOnNineAfter}
        <<<<
        */
        webController.moveSouth();
        webController.moveEast();
        viewAndStore("viewBefore");
        WebResponse grab = webController.grab();
        store("grabUrl", grab.getURL());
        store("grabResponse", grab.getText());
        assertEquals("1\n", grab.getText());
        viewAndStore("viewAfter");
        webController.moveSouth();
        viewAndStore("viewOnNine");
        WebResponse grab2 = webController.grab();
        store("grabResponse2", grab2.getText());
        viewAndStore("viewOnNineAfter");
    }

    @Test
    public void checkHowMuchYouAreCarrying() throws Exception {
        /*!
        Now that you have started picking up gold, you might want to see how much you are carrying.
        action || url || response
        carrying | #{carryingUrl} | #{carryingResponse}
        */
        WebResponse carrying = webController.carrying();
        store("carryingUrl", carrying.getURL());
        store("carryingResponse", carrying.getText());
        assertEquals("3\n", carrying.getText());
    }

    @Test
    public void dropCarriedGold() throws Exception {
        /*!
        You cannot carry more than 3 pieces of gold at a time. When you have found and grabbed
        three pieces, you must drop the gold somewhere. This is the view before:
        >>>>
        #{viewBefore}
        <<<<
        action || url || response
        drop | #{dropUrl} | #{dropResponse}
        The response will tell you how many pieces you dropped. This is the view after:
        >>>>
        #{viewAfter}
        <<<<
        */
        webController.moveWest();
        viewAndStore("viewBefore");
        WebResponse drop = webController.drop();
        store("dropUrl", drop.getURL());
        store("dropResponse", drop.getText());
        assertEquals("3\n", drop.getText());
        viewAndStore("viewAfter");
    }

    @Test
    public void maximumNumberOfPiecesOnOneSquare() throws Exception {
        /*!
        A square can hold *nine* pieces of gold, so you can drop up to that amount on one square. 
        If you carry more than the square can absorb, you keep the difference
        >>>>
        #{viewBefore}
        <<<<
        
        action || response
        grab | #{grabResponse}
        carrying | #{carryingResponse}
        move east | #{moveEast}
        
        Then lets drop the gold there
        
        action || response
        drop | #{dropResponse}
        
        The field looks like this:
        >>>>
        #{viewAfter}
        <<<<
        ...and you are still carrying
        action || response
        carrying | #{carryingAfterResponse}
        */
        viewAndStore("viewBefore");
        WebResponse grab = webController.grab();
        store("grabResponse", grab.getText());
        WebResponse moveEast = webController.moveEast();
        store("moveEast", moveEast.getText());
        WebResponse carrying = webController.carrying();
        store("carryingResponse", carrying.getText());
        WebResponse drop = webController.drop();
        store("dropUrl", drop.getURL());
        store("dropResponse", drop.getText());
        assertEquals("2\n", drop.getText());
        viewAndStore("viewAfter");
        WebResponse carryingAfter = webController.carrying();
        store("carryingAfterResponse", carryingAfter.getText());
        assertEquals("1\n", carryingAfter.getText());
    }

    @Test
    public void cashTheGoldInTheBank() throws Exception {
        /*!
        To cash the gold, you need to go to a square with a **b** on it and drop what you are 
        carrying there. The gold will then be credited to your account, and **this is the only way to score points**.
        >>>>
        #{bank}
        <<<<
        action || url || response
        drop | #{cashedUrl} | #{cashedResponse}
         */
        WebResponse grabbed = webController.grab();
        moveToOrigin();
        WebResponse onBank = webController.view();
        System.out.println(onBank.getText());
        store("bank", onBank.getText());
        WebResponse cashed = webController.drop();
        store("cashedUrl", cashed.getURL());
        store("cashedResponse", cashed.getText());
        assertEquals("2\n", grabbed.getText());
        assertEquals("3\n", cashed.getText());
        
    }
    

    @Test
    public void checkHowMuchYouHaveInTheBank() throws Exception {
        /*!
        When you have cashed your gold, you can check your balance score like this
        action || url || response
        score | #{scoreUrl} | #{scoreResponse}
        */
        WebResponse score = webController.score();
        store("scoreUrl", score.getURL());
        store("scoreResponse", score.getText());
        assertEquals("3\n", score.getText());
    }

    @Test
    public void moveToTheNextField() throws Exception {
        /*!
        When you have cashed all the gold in one field you are allowed to move to the next.
        If you try to move to the next field *before* you have picked all gold pieces in the 
        current field you will get a message like this
        action || url || response
        next | #{nextUrl} | #{nextResponse}
        ...and you will remain in the current field until you have picked them all.
        
        After having picked all gold pieces you will get this response:
        action || url || response
        next | #{nextAfterPickingAllGoldUrl} | #{nextAfterPickingAllGoldResponse}
        ...and you will be moved to the next field.
        
        */
        WebResponse scoreBefore = webController.score();
        WebResponse next = webController.nextField();
        WebResponse scoreAfterFailingnext = webController.score();
        moveToOrigin();
        webController.moveSouth();
        webController.moveEast();
        webController.grab();
        moveToOrigin();
        webController.drop();
        for(int i = 0; i<3; i++) {
            webController.moveSouth();
            webController.moveSouth();
            webController.moveEast();
            webController.grab();
            moveToOrigin();
            webController.drop();
        }
        assertEquals("10\n", webController.score().getText());
        WebResponse nextAfterPickingAllGold = webController.nextField();
        store("nextAfterPickingAllGoldResponse", nextAfterPickingAllGold.getText());
        store("nextAfterPickingAllGoldUrl", nextAfterPickingAllGold.getURL());
        
        store("nextResponse", next.getText());
        store("nextUrl", next.getURL());
        store("scoreBefore", scoreBefore.getText());
        store("scoreAfterFailingnext", scoreAfterFailingnext.getText());
        assertEquals(0, Integer.parseInt(scoreAfterFailingnext.getText().trim())-Integer.parseInt(scoreBefore.getText().trim()));
        assertEquals("FAILED\n", next.getText());
    }

    @Test
    public void theFirstCoupleOfGoldFields() throws Exception {
        /*!
        Just to get you started and to give you an idea of what the fields look like, we reveal the first
        couple of fields. Later fields will be larger, more elaborate and random. 
        >>>>
        #{File.new('src/main/resources/com/agical/golddigger/model/fieldcreator/fields/1.field').read}
        <<<<
        >>>>
        #{File.new('src/main/resources/com/agical/golddigger/model/fieldcreator/fields/2.field').read}
        <<<<
        >>>>
        #{File.new('src/main/resources/com/agical/golddigger/model/fieldcreator/fields/3.field').read}
        <<<<
        >>>>
        #{File.new('src/main/resources/com/agical/golddigger/model/fieldcreator/fields/4.field').read}
        <<<<
        >>>>
        #{File.new('src/main/resources/com/agical/golddigger/model/fieldcreator/fields/5.field').read}
        <<<<
        */
    }
    
    private void viewAndStore(String parameterName) throws IOException {
        WebResponse viewS = webController.view();        
        store(parameterName, viewS.getText());
    }
    
}
