package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.simulation.Cell;
import ca.bcit.comp2526.a2b.simulation.TraverseStratedgy;

/**
 * Behavior of an organisms ability to access locations.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public interface AccessibilityBehavior {

    /**
     * Gets a set of accessible cells given the organism is at a location.
     * @param from The location of the organism.
     * @return Set of a accessible cells from that location.
     */
    Cell[] getAccessibleCells(Cell from);

    /**
     * Tests whether the entity can access spaces that required a particular
     * traversal method.
     * @param stratedgy The traversal method neccessary to reach a location.
     * @return Whether the organism can access the location
     */
    boolean canAccess(TraverseStratedgy stratedgy);

    /**
     * An accessiblilit behavior with no accessibility.
     */
    public final class NullAccessibilityBehavior implements AccessibilityBehavior {

        /**
         * Returns a set of zero cells.
         * @param from The location from which to test accessbility.
         * @return A set of zero cells.
         */
        @Override
        public Cell[] getAccessibleCells(Cell from) {
            return new Cell[0];
        }

        /**
         * Returns false.
         * @param stratedgy The stratedgy to test the accessbility against.
         * @return false
         */
        @Override
        public boolean canAccess(TraverseStratedgy stratedgy) {
            return false;
        }

    }
}
