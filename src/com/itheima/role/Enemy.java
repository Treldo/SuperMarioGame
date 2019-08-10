package com.itheima.role;

import com.itheima.ui.GameFrame;

import java.awt.Image;

//障碍物的抽象父类
public abstract class Enemy extends Thread {
	public int x, y;
	public int width, height;
	public Image img;
	public GameFrame gf;

	public Enemy(int x, int y, int width, int height, Image img) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = img;
	}
	public Enemy() { }

	public void setImg(Image img) {
		this.img = img;
	}

	public void setX(int x) {
		this.x += x;
	}

	public void setY(int y) {
		this.y += y;
	}

	public void die() {
		this.y = 600;
	}
}