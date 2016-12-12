package ca.bcit.comp2526.a2b.simulation;

import ca.bcit.comp2526.a2b.simulation.behavior.GraphicBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.ReproductionBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.HuntingBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.AccessibilityBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.SteeringBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.AccessibilityBehavior.NullAccessibilityBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.GraphicBehavior.NullGraphicBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.HuntingBehavior.NullHuntingBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.ReproductionBehavior.NullReproductionBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.SteeringBehavior.NullSteeringBehavior;

/**
 * Builds new organisms.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public final class OrganismBuilder {

    private SteeringBehavior steeringBehavior;
    private ReproductionBehavior reproductionBehavior;
    private AccessibilityBehavior mobilityBehavior;
    private HuntingBehavior huntingBehavior;
    private GraphicBehavior graphicBehavior;

    private int visibility;
    private int lifeTime;
    private Flesh flesh;

    public OrganismBuilder() {
        reset();
    }

    /**
     * Resets the builder attributes to their default value.
     */
    public void reset() {
        steeringBehavior = new NullSteeringBehavior();
        reproductionBehavior = new NullReproductionBehavior();
        mobilityBehavior = new NullAccessibilityBehavior();
        huntingBehavior = new NullHuntingBehavior();
        graphicBehavior = new NullGraphicBehavior();
        visibility = 1;
        lifeTime = 5;
        flesh = Flesh.None;
    }

    /**
     * Sets the graphical behavior.
     * @param graphicBehavior The graphical behavior to use.
     */
    public void setGraphicBehavior(GraphicBehavior graphicBehavior) {
        this.graphicBehavior = graphicBehavior;
    }

    /**
     * Sets the steering behavior to use.
     * @param steeringBehavior The steering behavior to use.
     */
    public void setSteeringBehavior(SteeringBehavior steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    /**
     * Sets the reproduction behavior to use.
     * @param reproductionBehavior The reproduction behavior to use.
     */
    public void setReproductionBehavior(ReproductionBehavior reproductionBehavior) {
        this.reproductionBehavior = reproductionBehavior;
    }

    /**
     * Sets the accesbility behavior to use.
     * @param mobilityBehavior The accessibility behavior to use.
     */
    public void setAccessibilityBehavior(AccessibilityBehavior mobilityBehavior) {
        this.mobilityBehavior = mobilityBehavior;
    }

    /**
     * Sets the hunting behavior.
     * @param huntingBehavior The hunting behavior to use.
     */
    public void setHuntingBehavior(HuntingBehavior huntingBehavior) {
        this.huntingBehavior = huntingBehavior;
    }

    /**
     * Sets the organisms visibility
     * @param visibility The visibility in tiles of the organisms
     */
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    /**
     * Sets the life time of the organisms
     * @param lifeTime The lifetime of the organisms.
     */
    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    /**
     * Sets the flesh type of this organism.
     * @param flesh The flesh type of this organism.
     */
    public void setFlesh(Flesh flesh) {
        this.flesh = flesh;
    }

    /**
     * Builds the new organism.
     * @param speciesName The species name of the new organism.
     * @param location The ,ocation of the new organism.
     * @return The new organism.
     */
    public Organism build(String speciesName, Cell location) {
        return new Organism(speciesName, location, flesh, lifeTime, visibility,
                mobilityBehavior, steeringBehavior,
                huntingBehavior, reproductionBehavior,
                graphicBehavior);
    }
}