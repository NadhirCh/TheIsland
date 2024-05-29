package org.example.GUI.ui;


import java.nio.Buffer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.example.GUI.gamestates.GameState;
import java.awt.Rectangle;

import org.example.GUI.gamestates.GameState;
import static  org.example.GUI.ui.Buttons.UI.Button.* ;

public class VolumeButton extends PauseButton{

        public static final int VOLUME_DEFAULT_WIDTH = 28;
        public static final int VOLUME_DEFAULT_HEIGHT = 44;
        public static final int SLIDER_DEFAULT_WIDTH = 215;
        public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * 1.5f);
        public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * 1.5f);
        public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * 1.5f);
        private boolean mouseOver, mousePressed;
        private int buttonX, minX, maxX;
        private float floatValue = 0f;
        private int xPos, yPos, rowIndex, index;
        private int xOffsetCenter = B_WIDTH / 2;
        private GameState state;
        private BufferedImage[] imgs;
        private Rectangle bounds = new Rectangle();
        private BufferedImage slider;

        public VolumeButton(int xPos, int yPos, int width, int height) {
            super(xPos + width / 2, yPos, VOLUME_WIDTH, height);
            this.height = height;
            bounds.x -= VOLUME_WIDTH / 2;
            buttonX =xPos+ width / 2;
            this.xPos = xPos;
            this.width = width;
            minX =xPos+ VOLUME_WIDTH / 2;
            maxX =xPos+ width - VOLUME_WIDTH / 2;
            loadImgs();
        }

        private void loadImgs() {
                BufferedImage temp = ImageDealingWith.GetSpriteAtlas(ImageDealingWith.VOLUME_BUTTONS);
                imgs = new BufferedImage[3];
                for (int i = 0; i < imgs.length; i++)
                        imgs[i] = temp.getSubimage(i * VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);

                 slider = temp.getSubimage(3 * VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);

        }

        public void update() {
                index = 0;
                if (mouseOver)
                        index = 1;
                if (mousePressed)
                        index = 2;

        }

        public void draw(Graphics g) {

                //g.drawImage(slider, xPos, yPos, width, height, null);
                //g.drawImage(imgs[index], buttonX - VOLUME_WIDTH / 2, yPos, VOLUME_WIDTH, height, null);

        }

        public void changeX(int x) {
                if (x < minX)
                        buttonX = minX;
                else if (x > maxX)
                        buttonX = maxX;
                else
                        buttonX = x;
                updateFloatValue();
                bounds.x = buttonX - VOLUME_WIDTH / 2;

        }

        private void updateFloatValue() {
                float range = maxX - minX;
                float value = buttonX - minX;
                floatValue = value / range;
        }

        public void resetBools() {
                mouseOver = false;
                mousePressed = false;
        }

        public boolean isMouseOver() {
                return mouseOver;
        }

        public void setMouseOver(boolean mouseOver) {
                this.mouseOver = mouseOver;
        }

        public boolean isMousePressed() {
                return mousePressed;
        }

        public void setMousePressed(boolean mousePressed) {
                this.mousePressed = mousePressed;
        }

        public float getFloatValue() {
                return floatValue;
        }


}

