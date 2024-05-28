package org.example.GUI.Inputs;

import org.example.GUI.gamestates.CurrentTurn;
import org.example.GUI.mainGame.GamePanel;
import org.example.GUI.gamestates.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state){
            case PIONS_SELECTION:
                gamePanel.getGame().getPionSelection().getPawnSelectionOverlay().keyPressed(e);
                break;
            case MENU:
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
        }
    }
}