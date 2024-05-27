package org.example.Logic.Model;

import org.example.GUI.gamestates.Couleur;

public class Pion {
    private Couleur couleur;
    private int points;
    private int x;
    private int y;
    private boolean nageur;
    private boolean onIsland;
    private int moveCounter ;
    public Pion(Couleur couleur, int points) {
        this.couleur = couleur;
        this.points = points;
        this.nageur = false; // Un pion n'est pas nageur par défaut
        this.onIsland = true;
        this.moveCounter = 0;
    }

    public boolean deplacer(){
        if(nageur && moveCounter == 1){
            return false;
        }
        else if(!nageur && moveCounter ==3){
            return false;
        }
        moveCounter++;
        return true;
    }
    public void setMoveCounter(int counter){
        this.moveCounter = counter;
    }

    public Couleur getColor() {
        return couleur;
    }

    public int getPoints() {
        return points;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void quitIsland(){
        this.onIsland = false;
    }

    public boolean isNageur() {
        return nageur;
    }

    public void setNageur(boolean nageur) {
        this.nageur = nageur;
    }
}