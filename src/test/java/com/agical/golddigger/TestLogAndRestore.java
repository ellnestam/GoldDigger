package com.agical.golddigger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.agical.golddigger.gui.GolddiggerGame;
import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.Diggers;
import com.agical.golddigger.model.fieldcreator.ResourceFieldCreator;
import com.agical.golddigger.model.fieldcreator.fields.CompetitionFields;
import com.agical.golddigger.server.PathExecutor;
import com.agical.golddigger.server.VoidWriter;

public class TestLogAndRestore {
    
    private Diggers diggers;
    
    @Before
    public void before() throws Exception {
        diggers = new Diggers(CompetitionFields.factory(6));
    }
    
    @Test
    public void restoreFromLog() throws Exception {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("calls.log"), "UTF-8");
        PathExecutor executor = new PathExecutor(diggers, new VoidWriter());
        executor.restoreFromLog(reader);
        assertEquals(4, diggers.getDiggers().size());
        Digger digger = diggers.getDiggers().get(0);
        assertEquals(3, digger.getGoldInTheBank());
        assertEquals(0, digger.getCarriedGold());
    }
    
    @Test
    public void restoreFromLogWithDelay() throws Exception {
        long currentTime = System.currentTimeMillis();
        String log = currentTime + " admin/ccret/add/Jack/Daniels\n" 
                + (currentTime + 1)+ " digger/Daniels/move/south\n" 
                + (currentTime + 2) + " digger/Daniels/move/east\n"
                + (currentTime + 3) + " digger/Daniels/grab\n" 
                + (currentTime + 4) + " digger/Daniels/move/north\n"
                + (currentTime + 5) + " digger/Daniels/move/west\n" 
                + (currentTime + 6) + " digger/Daniels/drop\n";
        Reader reader = new StringReader(log);
        PathExecutor executor = new PathExecutor(diggers, new VoidWriter());
        executor.restoreFromLogWithDelay(reader, 1000);
        assertEquals(1, diggers.getDiggers().size());
        Digger digger = diggers.getDiggers().get(0);
        assertEquals(1, digger.getGoldInTheBank());
        assertEquals(0, digger.getCarriedGold());
        assertTrue(System.currentTimeMillis() - currentTime > 1000);
    }
    
    @Test
    public void restoreFromLogContinuously() throws Exception {
        long currentTime = System.currentTimeMillis();
        PipedWriter src = new PipedWriter();
        final PipedReader reader = new PipedReader(src);
        final List<Exception> exceptions = new ArrayList<Exception>();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    PathExecutor executor = new PathExecutor(diggers, new VoidWriter());
                    executor.restoreFromLogWithDelay(reader, 1000);
                } catch (Exception e) {
                    exceptions.add(e);
                }
            }
        });
        thread.start();
        writeLog(src);
        src.close();
        thread.join();
        assertEquals(0, exceptions.size());
        assertEquals(1, diggers.getDiggers().size());
        Digger digger = diggers.getDiggers().get(0);
        assertEquals(1, digger.getGoldInTheBank());
        assertEquals(0, digger.getCarriedGold());
        assertTrue(System.currentTimeMillis() - currentTime >= 1000);
    }

    @Test
    @Ignore
    public void gameAndPlaybackFromRestoredGame() throws Exception {
        String mainLogFile = "/tmp/golddigger/main.log";
        String playbackApplicationLogFile = "/tmp/golddigger/playback.log";
        String restoreFile = "/tmp/golddigger/restore.log";
        GolddiggerGame.startGameWithPlayback(1000, 8066, 8067, mainLogFile, playbackApplicationLogFile, restoreFile);
    }
    
    private void writeLog(Writer src) throws IOException {
        src.write(System.currentTimeMillis() + " admin/ccret/add/Jack/Daniels\n");
        src.write(System.currentTimeMillis() + " digger/Daniels/move/south\n");
        src.write(System.currentTimeMillis() + " digger/Daniels/move/east\n");
        src.write(System.currentTimeMillis() + " digger/Daniels/grab\n");
        src.write(System.currentTimeMillis() + " digger/Daniels/move/north\n");
        src.write(System.currentTimeMillis() + " digger/Daniels/move/west\n");
        src.write(System.currentTimeMillis() + " digger/Daniels/drop\n");
    }
    
}
