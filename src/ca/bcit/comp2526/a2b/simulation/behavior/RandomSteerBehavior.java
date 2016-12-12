package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.simulation.Cell;
import java.util.Random;

/**
 * Random movement behavior.
 * @author jeremy
 * @version 1.0
 */
public class RandomSteerBehavior implements SteeringBehavior {

    private final Random random = new Random();

    /**
     * Gets a random movement.
     * @param location Start location.
     * @param accessible Accessible cells.
     * @param visibleCells Visible cells.
     * @return A random cell.
     */
    @Override
    public Cell getMovement(Cell location, Cell[] accessible, Cell[] visibleCells) {
        if (accessible.length == 0) {
            return null;
        }

        return accessible[random.nextInt(accessible.length)];
    }
}
