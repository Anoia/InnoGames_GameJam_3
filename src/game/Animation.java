package game;

import processing.core.PImage;

public class Animation
{
	Game game;
	PImage[] woodHarvest;
	PImage[] stoneHarvest;
	PImage[] berryHarvest;
	PImage[] berryFullIdle;
	PImage[] berryEmpty;
	PImage[] playerWalk;
	PImage[] grasIdle;
	PImage[] explosion;
	PImage[] woodIdle;
	PImage[] waterIdle;

	public Animation(Game game)
	{
		this.game = game;
		// init and load animation frames

		// wood animation
		this.woodHarvest = new PImage[4];
		this.woodHarvest[0] = this.load("tree1.png");
		this.woodHarvest[1] = this.load("tree2.png");
		this.woodHarvest[2] = this.load("tree3.png");
		this.woodHarvest[3] = this.load("tree4.png");

		this.woodIdle = new PImage[3];
		this.woodIdle[0] = this.load("wood_idle1.png");
		this.woodIdle[1] = this.load("wood_idle2.png");
		this.woodIdle[2] = this.load("wood_idle3.png");

		this.stoneHarvest = new PImage[5];
		this.stoneHarvest[0] = this.load("stone1.png");
		this.stoneHarvest[1] = this.load("stone2.png");
		this.stoneHarvest[2] = this.load("stone3.png");
		this.stoneHarvest[3] = this.load("stone4.png");
		this.stoneHarvest[4] = this.load("stone5.png");

		this.berryHarvest = new PImage[3];
		this.berryHarvest[0] = this.load("berry_gathering1.png");
		this.berryHarvest[1] = this.load("berry_gathering2.png");
		this.berryHarvest[2] = this.load("berry_gathering3.png");

		this.berryFullIdle = new PImage[2];
		this.berryFullIdle[0] = this.load("berry_full_idle1.png");
		this.berryFullIdle[1] = this.load("berry_full_idle2.png");

		this.berryEmpty = new PImage[2];
		this.berryEmpty[0] = this.load("berry_empty_idle1.png");
		this.berryEmpty[1] = this.load("berry_empty_idle2.png");

		this.playerWalk = new PImage[4];
		this.playerWalk[0] = this.load("player1.png");
		this.playerWalk[1] = this.load("player2.png");
		this.playerWalk[2] = this.load("player3.png");
		this.playerWalk[3] = this.load("player4.png");

		this.grasIdle = new PImage[2];
		this.grasIdle[0] = this.load("gras1.png");
		this.grasIdle[1] = this.load("gras2.png");

		this.explosion = new PImage[5];
		this.explosion[0] = this.load("explosion1.png");
		this.explosion[1] = this.load("explosion2.png");
		this.explosion[2] = this.load("explosion3.png");
		this.explosion[3] = this.load("explosion4.png");
		this.explosion[4] = this.load("explosion5.png");

		this.waterIdle = new PImage[2];
		this.waterIdle[0] = this.load("water1.png");
		this.waterIdle[1] = this.load("water2.png");
	}

	PImage load(String res)
	{
		return this.game.loadImage("images/" + res);
	}

	public PImage get(int resId)
	{
		PImage img = null;
		int tick = this.game.tick;

		Tile playerTile = this.game.player.mining;
		int animProgress = 0;
		int animDuration = 0;
		PImage[] anim = null;

		switch (resId)
		{
			case ResourceId.wood:
				if (playerTile != null && playerTile.used && playerTile.x == this.game.currentTile.x
				        && playerTile.y == this.game.currentTile.y)
				{
					// harvest
					animProgress = Tile.c_duration_wood - 1 - this.game.player.busy;
					animDuration = Tile.c_duration_wood;
					anim = this.woodHarvest;
				}
				else
				{
					// idle
					animProgress = tick % (2 * Game.c_fps);
					animDuration = 2 * Game.c_fps;
					anim = this.woodIdle;
				}
				break;

			case ResourceId.stone:
				if (playerTile != null && playerTile.used && playerTile.x == this.game.currentTile.x
				        && playerTile.y == this.game.currentTile.y)
				{
					animProgress = Tile.c_duration_stone - 1 - this.game.player.busy;
					animDuration = Tile.c_duration_stone;
					anim = this.stoneHarvest;
				}
				break;

			case ResourceId.berryFull:
				if (playerTile != null && playerTile.used && playerTile.x == this.game.currentTile.x
				        && playerTile.y == this.game.currentTile.y)
				{
					// harvest
					animProgress = Tile.c_duration_berry - 1 - this.game.player.busy;
					animDuration = Tile.c_duration_berry;
					anim = this.berryHarvest;
				}
				else
				{
					// idle
					animProgress = tick % (2 * Game.c_fps);
					animDuration = 2 * Game.c_fps;
					anim = this.berryFullIdle;
				}
				break;

			case ResourceId.player:
				// anim
				if (playerTile == null)
				{
					animProgress = this.game.player.speed - 1 - this.game.player.busy;
					animDuration = this.game.player.speed;
					anim = this.playerWalk;
				}
				break;

			case ResourceId.gras:
				animProgress = tick % (2 * Game.c_fps);
				animDuration = 2 * Game.c_fps;
				anim = this.grasIdle;
				break;

			case ResourceId.explosion:
				Explosion explosion = this.game.explosion;
				animProgress = explosion.speed - 1 - explosion.busy;
				animDuration = explosion.speed;
				anim = this.explosion;
				break;

			case ResourceId.water:
				animProgress = (1 + tick) % (2 * Game.c_fps);
				animDuration = 2 * Game.c_fps;
				anim = this.waterIdle;
				break;

			case ResourceId.berryEmpty:
				animProgress = (1 + tick) % (2 * Game.c_fps);
				animDuration = 2 * Game.c_fps;
				anim = this.berryEmpty;
				break;
		}

		// check if we found an animation
		if (anim != null)
		{
			int animSteps = anim.length;
			int animIndex = (int) (animProgress * (animSteps / (float) animDuration));
			img = anim[animIndex];
		}

		// if no animation found fallback to image
		if (img == null)
		{
			img = ResourceLoader.getImg(resId);
		}

		return img;
	}

	public void animateEntity(Entity entity, int x, int y)
	{
		// this.game.stroke(0, 0, 255);
		// this.game.rect(x * this.game.c_tilesize, y * this.game.c_tilesize, this.game.c_tilesize,
		// this.game.c_tilesize);

		// moving
		if (entity.busy > 0 && entity.center < 2)
		{
			entity.xold = x - entity.dx;
			entity.yold = y - entity.dy;

			float progress = ((float) entity.speed - entity.busy) / entity.speed;
			entity.xprogress = (int) (entity.dx * progress * this.game.c_tilesize);
			entity.yprogress = (int) (entity.dy * progress * this.game.c_tilesize);

			this.game.image(this.get(entity.resourceId), entity.xold * this.game.c_tilesize + entity.xprogress,
			        entity.yold * this.game.c_tilesize + entity.yprogress, this.game.c_tilesize, this.game.c_tilesize);
		}
		// standing around
		else
		{
			entity.xprogress = 0;
			entity.yprogress = 0;

			this.game.image(ResourceLoader.getImg(entity.resourceId), x * this.game.c_tilesize, y
			        * this.game.c_tilesize, this.game.c_tilesize, this.game.c_tilesize);
		}
	}
}
