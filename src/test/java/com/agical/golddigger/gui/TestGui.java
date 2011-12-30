package com.agical.golddigger.gui;

import static org.junit.Assert.*;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Test;

import com.agical.golddigger.server.AdminWebController;
import com.agical.golddigger.server.GolddiggerServer;
import com.agical.jambda.Tuples.Tuple2;
import com.meterware.httpunit.WebResponse;


public class TestGui {

    private Tuple2<Tuple2<GolddiggerGui, GolddiggerServer>, Tuple2<GolddiggerGui, GolddiggerServer>> gameWithPlayback;

    @Test
    public void newPlayerShowsUp() throws Exception {
        gameWithPlayback = GolddiggerGame.startGameWithPlayback(100L, 8066, 8067, "target/calls.log", "target/playbackCalls.log");
        AdminWebController admin1 =new AdminWebController(8066);

        admin1.add("name1", "secret1");
        admin1.add("name2", "secret2");
        admin1.add("name3", "secret3");
        admin1.add("name4", "secret4");
        WebResponse listdiggers = admin1.listdiggers();
        String text = listdiggers.getText();
        assertTrue(text.contains("name1"));
        assertTrue(text.contains("name2"));
        assertTrue(text.contains("name3"));
        assertTrue(text.contains("name4"));
        JLabel score1 = gameWithPlayback.getFirst().getFirst().getScoreFor("name1");
        JLabel score2 = gameWithPlayback.getFirst().getFirst().getScoreFor("name2");
        JLabel score3 = gameWithPlayback.getFirst().getFirst().getScoreFor("name3");
        JLabel score4 = gameWithPlayback.getFirst().getFirst().getScoreFor("name4");
        Thread.sleep(2000);
        assertTrue(score1.isShowing());
        assertTrue(score2.isShowing());
        assertTrue(score3.isShowing());
        assertTrue(score4.isShowing());
        assertEquals("name1: 0 [0]", score1.getText());
        assertEquals("name2: 0 [0]", score2.getText());
        assertEquals("name3: 0 [0]", score3.getText());
        assertEquals("name4: 0 [0]", score4.getText());
    }

    @After
    public void stopMainServer() throws Exception {
        gameWithPlayback.getFirst().getSecond().stop();
    }
    @After
    public void stopPlaybackServer() throws Exception {
        gameWithPlayback.getSecond().getSecond().stop();
    }
}
