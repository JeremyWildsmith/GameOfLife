package ca.bcit.comp2526.a2b.graphic;

import java.awt.Graphics2D;

/**
 * Represents a visual graphic.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public interface Graphic {

    /**
     * Draws this graphic on to a specified graphics context.
     * @param g The graphics context on which this graphic is drawn.
     * @param width The destination width.
     * @param height The destination height.
     */
    void draw(Graphics2D g, int width, int height);

    /**
     * Duplicates this graphic object.
     * @return A duplicate of this graphic object.
     */
    Graphic duplicate();

    /**
     * A null graphic, or graphic with no contents.
     * @author Jeremy Wildsmith
     * @version 1.0
     */
    public final class NullGraphic implements Graphic {

        /**
         * Performs no operation.
         * @param g The graphics object to be drawn on.
         * @param width The destination width.
         * @param height The destination height.
         */
        @Override
        public void draw(Graphics2D g, int width, int height) {
        }

        /**
         * Duplicates this object.
         * @return A duplicate of this object.
         */
        @Override
        public Graphic duplicate() {
            return new NullGraphic();
        }
    }
}
