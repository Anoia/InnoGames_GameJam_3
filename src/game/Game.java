package game;

import java.util.ArrayList;

import menu.Menu;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Game extends PApplet
{
	public static final int c_fps = 60;
	public static final int c_mapsize = 100;
	public static final int c_portsize = 25;
	public static final int c_end = c_fps * 60 * 2;

	public int c_tilesize = 800 / c_portsize;

	public int tick = 0;

	Tile[][] map;

	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	Explosion explosion;

	// player
	public Player player;
	int dx;
	int dy;

	public int death = 1;

	// viewport
	int portX;
	int portY;

	int screenPlayerX;
	int screenPlayerY;

	// menu
	Menu menu;

	// outline
	int outlineX;
	int outlineY;

	// chest
	public boolean chestOpen = false;

	// animation
	Animation animation;
	Tile currentTile;
	public boolean house = false;

	@Override
	public void setup()
	{
		// load map
		this.map = new MapLoader(this).load(c_mapsize);

		this.animation = new Animation(this);
		ResourceLoader.loadTiles(this);

		this.size(1000, 800);
		this.frameRate(c_fps);

		this.player = new Player(this, 61, 60);
		this.menu = new Menu(this);
		this.enemies.add(new Enemy(this, 50, 50));
		this.enemies.add(new Enemy(this, 63, 60));
	}

	@Override
	public void draw()
	{
		if (this.player.health <= 0)
		{
			PFont font = this.loadFont("../fonts/Calibri-20.vlw");
			this.textFont(font, 40);
			this.stroke(255, 0, 0);
			this.fill(0, 0, 0);
			this.rect(0, 0, 1000, 800);
			this.fill(255, 0, 0);
			this.text("GAME OVER", 350, 390);
		}
		else
		{
			++this.tick;

			// update logic
			this.step();

			// display
			this.drawMap();
			this.drawPlayer();
			this.drawEnemies();
			this.drawExplosions();
			this.drawOutline();

			// update menu
			this.menu.tick();
			if (this.house)
			{
				this.image(ResourceLoader.getImg(ResourceId.house), (62 - this.portX) * this.c_tilesize,
				        (57 - this.portY) * this.c_tilesize);
				this.enemies.clear();
			}

		}
	}

	void step()
	{
		this.player.move(this.dx, this.dy);
		this.moveEnemies();
		this.updateViewport();

		// insert enemy
		if ((this.random(100 * c_fps) < this.death * c_fps) && this.enemies.size() < 200)
		{
			int x = (int) this.random(c_mapsize);
			int y = (int) this.random(c_mapsize);
			if (this.map[x][y].walkable)
			{
				this.enemies.add(new Enemy(this, x, y));
			}
		}
	}

	void updateViewport()
	{
		// viewport X
		if ((this.player.x - c_portsize / 2) < 0)
		{
			// left edge
			this.portX = 0;
			this.screenPlayerX = this.player.x;
			this.player.center = 0;
		}
		else if ((this.player.x + c_portsize / 2) >= c_mapsize)
		{
			// right edge
			this.portX = c_mapsize - c_portsize;
			this.screenPlayerX = this.player.x - this.portX;
			this.player.center = 0;
		}
		else
		{
			// center
			this.portX = this.player.x - c_portsize / 2;
			this.screenPlayerX = c_portsize / 2;
			this.player.center += 1;
		}

		// viewport Y
		if ((this.player.y - c_portsize / 2) < 0)
		{
			// left edge
			this.portY = 0;
			this.screenPlayerY = this.player.y;
			this.player.center = 0;
		}
		else if ((this.player.y + c_portsize / 2) >= c_mapsize)
		{
			// right edge
			this.portY = c_mapsize - c_portsize;
			this.screenPlayerY = this.player.y - this.portY;
			this.player.center = 0;
		}
		else
		{
			// center
			this.portY = this.player.y - c_portsize / 2;
			this.screenPlayerY = c_portsize / 2;
			this.player.center += 1;
		}
	}

	private void moveEnemies()
	{
		for (int i = 0; i < this.enemies.size(); ++i)
		{
			Enemy enemy = this.enemies.get(i);
			enemy.move();
			if (enemy.x == this.player.x && enemy.y == this.player.y)
			{
				this.hitByEnemy(enemy);
			}
		}
	}

	// ### events ###

	@Override
	public void keyPressed()
	{
		// get player direction
		switch (this.keyCode)
		{
		// UP
			case 87:
			case 38:
				this.dy = -1;
				this.dx = 0;
				break;

			// DOWN
			case 83:
			case 40:
				this.dy = 1;
				this.dx = 0;
				break;

			// LEFT
			case 65:
			case 37:
				this.dx = -1;
				this.dy = 0;
				break;

			// RIGHT
			case 68:
			case 39:
				this.dx = 1;
				this.dy = 0;
				break;

			// cheat codes
			case '1':
				// give pickaxe - set capacity to 10
				this.player.pickaxe = true;
				this.player.capacity = 10;
				this.player.chestStone = 100;
				this.player.chestWood = 100;
				break;
			case '2':
				this.player.health = 0;
				break;
		}
	}

	@Override
	public void keyReleased()
	{
		// reset player movement
		switch (this.keyCode)
		{
		// UP
		// DOWN
			case 87:
			case 38:
			case 83:
			case 40:
				this.dy = 0;
				break;

			// LEFT
			// RIGHT
			case 65:
			case 37:
			case 68:
			case 39:
				this.dx = 0;
				break;
		}
	}

	@Override
	public void mouseClicked()
	{
		int x = this.mouseX / this.c_tilesize + this.portX;
		int y = this.mouseY / this.c_tilesize + this.portY;

		// click inside map?
		if (x >= 0 && y >= 0 && this.mouseX <= 800 && this.mouseY <= 800)
		{

			// is tile next to player? && is player busy?
			if (this.inRange(x, y) && this.player.busy == 0)
			{
				this.tileAction(this.map[x][y]);
			}

		}
		// propably menu
		else
		{
			this.menu.click(this.mouseX, this.mouseY);
		}

	}

	public void tileAction(Tile tile)
	{
		switch (tile.foreground_type)
		{
			case ResourceId.wood:
				// if capacity > 1
				if (this.player.capacity > (this.player.carryStone + this.player.carryWood))
				{
					// abbauen
					// player busy
					this.player.busy = tile.miningDuration;
					this.player.mining = tile;
					tile.used = true;
				}
				break;

			case ResourceId.stone:
				// if capacity > 1
				if (this.player.pickaxe && this.player.capacity > (this.player.carryStone + this.player.carryWood))
				{
					// abbauen
					// player busy
					this.player.busy = tile.miningDuration;
					this.player.mining = tile;
					tile.used = true;
				}
				break;

			case ResourceId.chest: // store resources
				this.player.chestWood += this.player.carryWood;
				this.player.carryWood = 0;
				this.player.chestStone += this.player.carryStone;
				this.player.carryStone = 0;

				this.chestOpen = true;
				break;

			case ResourceId.berryFull:
				// get Berrys
				// System.out.println("nomnom");
				// wenn health nicht voll
				if (this.player.health < 3)
				{
					this.player.busy = tile.miningDuration;
					this.player.mining = tile;
					tile.used = true;
				}
				break;
		}
	}

	@Override
	public void mouseMoved()
	{
		this.outlineX = this.getMapX(this.mouseX);
		this.outlineY = this.getMapY(this.mouseY);
	}

	// ### display ###
	void drawMap()
	{
		for (int y = this.portY, screenY = 0; screenY < c_portsize; ++y, ++screenY)
		{
			for (int x = this.portX, screenX = 0; screenX < c_portsize; ++x, ++screenX)
			{
				this.currentTile = this.map[x][y];
				float progress = ((float) this.player.speed - this.player.busy) / this.player.speed;
				int xprogress = (int) (this.player.dx * progress * this.c_tilesize);
				int yprogress = (int) (this.player.dy * progress * this.c_tilesize);

				if (this.player.center < 2)
				{
					xprogress = 0;
					yprogress = 0;
				}
				// int posx = (screenX * this.c_tilesize - xprogress);
				// int posy = (screenY * this.c_tilesize - yprogress);
				int posx = (screenX * this.c_tilesize);
				int posy = (screenY * this.c_tilesize);

				// draw background
				PImage backgroundImg = this.animation.get(this.currentTile.background_type);
				this.image(backgroundImg, posx, posy, this.c_tilesize, this.c_tilesize);

				// draw foreground
				PImage foregroundImg = this.animation.get(this.currentTile.foreground_type);
				if (foregroundImg != null)
				{
					this.image(foregroundImg, posx, posy, this.c_tilesize, this.c_tilesize);
				}
			}
		}
	}

	void drawPlayer()
	{
		this.animation.animateEntity(this.player, this.screenPlayerX, this.screenPlayerY);
	}

	void drawEnemies()
	{
		for (Enemy enemy : this.enemies)
		{
			this.animation.animateEntity(enemy, enemy.x - this.portX, enemy.y - this.portY);
		}
	}

	void drawExplosions()
	{
		if (this.explosion != null && this.explosion.busy > 0)
		{
			--this.explosion.busy;
			this.animation.animateEntity(this.explosion, this.explosion.x - this.portX, this.explosion.y - this.portY);
		}
	}

	void drawOutline()
	{
		if (this.inRange(this.portX + this.outlineX, this.portY + this.outlineY))
		{
			this.stroke(0, 255, 0);
		}
		else
		{
			this.stroke(255, 0, 0);
		}
		this.noFill();
		this.rect(this.getScreenX(this.outlineX), this.getScreenY(this.outlineY), this.c_tilesize, this.c_tilesize);
	}

	void hitByEnemy(Enemy enemy)
	{
		// remove enemy
		this.player.health--;
		this.enemies.remove(enemy);
		this.explosion = new Explosion(this, enemy.x, enemy.y);
	}

	// ### helper ###
	int getMapX(int screenX)
	{
		return screenX / this.c_tilesize;
	}

	int getMapY(int screenY)
	{
		return screenY / this.c_tilesize;
	}

	int getScreenX(int mapX)
	{
		return mapX * this.c_tilesize;
	}

	int getScreenY(int mapY)
	{
		return mapY * this.c_tilesize;
	}

	public long getTicksLeft()
	{
		return c_end - this.tick;
	}

	public boolean getChestOpen()
	{
		return this.chestOpen;
	}

	boolean inRange(int x, int y)
	{
		return (x == this.player.x && y == this.player.y - 1 || x == this.player.x - 1 && y == this.player.y
		        || x == this.player.x + 1 && y == this.player.y || x == this.player.x && y == this.player.y + 1);
	}

    static public void main(String args[]) {
        PApplet.main(new String[] { "game.Game" });
    }
}
