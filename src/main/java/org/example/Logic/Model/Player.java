package org.example.Logic.Model;

import org.example.GUI.gamestates.Couleur;
import org.example.GUI.mainGame.Hexagon;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int[] pawnsCollection;
    private List<Tuile> handTiles;
    private List<Pion> explorers;
    private Hexagon powerInUse;
    private ArrayList<Hexagon>Pouvoires;



    public Hexagon getPowerInUse() {
        return powerInUse;
    }

    //choix de la tuile à utiliser
    public void UsePower(int i){
        if(HavePouvoir()){
            powerInUse=Pouvoires.remove(i);
        }
    }
    public void UseWhaleDefensePower(){
        for(Hexagon hex : Pouvoires){
            if(hex.getEffet() == Hexagon.Effect.WHALEDEFENSE){
                Pouvoires.remove(hex);
                break;
            }
        }
    }
    public void UseSharkDefensePower(){
        for(Hexagon hex : Pouvoires){
            if(hex.getEffet() == Hexagon.Effect.SHARKDEFENSE){
                Pouvoires.remove(hex);
                break;
            }
        }
    }

    public ArrayList<Hexagon> getPouvoires() {
        return Pouvoires;
    }

    public boolean HavePouvoir(){
        return !Pouvoires.isEmpty();
    }

    public void addPouvoir(Hexagon hex){
        Pouvoires.add(hex);
    }



    private Couleur couleur;

    public Player(String name, int[] pawnsCollection, Couleur couleur) {
        this.name = name;
        this.pawnsCollection = pawnsCollection.clone();
        this.couleur = couleur;
        this.handTiles = new ArrayList<>();
        this.explorers = new ArrayList<>();
        this.Pouvoires=new ArrayList<>();

    }

    public String getName() {
        return name;
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

}
