package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.simulation.Cell;
import ca.bcit.comp2526.a2b.simulation.Organism;

/**
 * Reproduction behavior.
 *
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public interface ReproductionBehavior {

    /**
     * Attempts to reproduce.
     *
     * @param current The current location the organism.
     * @param huntable Huntable cells for the organism.
     * @param accessible Accessible cells.
     * @param r Reproduces organism.
     * @return Whether the reproduction was successful.
     */
    boolean reproduce(Cell current, Cell[] huntable, Cell[] accessible, Reproducer r);

    /**
     * Reproduces the organism onto the specified cell.
     */
    public interface Reproducer {

        /**
         * Reproduces an organism.
         *
         * @param location The locaiton where the organism is placed.
         * @return The new organism.
         */
        Organism reproduce(Cell location);
    }

    /**
     * An empty reproduce behavior.
     *
     * @author Jeremy Wildsmith
     * @version 1.0
     */
    public final class NullReproductionBehavior implements ReproductionBehavior {

        /**
         * Attempts to reproduce.
         *
         * @param current The current location the organism.
         * @param huntable Huntable cells for the organism.
         * @param accessible Accessible cells.
         * @param r Reproduces organism.
         * @return Whether the reproduction was successful.
         */
        @Override
        public boolean reproduce(Cell current, Cell[] huntable, Cell[] accessible, Reproducer r) {
            return false;
        }
    }
}
