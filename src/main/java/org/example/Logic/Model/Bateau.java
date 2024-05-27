package org.example.Logic.Model;

import org.example.GUI.gamestates.Couleur;

import java.util.ArrayList;
import java.util.List;

public class Bateau {

    private List<Pion> explorers;
    private List<Player> controlleursBateau;

    public Bateau(){
        this.explorers = new ArrayList<>();

    }

    public boolean addExplorer(Pion pion){
        if(explorers.size() < 3){
            explorers.add(pion);
            return true;
        }
        return false;
    }
    public void removeExplorer(Pion pion){
        explorers.remove(pion);
    }

    public boolean isFull(){
        return explorers.size() == 3;
    }

    public boolean isEmpty(){
        return explorers.isEmpty();
    }


    // Cette fonction permet de retourner la couleur du joueur qui controlle le bateau
    public List<Couleur> getControlleurBateau() {
        List<Couleur> controlleurs = new ArrayList<>();
        int redPawnsCounter = 0;
        int bluePawnsCounter = 0;
        int greenPawnsCounter = 0;
        int yellowPawnsCounter = 0;

        if (this.explorers.isEmpty()) {
            controlleurs.add(Couleur.JAUNE);
            controlleurs.add(Couleur.BLEU);
            controlleurs.add(Couleur.VERT);
            controlleurs.add(Couleur.ROUGE);
            return controlleurs;
        } else {
            // Count the number of each color of pawns on the boat
            for (Pion pion : explorers) {
                switch (pion.getColor()) {
                    case BLEU -> bluePawnsCounter++;
                    case ROUGE -> redPawnsCounter++;
                    case VERT -> greenPawnsCounter++;
                    case JAUNE -> yellowPawnsCounter++;
                }
            }

            // Find the maximum number of pawns of any color
            int maxPawns = Math.max(Math.max(redPawnsCounter, bluePawnsCounter), Math.max(greenPawnsCounter, yellowPawnsCounter));

            if (redPawnsCounter == maxPawns) {
                controlleurs.add(Couleur.ROUGE);
            }
            if (bluePawnsCounter == maxPawns) {
                controlleurs.add(Couleur.BLEU);
            }
            if (greenPawnsCounter == maxPawns) {
                controlleurs.add(Couleur.VERT);
            }
            if (yellowPawnsCounter == maxPawns) {
                controlleurs.add(Couleur.JAUNE);
            }
        }

        return controlleurs;
    }

    public int getCount(Couleur couleur) {
        int count = 0;
        for (Pion pion : explorers) {
            if (pion.getColor() == couleur) {
                count++;
            }
        }
        return count;
    }

}
