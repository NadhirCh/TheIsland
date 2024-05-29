package org.example.GUI.gamestates;

/**
 * The CurrentTurn enum represents the current turn in the game.
 * This enum includes different turn phases: JOUER_TUILE (play tile), DEPLACER_ELEMENT (move element),
 * RETIRER_TUILE (remove tile), LANCER_DE (roll dice).
 * The default current turn is set to DEPLACER_ELEMENT (move element).
 */
public enum CurrentTurn {
    JOUER_TUILE, DEPLACER_ELEMENT, RETIRER_TUILE, LANCER_DE;
    
    /**
     * The default current turn.
     */
    public static CurrentTurn currentTurn = DEPLACER_ELEMENT;
}
