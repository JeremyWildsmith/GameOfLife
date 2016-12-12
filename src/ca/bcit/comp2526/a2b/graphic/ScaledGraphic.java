package ca.bcit.comp2526.a2b.graphic;

import java.awt.Graphics2D;

/**
 * A graphic that is scaled down.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public class ScaledGraphic implements Graphic {

    private final float scale;
    private final Graphic graphic;

    /**
     * Constructs a new scaled graphic based off of the specified graphic
     * @param scale The scale of the graphic.
     * @param graphic The graphic to be scaled.
     */
    public ScaledGraphic(float scale, Graphic graphic) {
        this.scale = scale;
        this.graphic = graphic;
    }

    /**
     * Draws the graphic at its respective scale onto the graphics context.
     * @param g The graphics context on which the scaled graphic is drawn.
     * @param width The destination width.
     * @param height The destination height.
     */
    @Override
    public void draw(Graphics2D g, int width, int height) {
        int w = (int) (width * scale);
        int h = (int) (height * scale);

        int offX = (width - w) / 2;
        int offY = (height - h) / 2;

        g.translate(offX, offY);
        graphic.draw(g, w, h);
        g.translate(-offX, -offY);
    }

    /**
     * Duplicates this graphic.
     * @return A duplicate of this graphic.
     */
    @Override
    public Graphic duplicate() {
        return new ScaledGraphic(scale, graphic);
    }

}
