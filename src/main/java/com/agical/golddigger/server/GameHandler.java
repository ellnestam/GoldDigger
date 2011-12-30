package com.agical.golddigger.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.agical.golddigger.model.Diggers;

public class GameHandler extends HttpServlet {
    private static final long serialVersionUID = 5559373883024482574L;
    private Writer log;
    private PathExecutor pathExecutor;
    private Set<String> executingSecrets = new HashSet<String>();
    public GameHandler(Diggers diggers, String logFile) {
        this(diggers, getLogWriter(logFile));
    }
    
    public GameHandler(Diggers diggers, Writer log) {
        pathExecutor = new PathExecutor(diggers, new VoidWriter());
        this.log = log;
    }
    
    private static Writer getLogWriter(String logFileName) {
        try {
            new File(logFileName).getParentFile().mkdirs();
            return new SynchronizedWriter(new FileWriter(logFileName));
        } catch (Exception e) {
            throw new Error("Cannot open log file", e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] splitPath = req.getPathInfo().split("/");
        String sync = "defaultSyncstring";
        if (splitPath.length > 2) sync = splitPath[2];
        try {
            boolean isExecuting = true;
            while (isExecuting) {
                synchronized (executingSecrets) {
                    if(executingSecrets.contains(sync)) {
                        isExecuting = true;
                    } else {
                        isExecuting = false;
                        executingSecrets.add(sync);
                    }
                }
                if(isExecuting) Thread.sleep(50);
            }
            synchronized(sync) {
                long sleep = 100;
                // long sleep = 20; // The sleep value is only used as a load limiter, no client should be able to overload the server. Change it if you want to test and is in a hurry
                String header = req.getHeader("sleep");
                if (header != null) {
                    try {
                        sleep = Long.parseLong(header);
                    } catch (NumberFormatException e) {
                        // never mind
                    }
                }
                Thread.sleep(sleep);
            }
            String pathInfo = req.getPathInfo().substring(1);
            log.append(System.currentTimeMillis() + " " + pathInfo + "\n");
            log.flush();
            PrintWriter writer = resp.getWriter();
            pathExecutor.executePath(pathInfo, writer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executingSecrets.remove(sync);
        }
    }
}
