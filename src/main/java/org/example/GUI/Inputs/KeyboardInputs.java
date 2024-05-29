package org.example.GUI.Inputs;

import org.example.GUI.gamestates.CurrentTurn;
import org.example.GUI.mainGame.GamePanel;
import org.example.GUI.gamestates.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Handles keyboard inputs for the game.
 * This class implements the KeyListener interface and provides methods to handle key presses and releases.
 */
public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    /**
     * Constructs a KeyboardInputs object with the specified GamePanel.
     * @param gamePanel The GamePanel to associate with the KeyboardInputs.
     */
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Invoked when a key is typed.
     * @param e The KeyEvent object representing the key typed event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed for key typing
    }

    /**
     * Invoked when a key is released.
     * @param e The KeyEvent object representing the key released event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // No action needed for key release
    }

    /**
     * Invoked when a key is pressed.
     * This method switches between game states and calls corresponding keyPressed methods of the current state.
     * @param e The KeyEvent object representing the key pressed event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state){
            case PIONS_SELECTION:
                gamePanel.getGame().getPionSelection().getPawnSelectionOverlay().keyPressed(e);
                break;
            case MENU:
                // No action needed for menu state
                break;
            case PLAYING:
                switch (CurrentTurn.currentTurn){
                    case RETIRER_TUILE :
                        gamePanel.getGame().getRetirerTuile().keyPressed(e);
                        break;
                    case LANCER_DE:
                        gamePanel.getGame().getLancerDe().keyPressed(e);
                        break;
                    case JOUER_TUILE:
                        gamePanel.getGame().getJouerTuile().keyPressed(e);
                        break;
                }
                break;
            case BATEAU_SELECTION:
                gamePanel.getGame().getBateauSelection().keyPressed(e);
                break;
        }
    }
}