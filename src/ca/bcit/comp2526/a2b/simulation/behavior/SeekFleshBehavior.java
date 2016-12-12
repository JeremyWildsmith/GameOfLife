/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.simulation.Cell;
import ca.bcit.comp2526.a2b.simulation.Flesh;
import ca.bcit.comp2526.a2b.simulation.Organism;
import java.util.Arrays;

/**
 * A seek flesh steering behavior.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public final class SeekFleshBehavior implements SteeringBehavior {

    private final Flesh[] seekFlesh;
    private final boolean isCannibal;

    /**
     * Constructs a new seek flesh steering behavior.
     * @param seekFlesh The flesh to seek.
     * @param isCannibal Whether the organism is a cannibal.
     */
    public SeekFleshBehavior(Flesh[] seekFlesh, boolean isCannibal) {
        this.seekFlesh = seekFlesh;
        this.isCannibal = isCannibal;
    }

    private boolean seeksFlesh(Flesh flesh) {
        return Arrays.asList(seekFlesh).contains(flesh);
    }

    /*
     * Gets the organism on the specified cell.
     * @param cell The cell to get the organisms.
     * @return The organism.
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
     * Gets the movement.
     * @param location The start location.
     * @param accessible All accessible cells.
     * @param visibleCells Visible cells
     * @return The cell to move to.
     */
    @Override
    public Cell getMovement(Cell location, Cell[] accessible, Cell[] visibleCells) {
        RandomSteerBehavior random = new RandomSteerBehavior();

        Cell lastCell = null;
        float lastDist = Float.MAX_VALUE;

        Organism acter = getOrganism(location);

        if (acter == null) {
            return random.getMovement(location, accessible, visibleCells);
        }

        for (Cell c : accessible) {
            for (Cell d : visibleCells) {
                Organism o = getOrganism(d);

                if (o == null || !seeksFlesh(o.getFlesh())
                        || (isCannibal && o.getSpeciesName().compareTo(acter.getSpeciesName()) == 0)) {
                    continue;
                }

                float dist = (float) c.getLocation().distance(d.getLocation());

                if (dist < lastDist) {
                    lastCell = c;
                }
            }
        }

        if (lastCell == null) {
            return random.getMovement(location, accessible, visibleCells);
        }

        return lastCell;
    }
}
