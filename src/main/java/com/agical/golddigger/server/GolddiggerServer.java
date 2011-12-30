package com.agical.golddigger.server;

import java.io.File;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.DefaultServlet;
import org.mortbay.jetty.servlet.ServletHolder;

import com.agical.golddigger.model.Diggers;

public class GolddiggerServer {

    private Server server;
    private int port;
    private String contextName;

    
    public GolddiggerServer(int port, String contextName) {
        super();
        this.port = port;
        this.contextName = contextName;
    }
    
    public GolddiggerServer() {
        this(8066, "golddigger");
    }

    public void start(Diggers diggers, String logFile) {
        try {
            server = new Server(port);
            Context root = new Context(server, "/", Context.SESSIONS);
            root.addServlet(new ServletHolder(new GameHandler(diggers, logFile)), "/" + contextName + "/*");
            root.setResourceBase(new File("./target/site").getAbsolutePath());
            root.addServlet(DefaultServlet.class.getName(), "/");

            server.start();
            System.out.println("startServer");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }

    public void stop() {
        try {
            server.stop();
            System.out.println("stopServer");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
