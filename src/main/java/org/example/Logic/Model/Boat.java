package org.example.Logic.Model;

import org.example.GUI.gamestates.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Boat {
    private List<Pion> explorers;

    public Boat(){
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

    public List<Pion> getExplorers() {
        return explorers;
    }

    public List<Color> getControllers() {
        Map<Color, Integer> colorCount = new HashMap<>();

        // Compter le nombre d'explorateurs de chaque couleur
        for (Pion pion : explorers) {
            Color color = pion.getColor();
            colorCount.put(color, colorCount.getOrDefault(color, 0) + 1);
        }

        // Trouver le(s) joueur(s) ayant le plus grand nombre d'explorateurs
        int maxCount = 0;
        for (int count : colorCount.values()) {
            if (count > maxCount) {
                maxCount = count;
            }
        }

        // Trouver tous les joueurs qui ont le maximum de pions
        List<Color> controllers = new ArrayList<>();
        for (Map.Entry<Color, Integer> entry : colorCount.entrySet()) {
            if (entry.getValue() == maxCount) {
                controllers.add(entry.getKey());
            }
        }

        return controllers;
    }
}
