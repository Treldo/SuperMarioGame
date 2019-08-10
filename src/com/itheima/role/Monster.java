package com.itheima.role;

import com.itheima.ui.GameFrame;

import javax.swing.*;
import java.awt.*;

public class Monster extends Enemy implements Runnable {
    public int x = 360;
    public int y = 360;
    public int width = 30;
    public int height = 30;
    private int xspeed = 1;
    public boolean direction = true;
    private GameFrame gf;

    public Monster(GameFrame gf) {
        this.gf = gf;
    }

    @Override
    public void run() {
        while (true) {
            if (direction) {
                this.x += xspeed;
                this.img = new ImageIcon("images/enemy/monster/stand.png").getImage();
            } else {
                this.x -= xspeed;
                this.img = new ImageIcon("images/enemy/monster/stand.png").getImage();
            }
            if (this.hit("Left") || this.hit("Right")) {
                direction = !direction;
            }
            if (attact("Up")) {
                xspeed = 0;
                img = new ImageIcon("images/enemy/monster/die.png").getImage();
                break;
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
        Rectangle monsterRec = new Rectangle(x, y, width, height);
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
        if (monsterRec.intersects(marioRec) && ((dir.equals("Left") || dir.equals("Right")))) {
            return true;
        } else if (monsterRec.intersects(marioRec) && dir.equals("Up")) {
            return true;
        }
        return false;
    }

    private boolean hit(String dir) {
        Rectangle monsterRec = new Rectangle(x, y, width, height);
        Rectangle rec = null;
        for (int i = 0; i < gf.eneryList.size(); i++) {
            Enemy enemy = gf.eneryList.get(i);
            switch (dir) {
                case "Left":
                    rec = new Rectangle(enemy.x + 2, enemy.y, enemy.width, enemy.height);
                    break;
                case "Right":
                    // 右侧碰撞物检测。
                    rec = new Rectangle(enemy.x - 2, enemy.y, enemy.width, enemy.height);
                    break;
            }
            assert rec != null;
            if (monsterRec.intersects(rec)) {
                return true;
            }
        }
        return false;
    }
}
