package org.example.Logic.Model;

import org.example.GUI.gamestates.Couleur;

/**
 * Represents a pawn in the game.
 */
public class Pion {
    private Couleur couleur;
    private int points;
    private int x;
    private int y;
    private boolean nageur;
    private boolean onIsland;
    private int canExist= -1;
    private int moveCounter ;

     /**
     * Initializes a new pawn with the specified color and points.
     *
     * @param couleur The color of the pawn.
     * @param points  The points of the pawn.
     */
    public Pion(Couleur couleur, int points) {
        this.couleur = couleur;
        this.points = points;
        this.nageur = false; 
        this.onIsland = true;
        this.moveCounter = 0;
    }

    /**
     * Sets the ability of the pawn to exit the island.
     *
     * @param canExist The ability of the pawn to exit the island.
     */
    public void setCanExit(int canExist){
        this.canExist=canExist;
    }

     /**
     * Gets the ability of the pawn to exit the island.
     *
     * @return The ability of the pawn to exit the island.
     */
    public int canExit() {
        return canExist;
    }

    /**
     * Moves the pawn.
     *
     * @return True if the pawn is successfully moved; false otherwise.
     */
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

     /**
     * Checks if the pawn is on the island.
     *
     * @return True if the pawn is on the island; false otherwise.
     */
    public boolean isOnIsland(){
        return onIsland;
    }

    /**
     * Sets the move counter of the pawn.
     *
     * @param counter The move counter to set.
     */
    public void setMoveCounter(int counter){
        this.moveCounter = counter;
    }

    // Getters And Setters
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