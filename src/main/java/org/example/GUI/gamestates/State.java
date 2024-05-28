package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.ui.MenuButton;

import java.awt.event.MouseEvent;


public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean isIn(MouseEvent e , MenuButton mb){
        return mb.getBounds().contains(e.getX(),e.getY());
    }
}