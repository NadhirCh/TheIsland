package org.example.GUI.ui;


import java.nio.Buffer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.example.GUI.gamestates.GameState;
import java.awt.Rectangle;

import org.example.GUI.gamestates.GameState;
import static  org.example.GUI.ui.Buttons.UI.Button.* ;

/**
 * Represents a button used for controlling volume settings in the GUI.
 * Extends the PauseButton class.
 */
public class VolumeButton extends PauseButton {

        /**
         * The default width of the volume button.
         */
        public static final int VOLUME_DEFAULT_WIDTH = 28;
    
        /**
         * The default height of the volume button.
         */
        public static final int VOLUME_DEFAULT_HEIGHT = 44;
    
        /**
         * The default width of the slider.
         */
        public static final int SLIDER_DEFAULT_WIDTH = 215;
    
        /**
         * The scaled width of the volume button.
         */
        public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * 1.5f);
    
        /**
         * The scaled height of the volume button.
         */
        public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * 1.5f);
    
        /**
         * The scaled width of the slider.
         */
        public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * 1.5f);

    
        private boolean mouseOver, mousePressed; // Tracks mouse interaction with the button
        private int buttonX, minX, maxX; // Positions and boundaries of the button
        private float floatValue = 0f; // Value of the volume represented as a float between 0 and 1
        private int xPos, yPos, rowIndex, index; // Position and index variables for rendering
        private int xOffsetCenter = B_WIDTH / 2; // Offset for button positioning
        private BufferedImage[] imgs; // Images for different states of the volume button
        private Rectangle bounds = new Rectangle(); // Bounds of the volume button
        private BufferedImage slider; // Image for the slider
    
        /**
         * Constructs a VolumeButton object with specified coordinates and dimensions.
         *
         * @param xPos   The x-coordinate of the button.
         * @param yPos   The y-coordinate of the button.
         * @param width  The width of the button.
         * @param height The height of the button.
         */

        public VolumeButton(int xPos, int yPos, int width, int height) {
            super(xPos + width / 2, yPos, VOLUME_WIDTH, height); // Calls constructor of the superclass
            this.height = height;
            bounds.x -= VOLUME_WIDTH / 2;
            buttonX = xPos + width / 2;
            this.xPos = xPos;
            this.yPos = yPos;
            this.width = width;
            minX = xPos + VOLUME_WIDTH / 2;
            maxX = xPos + width - VOLUME_WIDTH / 2;
            loadImgs(); // Loads images for the volume button
        }
    
        /**
         * Loads images for the volume button and slider from a sprite atlas.
         */
        private void loadImgs() {
            BufferedImage temp = ImageDealingWith.GetSpriteAtlas(ImageDealingWith.VOLUME_BUTTONS);
            imgs = new BufferedImage[3];
            for (int i = 0; i < imgs.length; i++)
                imgs[i] = temp.getSubimage(i * VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
    
            slider = temp.getSubimage(3 * VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
        }
    
        /**
         * Updates the state of the volume button based on user interaction.
         */
        public void update() {
            index = 0;
            if (mouseOver)
                index = 1;
            if (mousePressed)
                index = 2;
        }
    
        /**
         * Draws the volume button and slider.
         *
         * @param g The Graphics object for rendering.
         */
        public void draw(Graphics g) {


                g.drawImage(slider, xPos, yPos, width, height, null);
                g.drawImage(imgs[index], buttonX - VOLUME_WIDTH / 2, yPos, VOLUME_WIDTH, height, null);


        }
    
        /**
         * Changes the x-coordinate of the button based on user interaction.
         *
         * @param x The new x-coordinate.
         */
        public void changeX(int x) {

                if (x < minX)
                        buttonX = minX;
                else if (x > maxX)
                        buttonX = maxX;
                else
                        buttonX = x;
                updateFloatValue();
                bounds.x = buttonX - VOLUME_WIDTH / 2;

        }
    
        /**
         * Updates the float value of the volume based on the button position.
         */
        private void updateFloatValue() {
            float range = maxX - minX;
            float value = buttonX - minX;
            floatValue = value / range;
        }
    
        /**
         * Resets the boolean flags tracking mouse interaction with the button.
         */
        public void resetBools() {
            mouseOver = false;
            mousePressed = false;
        }
    
        /**
         * Checks if the mouse is currently over the button.
         *
         * @return true if the mouse is over the button, otherwise false.
         */
        public boolean isMouseOver() {
            return mouseOver;
        }
    
        /**
         * Sets the flag indicating whether the mouse is over the button.
         *
         * @param mouseOver true to indicate the mouse is over the button, otherwise false.
         */
        public void setMouseOver(boolean mouseOver) {
            this.mouseOver = mouseOver;
        }
    
        /**
         * Checks if the button is currently being pressed.
         *
         * @return true if the button is being pressed, otherwise false.
         */
        public boolean isMousePressed() {
            return mousePressed;
        }
    
        /**
         * Sets the flag indicating whether the button is being pressed.
         *
         * @param mousePressed true to indicate the button is being pressed, otherwise false.
         */
        public void setMousePressed(boolean mousePressed) {
            this.mousePressed = mousePressed;
        }
    
        /**
         * Gets the float value representing the volume level.
         *
         * @return The float value of the volume (between 0 and 1).
         */
        public float getFloatValue() {
            return floatValue;
        }
    }
    

