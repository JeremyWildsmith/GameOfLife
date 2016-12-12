package ca.bcit.comp2526.a2b.simulation;

import javax.swing.JComponent;

/**
 * A simulation which takes turns / simulates steps as time progresses.
 *
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public class AutoplayedSimulation implements Simulation {

    private final int simulateInterval;
    private final Simulation simulation;

    private int timeSinceSimulate = 0;

    /**
     * Constructs a new auto-played simulation.
     *
     * @param simulation The simulation to be auto-played.
     * @param simulateInterval The interval in milliseconds between steps.
     */
    public AutoplayedSimulation(Simulation simulation, int simulateInterval) {
        this.simulateInterval = simulateInterval;
        this.simulation = simulation;
    }

    /**
     * Gets a cell at the specified location in the simulation.
     *
     * @param x The x coordinate of the cell.
     * @param y The y coordinate of the cell.
     * @return The cell at the respective coordinates.
     */
    @Override
    public JComponent getCellAt(int x, int y) {
        return simulation.getCellAt(x, y);
    }

    /**
     * Gets the height of the simulation world.
     *
     * @return the height of the simulation world.
     */
    @Override
    public int getHeight() {
        return simulation.getHeight();
    }

    /**
     * Gets the width of the simulation world.
     *
     * @return The width of the simulation world.
     */
    @Override
    public int getWidth() {
        return simulation.getWidth();
    }

    /**
     * Updates the simulation and, if enough time has elapsed, progresses on to
     * the next step.
     *
     * @param interval The time that has passed since the last call to update.
     */
    @Override
    public void update(int interval) {
        simulation.update(interval);

        timeSinceSimulate += interval;
        if (timeSinceSimulate >= simulateInterval) {
            timeSinceSimulate = 0;
            simulation.simulate();
        }
    }

    /**
     * Performs no action since simulation is based upon time.
     */
    @Override
    public void simulate() {
    }
}
