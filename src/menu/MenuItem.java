package menu;

abstract public class MenuItem
{
	int x;
	int y;
	int w;
	int h;

	public MenuItem()
	{
	}

	public MenuItem(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean check(int px, int py)
	{
		return px > this.x && px < (this.x + this.w) && py > this.y && py < (this.y + this.h);
	}

	abstract public String getText();

	abstract public void action();
}
