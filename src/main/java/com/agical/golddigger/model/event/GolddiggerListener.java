package com.agical.golddigger.model.event;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.GoldField;

public interface GolddiggerListener {
    void update(Digger digger);
    void newGame(Digger digger);
    void update(GoldField goldField);
    void newDigger(Digger digger);
}
