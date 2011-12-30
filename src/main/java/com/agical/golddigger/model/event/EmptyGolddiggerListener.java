/**
 * 
 */
package com.agical.golddigger.model.event;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.GoldField;

public class EmptyGolddiggerListener implements GolddiggerListener {
    public void newDigger(Digger digger) {}
    public void newGame(Digger digger) {}
    public void update(Digger digger) {}
    public void update(GoldField goldField) {}
}