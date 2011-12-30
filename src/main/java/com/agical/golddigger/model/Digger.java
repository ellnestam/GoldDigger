package com.agical.golddigger.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.agical.golddigger.model.event.GolddiggerNotifier;
import com.agical.jambda.Functions;
import com.agical.jambda.Option;
import com.agical.jambda.Unit;
import com.agical.jambda.Functions.Fn1;
import com.agical.jambda.Functions.Fn2;


public class Digger {
    private Position position = new Position(1, 1);
    private int carrying;
    private int cashed;
    private GoldField goldField;
    private Option<GolddiggerNotifier> golddiggerNotifier = Option.none();
    private final String name;
    private final String secretName;
    private Writer log;
    private Fn2<Digger, GolddiggerNotifier, Unit> updateListeners = new Fn2<Digger,GolddiggerNotifier, Unit>() {
        public Unit apply(Digger digger, GolddiggerNotifier notifier) {
            notifier.updateListeners(digger);
            return Unit.unit;
        }
    };
    private static final int CARRYING_LIMITATION = 3;
    
    public Digger(String name, String secretName) {
        super();
        this.name = name;
        this.secretName = secretName;
        try {
            File file = new File("target/" + name + ".log");
            file.getParentFile().mkdirs();
            this.log = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException("Could not create log for " + name, e);
        }
    }

    @Override
    public String toString() {
        return "Digger[position=" + getPosition() + "][carrying=" + carrying  + "][cashed=" + cashed  + "][name=" + name +"]\n" +
        "Field:\n" + goldField;
    }
    public String getName() {
        return name;
    }
    
    public String getSecretName() {
        return secretName;
    }
    
    public void setGolddiggerNotifier(GolddiggerNotifier golddiggerNotifier) {
        this.golddiggerNotifier = Option.some(golddiggerNotifier);
    }
    
    public synchronized void setPosition(Position newPosition) {
        int longitude = newPosition.getLongitude();
        int latitude = newPosition.getLatitude();
        if(   latitude>=1&&
                longitude>=1&&
                latitude<=goldField.getMaxLatitude()&&
                longitude<=goldField.getMaxLongitude()&&
                goldField.isTreadable(newPosition)) {
            this.position = new Position(newPosition.getLatitude(), newPosition.getLongitude());
        } else {
            throw new RuntimeException("Bad new position:" + newPosition);
        }
    }
    
    private void update() {
        golddiggerNotifier.map(updateListeners.apply(this), Functions.<Unit>constantly(Unit.unit));
    }

    public Position getPosition() {
        return position;
    }

    public int getCarriedGold() {
        return carrying;
    }

    /**
     * 
     * @param pieces
     * @return The number of the pieces the digger couldn't carry
     */
    public int addPendingGold(int pieces) {
        int remainingCapacity = CARRYING_LIMITATION-carrying;
        if(pieces>remainingCapacity) {
            carrying = CARRYING_LIMITATION;
            update();
            return pieces-remainingCapacity;
        } else {
            carrying +=pieces;
            update();
            return 0;
        }
    }

    public int getGoldInTheBank() {
        return cashed;
    }

    public void cashGold() {
        cashed += carrying;
        updateScore();
        carrying = 0;
        update();
    }

    private void updateScore() {
        try {
            log.append(cashed + "\n");
            log.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newGame(GoldField goldField) {
        this.goldField = goldField;
        setPosition(new Position(1,1));
    }
    
    public synchronized Option<Position> move(Fn1<Position,Position> move) {
        Position newPosition = move.apply(getPosition());
        if(goldField.isTreadable(newPosition)) {
            setPosition(newPosition);
            update();
            return Option.some(getPosition());
        } else {
            return Option.<Position>none();
        }
    }

    public String getView() {
        return goldField.getDiggerView(this);
    }

    public GoldField getGoldField() {
        return goldField;
    }


    public void grab() {
        Square square = Position.square.apply(getPosition(), goldField);
        square.grabBy(this);
        square.viewed();
    }

    public void drop() {
        Position.square.apply(getPosition(), goldField).dropBy(this);
    }
    

}
