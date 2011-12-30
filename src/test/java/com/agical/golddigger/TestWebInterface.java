package com.agical.golddigger;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.agical.golddigger.emptyfield.EmptyField;
import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.Diggers;
import com.agical.golddigger.model.fieldcreator.ResourceFieldCreator;
import com.agical.golddigger.server.GolddiggerServer;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;


public class TestWebInterface {

    private GolddiggerServer server;
    private WebConversation wc;

    @Before
    public void startServer() throws Exception {
        Diggers diggers = new Diggers(ResourceFieldCreator.factory(EmptyField.class));
        Digger digger = diggers.createDigger("Diggers name", "secretname");
        diggers.newGame(digger);
        server = new GolddiggerServer();
        server.start(diggers, "target/calls.log");
        wc = new WebConversation();
    }
    
    @After
    public void stopServer() throws Exception {
        server.stop();
    }
    
    @Test
    public void view() throws Exception {
        WebResponse response = wc.getResponse("http://localhost:8066/golddigger/digger/secretname/view");
        assertEquals("www\nwb.\nw..\n", response.getText());
    }

    @Test
    public void score() throws Exception {
        WebResponse response = wc.getResponse("http://localhost:8066/golddigger/digger/secretname/score");
        assertEquals("0\n", response.getText());
    }

    @Test
    public void grab() throws Exception {
        WebResponse response = wc.getResponse("http://localhost:8066/golddigger/digger/secretname/grab");
        assertEquals("0\n", response.getText());
    }

    @Test
    public void moveToTheNextField() throws Exception {
        WebResponse response = wc.getResponse("http://localhost:8066/golddigger/digger/secretname/next");
        assertEquals("OK\n", response.getText());
        view();
        score();
    }

    @Test
    public void move() throws Exception {
        moveAndAssert("east", "www\nb..\n...\n");
        moveAndAssert("east", "www\n..w\n..w\n");
        moveAndAssert("south", "..w\n..w\n..w\n");
        moveAndAssert("south", "..w\n..w\nwww\n");
        moveAndAssert("west", "...\n...\nwww\n");
        moveAndAssert("west", "w..\nw..\nwww\n");
        moveAndAssert("north", "wb.\nw..\nw..\n");
        moveAndAssert("north", "www\nwb.\nw..\n");
    }

    private void moveAndAssert(String direction, String result) throws MalformedURLException, IOException, SAXException {
        WebResponse response = wc.getResponse("http://localhost:8066/golddigger/digger/secretname/move/" + direction);
        assertEquals("OK\n", response.getText());
        response = wc.getResponse("http://localhost:8066/golddigger/digger/secretname/view");
        assertEquals(result, response.getText());
    }
    
    
}
