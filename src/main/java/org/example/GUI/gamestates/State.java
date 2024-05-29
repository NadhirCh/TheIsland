package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.ui.Audio;
import org.example.GUI.ui.MenuButton;

import java.awt.event.MouseEvent;

/**
 * The State class serves as a base class for different game states in the application.
 * It provides common functionality and properties shared by all game states, such as handling the game instance,
 * checking mouse events, and setting the current game state.
 */
public class State {

    protected Game game;

    /**
     * Constructs a new State instance.
     *
     * @param game the Game instance associated with this state
     */
    public State(Game game) {
        this.game = game;
    }

    /**
     * Returns the game instance associated with this state.
     *
     * @return the Game instance
     */
    public Game getGame() {
        return game;
    }

    /**
     * Checks if a mouse event occurred within the bounds of a MenuButton.
     *
     * @param e the MouseEvent object containing details of the mouse event
     * @param mb the MenuButton to check against
     * @return true if the mouse event occurred within the bounds of the MenuButton, false otherwise
     */
    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    /**
     * Sets the current game state and plays the corresponding audio track based on the state.
     *
     * @param state the new GameState to set
     */
    public void setGameState(GameState state) {
        switch (state) {
            case MENU -> game.getAudioPlayer().playSong(Audio.MENU);
            case PLAYING -> game.getAudioPlayer().playSong(Audio.INGAME);
        }
        GameState.state = state;
    }
}
