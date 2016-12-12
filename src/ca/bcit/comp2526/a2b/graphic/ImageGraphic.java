package ca.bcit.comp2526.a2b.graphic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Objects;

/**
 * A visual graphic that is based off of a source image.
 *
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public final class ImageGraphic implements Graphic {

    private final int srcX;
    private final int srcY;
    private final int srcWidth;
    private final int srcHeight;

    private final Image srcImage;

    /**
     * Constructs a new image graphic based off of the specified source
     * parameters.
     *
     * @param src The source image.
     * @param srcX The source x coordinate of source image.
     * @param srcY The source y coordinate of source image.
     * @param srcWidth The source width from the source image.
     * @param srcHeight The source height from the source image.
     */
    public ImageGraphic(Image src, int srcX, int srcY, int srcWidth, int srcHeight) {
        srcImage = src;
        this.srcX = srcX;
        this.srcY = srcY;
        this.srcWidth = srcWidth;
        this.srcHeight = srcHeight;
    }

    /**
     * Constructs a new image graphic
     *
     * @param src The source image for this graphic.
     */
    public ImageGraphic(Image src) {
        this(src, 0, 0, src.getWidth(null), src.getHeight(null));
    }

    /**
     * Draws this graphic on to the specified graphics context.
     *
     * @param g The graphics context on which this image is drawn.
     * @param width The destination width.
     * @param height The destination height.
     */
    @Override
    public void draw(Graphics2D g, int width, int height) {
        g.drawImage(srcImage, 0, 0, width, height, srcX, srcY, srcX + srcWidth, srcY + srcHeight, null);
    }

    /**
     * The hash code for this object.
     *
     * @return The hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.srcX;
        hash = 97 * hash + this.srcY;
        hash = 97 * hash + this.srcWidth;
        hash = 97 * hash + this.srcHeight;
        hash = 97 * hash + Objects.hashCode(this.srcImage);
        return hash;
    }

    /**
     * Test for equality against another object.
     *
     * @param obj An object to test equality agianst.
     * @return Whether the two objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImageGraphic other = (ImageGraphic) obj;
        if (this.srcX != other.srcX) {
            return false;
        }
        if (this.srcY != other.srcY) {
            return false;
        }
        if (this.srcWidth != other.srcWidth) {
            return false;
        }
        if (this.srcHeight != other.srcHeight) {
            return false;
        }
        if (!Objects.equals(this.srcImage, other.srcImage)) {
            return false;
        }
        return true;
    }

    /**
     * Creates a duplicate of this graphic object.
     * @return A duplicate of this graphic objectsaasd.
     */
    @Override
    public Graphic duplicate() {
        return new ImageGraphic(srcImage, srcX, srcY, srcWidth, srcHeight);
    }

}
