package ca.bcit.comp2526.a2b.simulation;

import ca.bcit.comp2526.a2b.graphic.Graphic;
import ca.bcit.comp2526.a2b.graphic.Graphic.NullGraphic;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * A buffered simulation, which simulates ahead of time and stores the results
 * of each step as to avoid delay when transitioning between simulation steps.
 *
 * @author Jeremy Wildsmith
 */
public final class BufferedSimulation implements Simulation {

    private final List<Graphic[][]> frames = new ArrayList<>();
    private final DummyCell[][] currentFrame;
    private final WorldSimulation world;
    private int currentTurn = -1;

    /**
     * Constructs a new buffered simulation of the specified world simulation.
     *
     * @param world
     */
    public BufferedSimulation(WorldSimulation world) {
        this.world = world;

        currentFrame = new DummyCell[world.getWidth()][world.getHeight()];

        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                currentFrame[x][y] = new DummyCell();
            }
        }
    }

    /*
     * Tests whether the graphic contradicts the previous set of frames. 
     * @param startFrame The initial frame in history to test against.
     * @param x The x location of the graphic in the simulation.
     * @param y The y location of the graphic in the simulation.
     * @param graphic The graphic to test the history of.
     * @return 
     */
    private boolean contradictsHistory(int startFrame, int x, int y, Graphic graphic) {
        if (startFrame < 0) {
            return true;
        }

        for (int i = startFrame; i >= 0; i--) {
            Graphic old = frames.get(i)[x][y];

            if (old != null && !old.equals(graphic)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Simulates one step but does not display this simulation step, it is
     * stored into a buffer.
     */
    public void simulateAhead() {
        int lastFrame = frames.size() - 1;

        Graphic frame[][] = new Graphic[world.getHeight()][world.getHeight()];

        frames.add(frame);

        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                Graphic g = world.getCellAt(x, y).getGraphic();
                if (contradictsHistory(lastFrame, x, y, g)) {
                    frame[x][y] = g;
                }
            }
        }

        world.update(500);
        world.simulate();
    }

    /**
     * Gets the cell at the specifed location.
     *
     * @param x The x location of the cell.
     * @param y The y location of the cell.
     * @return The cell at the specified location.
     */
    @Override
    public DummyCell getCellAt(int x, int y) {
        return currentFrame[x][y];
    }

    /**
     * The height of this simulation world.
     *
     * @return Height of this simulation world.
     */
    @Override
    public int getHeight() {
        return world.getHeight();
    }

    /**
     * The width of this simulation world.
     *
     * @return Width of this simulation world.
     */
    @Override
    public int getWidth() {
        return world.getWidth();
    }

    /**
     * Performs no operation as updates are performed as turns are simulated.
     *
     * @param interval The time since this method was last called.
     */
    @Override
    public void update(int interval) {
    }

    /**
     * Gets the number of simulated turns.
     *
     * @return The number of turns simulated.
     */
    public int getSimulatedTurns() {
        return frames.size();
    }

    /**
     * Progresses to the next simulated turn in the buffer.
     */
    @Override
    public void simulate() {
        if (currentTurn == frames.size() - 1) {
            return;
        }

        currentTurn = (currentTurn + 1) % frames.size();

        Graphic[][] nextFrame = frames.get(currentTurn);

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                if (nextFrame[x][y] != null) {
                    currentFrame[x][y].setGraphic(currentTurn, x, y, nextFrame[x][y]);
                }
            }
        }

        currentFrame[0][0].getParent().repaint();
    }

    /**
     * A cell which contains a graphic that is drawn. The graphic is changed
     * each step in the simulation.
     */
    public final class DummyCell extends JPanel {

        private Graphic graphic = new NullGraphic();
        private int frame;
        private int x;
        private int y;

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            graphic.draw((Graphics2D) g, this.getWidth(), this.getHeight());
        }

        /**
         * Sets the graphic for this cell.
         * @param frame The frame index that this graphic new graphic is for.
         * @param x The x location of the graphic.
         * @param y They y locaiton of the graphic.
         * @param graphic The graphic to set this cell to contain.
         */
        public void setGraphic(int frame, int x, int y, Graphic graphic) {
            this.x = x;
            this.y = y;
            this.frame = frame;
            this.graphic = graphic;
        }
    }
}
