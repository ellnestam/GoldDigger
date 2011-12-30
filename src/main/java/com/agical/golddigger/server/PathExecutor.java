/**
 * 
 */
package com.agical.golddigger.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.Diggers;
import com.agical.golddigger.model.Position;
import com.agical.jambda.Functions;

public class PathExecutor {
    private final Diggers diggers;
    private final Writer log;
    
    public PathExecutor(Diggers diggers, Writer log) {
        super();
        this.diggers = diggers;
        this.log = log;
    }
    
    public void executePath(String pathInfo, PrintWriter writer) {
        String[] splitPath = pathInfo.split("/");
        String actor = splitPath[0];
        if (actor.equals("digger")) {
            handleDigger(writer, splitPath);
        } else if (actor.equals("admin")) {
            String secretName = splitPath[1];
            if (!secretName.equals("ccret")) {
                writer.write("bad command\n");
                return;
            }
            String action = splitPath[2];
            if (action.equals("listdiggers")) {
                for (Digger digger : diggers.getDiggers()) {
                    writer.write(digger.getName() + " " + digger.getSecretName() + "\n");
                }
            } else if (action.equals("add")) {
                String newName = splitPath[3];
                String newSecretName = splitPath[4];
                Digger digger = diggers.createDigger(newName, newSecretName);
                diggers.newGame(digger);
            }
        }
    }
    
    private void handleDigger(PrintWriter writer, String[] splitPath) {
        String secretName = splitPath[1];
        String action = splitPath[2];
        Digger digger = diggers.getDigger(secretName);
        try {
            if (action.equals("view")) {
                writer.write(digger.getView());
            }
            if (action.equals("score")) {
                int goldInTheBank = digger.getGoldInTheBank();
                writer.write(goldInTheBank + "\n");
            }
            if (action.equals("grab")) {
                int carriedBefore = digger.getCarriedGold();
                digger.grab();
                int carriedAfter = digger.getCarriedGold();
                writer.write((carriedAfter - carriedBefore) + "\n");
            }
            if (action.equals("drop")) {
                int carriedBefore = digger.getCarriedGold();
                digger.drop();
                int carriedAfter = digger.getCarriedGold();
                writer.write((carriedBefore - carriedAfter) + "\n");
            }
            if (action.equals("carrying")) {
                writer.write(digger.getCarriedGold() + "\n");
            }
            if (action.equals("next")) {
                if(digger.getGoldField().hasGold()) {
                    writer.write("FAILED\n");
                } else {
                    writer.write("OK\n");
                    diggers.newGame(digger);
                }
            }
            if (action.equals("move")) {
                String direction = splitPath[3].toLowerCase();
                String ok = "OK\n";
                String failed = "FAILED\n";
                if (direction.startsWith("n")) {
                    writer.write(digger.move(Position.NORTH).map(Functions.<Position, String> constantly(ok),
                            Functions.<String> constantly(failed)));
                }
                if (direction.startsWith("e")) {
                    writer.write(digger.move(Position.EAST).map(Functions.<Position, String> constantly(ok),
                            Functions.<String> constantly(failed)));
                }
                if (direction.startsWith("w")) {
                    writer.write(digger.move(Position.WEST).map(Functions.<Position, String> constantly(ok),
                            Functions.<String> constantly(failed)));
                }
                if (direction.startsWith("s")) {
                    writer.write(digger.move(Position.SOUTH).map(Functions.<Position, String> constantly(ok),
                            Functions.<String> constantly(failed)));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(digger.toString(), e);
        }
    }
    
    public void restoreFromLog(Reader reader) {
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String logRow = null;
            PrintWriter writer = new PrintWriter(new VoidOutputStream());
            while ((logRow = bufferedReader.readLine()) != null) {
                String pathInfo = logRow.substring(logRow.indexOf(" ") + 1);
                executePath(pathInfo, writer);
            }
        } catch (Exception e) {
            throw new Error(e);
        }
    }
    
    /**
     * Will read from the reader until it is closed. If the reader is blocked, this method blocks.
     * @param reader
     * @param delay
     */
    public void restoreFromLogWithDelay(Reader reader, long delay) {
        String logRow = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            PrintWriter writer = new PrintWriter(new VoidOutputStream());
            while ((logRow = bufferedReader.readLine()) != null) {
                long timestamp = Long.parseLong(logRow.substring(0, logRow.indexOf(" ")));
                long timeSinceLog = System.currentTimeMillis() - timestamp;
                if (timeSinceLog < delay) {
                    Thread.sleep(delay - timeSinceLog);
                }
                String pathInfo = logRow.substring(logRow.indexOf(" ") + 1);
                executePath(pathInfo, writer);
            }
        } catch (Exception e) {
            throw new RuntimeException("logRow is:" + logRow, e);
        }
    }
}