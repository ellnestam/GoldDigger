package com.agical.golddigger;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.Diggers;
import com.agical.golddigger.model.fieldcreator.ResourceFieldCreator;
import com.agical.golddigger.server.GolddiggerServer;
import com.agical.golddigger.server.WebController;
import com.meterware.httpunit.WebResponse;


public class TestWebInterfaceForNonEmptyField extends WebController {

    public TestWebInterfaceForNonEmptyField() {
        super("secretname", 8066, 0);
    }

    private GolddiggerServer server;

    @Before 
    public void startServer() throws Exception {
        Diggers diggers = new Diggers(ResourceFieldCreator.factory(getClass()));
        Digger digger = diggers.createDigger("Diggers name", "secretname");
        diggers.newGame(digger);
        server = new GolddiggerServer();
        server.start(diggers, "target/calls.log");
    }
    
    @After
    public void stopServer() throws Exception {
        server.stop();
    }
    
    @Test
    public void diggerView() throws Exception {
        WebResponse response = view();
        assertEquals("www\nwb.\nw.1\n", response.getText());
    }


    @Test
    public void cashGoldInBank() throws Exception {
        WebResponse response = score();
        grabPieceAndCash(response);
    }

    private void grabPieceAndCash(WebResponse response) throws IOException {
        assertEquals("0\n", response.getText());
        moveSouth();
        moveEast();
        response = grab();
        assertEquals("1\n", response.getText());
        moveNorth();
        moveWest();
        response = drop();
        assertEquals("1\n", response.getText());
        response = carrying();
        assertEquals("0\n", response.getText());
        response = score();
        assertEquals("1\n", response.getText());
    }

    @Test
    public void grabbingGold() throws Exception {
        WebResponse response = grab();
        assertEquals("0\n", response.getText());
        response = moveSouth();
        response = moveEast();
        response = grab();
        assertEquals("1\n", response.getText());
        response = carrying();
        assertEquals("1\n", response.getText());
    }

    @Test
    public void droppingGold() throws Exception {
        WebResponse response = grab();
        assertEquals("0\n", response.getText());
        response = moveSouth();
        response = moveEast();
        response = grab();
        assertEquals("1\n", response.getText());
        response = carrying();
        assertEquals("1\n", response.getText());
        response = drop();
        assertEquals("1\n", response.getText());
        response = carrying();
        assertEquals("0\n", response.getText());
    }
    
    @Test
    public void moveToNextField() throws Exception {
        WebResponse response = nextField();
        assertEquals("FAILED\n", response.getText());
        response = score();
        assertEquals("0\n", response.getText());
    }
    

}
