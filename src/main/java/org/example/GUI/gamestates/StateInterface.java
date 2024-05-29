package org.example.GUI.gamestates;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * The StateInterface defines the contract for game states in the application.
 * Classes implementing this interface will handle updating the state, drawing graphics,
 * and responding to mouse events.
 */
public interface StateInterface {

    /**
     * Updates the state. This method is called repeatedly to refresh the state logic.
     */
    public void update();

    /**
     * Draws the graphics for the state. This method is called to render the state on the screen.
     *
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g);

    /**
     * Handles mouse click events. This method is called when the mouse is clicked.
     *
     * @param e the MouseEvent object containing details of the mouse click
     */
    public void mouseClicked(MouseEvent e);

    /**
     * Handles mouse press events. This method is called when a mouse button is pressed.
     *
     * @param e the MouseEvent object containing details of the mouse press
     */
    public void mousePressed(MouseEvent e);

    /**
     * Handles mouse release events. This method is called when a mouse button is released.
     *
     * @param e the MouseEvent object containing details of the mouse release
     */
    public void mouseReleased(MouseEvent e);
}
