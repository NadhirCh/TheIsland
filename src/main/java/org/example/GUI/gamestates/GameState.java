package org.example.GUI.gamestates;

/**
 * The GameState enum represents the different states that the game can be in.
 * It includes states for playing the game, selecting pawns, navigating the menu,
 * adjusting options, quitting the game, and selecting boats.
 */
public enum GameState {
    PLAYING,         // State when the game is being played
    PIONS_SELECTION, // State for selecting pawns
    MENU,            // State for navigating the main menu
    OPTIONS,         // State for adjusting game options
    QUIT,            // State for quitting the game
    BATEAU_SELECTION;// State for selecting boats

    /**
     * The current state of the game.
     */
    public static GameState state;
}
