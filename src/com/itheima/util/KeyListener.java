package com.itheima.util;

import com.itheima.role.Boom;
import com.itheima.ui.GameFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


//���̰��¼�����
public class KeyListener extends KeyAdapter {

	// ���յ��˵�ǰ�����棺��Ϸ����
	private GameFrame gf;

	public KeyListener(GameFrame gf) {
		this.gf = gf;
	}

	// ���̼���
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
			// ������
			case KeyEvent.VK_D:
				gf.mario.right = true; // �ź�λ
				gf.mario.setDirection(true);
				break;
			// ������
			case KeyEvent.VK_A:
				gf.mario.left = true;
				gf.mario.setDirection(false);
				break;
			case KeyEvent.VK_J:
				addBoom();
				break;
			//������
			case KeyEvent.VK_K:
				gf.mario.up = true;
				break;
		}
	}

	// ����ӵ�
	private void addBoom() {
		Boom b = new Boom(gf.mario.x, gf.mario.y + 5, 10);
		if (gf.mario.left) b.speed = -2;
		if (gf.mario.right) b.speed = 2;
		gf.boomList.add(b);
	}

	// �����ͷż���
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