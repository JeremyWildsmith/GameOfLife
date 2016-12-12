package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.graphic.Graphic;
import ca.bcit.comp2526.a2b.graphic.Graphic.NullGraphic;
import ca.bcit.comp2526.a2b.simulation.Organism;

/**
 * The visual graphical behavior of an organism.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public interface GraphicBehavior {

    /**
     * Updates time-sensitive logic for the graphical behavior.
     * @param organism The organism
     * @return The new graphical state, or null if no changes needed.
     */
    Graphic update(Organism organism);

    /**
     * Duplicates this graphic behavior.
     * @return A duplicate of this graphic behavior.
     */
    GraphicBehavior duplicate();

    /**
     * An empty graphical behavior.
     * @author Jeremy Wildsmith
     * @version 1.0
     */
    public final class NullGraphicBehavior implements GraphicBehavior {

        /**
         * Updates the time-sensitive logic of this organism
         * @param o The organism
         * @return The new graphical state, or null if no changes are needed.
         */
        @Override
        public Graphic update(Organism o) {
            if (o.getGraphic() instanceof NullGraphic) {
                return null;
            }

            return new NullGraphic();
        }

        /**
         * Duplicates this graphic behavior.
         * @return A duplicate of this graphic behavior.
         */
        @Override
        public GraphicBehavior duplicate() {
            return new NullGraphicBehavior();
        }
    }
}
