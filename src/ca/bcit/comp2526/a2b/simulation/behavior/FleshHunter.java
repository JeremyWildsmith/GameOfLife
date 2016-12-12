package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.simulation.Cell;
import ca.bcit.comp2526.a2b.simulation.Flesh;
import ca.bcit.comp2526.a2b.simulation.Organism;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A flesh hunting behavior.
 * @author jeremy
 */
public final class FleshHunter implements HuntingBehavior {

    private final Random random = new Random();

    private final Flesh[] hunt;
    private boolean isCannibal;

    /**
     * Constructs a new flesh hunting behavior.
     * @param hunt The hunted flesh.
     * @param isCannibal If the organism is a cannibal.
     */
    public FleshHunter(Flesh[] hunt, boolean isCannibal) {
        this.hunt = hunt;
    }

    /*
     * Tests whether the flesh can be consumed
     * @param flesh The flesh to test consumability of.
     * @return Whether the flesh can be consumed.
     */
    private boolean canConsume(Flesh flesh) {
        return Arrays.asList(hunt).contains(flesh);
    }

    /**
     * Gets list of huntable cells.
     * @param hunterSpecies The hunter species name.
     * @param accessible Accessible cells
     * @return Set of cells that can be hunted.
     */
    @Override
    public Cell[] getHuntable(String hunterSpecies, Cell[] accessible) {
        ArrayList<Cell> huntable = new ArrayList<>();

        for (Cell c : accessible) {
            if (c.getEntity() != null && canConsume(c.getEntity().getFlesh())
                    && c.getEntity() instanceof Organism) {

                if (isCannibal || ((Organism) c.getEntity()).getSpeciesName().compareTo(hunterSpecies) != 0) {
                    huntable.add(c);
                }
            }
        }

        return huntable.toArray(new Cell[huntable.size()]);
    }

    /**
     * Chooses a cell to hunt from a set of huntable cells.
     * @param hunterSpecies The hunter species.
     * @param accessible The huntable cells.
     * @return The chosen cell to hunt from.
     */
    @Override
    public Cell hunt(String hunterSpecies, Cell[] accessible) {
        Cell[] huntable = getHuntable(hunterSpecies, accessible);

        if (huntable.length > 0) {
            return huntable[random.nextInt(huntable.length)];
        }

        return null;
    }
}
