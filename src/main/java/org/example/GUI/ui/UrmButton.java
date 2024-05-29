package org.example.GUI.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.example.GUI.ui.ImageDealingWith;


/**
 * Represents a button used for Urm in the GUI.
 * Extends the PauseButton class.
 */
public class UrmButton extends PauseButton {

    /**
     * The default size of the Urm button.
     */
    public static final int URM_DEFAULT_SIZE = 56;

    /**
     * The scaled size of the Urm button.
     */
    public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * 1.5f);

    private BufferedImage[] imgs; // Images for different states of the Urm button
    private int rowIndex, index; // Row index and index for rendering
    private boolean mouseOver, mousePressed; // Tracks mouse interaction with the button

    /**
     * Constructs a UrmButton object with specified coordinates, dimensions, and row index.
     *
     * @param x         The x-coordinate of the button.
     * @param y         The y-coordinate of the button.
     * @param width     The width of the button.
     * @param height    The height of the button.
     * @param rowIndex  The row index for determining the image in the sprite atlas.
     */
    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height); // Calls constructor of the superclass
        this.rowIndex = rowIndex;
        loadImgs(); // Loads images for the Urm button
    }

    /**
     * Loads images for the Urm button from a sprite atlas.
     */
    private void loadImgs() {
        BufferedImage temp = ImageDealingWith.GetSpriteAtlas(ImageDealingWith.URM_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * URM_DEFAULT_SIZE, rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
    }

    /**
     * Updates the state of the Urm button based on user interaction.
     */
    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    /**
     * Draws the Urm button.
     *
     * @param g The Graphics object for rendering.
     */
    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
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
}
