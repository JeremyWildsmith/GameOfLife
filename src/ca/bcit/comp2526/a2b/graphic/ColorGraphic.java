package ca.bcit.comp2526.a2b.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;
import java.util.Random;

/**
 * Represents a graphic of just a single colour.
 * @author Jeremy Wildsmith
 * @version 1.o
 */
public final class ColorGraphic implements Graphic {

    private static final int VARIANCE = 3;

    private final Color color;
    private final Color baseColor;

    /**
     * Creates a graphic of random variation of the specified colour.
     * @param color The color of the graphic.
     */
    public ColorGraphic(Color color) {
        Random r = new Random();

        float change = 1.0F - r.nextInt(VARIANCE + 1) * .1F;

        this.baseColor = color;

        this.color = new Color((int) (color.getRed() * change),
                (int) (color.getGreen() * change),
                (int) (color.getBlue() * change));
    }

    /**
     * Draws the color graphic to the specified graphics context.
     * @param g The graphics context on which the graphic is drawn.
     * @param width The destination width.
     * @param height The destination height.
     */
    @Override
    public void draw(Graphics2D g, int width, int height) {
        g.setColor(color);
        g.fillOval(0, 0, width, height);
    }

    /**
     * Gets the hash code for this object.
     * @return The hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.color);
        return hash;
    }

    /**
     * Tests whether this graphic is equal to another object
     * @param obj The object to test equality against.
     * @return Whether the two objects are equal or not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ColorGraphic other = (ColorGraphic) obj;
        return Objects.equals(this.color, other.color);
    }

    /**
     * Duplicates this graphic object.
     * @return A duplicate of this graphic object.
     */
    @Override
    public Graphic duplicate() {
        return new ColorGraphic(baseColor);
    }
}
