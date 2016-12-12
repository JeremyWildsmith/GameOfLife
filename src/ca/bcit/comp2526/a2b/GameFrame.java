package ca.bcit.comp2526.a2b;

import ca.bcit.comp2526.a2b.simulation.Simulation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * GameFrame.
 *
 * @author BCIT
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame {

    private static final int UPDATE_INTERVAL = 100;
    private final Simulation world;
    private final Timer timer;

    /**
     * Constructs an object of type GameFrame.
     *
     * @param world a World
     */
    public GameFrame(final Simulation world) {
        this.world = world;

        timer = new Timer(UPDATE_INTERVAL, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame.this.world.update(UPDATE_INTERVAL);
            }
        });

        timer.start();
    }

    /**
     * Initializes this GameFrame.
     */
    public void init() {
        setTitle("Assignment 2a");

        setLayout(new GridLayout(world.getHeight(), world.getWidth()));

        for (int row = 0; row < world.getHeight(); row++) {
            for (int col = 0; col < world.getWidth(); col++) {
                add(world.getCellAt(col, row));
            }
        }
        addMouseListener(new TurnListener());
    }

    /**
     * Takes a turn and repaints the world.
     */
    public void takeTurn() {
        world.simulate();
        repaint();
    }

    private class TurnListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            takeTurn();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
