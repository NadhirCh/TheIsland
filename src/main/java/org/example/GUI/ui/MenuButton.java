package org.example.GUI.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.example.GUI.gamestates.GameState;

/**
 * Represents a button used in the menu interface.
 */
public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private int xOffsetCenter;
    private GameState state;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    /**
     * Constructs a MenuButton with the specified position, row index, and game state.
     *
     * @param xPos      The x-coordinate of the button.
     * @param yPos      The y-coordinate of the button.
     * @param rowIndex  The row index of the button's sprite in the sprite atlas.
     * @param state     The game state associated with the button.
     */
    public MenuButton(int xPos, int yPos, int rowIndex, GameState state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        this.xOffsetCenter = Buttons.UI.Button.B_WIDTH_DEFAULT / 2;
        loadImgs();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, Buttons.UI.Button.B_WIDTH_DEFAULT, Buttons.UI.Button.B_HEIGHT_DEFAULT);
    }

    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = ImageDealingWith.GetSpriteAtlas(ImageDealingWith.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * Buttons.UI.Button.B_WIDTH_DEFAULT, rowIndex * Buttons.UI.Button.B_HEIGHT_DEFAULT, Buttons.UI.Button.B_WIDTH_DEFAULT, Buttons.UI.Button.B_HEIGHT_DEFAULT);
    }

    /**
     * Draws the button.
     *
     * @param g The Graphics object.
     */
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, Buttons.UI.Button.B_WIDTH_DEFAULT, Buttons.UI.Button.B_HEIGHT_DEFAULT, null);
    }

    /**
     * Updates the button's state based on mouse interaction.
     */
    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    /**
     * Checks if the mouse is over the button.
     *
     * @return True if the mouse is over the button, false otherwise.
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Sets the mouse over state of the button.
     *
     * @param mouseOver True to set the mouse over state, false otherwise.
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Checks if the button is pressed.
     *
     * @return True if the button is pressed, false otherwise.
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Sets the pressed state of the button.
     *
     * @param mousePressed True to set the pressed state, false otherwise.
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
     * Retrieves the bounding rectangle of the button.
     *
     * @return The bounding rectangle of the button.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Applies the associated game state when the button is clicked.
     */
    public void applyGamestate() {
        GameState.state = state;
    }

    /**
     * Resets the mouse over and pressed states of the button.
     */
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    /**
     * Retrieves the game state associated with the button.
     *
     * @return The game state associated with the button.
     */
    public GameState getState() {
        return state;
    }
}
