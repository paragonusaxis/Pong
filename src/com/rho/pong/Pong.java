package com.rho.pong;

/* 
 * You shouldn't really need to import any other class from the engine besides the ones below. The Axion 
 * class can already access all of the engine's modules through getter methods.
 */
import com.rho.axion.Axion;
import com.rho.axion.Game;
import com.rho.axion.audio.SoundClip;
import com.rho.axion.gfx.*;
import com.rho.axion.misc.Timer;

// You probably won't need many other imports, but you might need KeyEvent and MouseEvent for easy key and button mapping
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.rho.pong.classes.Player1;
import com.rho.pong.classes.Player2;
import com.rho.pong.classes.Ball;


@SuppressWarnings("unused")
public class Pong extends Game
{			
	private Player1 player1;
	private Player2 player2;
	private Ball ball;
	private int player1Score;
	private int player2Score;
	private int servingPlayer = 1;
	private int winningPlayer;
	private String gameState = "start";
	private SoundClip paddle;
	private SoundClip wall;
	private SoundClip score;
	
	@Override
	public void load()
	{
		axion.setTitle("Pong");
		player1 = new Player1(3, axion.getHeight() / 2 - 20, 8, 40);
		player2 = new Player2(axion.getWidth() - 8 - 3, axion.getHeight() / 2 - 20, 8, 40);
		ball = new Ball(2);
		player1Score = 0;
		player2Score = 0;
		paddle = new SoundClip("/sounds/paddle_hit.wav");
		wall = new SoundClip("/sounds/wall_hit.wav");
		score = new SoundClip("/sounds/score.wav");
	}
	
	@Override
	public void update(float dt)
	{
		if(gameState.equals("start"))
		{
			if(axion.keyboard().isKeyPressed(KeyEvent.VK_ENTER))
			{
				gameState = "serve";
			}
		}
		
		if(gameState.equals("done"))
		{
			if(axion.keyboard().isKeyPressed(KeyEvent.VK_ENTER))
			{
				player1Score = 0;
				player2Score = 0;
				
				if(winningPlayer == 1)
				{
					servingPlayer = 2;
				}
				else
				{
					servingPlayer = 1;
				}
				
				gameState = "serve";
			}
		}
		
		if(gameState.equals("serve"))
		{
			if(servingPlayer == 1)
			{
				ball.setDirection(ball.randomInt(-45, 45));
			}
			else
			{
				ball.setDirection(ball.randomInt(135, 225));
			}
			
			ball.setVelocities();
			
			if(axion.keyboard().isKeyPressed(KeyEvent.VK_SPACE))
			{
				gameState = "play";
			}
		}
		else if(gameState.contentEquals("play"))
		{
			ball.update();
			
			// Checks for collision with players
			if(ball.isColliding(player1))
			{
				ball.speedIncrease();
				ball.setX(player1.getX() + player1.getWidth());
				
				if(ball.getVy() < 0)
				{
					ball.setDirection(ball.randomInt(290, 350));
				}
	            else
	            {
	            	ball.setDirection(ball.randomInt(20, 70));
	            }
				
				ball.setVelocities();
				paddle.play();
			}
			else if(ball.isColliding(player2))
			{
				ball.speedIncrease();
				ball.setX(player2.getX() - ball.getWidth());
				
				if(ball.getVy() < 0)
				{
					ball.setDirection(ball.randomInt(200, 250));
				}
	            else
	            {
	            	ball.setDirection(ball.randomInt(110, 160));
	            }
				
				ball.setVelocities();
				paddle.play();
			}
			
			// Player 2 scored
			if(ball.getX() < 0)
			{
				score.play();
				player2Score++;
				ball.reset();
				
				if(player2Score == 10)
				{
					winningPlayer = 2;
					gameState = "done";
				}
				else
				{
					servingPlayer = 1;
					gameState = "serve";
				}
			}
			
			// Player 1 scored
			if(ball.getX() > Pong.axion.getWidth() - ball.getWidth())
			{
				score.play();
				player1Score++;
				ball.reset();
				
				if(player1Score == 10)
				{
					winningPlayer = 1;
					gameState = "done";
				}
				else
				{
					servingPlayer = 2;
					gameState = "serve";
				}
			}
			
			if(ball.getY() <= 30)
			{
				wall.play();
				ball.setY(30);
				ball.setVy(-ball.getVy());
			}
			
			if(ball.getY() >= Pong.axion.getHeight() - ball.getHeigth())
			{
				wall.play();
				ball.setY(Pong.axion.getHeight() - ball.getHeigth());
				ball.setVy(-ball.getVy());
			}
		}
		
		player1.update();
		player2.update();
	}

	@Override
	public void render()
	{
		if(gameState.equals("start"))
		{
			axion.graphics().printf("PONG", axion.getWidth() / 2, axion.getHeight() / 2 - 8, player1.getColor(), "center", "center");
			axion.graphics().printf("press enter to start", axion.getWidth() / 2, axion.getHeight() / 2 + 8, 0xffffffff, "center", "center");
			
		}
		else if(gameState.equals("done"))
		{
			if(winningPlayer == 1)
			{
				axion.graphics().printf("PLAYER 1 wins!", axion.getWidth() / 2, axion.getHeight() / 2 - 8, player1.getColor(), "center", "center");
			}
			else
			{
				axion.graphics().printf("PLAYER 2 wins!", axion.getWidth() / 2, axion.getHeight() / 2 - 8, player2.getColor(), "center", "center");
			}
			axion.graphics().printf("press enter to restart", axion.getWidth() / 2, axion.getHeight() / 2 + 8, 0xffffffff, "center", "center");
		}
		else
		{
			player1.render();
			player2.render();
			ball.render();
			axion.graphics().print(String.valueOf(player1Score), (axion.getWidth() / 2) - 10, 10, player1.getColor());
			axion.graphics().printf(String.valueOf(player2Score), (axion.getWidth() / 2) + 10, 10, player2.getColor(), "right", "top");
			axion.graphics().drawLine(0, 30, axion.getWidth(), 30, 0xffffffff);
			
			if(gameState.equals("serve"))
			{
				if(servingPlayer == 1)
				{
					axion.graphics().printf("PLAYER 1 is serving!", axion.getWidth() / 2, 40, player1.getColor(), "center", "top");
				}
				else
				{
					axion.graphics().printf("PLAYER 2 is serving!", axion.getWidth() / 2, 40, player2.getColor(), "center", "top");
				}
				axion.graphics().printf("press space to serve", axion.getWidth() / 2, 55, 0xffffffff, "center", "top");
			}
		}
	}
	
	/*
	 *  To start the game, declare main and initialize an Axion object, passing your game's class
	 *  (the one that implements the interface Game) as parameter. Then call axion.start();
	 */
	public static void main(String[] args)
	{
		axion = new Axion(new Pong());
		axion.start();
	}
}

