package game;

public class Enemy extends Entity
{

	Game game;

	public Enemy(Game game, int x, int y)
	{
		super(x, y);
		this.resourceId = ResourceId.enemy;
		this.game = game;
		this.speed = 30;
	}

	public void move()
	{

		if (this.busy > 0)
		{
			this.busy--;
		}
		else
		{
			int r = (int) this.game.random(4);
			int dx = 0;
			int dy = 0;

			switch (r)
			{
				case 0:
					dx = 1;
					break;
				case 1:
					dx = -1;
					break;
				case 2:
					dy = 1;
					break;
				case 3:
					dy = -1;
					break;
			}

			int newx = this.x + dx;
			int newy = this.y + dy;

			if (newx >= 0 && newx < Game.c_mapsize && newy >= 0 && newy < Game.c_mapsize
			        && this.game.map[newx][newy].walkable)
			{
				this.x = newx;
				this.y = newy;
				this.busy = this.speed;
				this.dx = dx;
				this.dy = dy;
			}
		}
	}
}
