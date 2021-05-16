package com.llg.pets.snake.core;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.llg.pets.snake.constant.Constant;

public class MyFrame extends Frame{
	/**
	 * 加载主窗口
	 */
	public void loadFrame(){
		this.setTitle("贪吃蛇");//标题
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);//宽度、高度
		this.setBackground(Color.BLACK);//背景颜色
		this.setLocationRelativeTo(null);//设定窗口默认位置，null表示屏幕中间
		//窗口关闭监听
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		//窗口显示
		this.setVisible(true);
		//启动重绘线程
		new RepaintThread().start();
	}

	/**
	 *
	 */
	private Image backImg = null;

	@Override
	public void update(Graphics g) {
		if (backImg == null) {
			backImg = createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		}
		Graphics backg = backImg.getGraphics();
		Color c = backg.getColor();
		backg.setColor(Color.BLACK);
		backg.fillRect(0, 0, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		backg.setColor(c);
		paint(backg);
		g.drawImage(backImg, 0, 0, null);
	}

	/**
	 * 重新绘制线程
	 */
	class RepaintThread extends Thread{
		@Override
		public void run() {
			while(true){
				repaint();
				try {
					sleep(30);//每30ms重绘一次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
