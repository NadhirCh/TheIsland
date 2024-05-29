package org.example.GUI.Inputs;

import org.example.GUI.gamestates.CurrentTurn;
import org.example.GUI.gamestates.GameState;
import org.example.GUI.mainGame.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

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

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case PIONS_SELECTION:
                gamePanel.getGame().getPionSelection().mouseClicked(e);
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
                }
            case BATEAU_SELECTION:
                gamePanel.getGame().getBateauSelection().mouseClicked(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().mouseClicked(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getGameOptions().mouseClicked(e);
                break;

        }
    }

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
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getGameOptions().mousePressed(e);
                break;

        }
    }

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
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getGameOptions().mouseReleased(e);
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
