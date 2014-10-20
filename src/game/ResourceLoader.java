package game;

import processing.core.PApplet;
import processing.core.PImage;

public class ResourceLoader
{
	// ### images ###
	static PImage desertImg;
	static PImage grasImg;
	static PImage snowImg;
	static PImage waterImg;
	static PImage bridgeImg;

	static PImage stoneImg;
	static PImage woodImg;
	static PImage bedrock;
	static PImage berryEmptyImg;
	static PImage berryFullImg;

	static PImage playerImg;

	static PImage menubackgroundImg;

	static PImage chestImg;

	static PImage enemyImg;

	static PImage houseImg;

	static PImage explosionImg;
	static PImage heartImg;
	static PImage heartEmptyImg;

	public static void loadTiles(PApplet pa)
	{
		// backgrounds
		desertImg = pa.loadImage("images/desert.png");
		grasImg = pa.loadImage("images/bluemchen.png");
		snowImg = pa.loadImage("images/snow.png");
		waterImg = pa.loadImage("images/water.png");
		bridgeImg = pa.loadImage("images/bridge.png");

		// foregrounds
		stoneImg = pa.loadImage("images/stone_nobg.png");
		woodImg = pa.loadImage("images/wood_nobg.png");

		bedrock = pa.loadImage("images/bedrock_nobg.png");

		berryEmptyImg = pa.loadImage("images/berry_empty_idle1.png");
		berryFullImg = pa.loadImage("images/berry_full_idle1.png");

		// other
		playerImg = pa.loadImage("images/player.png");

		menubackgroundImg = pa.loadImage("images/menubackground2.png");

		chestImg = pa.loadImage("images/chest.png");

		enemyImg = pa.loadImage("images/enemy.png");

		houseImg = pa.loadImage("images/house.png");

		explosionImg = pa.loadImage("images/explosion1.png");
		heartImg = pa.loadImage("images/heart.png");
		heartEmptyImg = pa.loadImage("images/heart_broken.png");
	}

	public static PImage getImg(int imgType)
	{
		switch (imgType)
		{
		// backgrounds
			case ResourceId.desert:
				return desertImg;
			case ResourceId.gras:
				return grasImg;
			case ResourceId.snow:
				return snowImg;
			case ResourceId.water:
				return waterImg;
			case ResourceId.bridge:
				return bridgeImg;

				// foregrounds
			case ResourceId.stone:
				return stoneImg;
			case ResourceId.wood:
				return woodImg;
			case ResourceId.berryEmpty:
				return berryEmptyImg;
			case ResourceId.berryFull:
				return berryFullImg;

			case ResourceId.bedrock:
				return bedrock;

				// stuff
			case ResourceId.player:
				return playerImg;
			case ResourceId.menubackground:
				return menubackgroundImg;
			case ResourceId.chest:
				return chestImg;
			case ResourceId.enemy:
				return enemyImg;
			case ResourceId.house:
				return houseImg;
			case ResourceId.explosion:
				return explosionImg;
			case ResourceId.heart:
				return heartImg;
			case ResourceId.heartEmpty:
				return heartEmptyImg;
		}
		return null;
	}
}
