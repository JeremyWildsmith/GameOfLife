package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.simulation.Cell;
import ca.bcit.comp2526.a2b.simulation.TraverseStratedgy;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * An accessibility behavior based on distance.
 * @author jeremy
 */
public final class RadiusAccessibilityBehaviour implements AccessibilityBehavior {

    private final int stepSize;
    private final TraverseStratedgy[] useableStratedgies;

    /**
     * Constructs a new accessibility behavior.
     * @param stepSize The distance that can be traveled.
     * @param useableStratedgies Useable traversal methods.
     */
    public RadiusAccessibilityBehaviour(int stepSize, TraverseStratedgy[] useableStratedgies) {
        this.stepSize = stepSize;
        this.useableStratedgies = useableStratedgies;
    }

    /**
     * Tests whether the organism can access the specified traversal.
     * @param strat The traveral method.
     * @return Whether the organism can access the specified traversal.
     */
    @Override
    public boolean canAccess(TraverseStratedgy strat) {
        return Arrays.asList(useableStratedgies).contains(strat);
    }

    /**
     * Gets a set of accessible cells
     * @param from The start cel.
     * @return Set of accessible cells.
     */
    @Override
    public Cell[] getAccessibleCells(Cell from) {
        ArrayList<Cell> traversable = new ArrayList<>();

        for (Cell c : from.getAdjacentCells(stepSize)) {
            if (canAccess(c.getTraverseStratedgy())) {
                traversable.add(c);
            }
        }

        return traversable.toArray(new Cell[traversable.size()]);
    }
}
