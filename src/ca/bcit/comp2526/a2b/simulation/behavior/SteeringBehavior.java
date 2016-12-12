package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.simulation.Cell;

/**
 * A steering behavior for the logic of how an organism moves.
 *
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public interface SteeringBehavior {

    /**
     * Choses a cell to move to.
     * @param location The current location of the orgaism.
     * @param accessible Accessible cells.
     * @param visibleCells Visible cells.
     * @return The cell to move to.
     */
    Cell getMovement(Cell location, Cell[] accessible, Cell[] visibleCells);

    /**
     * Empty steering behavior.
     *
     * @author Jeremy Wildsmith
     * @version 1.0
     */
    public final class NullSteeringBehavior implements SteeringBehavior {

        /**
         * Choses a cell to move to.
         * @param location The current location of the orgaism.
         * @param accessible Accessible cells.
         * @param visibleCells Visible cells.
         * @return The cell to move to.
         */
        @Override
        public Cell getMovement(Cell location, Cell[] accessible, Cell[] visibleCells) {
            return null;
        }
    }
}
