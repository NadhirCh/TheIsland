package org.example.GUI.gamestates;

import java.awt.event.MouseEvent;

import org.example.GUI.mainGame.Game;
import org.example.GUI.ui.MenueButton;


public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public boolean isIn(MouseEvent e , MenueButton mb){
        return mb.getBounds().contains(e.getX(),e.getY());
    }

    public Game getGame() {
        return game;
    }
}