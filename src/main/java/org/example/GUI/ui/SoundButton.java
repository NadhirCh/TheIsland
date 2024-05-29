package org.example.GUI.ui;

import java.nio.Buffer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.example.GUI.gamestates.GameState;
import java.awt.Rectangle;

import org.example.GUI.gamestates.GameState;
import static  org.example.GUI.ui.Buttons.UI.Button.* ;

/**
 * Represents a button used for controlling sound settings in the GUI.
 * Extends the PauseButton class.
 */
public class SoundButton extends PauseButton {

    /**
     * The default size of the sound button.
     */
    public static final int SOUND_SIZE_DEFAULT = 42;

    /**
     * The scaled size of the sound button.
     */
    public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * 1.5f);

    private BufferedImage[][] soundImgs; // Stores images for different states of the button
    private boolean mouseOver, mousePressed; // Tracks mouse interaction with the button
    private boolean muted; // Indicates if sound is muted
    private int rowIndex, colIndex; // Indices for selecting the appropriate image

    /**
     * Constructs a SoundButton object with specified coordinates and dimensions.
     *
     * @param x      The x-coordinate of the button.
     * @param y      The y-coordinate of the button.
     * @param width  The width of the button.
     * @param height The height of the button.
     */
    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height); // Calls constructor of the superclass

        loadSoundImgs(); // Loads images for the sound button
    }

    /**
     * Loads images for the sound button from a sprite atlas.
     */
    private void loadSoundImgs() {
        BufferedImage temp = ImageDealingWith.GetSpriteAtlas(ImageDealingWith.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];
        for (int j = 0; j < soundImgs.length; j++)
            for (int i = 0; i < soundImgs[j].length; i++)
                soundImgs[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
    }

    /**
     * Updates the state of the sound button based on user interaction and mute status.
     */
    public void update() {
        if (muted)
            rowIndex = 1;
        else
            rowIndex = 0;

        colIndex = 0;
        if (mouseOver)
            colIndex = 1;
        if (mousePressed)
            colIndex = 2;

    }

    /**
     * Resets the boolean flags tracking mouse interaction with the button.
     */
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    /**
     * Draws the sound button with the appropriate image based on its state.
     *
     * @param g The Graphics object for rendering.
     */
    public void draw(Graphics g) {
        g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
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
     * Checks if the sound is currently muted.
     *
     * @return true if the sound is muted, otherwise false.
     */
    public boolean isMuted() {
        return muted;
    }

    /**
     * Sets the flag indicating whether the sound is muted.
     *
     * @param muted true to mute the sound, otherwise false.
     */
    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
