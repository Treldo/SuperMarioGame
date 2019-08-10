package com.itheima.mario;

import com.itheima.role.*;
import com.itheima.ui.GameFrame;
import com.itheima.util.Music;

import javax.swing.*;
import java.awt.*;

//自己的角色类
public class Mario extends Thread {

    private static GameFrame gf;
    //
    public boolean isdead = false;
    private boolean jumpFlag = true;
    // 绘图 y 轴参考值
    private int y_standard = 360;
    // mario 的坐标
    public int x = 0;
    public int y = 360;
    // mario 的速度
    private int xspeed = 2, yspeed = 1;
    // mario 的宽高
    public int width = 24, height = 30;
    // mario 届时图片
    private Image img;
    public Image img_die = new ImageIcon("images/mario/mario_die.png").getImage();

    public Image getImg() {
        return this.img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    // 初始化 mario 状态图片，便于 setBig() 设置
    private Image marioR_stand = new ImageIcon("images/mario/small_mario/marioR/stand.png").getImage();
    private Image marioL_stand = new ImageIcon("images/mario/small_mario/marioL/stand.png").getImage();
    private Image marioR_jump = new ImageIcon("images/mario/small_mario/marioR/jump.png").getImage();
    private Image marioL_jump = new ImageIcon("images/mario/small_mario/marioL/jump.png").getImage();
    private Image marioR_run = new ImageIcon("images/mario/small_mario/marioR/run.gif").getImage();
    private Image marioL_run = new ImageIcon("images/mario/small_mario/marioL/run.gif").getImage();

    // mario 向右行进
    public boolean right = false;
    // mario 向左行进
    public boolean left = false;
    // mario 向上跳
    public boolean up = false;
    // mario 向下落
    public boolean down = false;

    // mario 的方向：1 代表面向右边，-1 代表面向左边，0 代表正在向上跳跃
    private boolean direction = true;

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    // true 代表 big_mario，false 代表 small_mario
    private boolean big = false;

    // 将 mario 变大的方法
    private void setBig() {
        big = true;
        y_standard = 340;
        y -= 20;
        height = 52;
        width = 48;
        marioR_stand = new ImageIcon("images/mario/big_mario/marioR/stand.png").getImage();
        marioR_jump = new ImageIcon("images/mario/big_mario/marioR/jump.png").getImage();
        marioR_run = new ImageIcon("images/mario/big_mario/marioR/run.gif").getImage();
        marioL_stand = new ImageIcon("images/mario/big_mario/marioL/stand.png").getImage();
        marioL_jump = new ImageIcon("images/mario/big_mario/marioL/jump.png").getImage();
        marioL_run = new ImageIcon("images/mario/big_mario/marioL/run.gif").getImage();
        for (int i = 0; i < 3; i++) {
            try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            y += 20;
            height = 30;
            try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            y -= 20;
            height = 50;
        }
    }

    // mario 构造方法，开启重力线程
    public Mario(GameFrame gf) {
        this.gf = gf;
        Gravity();
    }

    // mario 移动逻辑
    @Override
    public void run() {
        while (true) {
            if (direction) {
                if (hit("Right")) {
                    xspeed = 0;
                }
                if (right) {
                    if (x < 400) {
                        x += xspeed;
                    } else {
                        gf.bg.x -= xspeed;
                        for (int i = 0; i < gf.monList.size(); i++) {
                            Monster m = gf.monList.get(i);
                            m.x -= xspeed;
                        }
                        for (int i = 0; i < gf.turList.size(); i++) {
                            Turtle t = gf.turList.get(i);
                            t.x -= xspeed;
                        }
                        for (int i = 0; i < gf.eneryList.size(); i++) {
                            Enemy enery = gf.eneryList.get(i);
                            enery.x -= xspeed;
                        }
                        xspeed = 2;
                    }
                    img = marioR_run;
                } else {
                    img = marioR_stand;
                }
                if (up) {
                    if (jumpFlag && !isGravity) {
                        jumpFlag = false;
                        new Thread(() -> {
                            jump();
                            jumpFlag = true;
                        }).start();
                    }
                    img = marioR_jump;
                }
            } else {
                if (hit("Left")) {
                    xspeed = 0;
                }
                if (left) {
                    x -= xspeed;
                    img = marioL_run;
                    xspeed = 2;
                } else {
                    img = marioL_stand;
                }
                if (up) {
                    if (jumpFlag && !isGravity) {
                        jumpFlag = false;
                        new Thread(() -> {
                            jump();
                            jumpFlag = true;
                        }).start();
                    }
                    img = marioL_jump;
                }
            }
            try {
                sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void beJump() {
        int jumpHeight = 0;
        for (int i = 0; i < 100; i++) {
            y -= yspeed;
            jumpHeight++;
            if (i == 99) {
                try {
                    sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < jumpHeight; i++) {
            y += yspeed / 4;
            if (hit("Down")) {
                yspeed = 0;
                break;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        yspeed = 1;
    }

    // jump 方法
    private void jump() {
        int jumpHeight = 0;
        for (int i = 0; i < 150; i++) {
            y -= yspeed;
            jumpHeight++;

            if (hit("Up")) {
                break;
            }
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 149) {
                try {
                    sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < jumpHeight; i++) {
            y += yspeed;
            if (hit("Down")) {
                yspeed = 0;
                break;
            }
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 还原 y 速度
        yspeed = 1;
    }

    //检测碰撞
    private boolean hit(String dir) {
        // Swing技术中，人家已经提供了！！
        //马里奥
        Rectangle myrect = new Rectangle(x, y, width, height);

        Rectangle rect = null;

        for (int i = 0; i < gf.eneryList.size(); i++) {
            Enemy enery = gf.eneryList.get(i);

            switch (dir) {
                case "Left":
                    rect = new Rectangle(enery.x + 2, enery.y, enery.width, enery.height);
                    break;
                case "Right":
                    // 右侧碰撞物检测。
                    rect = new Rectangle(enery.x - 2, enery.y, enery.width, enery.height);
                    break;
                case "Up":
                    rect = new Rectangle(enery.x, enery.y + 1, enery.width, enery.height);
                    break;
                case "Down":
                    rect = new Rectangle(enery.x, enery.y - 2, enery.width, enery.height);
                    break;
            }

            // 碰撞检测
            assert rect != null;
            if (myrect.intersects(rect) && (dir.equals("Left") || dir.equals("Right"))) {
                if (enery instanceof Mushroom) {
                    enery.die();
                    if (!big) {
                        setBig();
                    }
                }
            }
            if (myrect.intersects(rect) && dir.equals("Up")) {
                // 顶到金币
                if (enery instanceof Coin) {
                    // 添加吃金币音效
                    Music coinMusic = new Music("music/eatCoin.mp3");
                    coinMusic.start();
                    StaticBrick staticbrick = new StaticBrick(enery.x, enery.y, 30, 30, new ImageIcon("images/brick/coin_brick_null.png").getImage());
                    gf.eneryList.add(staticbrick);
                    Coin coin = new Coin(enery.x, enery.y - 30, 30, 30, new ImageIcon("images/money.png").getImage());
                    gf.eneryList.add(coin);

                    new Thread(() -> {
                        for (int k = 0; k <= 3; k++) {
                            if (k == 3) {
                                coin.setImg(new ImageIcon("images/money.png").getImage());
                                coin.setY(+21);
                                coin.setImg(new ImageIcon("images/number/score/100.png").getImage());
                                for (k = 0; k < 3; k++) {
                                    coin.setY(-7);
                                    try {
                                        sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                coin.die();
                            }
                            coin.setY(-7);
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();

                    enery.die();
                    return true;
                } else if (enery instanceof Mushroom) {
                    //顶到蘑菇砖，重置图片
                    StaticBrick staticBrick = new StaticBrick(enery.x, enery.y, 30, 30, new ImageIcon("images/brick/coin_brick_null.png").getImage());
                    gf.eneryList.add(staticBrick);

                    // 生成蘑菇
                    Mushroom mush = new Mushroom(enery.x + 5, enery.y - 20, 20, 20, new ImageIcon("images/mushroom/mushroom.png").getImage());
                    gf.eneryList.add(mush);
                    try {
                        sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    enery.die();
                    return true;
                } else if (enery instanceof Brick) {
                    // 顶到砖块，根据 mario 的形态展示不同效果
                    if (big) {
                        // TODO: 2019-07-16     添加砖块特效
                        enery.die();

                        // 添加砖裂音效
                        Music brick_break = new Music("music/brick_break.mp3");
                        brick_break.start();
                    } else {
                        // 砖块移动特效
                        new Thread(() -> {
                            int brickHeight = 0;
                            int brickspeed = 1;
                            for (int j = 0; j < 20; j++) {
                                enery.y -= brickspeed;
                                brickHeight++;
                                try {
                                    Thread.sleep(6);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            for (int j = 0; j < brickHeight; j++) {
                                enery.y += brickspeed;
                                try {
                                    Thread.sleep(6);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        // 添加砖块移动音效
                        Music brick_move = new Music("music/brick_move.mp3");
                        brick_move.start();
                    }
                }
                // 撞到障碍物时返回 true
                return true;
            } else if (myrect.intersects(rect)) {
                // 落入 trap 里
                if (enery instanceof Trap) {
//                    for (int j = 0; j < 150; j++) {
//                        try {
//                            sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        // 游戏结束
//                        gf.game_over = true;
//                        y++;
//                    }
                    // TODO: 2019-07-18 此处 die() 方法执行两次
                    die();
                    break;
                }
                return true;
            }
        }
        return false;
    }

    // 检查是否贴地
    private boolean isGravity = false;

    // 重力线程
    private void Gravity() {
        new Thread(() -> {
            while (true) {
                try {
                    sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (true) {
                    if (!jumpFlag) {
                        break;
                    }
                    if (hit("Down")) {
                        isGravity = false;
                        break;
                    }
                    if (y >= y_standard) {
                        // 人物低于画面
                        // TODO: 2019-07-16 跳转至结束界面
                        isGravity = false;
                    } else {
                        isGravity = true;
                        y += yspeed;
                    }
                    try {
                        sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void die() {
        gf.bgm.stop();
        isdead = true;
        Music die = new Music("music/die.mp3");
        die.start();
        //img = img_die;
        int dieHeight = 0;
        for (int i = 0; i < 150; i++) {
            y -= 1;
            dieHeight++;
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 149) {
                try {
                    sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < dieHeight + 120; i++) {
            y += 1;
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gf.game_over = true;
        //游戏结束 切换到游戏结束界面
    }
}