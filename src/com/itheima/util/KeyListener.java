package com.itheima.util;

import com.itheima.role.Boom;
import com.itheima.ui.GameFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


//键盘按下监听类
public class KeyListener extends KeyAdapter {

	// 接收到了当前主界面：游戏界面
	private GameFrame gf;

	public KeyListener(GameFrame gf) {
		this.gf = gf;
	}

	// 键盘监听
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
			// 向右走
			case KeyEvent.VK_D:
				gf.mario.right = true; // 信号位
				gf.mario.setDirection(true);
				break;
			// 向左走
			case KeyEvent.VK_A:
				gf.mario.left = true;
				gf.mario.setDirection(false);
				break;
			case KeyEvent.VK_J:
				addBoom();
				break;
			//向上跳
			case KeyEvent.VK_K:
				gf.mario.up = true;
				break;
		}
	}

	// 添加子弹
	private void addBoom() {
		Boom b = new Boom(gf.mario.x, gf.mario.y + 5, 10);
		if (gf.mario.left) b.speed = -2;
		if (gf.mario.right) b.speed = 2;
		gf.boomList.add(b);
	}

	// 键盘释放监听
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_D) {
			gf.mario.right = false;
		}
		if (code == KeyEvent.VK_A) {
			gf.mario.left = false;
		}
		if (code == KeyEvent.VK_K) {
			gf.mario.up = false;
		}
	}
}