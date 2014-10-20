package game;

public class Player extends Entity
{
	public int capacity = 1;

	public int carryWood = 0;
	public int carryStone = 0;
	public int chestWood = 0;
	public int chestStone = 0;

	public Tile mining;
	public boolean pickaxe = false;

	public int health = 3;

	public Game game;

	public Player(Game game, int x, int y)
	{
		super(x, y);
		this.resourceId = ResourceId.player;
		this.game = game;
		this.speed = 8;
	}

	public void move(int dx, int dy)
	{

		// check death
		if (this.game.getTicksLeft() <= 0)
		{
			this.game.death = 100;
		}

		// System.out.println(this.capacity);
		// keep old direction
		if (!(dx == 0 && dy == 0))
		{
			this.dx = dx;
			this.dy = dy;
		}

		// reduce freeze if player is not allowed to move yet
		if (this.busy > 0)
		{
			this.busy--;
		}
		else
		{
			if (this.mining != null)
			{
				// add resource
				switch (this.mining.foreground_type)
				{
					case ResourceId.wood: // add wood
						this.carryWood++;
						break;
					case ResourceId.stone: // add stone
						this.carryStone++;
						break;
					case ResourceId.berryFull:
						this.health++;
						break;
				}
				this.mining.foreground_type = ResourceId.nothing;
				this.mining.walkable = true;
				this.mining = null;
			}

			// move player
			if (dx != 0 || dy != 0)
			{
				int newx = this.x + dx;
				int newy = this.y + dy;

				if (newx >= 0 && newx < Game.c_mapsize && newy >= 0 && newy < Game.c_mapsize)
				{
					// check if new tile walkable
					if (this.game.map[newx][newy].walkable)
					{
						this.x = newx;
						this.y = newy;
						this.busy = this.speed;

						this.game.chestOpen = false;
					}
				}
			}
		}
	}

	// ### interface from menu ###
	public void upgradeBackpack()
	{

		if (this.game.chestOpen && this.capacity > 1 && this.chestWood >= 5)
		{
			// upgrade backpack 2
			this.capacity = 15;
			this.chestWood -= 5;
			this.chestWood -= 3;
		}
		if (this.game.chestOpen && this.capacity == 1 && this.chestWood >= 5)
		{
			// upgrade backpack
			this.capacity = 5;
			this.chestWood -= 5;
		}

	}

	public void buyPickaxe()
	{
		if (this.game.chestOpen && this.chestWood >= 10)
		{
			// unlock stone
			this.pickaxe = true;
			this.chestWood -= 10;
		}
	}

	public void buyObjective()
	{
		if (this.game.chestOpen && this.chestWood >= 50 && this.chestStone >= 30)
		{
			// build objective
			// end game
			this.chestWood -= 50;
			this.chestStone -= 30;
			System.out.println("juhuu gewonnen!");
			this.game.house = true;
		}
	}

	public void heal()
	{
		// if (this.sick > 1 && this.chestWood >= 5 && this.chestStone >= 5)
		// {
		// this.chestWood -= 5;
		// this.chestStone -= 5;
		// this.sick = 1;
		// }
	}
}
