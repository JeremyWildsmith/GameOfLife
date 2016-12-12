package ca.bcit.comp2526.a2b.simulation;

import ca.bcit.comp2526.a2b.graphic.Graphic;

/**
 * Represents a world surface type.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public interface Surface {

    /**
     * Updates time-sensitive logic associated with this surface.
     * @param deltaTime The time since this method was last called.
     * @return Whether the graphic representation of this surface changed.
     */
    boolean update(int deltaTime);

    /**
     * Gets the graphical representation of this surface.
     * @return The graphical representation of this surface.
     */
    Graphic getGraphic();

    /**
     * Gets the method required to traverse this surface.
     * @return The method required to traverse this surface.
     */
    TraverseStratedgy getTraverseStratedgy();
}
