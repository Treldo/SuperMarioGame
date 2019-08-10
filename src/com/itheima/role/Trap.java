package com.itheima.role;

import java.awt.*;

//陷阱：用来判定马里奥掉进坑里
public class Trap extends Enemy {
    public Trap(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
    }
}