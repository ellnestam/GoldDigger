package com.agical.golddigger.model;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class PlayerReaderTest {

    @Test
    public void readPlayers() throws IOException {
        String fileName = "players.txt";
        Map<String, String> players = PlayerReader.read(fileName);

        String player = "foo";
        String password = "bar";
        assertTrue(players.containsKey(player));
        String actualPassword = players.get(player);
        assertThat(actualPassword, is(password));

        String player2 = "apa";
        String password2 = "bapa";
        assertTrue(players.containsKey(player2));
        actualPassword = players.get(player2);
        assertThat(actualPassword, is(password2));
    }
}
