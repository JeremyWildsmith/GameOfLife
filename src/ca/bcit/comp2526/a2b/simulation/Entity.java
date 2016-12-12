package ca.bcit.comp2526.a2b.simulation;

import ca.bcit.comp2526.a2b.graphic.Graphic;

/**
 * Represents an entity in the simulation world.
 *
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public abstract class Entity {

    private final Flesh type;
    private Cell location;

    /**
     * Constructs a new entity at the specified location.
     *
     * @param location The location where the entity is placed.
     * @param flesh The flesh of the entity,
     */
    public Entity(Cell location, Flesh flesh) {
        type = flesh;
        this.location = location;
    }

    /**
     * Gets the cell location of this entity.
     *
     * @return The cell location of this entity.
     */
    public final Cell getLocation() {
        return location;
    }

    public final Flesh getFlesh() {
        return type;
    }

    public final void setCell(Cell moveTo) {
        if (moveTo != null) {
            moveTo.setEntity(this);
        }

        if (location != null) {
            location.clearEntity();
        }

        location = moveTo;
    }

    public final boolean takeTurn() {
        boolean usedTurn = doTurnLogic();

        if (usedTurn && location != null) {
            location.repaint();
        }

        return usedTurn;
    }

    public abstract void update(int deltaTime);

    protected abstract boolean doTurnLogic();

    protected abstract Graphic getGraphic();
}
