package ca.bcit.comp2526.a2b.simulation;

import ca.bcit.comp2526.a2b.graphic.AnimatedGraphic;
import ca.bcit.comp2526.a2b.graphic.AnimatedGraphicFactory;
import ca.bcit.comp2526.a2b.graphic.Graphic;
import ca.bcit.comp2526.a2b.res.CachedImageFactory;
import java.awt.Image;
import java.util.Random;

/**
 * Represents a water surface.
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public final class Water implements Surface {

    private static final String SOURCE_IMAGE = "water.png";
    private static final int SOURCE_IMAGE_WIDTH = 32;
    private static final int BASE_CHANGE_RATE = 500;
    private static final float VARIANCE = 0.3F;

    private final AnimatedGraphic graphic;

    /**
     * Constructs a new water surface.
     */
    public Water() {
        Random r = new Random();
        int changeRate = (int) (BASE_CHANGE_RATE * r.nextFloat() * (1.0F - VARIANCE));

        Image srcImage = new CachedImageFactory().create(SOURCE_IMAGE);
        graphic = new AnimatedGraphicFactory().createSliceAnimation(srcImage, SOURCE_IMAGE_WIDTH, changeRate);
        graphic.setFrame(r.nextInt(graphic.getFrameCount()));
    }

    /**
     * Gets the graphical representation of this surface.
     * @return The graphical representation of this surface.
     */
    @Override
    public Graphic getGraphic() {
        return graphic.getCurrentFrame();
    }

    /**
     * Updates the animaiton of this surface.
     * @param deltaTime The time since this method was last called.
     * @return Whether the graphic changed or not.
     */
    @Override
    public boolean update(int deltaTime) {
        return graphic.update(deltaTime);
    }

    /**
     * The traversal method required to traverse this surface.
     * @return The traversal method required to traverse this surface.
     */
    @Override
    public TraverseStratedgy getTraverseStratedgy() {
        return TraverseStratedgy.Swim;
    }
}
