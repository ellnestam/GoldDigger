package com.agical.golddigger.clients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.agical.golddigger.gui.GolddiggerGame;
import com.agical.golddigger.server.AdminWebController;
import com.agical.golddigger.server.WebController;
import com.agical.jambda.Parallel;
import com.agical.jambda.Sequence;
import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.meterware.httpunit.WebResponse;

public class MisbehavingDigger {
    private int port = 8066;
    private int playbackPort = 8067;
    private String mainLog = "target/calls.log";
    private String playbackLog = "target/playbackCalls.log";
    
    @Before
    public void before() throws Exception {
//        GolddiggerGame.startGameWithPlayback(1000, port, playbackPort, mainLog, playbackLog);
    }
    
    @Test
    public void staggerAround() throws Exception {
//        AdminWebController adminWebController = new AdminWebController(port);
//        adminWebController.add("Jack", "Daniels");
        
        final WebController controller1 = new WebController("Daniels", port, 200);
        final WebController controller2 = new WebController("Daniels", port, 200);
        final WebController controller3 = new WebController("Daniels", port, 200);
        final WebController controller4 = new WebController("Daniels", port, 200);
        Iterable<WebController> parallel = Parallel.parallel(Sequence.createSequence(
                DrunkenDigger.staggerFn.curry(controller1),
                DrunkenDigger.staggerFn.curry(controller2), 
                DrunkenDigger.staggerFn.curry(controller3), 
                DrunkenDigger.staggerFn.curry(controller4)), 4);
        Iterator<WebController> iterator = parallel.iterator();
        WebController wc = iterator.next();
        wc = iterator.next();
        wc = iterator.next();
        wc = iterator.next();
    }
    
    
}
