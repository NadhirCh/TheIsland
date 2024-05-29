package org.example.GUI.ui;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * A utility class for dealing with images used in the GUI.
 */
public class ImageDealingWith {
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "background_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String OPTIONS_MENU = "options_background.png";

    /**
     * Loads a sprite atlas image from the specified file name.
     *
     * @param fileName The name of the image file to load.
     * @return The loaded BufferedImage, or null if the loading fails.
     */
    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = ImageDealingWith.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}
