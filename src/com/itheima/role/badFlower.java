package com.itheima.role;

import javax.swing.*;
import java.awt.*;


public class badFlower extends Enemy {
    public badFlower(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
    }

    public void move() {
        ImageIcon[] images = new ImageIcon[36];
        for (int i = 0; i < 36; i++) {
            images[i] = new ImageIcon("images/badflower/" + i + ".png");
            this.setImg(images[i].getImage());
            try {
                if ((i % 35) < 17) {
                    y += 2;
                } else if ((i % 35) % 17 == 0) {
                    sleep(5000);
                } else {
                    y -= 2;
                }
                sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
