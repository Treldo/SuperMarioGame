package com.itheima.role;

import com.itheima.ui.GameFrame;

import javax.swing.*;
import java.awt.*;

public class Turtle extends Enemy implements Runnable {

    public int x = 1000;
    public int y = 360;
    public int width = 30;
    public int height = 30;
    private int xspeed = 1;
    private boolean safe = true;
    private boolean direction = true;
    public Image img = new ImageIcon("images/enemy/turtle/right.png").getImage();
    private GameFrame gf;

    public Turtle(GameFrame gf) {
        this.gf = gf;
    }

    @Override
    public void run() {
        while (true) {
            if (direction) {
                this.x += xspeed;
                if (safe) {
                    this.img = new ImageIcon("images/enemy/turtle/right.png").getImage();
                } else{
                    this.img = new ImageIcon("images/enemy/turtle/round.gif").getImage();
                }
            } else {
                this.x -= xspeed;
                if (safe) {
                    this.img = new ImageIcon("images/enemy/turtle/left.png").getImage();
                } else {
                    this.img = new ImageIcon("images/enemy/turtle/round.gif").getImage();
                }
            }
            if (this.hit("Left") || this.hit("Right")) {
                direction = !direction;
            }
            if (attact("Up")) {
                xspeed = 4;
//                gf.mario.y -= 60;
                gf.mario.beJump();
                safe = false;
                img = new ImageIcon("images/enemy/turtle/round.gif").getImage();
            } else if (attact("Left") || attact("Right")) {
                gf.mario.die();
                break;
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean attact(String dir) {
        Rectangle turtleRec = new Rectangle(x, y, width, height);
        Rectangle marioRec = null;
        switch (dir) {
            // TODO: 2019-07-17 待优化
            case "Left":
                marioRec = new Rectangle(gf.mario.x + 1, gf.mario.y + 10, gf.mario.width - 10, gf.mario.height - 20);
                break;
            case "Right":
                marioRec = new Rectangle(gf.mario.x - 1, gf.mario.y + 10, gf.mario.width - 10, gf.mario.height - 20);
                break;
            case "Up":
                marioRec = new Rectangle(gf.mario.x + 5, gf.mario.y + 31, gf.mario.width - 20, gf.mario.height - 20);
                break;
        }
        assert marioRec != null;
        if (turtleRec.intersects(marioRec) && ((dir.equals("Left") || dir.equals("Right")))) {
            return true;
        }
        if (turtleRec.intersects(marioRec) && dir.equals("Up")) {
            return true;
        }
        return false;
    }

    private boolean hit(String direction) {

        Rectangle turtleRec = new Rectangle(this.x, this.y, this.width, this.height);

        Rectangle rec = null;
        for (int i = 0; i < gf.eneryList.size(); i++) {
            Enemy enemy = gf.eneryList.get(i);
            switch (direction) {
                case "Left":
                    rec = new Rectangle(enemy.x + 2, enemy.y, enemy.width, enemy.height);
                    break;
                case "Right":
                    // 右侧碰撞物检测。
                    rec = new Rectangle(enemy.x - 2, enemy.y, enemy.width, enemy.height);
                    break;
                case "Up":
                    rec = new Rectangle(enemy.x, enemy.y + 1, enemy.width, enemy.height);
                    break;
                case "Down":
                    rec = new Rectangle(enemy.x, enemy.y - 2, enemy.width, enemy.height);
                    break;
            }

            assert rec != null;
            if (turtleRec.intersects(rec) && direction.equals("Left")) {
                return true;
            } else if (turtleRec.intersects(rec) && direction.equals("Left")) {
                return true;
            }
        }

        return false;
    }
}
