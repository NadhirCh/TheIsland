package org.example.GUI.animations;

import java.awt.image.BufferedImage;

/**
 * The DiceAnimation class handles the animation of rolling dice.
 * It cycles through an array of dice images to simulate a rolling dice animation.
 */
public class DiceAnimation {
    private BufferedImage[] diceImages;
    private BufferedImage currentDiceImage;
    private long startTime;
    private int duration;
    private boolean rolling;

    /**
     * Constructs a DiceAnimation with the specified array of dice images.
     * The first image in the array is set as the current dice image initially.
     *
     * @param diceImages an array of BufferedImage representing the faces of the dice
     */
    public DiceAnimation(BufferedImage[] diceImages) {
        this.diceImages = diceImages;
        this.currentDiceImage = diceImages[0];
        this.rolling = false;
    }

    /**
     * Starts the rolling animation for a specified duration.
     *
     * @param duration the duration of the dice roll in milliseconds
     */
    public void startRoll(int duration) {
        this.startTime = System.currentTimeMillis();
        this.duration = duration;
        this.rolling = true;
    }

    /**
     * Updates the current dice image based on the elapsed time since the roll started.
     * If the rolling animation has completed, it randomly selects a final dice face.
     */
    public void update() {
        if (rolling) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= duration) {
                rolling = false;
                currentDiceImage = diceImages[(int) (Math.random() * diceImages.length)];
            } else {
                // Calculate the frame based on elapsed time and total duration
                int frameIndex = (int) ((elapsedTime / (float) duration) * diceImages.length);
                currentDiceImage = diceImages[frameIndex % diceImages.length];
            }
        }
    }

    /**
     * Returns the current dice image being displayed.
     *
     * @return the current BufferedImage of the dice
     */
    public BufferedImage getCurrentDiceImage() {
        return currentDiceImage;
    }

    /**
     * Checks if the dice is currently rolling.
     *
     * @return true if the dice is rolling, false otherwise
     */
    public boolean isRolling() {
        return rolling;
    }
}
