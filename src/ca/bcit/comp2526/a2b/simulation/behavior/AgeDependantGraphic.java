package ca.bcit.comp2526.a2b.simulation.behavior;

import ca.bcit.comp2526.a2b.graphic.Graphic;
import ca.bcit.comp2526.a2b.simulation.Organism;
import java.util.HashMap;
import java.util.Map;

/**
 * An graphic that depends on an organisms age.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public final class AgeDependantGraphic implements GraphicBehavior {

    private final Map<Integer, Graphic> transitions = new HashMap<>();

    /**
     * Constructs a new age dependent graphic with the specified transitions.
     * @param transitions A set of transitions to be made as the organism ages,
     */
    public AgeDependantGraphic(GraphicTransition... transitions) {
        if (transitions.length == 0) {
            throw new IllegalArgumentException();
        }

        for (GraphicTransition t : transitions) {
            this.transitions.put(t.maxAge, t.graphic);
        }
    }

    /**
     * Updates the time-sensitive logic og an organism graphic.
     * @param organism The organism.
     * @return The current graphic for the organism.
     */
    @Override
    public Graphic update(Organism organism) {
        Graphic current = organism.getGraphic();
        int lastTransition = Integer.MAX_VALUE;

        for (Map.Entry<Integer, Graphic> e : transitions.entrySet()) {
            if (e.getKey() < lastTransition && organism.getAge() <= e.getKey()) {
                current = e.getValue();
                lastTransition = e.getKey();
            }
        }

        if (current == organism.getGraphic()) {
            return null;
        }

        return current;
    }

    /**
     * Duplicates this graphic.
     * @return A duplicate of this graphic.
     */
    @Override
    public GraphicBehavior duplicate() {
        return this;
    }

    /**
     * @author Jeremy Wildsmith
     * @version 1.0
     */
    public static class GraphicTransition {

        private int maxAge;
        private Graphic graphic;

        /**
         * Constructs a new graphic transition.
         * @param maxAge The age threshold of the transition.
         * @param graphic The graphic.
         */
        public GraphicTransition(int maxAge, Graphic graphic) {
            this.maxAge = maxAge;
            this.graphic = graphic;
        }
    }
}
