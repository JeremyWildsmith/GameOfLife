package ca.bcit.comp2526.a2b.res;

import java.awt.Image;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * A cached image loader / facotry.
 * @author jeremy
 */
public final class CachedImageFactory {

    private static final HashMap<String, Image> IMAGE_CACHE = new HashMap<>();

    private String imagePath;

    /**
     * Creates the specified image, or loads it from the cache if it has already
     * been loaded.
     * @param src The path of the source image.
     * @return The constructed image.
     */
    public Image create(String src) {
        try {
            //Normalize image path string so that differently formated paths which
            //refer to the same image both hit the right image in the cache
            imagePath = new URI(src).normalize().toString();

            if (IMAGE_CACHE.containsKey(imagePath)) {
                return IMAGE_CACHE.get(imagePath);
            }

            Image img = ImageIO.read(this.getClass().getResource(imagePath));

            IMAGE_CACHE.put(imagePath, img);

            return img;
        } catch (URISyntaxException | IOException e) {
            //Since resources are packaged with jar
            //and paths are specified by the programmer (i.e both are trusted sources)
            //these exceptions can be interpreted as a programmer error
            throw new RuntimeException(e);
        }
    }
}
