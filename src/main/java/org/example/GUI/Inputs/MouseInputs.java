package org.example.GUI.Inputs;

import org.example.GUI.gamestates.CurrentTurn;
import org.example.GUI.gamestates.GameState;
import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/**
 * Handles mouse inputs for the game.
 * This class implements the MouseListener and MouseMotionListener interfaces
 * to provide methods for handling mouse clicks, presses, releases, movements, and dragging.
 */
public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    /**
     * Constructs a MouseInputs object with the specified GamePanel.
     * @param gamePanel The GamePanel to associate with the MouseInputs.
     */
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Invoked when the mouse is dragged.
     * @param e The MouseEvent object representing the mouse dragged event.
     */
    @Override
    public void mouseDragged(MouseEvent e) {

        switch (GameState.state){
            case OPTIONS:
                gamePanel.getGame().getGameOptions().mouseDragged(e);
                break;

        }
    }

    /**
     * Invoked when the mouse is moved.
     * This method switches between game states and calls corresponding mouseMoved methods of the current state.
     * @param e The MouseEvent object representing the mouse moved event.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case PIONS_SELECTION:
                gamePanel.getGame().getPionSelection().mouseMoved(e);
                break;
            case PLAYING:
                switch (CurrentTurn.currentTurn) {
                    case DEPLACER_ELEMENT:
                        gamePanel.getGame().getMoveElement().mouseMoved(e);
                        break;
                    case LANCER_DE:
                        gamePanel.getGame().getLancerDe().mouseMoved(e);
                        break;
                }
                break;
            case BATEAU_SELECTION:
                gamePanel.getGame().getBateauSelection().mouseMoved(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getGameOptions().mouseMoved(e);
                break;
        }
    }

    /**
     * Invoked when the mouse is clicked.
     * This method switches between game states and calls corresponding mouseClicked methods of the current state.
     * @param e The MouseEvent object representing the mouse clicked event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case PIONS_SELECTION:
                gamePanel.getGame().getPionSelection().mouseClicked(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().mouseClicked(e);
                break;
            case PLAYING:
                switch (CurrentTurn.currentTurn) {
                    case DEPLACER_ELEMENT:
                        gamePanel.getGame().getMoveElement().mouseClicked(e);
                        break;
                    case LANCER_DE:
                        gamePanel.getGame().getLancerDe().mouseClicked(e);
                        break;
                    case RETIRER_TUILE:
                        gamePanel.getGame().getRetirerTuile().mouseClicked(e);
                        break;
                    case JOUER_TUILE:
                        gamePanel.getGame().getJouerTuile().mouseClicked(e);
                        break;
                }
                break;
            case BATEAU_SELECTION:
                gamePanel.getGame().getBateauSelection().mouseClicked(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getGameOptions().mouseClicked(e);
                break;
        }
    }

    /**
     * Invoked when a mouse button is pressed.
     * This method switches between game states and calls corresponding mousePressed methods of the current state.
     * @param e The MouseEvent object representing the mouse pressed event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case PIONS_SELECTION:
                gamePanel.getGame().getPionSelection().mousePressed(e);
                break;
            case PLAYING:
                switch (CurrentTurn.currentTurn) {
                    case DEPLACER_ELEMENT:
                        gamePanel.getGame().getMoveElement().mousePressed(e);
                        break;
                }
                break;
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getGameOptions().mousePressed(e);
                break;
        }
    }

    /**
     * Invoked when a mouse button is released.
     * This method switches between game states and calls corresponding mouseReleased methods of the current state.
     * @param e The MouseEvent object representing the mouse released event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case PIONS_SELECTION:
                gamePanel.getGame().getPionSelection().mouseReleased(e);
                break;
            case PLAYING:
                switch (CurrentTurn.currentTurn) {
                    case DEPLACER_ELEMENT:
                        gamePanel.getGame().getMoveElement().mouseReleased(e);
                        break;
                }
                break;
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getGameOptions().mouseReleased(e);
                break;
        }
    }

    /**
     * Invoked when the mouse enters a component.
     * @param e The MouseEvent object representing the mouse entered event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // No action needed for mouse entering
    }

    /**
     * Invoked when the mouse exits a component.
     * @param e The MouseEvent object representing the mouse exited event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // No action needed for mouse exiting
    }
}
