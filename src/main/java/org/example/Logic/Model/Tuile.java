package org.example.Logic.Model;

public class Tuile{
    private int type;
    private int effect;

    public Tuile(int type,int effect){
        this.type=type;
        this.effect=effect;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }
}