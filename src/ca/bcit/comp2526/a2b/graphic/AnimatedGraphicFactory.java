package ca.bcit.comp2526.a2b.graphic;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * A convenience class for constructing animated graphics.
 *
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public final class AnimatedGraphicFactory {

    /**
     * Builds an animation with the respective frame builder and interval.
     * @param interval The interval between each frame.
     * @param frameBuilder The frame builder, responsible for producing frames in
     *        the animation.
     * @return A new animation with the approriate frames and interval.
     */
    public AnimatedGraphic create(int interval, FrameBuilder frameBuilder) {
        List<Graphic> frames = new ArrayList<>();

        Graphic frame;
        for (int i = 0; (frame = frameBuilder.create(i)) != null; i++) {
            frames.add(frame);
        }

        return new AnimatedGraphic(frames.toArray(new Graphic[frames.size()]), interval);
    }

    /**
     * Constructs an animation of several frames where each frame is a slice of
     * a single source sprite sheet.
     * @param src The source image where all frames are rendered on.
     * @param widthPerFrame The width per frame on the sprite sheet.
     * @param interval The interval in time between each frame.
     * @return The animation constructed from the spritesheet.
     */
    public AnimatedGraphic createSliceAnimation(Image src, int widthPerFrame, int interval) {
        final int numFrames = src.getWidth(null) / widthPerFrame;

        //This could be a lambda expression, but for simplicity and compatibility
        //it will be left as an anonymouse class.
        return create(interval, new FrameBuilder() {
            @Override
            public Graphic create(int num) {
                if (num == numFrames) {
                    return null;
                }

                int x = num * widthPerFrame;

                return new ImageGraphic(src, x, 0, widthPerFrame, src.getHeight(null));
            }
        });
    }

    /**
     * Constructs frames for an animation.
     * @author Jeremy Wildsmith
     * @version 1.0
     */
    public interface FrameBuilder {

        /**
         * Creates graphic frames.
         * @param num The frame index for which the graphic is to be 
         *        produced for.
         * @return The graphic for the respective frame index.
         */
        Graphic create(int num);
    }
}
