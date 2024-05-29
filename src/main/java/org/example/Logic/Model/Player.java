package org.example.Logic.Model;

import org.example.GUI.gamestates.Couleur;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.mainGame.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class Player {
    private String name;
    private int[] pawnsCollection;
    private List<Tuile> handTiles;
    private List<Pion> explorers;
    private Hexagon powerInUse;
    private ArrayList<Hexagon>Pouvoires;
    private Game game;

    private Couleur couleur;

    /**
     * Initializes a new player with the specified name, pawn collection, and color.
     *
     * @param name            The name of the player.
     * @param pawnsCollection The collection of pawns for the player.
     * @param couleur         The color of the player.
     */
    public Player(String name, int[] pawnsCollection, Couleur couleur) {
        this.name = name;
        this.pawnsCollection = pawnsCollection.clone();
        this.couleur = couleur;
        this.handTiles = new ArrayList<>();
        this.explorers = new ArrayList<>();
        this.Pouvoires=new ArrayList<>();

    }

     /**
     * Uses a power at the specified index from the list of powers.
     *
     * @param i The index of the power to use.
     */
    public void UsePower(int i){
        if(HavePouvoir()){
            powerInUse=Pouvoires.remove(i);
        }
    }

    /**
     * Uses the whale defense power from the list of powers.
     */
    public void UseWhaleDefensePower(){
        for(Hexagon hex : Pouvoires){
            if(hex.getEffet() == Hexagon.Effect.WHALEDEFENSE){

                Pouvoires.remove(hex);
                break;
            }
        }
    }

    /**
     * Uses the shark defense power from the list of powers.
     */

    public void UseSharkDefensePower(){
        for(Hexagon hex : Pouvoires){
            if(hex.getEffet() == Hexagon.Effect.SHARKDEFENSE){
                Pouvoires.remove(hex);
                break;
            }
        }
    }

     /**
     * Checks if the player has any powers.
     *
     * @return True if the player has powers; false otherwise.
     */
    public boolean HavePouvoir(){
        return !Pouvoires.isEmpty();
    }

    /**
     * Adds a power to the list of powers.
     *
     * @param hex The power to add.
     */
    public void addPouvoir(Hexagon hex){
        Pouvoires.add(hex);
    }

    /**
     * Updates the collection of pawns for the player.
     *
     * @param index The index of the pawn to update.
     * @return 1 if the pawn is successfully updated, -1 if there are still pawns left but the specified pawn is not available,
     * or 0 if there are no more pawns left.
     */
    public int updatePawnsCollection(int index) {
        if (pawnsCollection[index] > 0) {
            pawnsCollection[index]--;
            return 1;
        } else {
            for( int i=0;i<pawnsCollection.length;i++){
                if(pawnsCollection[i] !=0 ){
                    return -1;
                }
            }
            return 0;
        }
    }

    // Getters and Setters
    public String getName() {
        return name;
    }
    public Hexagon getPowerInUse() {
        return powerInUse;
    }

    public Couleur getColor(){
        return this.couleur;
    }
    public void setColor(Couleur couleur){
        this.couleur = couleur;
    }
    public int[] getPawnsCollection() {
        return pawnsCollection;
    }
    public ArrayList<Hexagon> getPouvoires() {
        return Pouvoires;
    }

}
