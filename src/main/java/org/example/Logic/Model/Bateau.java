package org.example.Logic.Model;

import org.example.GUI.gamestates.Couleur;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Bateau représente un bateau dans le modèle de jeu.
 * 
 * Un bateau peut transporter jusqu'à trois explorateurs de différentes couleurs.
 */
public class Bateau {

    private List<Pion> explorers;
    private List<Player> controlleursBateau;

    /**
     * Initialise un nouveau bateau sans explorateurs.
     */
    public Bateau(){
        this.explorers = new ArrayList<>();

    }

     /**
     * Ajoute un explorateur au bateau si celui-ci n'est pas plein.
     * 
     * @param pion L'explorateur à ajouter.
     * @return Vrai si l'ajout est réussi, sinon faux.
     */
    public boolean addExplorer(Pion pion){
        if(explorers.size() < 3){
            explorers.add(pion);
            return true;
        }
        return false;
    }


    /**
     * Retire un explorateur du bateau.
     * 
     * @param pion L'explorateur à retirer.
     */
    public void removeExplorer(Pion pion){
        explorers.remove(pion);
    }

    /**
     * Vérifie si le bateau est plein (contient 3 explorateurs).
     * 
     * @return Vrai si le bateau est plein, sinon faux.
     */
    public boolean isFull(){
        return explorers.size() == 3;
    }

    /**
     * Vérifie si le bateau est vide (ne contient aucun explorateur).
     * 
     * @return Vrai si le bateau est vide, sinon faux.
     */
    public boolean isEmpty(){
        return explorers.isEmpty();
    }

    /**
     * Retourne les couleurs des joueurs contrôlant le bateau.
     * 
     * @return Liste des couleurs des joueurs contrôlant le bateau.
     */
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
    
    //getters
    public List<Pion> getExplorers() {
        return explorers;
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
