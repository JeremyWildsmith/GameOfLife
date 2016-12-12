package ca.bcit.comp2526.a2b.simulation;

import ca.bcit.comp2526.a2b.graphic.Graphic;
import ca.bcit.comp2526.a2b.graphic.ImageGraphic;
import ca.bcit.comp2526.a2b.res.CachedImageFactory;

/**
 * A dirt surface.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public final class Dirt implements Surface {

    private static final String DIRT_SOURCE = "dirt.png";
    private final Graphic graphic;

    /**
     * Constructs a new dirt surface,
     */
    public Dirt() {
        graphic = new ImageGraphic(new CachedImageFactory().create(DIRT_SOURCE));
    }

    /**
     * Updates the surface.
     * @param deltaTime The time since this method was last called.
     * @return Whether the surface graphic changed.
     */
    @Override
    public boolean update(int deltaTime) {
        return false;
    }

    /**
     * Gets the graphic representation of the surface.
     * @return 
     */
    @Override
    public Graphic getGraphic() {
        return graphic;
    }

    /**
     * Gets the traverse stretedgy required to traverse this surface.
     * @return 
     */
    @Override
    public TraverseStratedgy getTraverseStratedgy() {
        return TraverseStratedgy.Walk;
    }
}
