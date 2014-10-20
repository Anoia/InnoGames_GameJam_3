package game;

public class Explosion extends Entity
{
	public Explosion(Game game, int x, int y)
	{
		super(x, y);
		this.resourceId = ResourceId.explosion;
		this.busy = 30;
		this.speed = 28;
	}
}
