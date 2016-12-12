package ca.bcit.comp2526.a2b.graphic;

import java.awt.Graphics2D;

/**
 * An animated graphic which transitions between frames over time.
 *
 * @author Jeremy Wildsmith
 * @version 1.o
 */
public final class AnimatedGraphic implements Graphic {

    private final int updateInterval;
    private final Graphic frames[];

    private int timeSinceUpdate = 0;

    private int curFrame = 0;

    /**
     * Constructs a new instance of an animation.
     *
     * @param frames All frames in the animation
     * @param updateInterval The time interval between each frame.
     * @throws IllegalArgumentException thrown if no frames are provided.
     */
    public AnimatedGraphic(Graphic[] frames, int updateInterval) throws IllegalArgumentException {
        if (frames.length == 0) {
            throw new IllegalArgumentException("Must have at least one frame in animation.");
        }

        this.updateInterval = updateInterval;
        this.frames = frames;
    }

    /**
     * Updates the animation, causing it to switch frames if enough time has
     * elapsed.
     *
     * @param deltaTime The difference in time since the last call to update.
     * @return Whether the call to update called a frame transition.
     */
    public boolean update(int deltaTime) {
        boolean changedFrame = false;

        if(updateInterval == 0)
            return false;
        
        timeSinceUpdate += deltaTime;
        for (; timeSinceUpdate >= updateInterval; timeSinceUpdate -= updateInterval) {
            curFrame = (curFrame + 1) % this.frames.length;
            changedFrame = true;
        }

        return changedFrame;
    }

    /**
     * Gets the number of frames in the animation.
     *
     * @return The number of frames in the animation.
     */
    public int getFrameCount() {
        return frames.length;
    }

    /**
     * Gets the graphic for the respective frame.
     *
     * @param frame The frame index to get the graphic for.
     * @return The graphic for the respective frame index.
     */
    public Graphic getFrame(int frame) {
        return frames[frame];
    }

    /**
     * Sets the current frame in the animation.
     *
     * @param frame Frame index to make the current frame.
     */
    public void setFrame(int frame) {
        curFrame = frame;
    }

    /**
     * Returns the graphic for the current frame.
     *
     * @return The graphic for the current frame,
     */
    public Graphic getCurrentFrame() {
        return frames[curFrame];
    }

    /**
     * Draws the current graphic to the specified graphics object.
     *
     * @param g The graphic object on which the current graphic is rendered.
     * @param width The destination width of the graphic frame.
     * @param height The destination height of the graphic frame.
     */
    @Override
    public void draw(Graphics2D g, int width, int height) {
        frames[curFrame].draw(g, width, height);
    }

    /**
     * Duplicates the graphic object.
     * @return A duplicate of this graphic object.
     */
    @Override
    public Graphic duplicate() {
        return new AnimatedGraphic(frames, updateInterval);
    }
}
