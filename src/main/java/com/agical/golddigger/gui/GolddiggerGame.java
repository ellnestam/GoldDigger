package com.agical.golddigger.gui;

import com.agical.golddigger.model.Diggers;
import com.agical.golddigger.model.PlayerReader;
import com.agical.golddigger.model.fieldcreator.FieldCreator;
import com.agical.golddigger.model.fieldcreator.fields.CompetitionFields;
import com.agical.golddigger.server.AdminWebController;
import com.agical.golddigger.server.GolddiggerServer;
import com.agical.golddigger.server.PathExecutor;
import com.agical.golddigger.server.VoidWriter;
import com.agical.jambda.Functions.Fn0;
import com.agical.jambda.Tuples;
import com.agical.jambda.Tuples.Tuple2;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class GolddiggerGame {

    public static void main(String[] args) throws IOException {
        //GolddiggerGame.startGameWithoutPlayback(8066, "target/documentationCalls.log");
        AdminWebController adminWebController = new AdminWebController(8066);
        //adminWebController.add("DaDiggas", "veryVerySecret");
        startGameWithPlayback(2 * 60 * 1000L, 8066, 62986, "target/calls.log", "target/playBack.log");

        addPlayers(adminWebController);
    }

    private static void addPlayers(AdminWebController adminWebController) throws IOException {
        String fileName = "players.txt";
        Map<String, String> players = PlayerReader.read(fileName);
        if (players.size() > 0) {
            Set<String> playersNames = players.keySet();
            for (String playerName : playersNames) {
                String password = players.get(playerName);
                adminWebController.add(playerName, password);
            }
        } else {
            adminWebController.add("SundenInc", "sjdhrtsd");
            adminWebController.add("SegolssonAB", "orlskldr");
            adminWebController.add("InimulAB", "tieksdfj");
            adminWebController.add("FJonsaAS", "msndjfhi");
        }
    }

    //Start a "master" game and a "slave" game 
    public static Tuple2<Tuple2<GolddiggerGui, GolddiggerServer>, Tuple2<GolddiggerGui, GolddiggerServer>> startGameWithPlayback(long delay, int port, int playbackPort, String mainLogFile, String playbackApplicationLogFile, String... restoreFile) throws IOException {
        Tuple2<GolddiggerGui, GolddiggerServer> masterGame = startGameWithoutPlayback(port, mainLogFile, restoreFile);
        Tuple2<GolddiggerGui, GolddiggerServer> slaveGame = startPlayback(delay, playbackPort, mainLogFile, playbackApplicationLogFile);
        return Tuples.duo(masterGame, slaveGame);
    }

    // Start a master game
    public static Tuple2<GolddiggerGui, GolddiggerServer> startGameWithoutPlayback(int port, String mainLogFile, String... restoreFile) {
        return startGameWithoutPlayback(port, mainLogFile, CompetitionFields.factory, restoreFile);
    }

    public static Tuple2<GolddiggerGui, GolddiggerServer> startGameWithoutPlayback(int port, String mainLogFile, Fn0<FieldCreator> fieldCreator, String... restoreFile) {
        Diggers diggers = new Diggers(fieldCreator);
        GolddiggerServer golddiggerServer = new GolddiggerServer(port, "golddigger");
        golddiggerServer.start(diggers, mainLogFile);
        GolddiggerGui golddiggerGui = new GolddiggerGui(diggers, port);
        return Tuples.duo(golddiggerGui, golddiggerServer);
    }

    // Start a slave game
    public static Tuple2<GolddiggerGui, GolddiggerServer> startPlayback(long delay, int playbackPort, String logFileToPlaybackFrom, String playbackApplicationLogFile) throws FileNotFoundException, IOException {
        GolddiggerGame playbackGame = new GolddiggerGame();

        final BufferedReader reader = new BufferedReader(new FileReader(logFileToPlaybackFrom));
        final PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    String read = null;
                    while (true) {
                        read = reader.readLine();
                        if (read == null) {
                            Thread.sleep(300);
                        } else {
                            pipedWriter.write(read + "\n");
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
        thread.start();

        Tuple2<Diggers, GolddiggerServer> playbackDiggers = playbackGame.startGameFromLogWithDelay(playbackPort, delay, pipedReader, playbackApplicationLogFile);
        GolddiggerGui golddiggerGui = new GolddiggerGui(playbackDiggers.getFirst(), playbackPort);

        return Tuples.duo(golddiggerGui, playbackDiggers.getSecond());
    }

    private Tuple2<Diggers, GolddiggerServer> startGameFromLogWithDelay(int playbackPort, final long delay, final Reader reader, String logFile) {
        final Diggers diggers = new Diggers(CompetitionFields.factory);
        GolddiggerServer golddiggerServer = new GolddiggerServer(playbackPort, "golddigger");
        golddiggerServer.start(diggers, logFile);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                PathExecutor pathExecutor = new PathExecutor(diggers, new VoidWriter());
                pathExecutor.restoreFromLogWithDelay(reader, delay);
            }
        });
        thread.start();
        return Tuples.duo(diggers, golddiggerServer);
    }
}
