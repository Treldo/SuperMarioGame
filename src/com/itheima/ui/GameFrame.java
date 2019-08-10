package com.itheima.ui;

import com.itheima.mario.Mario;
import com.itheima.role.*;
import com.itheima.util.Map;
import com.itheima.util.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

/**
 * 主体窗口界面：展示角色。
 */
public class GameFrame extends JFrame {
    // 超级玛丽:界面需要一个超级玛丽的。
    public Mario mario;

    public BackgroundImage bg;
    public Music bgm;
    private Image GAME_END = new ImageIcon("images/over.png").getImage();
    // 定义一个集合容器装敌人对象 敌人：障碍物、怪物等
    public Vector<Enemy> eneryList = new Vector<>();
    public Vector<Monster> monList = new Vector<>();
    public Vector<Turtle> turList = new Vector<>();
    // 子弹容器
    public Vector<Boom> boomList = new Vector<>();
    //子弹的速度
    public int bspeed = 0;

    public boolean game_over = false;

    //地图数据，制定规则，是1画砖头，是2画金币，是3画水管
    private int[][] map = null;

    {   //构造代码块：每次实例化对象时都会运行一次
        // 实例代码块中初始化地图资源的数据
        Map mp = new Map();
        map = mp.readMap();
    }

    //构造函数里面初始化背景图片和马里奥对象
    public GameFrame() throws Exception {
        //初始化窗体相关属性信息数据
        // this代表了当前主界面对象。
        this.setSize(800, 450);
        this.setTitle("Super Mario");
        this.setResizable(false);
        // 居中展示窗口
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        // 创建玛丽对象
        mario = new Mario(this);

        // monster 创建部分
        Monster monster = new Monster(this);
        Monster monster2 = new Monster(this);
        monster2.x = 200;
        monster2.direction = false;
        Monster monster3 = new Monster(this);
        monster3.x = 1300;
        Monster monster4 = new Monster(this);
        monster4.x = 292 * 30;

        monList.add(monster);
        monList.add(monster2);
        monList.add(monster3);
        monList.add(monster4);

        // turtle 创建部分
        Turtle turtle = new Turtle(this);
        Turtle turtle2 = new Turtle(this);
        turtle2.x = 2000;
        Turtle turtle3 = new Turtle(this);
        turtle3.x = 342 * 30;

        turList.add(turtle);
        turList.add(turtle2);
        turList.add(turtle3);

        // 创建背景图片
        bg = new BackgroundImage();

        // 读取地图，并配置地图
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                // 读到1，画砖头
                if (map[i][j] == 1) {
                    // x
                    Enemy brick = new Brick(j * 30, i * 30, 30, 30, new ImageIcon("images/brick/brick.png").getImage());
                    eneryList.add(brick);
                }
                // 读到2，画金币砖
                if (map[i][j] == 2) {
                    Enemy coin = new Coin(j * 30, i * 30, 30, 30, new ImageIcon("images/brick/coin_brick.png").getImage());
                    eneryList.add(coin);
                }
                // 读到3，画水管
                if (map[i][j] == 3) {
                    Enemy pipe = new Pipe(j * 30, i * 30, 60, 120, new ImageIcon("images/pipe.png").getImage());
                    eneryList.add(pipe);
                }
                // 读到4，画食人花
                if (map[i][j] == 4) {
                    // 已弃用
                }
                // 读到5，画陷阱
                if (map[i][j] == 5) {
                    Enemy trap = new Trap(j * 30, 358 + 32, 30, 30, new ImageIcon("images/blue.png").getImage());
                    eneryList.add(trap);
                }
                // 读到6，画蘑菇砖
                if (map[i][j] == 6) {
                    Mushroom mushroom = new Mushroom(j * 30, i * 30, 30, 30, new ImageIcon("images/brick/coin_brick.png").getImage());
                    eneryList.add(mushroom);
                }
                // 读到7，画不能顶的砖头
                if (map[i][j] == 7) {
                    Enemy static_brick = new StaticBrick(j * 30, i * 30, 30, 30, new ImageIcon("null.png").getImage());
                    eneryList.add(static_brick);
                }
            }
        }
        // 开启 mario 线程
        mario.start();

        //
        bgm = new Music("music/background.mp3");
        bgm.start();

        // 开启 turList，monList 中线程
        for (Turtle t : turList) {
            Thread turtleTest = new Thread(t);
            t.start();
        }
        for (Monster m : monList) {
            Thread monTest = new Thread(m);
            m.start();
        }

        // 开启一个负责界面窗体重绘的线程
        new Thread(() -> {
            while (true) {
                // 重绘窗体
                repaint();
                //检查子弹是否出界
                checkBoom();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void paint(Graphics g) {
        if (!game_over) {
            // 利用双缓冲画背景图片和 mario
            BufferedImage bi = (BufferedImage) createImage(getSize().width, getSize().height);
            Graphics background = bi.getGraphics();
            background.drawImage(bg.img, bg.x, bg.y, null);

            // 绘制 enemy 对象
            for (Enemy e : eneryList) {
                background.drawImage(e.img, e.x, e.y, e.width, e.height, null);
            }
            for (Monster m : monList) {
                background.drawImage(m.img, m.x, m.y, m.width, m.height, null);
            }
            for (Turtle t : turList) {
                background.drawImage(t.img, t.x, t.y, t.width, t.height, null);
            }

            // 画子弹
            for (Boom b : boomList) {
                Color c = background.getColor();
                background.setColor(Color.red);
                background.fillOval(b.x += b.speed, b.y, b.width, b.width);
                background.setColor(c);
            }

            //画人物 mario
            if (!mario.isdead) {
                background.drawImage(mario.getImg(), mario.x, mario.y, mario.width, mario.height, null);
                g.drawImage(bi, 0, 0, null);
            } else {
                background.drawImage(mario.img_die, mario.x, mario.y, mario.width, mario.height, null);
                g.drawImage(bi, 0, 0, null);
            }
        } else {
            g.drawImage(GAME_END, 0, 0, 900, 600, null);
        }
    }

    //检查子弹是否出界，出界则从容器中移除，不移除的话，内存会泄漏
    private void checkBoom() {
        for (int i = 0; i < boomList.size(); i++) {
            Boom b = boomList.get(i);
            if (b.x < 0 || b.x > 800) {
                boomList.remove(i);
            }
        }
    }
}