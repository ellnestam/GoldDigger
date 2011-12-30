package com.agical.golddigger.clients;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.agical.golddigger.server.AdminWebController;
import com.agical.golddigger.server.WebController;
import com.agical.jambda.Parallel;
import com.agical.jambda.Sequence;
import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Functions.Fn1;
import com.meterware.httpunit.WebResponse;

public class DrunkenDigger {
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
        adminWebController.add("Jack", "Daniels");
        adminWebController.add("Cala", "Isla");
        adminWebController.add("Johnny", "Walker");
        adminWebController.add("Tullamore", "Dew");
        
        final WebController controller = new WebController("Daniels", port, 200);
        final WebController controller2 = new WebController("Isla", port, 200);
        final WebController controller3 = new WebController("Walker", port, 200);
        final WebController controller4 = new WebController("Dew", port, 200);
        Iterable<WebController> parallel = Parallel.parallel(Sequence.createSequence(staggerFn.curry(controller),
                staggerFn.curry(controller2), staggerFn.curry(controller3), staggerFn.curry(controller4)), 4);
        Iterator<WebController> iterator = parallel.iterator();
        WebController wc = iterator.next();
        wc = iterator.next();
        wc = iterator.next();
        wc = iterator.next();
    }
    
    public static final Fn1<WebController, WebController> staggerFn = new Fn1<WebController, WebController>() {
        public WebController apply(WebController controller) {
            List<Fn0<WebResponse>> moves = new ArrayList<Fn0<WebResponse>>();
            configureMoves(controller, moves);
            for (int nrOfFields = 0; nrOfFields < 6; nrOfFields++) {
                for (int nrOfActions = 0; nrOfActions < 300; nrOfActions++) {
                    Fn0<WebResponse> move = moves.get((int) (Math.random() * 4));
                    controller.view();
                    controller.grab();
                    move.apply();
                    controller.view();
                    controller.drop();
                    controller.score();
                }
                controller.nextField();
            }
            return controller;
        }};
    
    public static void configureMoves(final WebController controller, List<Fn0<WebResponse>> moves) {
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
