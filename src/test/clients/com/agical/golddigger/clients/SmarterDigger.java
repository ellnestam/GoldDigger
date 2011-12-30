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

public class SmarterDigger {
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
        AdminWebController adminWebController = new AdminWebController(port);
        adminWebController.add("Drunken", "Drunken");
        adminWebController.add("Smarter", "Smarter");
        
        
        final WebController controller = new WebController("Drunken", port, 200);
        final WebController controller2 = new WebController("Smarter", port, 200);
        Iterable<WebController> parallel = Parallel.parallel(
                Sequence.createSequence(
                        DrunkenDigger.staggerFn.curry(controller),
                        staggerFn.curry(controller2)
                        ), 4);
        Iterator<WebController> iterator = parallel.iterator();
        WebController wc = iterator.next();
        wc = iterator.next();
    }
    
    Fn1<WebController, WebController> staggerFn = new Fn1<WebController, WebController>() {
        public WebController apply(WebController controller) {
            try {
                List<Fn0<WebResponse>> moves = new ArrayList<Fn0<WebResponse>>();
                configureMoves(controller, moves);
                List<Fn0<WebResponse>> returnPath = new ArrayList<Fn0<WebResponse>>();
                for (int nrOfFields = 0; nrOfFields < 4; nrOfFields++) {
                    for (int nrOfActions = 0; nrOfActions < 80; nrOfActions++) {
                        int index = (int) (Math.random() * 4);
                        Fn0<WebResponse> move = moves.get(index);
                        String load = controller.grab().getText();
                        if(!load.contains("0")) {
                            for (Fn0<WebResponse> returnMove : Sequence.reverse(returnPath)) {
                                returnMove.apply();
                            }
                            controller.drop();
                            returnPath.clear();
                        } else {
                            WebResponse apply = move.apply();
                            if(apply.getText().contains("OK")) {
                                returnPath.add(moves.get((index+2)%4));
                            }
                            controller.view();
                            controller.drop();
                            controller.score();
                        }
                    }
                    controller.nextField();
                }
                return controller;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
    };
    
    private void configureMoves(final WebController controller, List<Fn0<WebResponse>> moves) {
        // Fn1 med controller som arg
        Fn0<WebResponse> north = new Fn0<WebResponse>() {
            public WebResponse apply() {
                return controller.moveNorth();
            }
        };
        Fn0<WebResponse> east = new Fn0<WebResponse>() {
            public WebResponse apply() {
                return controller.moveEast();
            }
        };
        Fn0<WebResponse> south = new Fn0<WebResponse>() {
            public WebResponse apply() {
                return controller.moveSouth();
            }
        };
        Fn0<WebResponse> west = new Fn0<WebResponse>() {
            public WebResponse apply() {
                return controller.moveWest();
            }
        };
        moves.add(north);
        moves.add(east);
        moves.add(south);
        moves.add(west);
    }
    
}
