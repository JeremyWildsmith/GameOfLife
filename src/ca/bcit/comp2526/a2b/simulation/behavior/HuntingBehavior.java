package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.simulation.Cell;

/**
 * Hunting behavior of an organism.
 * @author jeremy
 * @version 1.0
 */
public interface HuntingBehavior {

    /**
     * Hunts a particular cell given a list of options.
     * @param hunterSpecies The species of the hunter.
     * @param accessible Accessible cells.
     * @return The cell to hunt, or null if no valid options.
     */
    Cell hunt(String hunterSpecies, Cell[] accessible);

    /**
     * Gets a set of huntable cells.
     * @param hunterSpecies The hunter species.
     * @param accessible A set of cells accessible to the organism.
     * @return A set a valid cells to hunt from.
     */
    Cell[] getHuntable(String hunterSpecies, Cell[] accessible);

    /**
     * An empty hunting behavior.
     * @author Jeremy Wildsmith
     * @version 1.0
     */
    public final class NullHuntingBehavior implements HuntingBehavior {

        /**
         * Returns null.
         * @param hunterSpecies The the hunter species.
         * @param accessible Accessible cells.
         * @return null
         */
        @Override
        public Cell hunt(String hunterSpecies, Cell[] accessible) {
            return null;
        }

        /**
         * An empty array.
         * @param hunterSpecies The hunter species.
         * @param accessible Accessible cells.
         * @return An empty array.
         */
        @Override
        public Cell[] getHuntable(String hunterSpecies, Cell[] accessible) {
            return new Cell[0];
        }
    }
}
