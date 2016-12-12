package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.simulation.Cell;
import ca.bcit.comp2526.a2b.simulation.Organism;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Specifies a reproduction behavior based of adjacent cells.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public class CellBasedReproductionBehavior implements ReproductionBehavior {

    private final Random random = new Random();

    private final int searchCellRange;
    private final int parentsRequired;
    private final int foodRequired;
    private final int spaceRequired;
    private final int maxChildren;
    private final int minChildren;
    private final int minReproduceAge;
    private final int maxReproduceAge;

    /**
     * Constructs a new reproduction behavior.
     * @param searchCellRange The cell range to search for resources and partner
     * @param spaceRequired Space required for reproduction
     * @param parentsRequired The number of parents required
     * @param foodRequired The food required.
     * @param minChildren The minimum number of children produced.
     * @param maxChildren The maximum number of children preoduced.
     * @param minReproduceAge The minimum reproduction age.
     * @param maxReproduceAge  The maximum reproduction age.
     */
    public CellBasedReproductionBehavior(int searchCellRange, int spaceRequired, int parentsRequired, int foodRequired,
            int minChildren, int maxChildren,
            int minReproduceAge,
            int maxReproduceAge) {
        if (maxChildren > spaceRequired || maxChildren <= 0) {
            throw new IllegalArgumentException("There must be at least one space require for each child.");
        }

        this.searchCellRange = searchCellRange;
        this.parentsRequired = parentsRequired;
        this.foodRequired = foodRequired;
        this.spaceRequired = spaceRequired;
        this.minChildren = minChildren;
        this.maxChildren = maxChildren;
        this.minReproduceAge = minReproduceAge;
        this.maxReproduceAge = maxReproduceAge;
    }

    /*
     * Gets the organism at the specified cell.
     * @param cell The cell to get the organism from.
     * @return The organism at the cell.
     */
    private Organism getOrganism(Cell cell) {
        if (cell.isEmpty()) {
            return null;
        }

        if (cell.getEntity() instanceof Organism) {
            return (Organism) (cell.getEntity());
        }

        return null;
    }

    /**
     * Gets cells accessible for reproduction.
     * @param current The current locaiton of the organism.
     * @param accessible Set of accessible cells.
     * @param searchRadius The search radius.
     * @return Set of accessible reproduction cells.
     */
    private Cell[] getAccessibleReproduceCells(Cell current, Cell[] accessible, int searchRadius) {
        ArrayList<Cell> reproduceCells = new ArrayList<>();
        List<Cell> accessibleCells = Arrays.asList(accessible);

        Cell[] search = current.getAdjacentCells(searchRadius);

        for (Cell c : search) {
            if (accessibleCells.contains(c)) {
                reproduceCells.add(c);
            }
        }

        return reproduceCells.toArray(new Cell[reproduceCells.size()]);
    }

    /**
     * Attempts to reproduce.
     * @param current The current location of the organism.
     * @param huntable The huntable food sources.
     * @param accessible The accessible cells.
     * @param reproducer The reproducer responsible for producing children.
     * @return Whether reproduction occured or not.
     */
    @Override
    public boolean reproduce(Cell current, Cell[] huntable, Cell[] accessible, Reproducer reproducer) {
        Queue<Cell> empty = new LinkedList<>();
        int parents = 1;

        Organism birthParent = getOrganism(current);

        if (birthParent == null) {
            return false;
        }

        if (birthParent.getAge() < minReproduceAge || birthParent.getAge() > maxReproduceAge) {
            return false;
        }

        Cell reproduceable[] = getAccessibleReproduceCells(current, accessible, searchCellRange);

        for (Cell c : reproduceable) {
            if (c.isEmpty()) {
                empty.add(c);
            }
        }

        for (Cell c : reproduceable) {
            Organism o = getOrganism(c);

            if (o != null && o.getSpeciesName().compareTo(birthParent.getSpeciesName()) == 0
                    && o.getAge() >= minReproduceAge && birthParent.getAge() <= maxReproduceAge) {
                parents++;
            }
        }

        if (empty.size() < spaceRequired
                || huntable.length < foodRequired
                || parents < parentsRequired) {
            return false;
        }

        int numReproduce = random.nextInt(maxChildren - minChildren + 1) + minChildren;

        for (int i = 0; i < numReproduce; i++) {
            Cell childCell = empty.remove();
            Organism child = reproducer.reproduce(childCell);
            childCell.setEntity(child);
        }

        return true;
    }
}
