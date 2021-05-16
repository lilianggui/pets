package com.llg.pets.snake.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.llg.pets.snake.core.Food;
import com.llg.pets.snake.core.MyFrame;
import com.llg.pets.snake.core.MySnake;
import com.llg.pets.snake.util.ImageUtil;

public class SnakeClient extends MyFrame{

	private MySnake mySnake = new MySnake(100, 100);//蛇
	private Food food = new Food();//食物
	private Image background = ImageUtil.images.get("background");//背景图
	private Image fail = ImageUtil.images.get("fail");//失败背景图

	@Override
	public void loadFrame() {
		super.loadFrame();
		//键盘监听
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				mySnake.keyPressed(e);
			}
		});
	}

	/**
	 * 绘制
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);//绘制背景图
		if(mySnake.live){//蛇还活着
			mySnake.draw(g);
			if(food.live){//食物还没被吃掉
				food.draw(g);
				food.eaten(mySnake);//判断食物是否被吃
			}else{//食物被吃掉了
				food = new Food();
			}
		} else {//蛇死了，绘制game over背景
			g.drawImage(
				fail,
				(background.getWidth(null) - fail.getWidth(null)) / 2,
				(background.getHeight(null) - fail.getHeight(null)) / 2,
				null
			);
		}
		drawScore(g);// 绘制分数
	}


	/**
	 * 绘制分数
	 * @param g 画笔
	 */
	private void drawScore(Graphics g){
		g.setFont(new Font("Courier New", Font.BOLD, 40));
		g.setColor(Color.WHITE);
		g.drawString("SCORE:" + mySnake.score,700,100);
	}
}
