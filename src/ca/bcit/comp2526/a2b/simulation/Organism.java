package ca.bcit.comp2526.a2b.simulation;

import ca.bcit.comp2526.a2b.simulation.behavior.AccessibilityBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.GraphicBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.HuntingBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.ReproductionBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.SteeringBehavior;
import ca.bcit.comp2526.a2b.graphic.Graphic;
import ca.bcit.comp2526.a2b.graphic.Graphic.NullGraphic;

/**
 * Represents an organism in the game world.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public final class Organism extends Entity {

    private final HuntingBehavior huntingBehavior;
    private final AccessibilityBehavior movementBehavior;
    private final SteeringBehavior steeringBehavior;
    private final ReproductionBehavior reproductionBehavior;
    private final GraphicBehavior graphicBehavior;

    private final int visibility;
    private final int lifeDuration;
    private final String speciesName;
    private Graphic graphic = new NullGraphic();

    private int life;
    private int age;

    /**
     * Constructs a new organism with the specified behaviors.
     * @param speciesName The name of the species organism
     * @param location The location where the organism is placed.
     * @param flesh The flesh type of the organism.
     * @param lifeDuration The life duration of the organism.
     * @param visibility The visibility of the organism.
     * @param movementBehavior The movement behavior of the organism.
     * @param steeringBehavior The steering behavior of the organism.
     * @param huntingBehavior The hunting behavior of the organism.
     * @param reproduceBehavior The reproduction behavior of the organism.
     * @param graphicBehavior The graphic behavior of the organism.
     */
    public Organism(String speciesName,
            Cell location, Flesh flesh, int lifeDuration, int visibility,
            AccessibilityBehavior movementBehavior,
            SteeringBehavior steeringBehavior,
            HuntingBehavior huntingBehavior,
            ReproductionBehavior reproduceBehavior,
            GraphicBehavior graphicBehavior) {

        super(location, flesh);
        this.speciesName = speciesName;
        this.lifeDuration = lifeDuration;
        this.life = lifeDuration;
        this.graphicBehavior = graphicBehavior;
        this.huntingBehavior = huntingBehavior;
        this.steeringBehavior = steeringBehavior;
        this.movementBehavior = movementBehavior;
        this.reproductionBehavior = reproduceBehavior;
        this.visibility = visibility;
        updateGraphic();
    }

    /**
     * Gets the organisms' species name.
     * @return The organisms species name.
     */
    public String getSpeciesName() {
        return speciesName;
    }

    /**
     * Moves the organisms.
     * @return whether the organism was moved or not.
     */
    private boolean move() {
        if (life == 0) {
            this.getLocation().clearEntity();
            return true;
        }
        life--;

        Cell[] visible = this.getLocation().getAdjacentCells(visibility);
        Cell[] accessible = movementBehavior.getAccessibleCells(this.getLocation());
        Cell[] huntable = huntingBehavior.getHuntable(this.getSpeciesName(), accessible);

        if (reproductionBehavior.reproduce(this.getLocation(), huntable, accessible, new OrganismReproducer())) {
            return true;
        }

        Cell hunt = huntingBehavior.hunt(speciesName, accessible);
        Cell steer = steeringBehavior.getMovement(this.getLocation(), accessible, visible);

        Cell moveTo = hunt == null ? steer : hunt;

        if (moveTo == null) {
            return false;
        }

        if (!moveTo.isEmpty() && hunt == null) {
            return false;
        }

        if (!moveTo.isEmpty()) {
            life = lifeDuration;
        }

        setCell(moveTo);
        return true;
    }

    /*
     * Updates the graphic according to the graphic behavior.
     */
    private void updateGraphic() {
        Graphic newGraphic = graphicBehavior.update(this);
        if (newGraphic != null) {
            this.graphic = newGraphic;
        }
    }

    /**
     * Gets the age of the organism.
     * @return The age of the organism.
     */
    public int getAge() {
        return age;
    }

    /**
     * Conducts a single turn for the organism.
     * @return Whether the turn was used or not (i.e, the organism did anything.)
     */
    @Override
    public boolean doTurnLogic() {
        boolean used = move();
        age++;

        updateGraphic();

        return used;
    }

    /**
     * Gets the graphical reprsentation of the organism.
     * @return The graphical representation of the organism.
     */
    @Override
    public Graphic getGraphic() {
        return graphic;
    }

    /**
     * Updates time sensitive logic for the organism.
     * @param delta The time since this method was last called.
     */
    @Override
    public void update(int delta) {
    }

    /**
     * Tests whether the organism can survive on its current location.
     * @return Whether the organism can survive on its current location.
     */
    public boolean canSurvive() {
        return this.movementBehavior.canAccess(this.getLocation().getTraverseStratedgy());
    }

    /*
     * The organisms reproduction reproducer.
     * @author Jeremy Wildsmith
     * @version 1.0
    */
    private class OrganismReproducer implements ReproductionBehavior.Reproducer {

        /**
         * Reproduces a copy of this organism.
         * @param location
         * @return 
         */
        @Override
        public Organism reproduce(Cell location) {
            return new Organism(speciesName,
                    location,
                    Organism.this.getFlesh(),
                    lifeDuration,
                    visibility,
                    movementBehavior,
                    steeringBehavior,
                    huntingBehavior,
                    reproductionBehavior,
                    graphicBehavior.duplicate());
        }
    }
}
