package com.llg.pets.snake.core;

import java.awt.Graphics;
import java.awt.Point;

import com.llg.pets.snake.constant.Constant;
import com.llg.pets.snake.util.ImageUtil;

public class Food extends SnakeObject{

	public Food(){
		this.live=true;
		this.img=ImageUtil.images.get("food");
		this.width=img.getWidth(null);
		this.height=img.getHeight(null);
		this.x=(int) (Math.random()*(Constant.GAME_WIDTH-width+10));
		this.y=(int) (Math.random()*(Constant.GAME_HEIGHT-40-height)+40);

	}
	/**
	 * 判断食物是否被吃掉
	 */
	public void eaten(MySnake mySnake){
		if(mySnake.getRectangle().intersects(this.getRectangle())&&live&&mySnake.live){
			this.live = false;
			mySnake.setLength(mySnake.getLength() + 1);
			mySnake.score+=10*mySnake.getLength();
		}
	}
	/**
	 * 随机位置绘制
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, x, y, null);
	}

}
