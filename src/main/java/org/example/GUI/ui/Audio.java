package org.example.GUI.ui;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Audio {

    public static int MENU = 0;
    public static int INGAME = 1;

    public static int MENUCLICK = 0;
    public static int PIECEMOVE = 1;
    public static int TILE = 2;
    public static int WATER = 3;
    public static int BOAT = 4;
    public static int WHALE = 5;
    public static int SHARK = 6;
    public static int DOLPHIN = 7;
    public static int WHIRLPOOL = 8;
    public static int VOLCANO = 9;
    public static int DICE = 10;


    private Clip[] songs, effects;
    private int currentSongID;
    private float effectsVolume = 0.75f;
    private float songVolume = effectsVolume * 0.85f;
    private boolean songMute, effectMute = false;
    //private int viewIndex;
    //private Random rand = new Random();


    private Clip getClip(String audioName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        URL url = getClass().getResource("/audio/" + audioName + ".wav");
        AudioInputStream audioInputStream;

        try {
            audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException exception){
            System.out.println("Error during Clip Loading: " + exception.getMessage());
        }

        return null;
    }


    private void loadSong() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String[] songNames = {"menuMusic", "inGameMusic"};
        songs = new Clip[songNames.length];
        for(int i = 0; i < songNames.length; i++){
            songs[i] = getClip(songNames[i]);
        }
    }

    private void loadEffects() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String[] effectNames = {"menuClick", "pieceMove", "tile", "water", "boat", "whale", "shark", "dolphin", "whirlpool", "volcano", "dice"};
        effects = new Clip[effectNames.length];
        for(int i = 0; i < effectNames.length; i++){
            effects[i] = getClip(effectNames[i]);
        }
        updateEffectsVolume();
    }


    public void setSongView(int viewIndex){

    }

    public void gameEnded(){

    }

    public void playEffect(int effect){
        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }

    public void setVolume(float songVolume, float effectsVolume){
        this.songVolume = songVolume;
        this.effectsVolume = effectsVolume;
        updateSongVolume();
        updateEffectsVolume();
    }


    public void stopSong(){
        if(songs[currentSongID].isActive()){
            songs[currentSongID].stop();
        }
    }

    public void playSong(int song){
        stopSong();
        currentSongID = song;
        updateSongVolume();
        songs[currentSongID].setMicrosecondPosition(0);
        songs[currentSongID].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void toggleSongMute(){
        this.songMute = !songMute;
        for(Clip clip: songs){
            BooleanControl boolControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            boolControl.setValue(songMute);
        }
    }

    public void toggleEffectsMute(){
        this.effectMute = !effectMute;
        for(Clip clip: effects){
            BooleanControl boolControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            boolControl.setValue(effectMute);
        }
        if(!effectMute){
            playEffect(MENUCLICK);
        }
    }

    public void updateSongVolume(){
        FloatControl gainControl = (FloatControl) songs[currentSongID].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float value = (range * songVolume) + gainControl.getMinimum();
        gainControl.setValue(value);
    }

    public void updateEffectsVolume(){
        for(Clip clip : effects) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float value = (range * effectsVolume) + gainControl.getMinimum();
            gainControl.setValue(value);
        }
    }

    public Audio() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        loadSong();
        loadEffects();
        playSong(MENU);
    }
}
