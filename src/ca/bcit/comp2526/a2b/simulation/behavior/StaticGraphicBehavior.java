package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.graphic.Graphic;
import ca.bcit.comp2526.a2b.simulation.Organism;

/**
 * A static graphic behavior.
 * @author Jeremy Wildsmith
 */
public class StaticGraphicBehavior implements GraphicBehavior {

    private final Graphic graphic;

    /**
     * Constructs a new static graphic behavior.
     * @param graphic The graphic.
     */
    public StaticGraphicBehavior(Graphic graphic) {
        this.graphic = graphic;
    }

    /**
     * Updates the organisms graphic
     * @param organism The subject organism.
     * @return The new graphic for the organism.
     */
    @Override
    public Graphic update(Organism organism ){
        if (organism.getGraphic() == this.graphic) {
            return null;
        }

        return this.graphic;
    }

    /**
     * Gets the name of the behavior.
     * @return The name of the behavior.
     */
    @Override
    public String toString() {
        return "Static Graphic Behavior";
    }

    /**
     * Creates a duplicate of this behavior.
     * @return A duplicate of this behavior.
     */
    @Override
    public GraphicBehavior duplicate() {
        return new StaticGraphicBehavior(graphic.duplicate());
    }
}
