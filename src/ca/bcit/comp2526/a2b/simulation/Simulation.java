package ca.bcit.comp2526.a2b.simulation;

import javax.swing.JComponent;

/**
 * Represents a world simulation.
 * @author jeremy
 * @version 1.0
 */
public interface Simulation {

    /**
     * Gets the cell at the specified location.
     * @param x The x coordinate of the cell.
     * @param y The y coordinate of the cell.
     * @return The cell at the specified coordinate.
     */
    JComponent getCellAt(int x, int y);

    /**
     * Gets the world simulation height.
     * @return The height of the world simulation.
     */
    int getHeight();

    /**
     * Gets the width of the world simulation.
     * @return The width of the world simulation.
     */
    int getWidth();

    /**
     * Updates the time-sensitive logic of the simulation.
     * @param interval The time since this method was last called.
     */
    void update(int interval);

    /**
     * Simulates one turn for the world simulation.
     */
    void simulate();
}
