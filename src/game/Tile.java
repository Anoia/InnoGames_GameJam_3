package game;

public class Tile
{
	public static int c_duration_wood = 40;
	public static int c_duration_stone = 70;
	public static int c_duration_berry = 40;

	public int foreground_type;
	public int background_type;

	public int x;
	public int y;

	public boolean walkable = false;

	public boolean used = false;

	public int miningDuration = 0;

	public Tile()
	{
	}

	public void init()
	{
		switch (this.foreground_type)
		{
			case ResourceId.wood:
				this.miningDuration = c_duration_wood;
				break;
			case ResourceId.stone:
				this.miningDuration = c_duration_stone;
				break;
			case ResourceId.berryFull:
				this.miningDuration = c_duration_berry;
				break;
		}
	}
}
