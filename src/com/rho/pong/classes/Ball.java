package com.rho.pong.classes;

import java.util.Random;

import com.rho.pong.Pong;

public class Ball
{
	private final int COLOR = 0xfffffff; 
	private final int INIT_X, INIT_Y;
	private int x, y, direction, width, heigth, radius;
	private int vx, vy = 0;
	private double speed;
	
	private Random ran = new Random();
	
	public Ball (int radius)
	{
		this.radius = radius;		
		width = heigth = radius * 2;		
		x = INIT_X = Pong.axion.getWidth() / 2 - radius;
		y = INIT_Y = Pong.axion.getHeight() / 2 - radius;
		speed = 3;
	}
	
	public boolean isColliding(Paddle player)
	{
		if(x > player.getX() + player.getWidth() || player.getX() > x + width) 
		{
			return false;
		}
		
		if(y > player.getY() + player.getHeigth() || player.getY() > y + heigth) 
		{
			return false;
		}
		
		return true;
	}
	
	public void reset()
	{
		speed = 3;
		x = INIT_X;
		y = INIT_Y;
		vx = 0;
		vy = 0;
	}
	
	public void update()
	{
		x += vx;
		y += vy;
	}
	
	public void render()
	{
		Pong.axion.graphics().drawFillCircle(x, y, radius, COLOR);
	}
	
	public int randomInt(int min, int max)
	{
		return ran.nextInt(max - min + 1) + min;
	}
	
	public void setVelocities()
	{
		double d = Math.toRadians(Double.valueOf(direction));
		double cos = Math.cos(d);
		double sin = Math.sin(d);
		
		vx = (int)(Math.round(speed * cos));
		vy = (int)(Math.round(speed * sin));
	}
	
	public void speedIncrease()
	{
		speed = speed * 1.05;
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

	public void setDirection(int direction)
	{
		this.direction = direction;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeigth()
	{
		return heigth;
	}
	
	public int getVy()
	{
		return vy;
	}

	public void setVy(int vy)
	{
		this.vy = vy;
	}
}
