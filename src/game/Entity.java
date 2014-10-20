package game;

public class Entity
{
	public int resourceId;

	public int x;
	public int y;

	public int dx;
	public int dy;

	public int busy = 0;
	public int speed;

	// meh =(
	public float xprogress = 0;
	public float yprogress = 0;
	public int xold;
	public int yold;
	public int center;

	public Entity(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
