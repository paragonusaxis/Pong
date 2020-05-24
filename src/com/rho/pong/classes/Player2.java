package com.rho.pong.classes;

import java.awt.event.KeyEvent;

import com.rho.pong.Pong;

public class Player2 extends Paddle
{
	private int color = 0x6abe30;
	
	public Player2(int x, int y, int width, int heigth)
	{
		super(x, y, width, heigth);
		setColor(color);
	}

	@Override
	public void update()
	{
		if(Pong.axion.keyboard().isKeyDown(KeyEvent.VK_UP))
		{
			setY(Math.max(30, getY() - getSPEED()));
		}
		else if(Pong.axion.keyboard().isKeyDown(KeyEvent.VK_DOWN))
		{
			setY(Math.min(Pong.axion.getHeight() - getHeigth(), getY() + getSPEED()));
		}
		
	}

}
