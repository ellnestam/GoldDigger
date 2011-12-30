package com.agical.golddigger.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.agical.golddigger.model.Digger;
import com.agical.golddigger.model.Diggers;
import com.agical.golddigger.model.GoldField;
import com.agical.golddigger.model.event.EmptyGolddiggerListener;
import com.agical.golddigger.model.event.GolddiggerListener;
import com.agical.golddigger.model.event.GolddiggerNotifier;

public class GolddiggerGui {
    
    private Map<Digger, JComponent> diggersPanels = new HashMap<Digger, JComponent>();
    private Map<Digger, JLabel> diggersScore = new HashMap<Digger, JLabel>();
    private GolddiggerNotifier golddiggerNotifier;
    private JFrame frame;
    private final Diggers diggers;
    
    public GolddiggerGui(final Diggers diggers, final int port) {
        this.diggers = diggers;
        this.golddiggerNotifier = diggers.getGolddiggerNotifier();
        
        frame = new JFrame();
        final JPanel panel = new JPanel(new GridLayout(2, 2));
        frame.getContentPane().add(panel);
        
        GolddiggerListener golddiggerListener = new EmptyGolddiggerListener() {
            public void newDigger(Digger digger) {
                initDigger(digger);
            }
            public void newGame(Digger digger) {
                JComponent oldPanel = diggersPanels.get(digger);
                int index = 0;
                if (oldPanel != null) {
                    Component[] components = panel.getComponents();
                    for (int i = 0; i < components.length; i++) {
                        if (oldPanel == components[i]) {
                            index = i;
                            break;
                        }
                    }
                    panel.remove(oldPanel);
                }
                
                FieldView board = new FieldView(digger);
                JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
                splitPane.setDividerSize(0);
                splitPane.setDividerLocation(25);
                
                splitPane.setLeftComponent(diggersScore.get(digger));
                splitPane.setRightComponent(board);
                panel.add(splitPane, index);
                diggersPanels.put(digger, splitPane);
                frame.validate();
            }
        };
        
        frame.setSize(new Dimension(600, 400));
        frame.setVisible(true);
        
        golddiggerNotifier.addListener(golddiggerListener);
    }
    
    private void initDigger(Digger digger) {
        final JLabel score = new JLabel(digger.getName() + ": 0 [0]");
        diggersScore.put(digger, score);
        GolddiggerListener golddiggerListener = new EmptyGolddiggerListener() {
            public void update(Digger digger2) {
                diggersScore.get(digger2).setText(
                        digger2.getName() + ": " + digger2.getGoldInTheBank() + " [" + digger2.getCarriedGold() + "]");
                diggersPanels.get(digger2).repaint();
                frame.validate();
            }
        };
        golddiggerNotifier.addListener(golddiggerListener);
    }
    
    public JFrame getFrame() {
        return frame;
    }
    public Diggers getDiggers() {
        return diggers;
    }
    public JLabel getScoreFor(String string) {
        for (Entry<Digger, JLabel> diggerToLabel : diggersScore.entrySet()) {
            if (diggerToLabel.getKey().getName().equals(string)) { return diggerToLabel.getValue(); }
        }
        return null;
    }
    
}
