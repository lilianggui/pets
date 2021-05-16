package com.llg.pets.snake.core;

import java.awt.Image;
import java.awt.Rectangle;

public abstract class SnakeObject implements Drawable {

	int x;
	int y;
	Image img;
	int width;
	int height;
	public boolean live;

	/**
	 * 获取位置和范围
	 */
	Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}
}
