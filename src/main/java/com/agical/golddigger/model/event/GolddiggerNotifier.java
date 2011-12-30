package com.agical.golddigger.model.event;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.GoldField;


public class GolddiggerNotifier {
    private List<GolddiggerListener> listeners = new ArrayList<GolddiggerListener>();

    public synchronized void addListener(GolddiggerListener golddiggerListener) {
        listeners.add(golddiggerListener);
    }

    public synchronized boolean removeListener(GolddiggerListener golddiggerListener) {
        return listeners.remove(golddiggerListener);
    }

    public synchronized void updateListeners(final Digger digger) {
        for (final GolddiggerListener listener : listeners) {
            SwingUtilities.invokeLater(new Runnable(){
                public void run() {
                    listener.update(digger);
                }
            });
        }
    }

    public synchronized void newGame(final Digger digger, GoldField goldField) {
        for (final GolddiggerListener listener : listeners) {
            SwingUtilities.invokeLater(new Runnable(){
                public void run() {
                    listener.newGame(digger);
                }
            });
        }
    }

    public synchronized void newDigger(final Digger digger) {
        for (final GolddiggerListener listener : listeners) {
            SwingUtilities.invokeLater(new Runnable(){
                public void run() {
                    listener.newDigger(digger);
                }
            });
        }
    }

}
