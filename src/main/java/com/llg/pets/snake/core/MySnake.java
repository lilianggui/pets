package com.llg.pets.snake.core;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import com.llg.pets.snake.constant.Constant;
import com.llg.pets.snake.util.GameUtil;
import com.llg.pets.snake.util.ImageUtil;

public class MySnake extends SnakeObject implements MoveAble {
	//蛇头
	private static final BufferedImage IMG_SNAKE_HEAD = (BufferedImage) ImageUtil.images.get("snake_head");

	private int speed;//速度
	private int length;//长度
	private int num;//
	private static List<Point> bodyPoints = new LinkedList<>();
	public int score = 0;//分数
	private static BufferedImage newImgSnakeHead;//蛇头
	private boolean up, down, left, right = true;//方向
	public MySnake(int x, int y) {
		this.live = true;
		this.x = x;
		this.y = y;
		this.img = ImageUtil.images.get("snake_body");
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		this.speed = 5;
		this.length = 1;
		this.num = width / speed;
		newImgSnakeHead = IMG_SNAKE_HEAD;
	}

	int getLength() {
		return length;
	}

	void setLength(int length) {
		this.length=length;
	}

	/**
	 * 按键监听
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			if (!down) {
				up = true;
				down = false;
				left = false;
				right = false;
				newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, -90);//��תͼƬ
			}
			break;
		case KeyEvent.VK_DOWN:
			if (!up) {
				up = false;
				down = true;
				left = false;
				right = false;
				newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, 90);
			}
			break;
		case KeyEvent.VK_LEFT:
			if (!right) {
				up = false;
				down = false;
				left = true;
				right = false;
				newImgSnakeHead = (BufferedImage) GameUtil.rotateImage(IMG_SNAKE_HEAD, -180);
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (!left) {
				up = false;
				down = false;
				left = false;
				right = true;
				newImgSnakeHead = IMG_SNAKE_HEAD;
			}
			break;
		}
	}

	/**
	 * 移动
	 */
	@Override
	public void move() {
		if (up)
			y -= speed;
		else if (down)
			y += speed;
		else if (left)
			x -= speed;
		else if (right)
			x += speed;
	}

	/**
	 * 绘制
	 */
	@Override
	public void draw(Graphics g) {
		outOfBounds();
		eatBody();
		bodyPoints.add(new Point(x, y));
		if (bodyPoints.size() == (this.length+1) * num) {
			bodyPoints.remove(0);
		}
		g.drawImage(newImgSnakeHead, x, y, null);
		drawBody(g);
		move();
	}

	/**
	 * 判断是否吃到身体
	 */
	private void eatBody(){
		for (Point point : bodyPoints) {
			for (Point point2 : bodyPoints) {
				if(point.equals(point2)&&point!=point2){
					this.live = false;
				}
			}
		}
	}

	/**
	 * 绘制蛇身
	 * @param g
	 */
	private void drawBody(Graphics g) {
		int length = bodyPoints.size() - 1-num;
		for (int i = length; i >= num; i -= num) {
			Point p = bodyPoints.get(i);
			g.drawImage(img, p.x, p.y, null);
		}
	}

	/**
	 * 碰壁判断
	 */
	private void outOfBounds() {
		boolean xOut = (x <= 0 || x >= (Constant.GAME_WIDTH - width));
		boolean yOut = (y <= 40 || y >= (Constant.GAME_HEIGHT - height));
		if (xOut || yOut) {
			live = false;
		}
	}
}
