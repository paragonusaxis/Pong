package com.rho.pong.classes;

import com.rho.pong.Pong;

public abstract class Paddle
{
	private int x, y, width, heigth;
	private final int SPEED = 5;
	private int color;
	
	public Paddle(int x, int y, int width, int heigth)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = heigth;
	}
	
	public abstract void update();
	
	public void render()
	{
		Pong.axion.graphics().drawFillRect(x, y, width, heigth, color);
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeigth()
	{
		return heigth;
	}

	public void setColor(int color)
	{
		this.color = color;
	}

	public int getColor()
	{
		return color;
	}

	public int getSPEED()
	{
		return SPEED;
	}

}
