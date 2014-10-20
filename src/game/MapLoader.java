package game;

import processing.core.PApplet;
import processing.core.PImage;

public class MapLoader
{
	PApplet pa;

	public MapLoader(PApplet pa)
	{
		this.pa = pa;
	}

	public Tile[][] load(int s)
	{
		Tile[][] map = new Tile[s][s];

		PImage mapImg = this.pa.loadImage("../maps/map04.png");
		mapImg.loadPixels();

		int[] pixels = mapImg.pixels;

		for (int y = 0; y < s; ++y)
		{
			for (int x = 0; x < s; ++x)
			{
				int i = y * s + x;
				int tile = pixels[i];
				Tile ctile = new Tile();
				ctile.x = x;
				ctile.y = y;
				ctile.foreground_type = ResourceId.nothing;
				ctile.walkable = false;

				switch (tile)
				{
					// ### raw backgrounds ###
					case 0xfffff25f: // Desert
						ctile.background_type = ResourceId.desert;
						ctile.walkable = true;
						break;
					case 0xff00ff00: // Gras
						ctile.background_type = ResourceId.gras;
						ctile.walkable = true;
						break;
					case 0xffffffff: // snow
						ctile.background_type = ResourceId.snow;
						ctile.walkable = true;
						break;
					case 0xff0000ff: // Water
						ctile.background_type = ResourceId.water;
						break;
					case 0xff880015: // bridge
						ctile.background_type = ResourceId.bridge;
						ctile.walkable = true;
						break;

					// ### mixed backgrounds ###
					// mixed tree
					case 0xff683c11: // tree on gras
						ctile.background_type = ResourceId.gras;
						ctile.foreground_type = ResourceId.wood;
						break;

					case 0xffa3810a: // tree on sand
						ctile.background_type = ResourceId.desert;
						ctile.foreground_type = ResourceId.wood;
						break;

					// mixed stone
					case 0xff818181: // stone on gras
						ctile.background_type = ResourceId.gras;
						ctile.foreground_type = ResourceId.stone;
						break;

					case 0xffffc90e: // stone on sand
						ctile.background_type = ResourceId.desert;
						ctile.foreground_type = ResourceId.stone;
						break;

					case 0xff99d9ea: // stone on water
						ctile.background_type = ResourceId.water;
						ctile.foreground_type = ResourceId.stone;
						break;

					// berries
					case 0xff8d5ba1:
						ctile.background_type = ResourceId.berryEmpty;
						ctile.foreground_type = ResourceId.berryFull;
						break;

					// mixed bedrock
					case 0xff3d322d: // bedrock on gras
						ctile.background_type = ResourceId.gras;
						ctile.foreground_type = ResourceId.bedrock;
						break;

					case 0xffc4a260: // bedrock on sand
						ctile.background_type = ResourceId.desert;
						ctile.foreground_type = ResourceId.bedrock;
						break;

					case 0xff000000: // bedrock on snow
						ctile.background_type = ResourceId.snow;
						ctile.foreground_type = ResourceId.bedrock;
						break;

					case 0xff3b3b3b: // bedrock on water
						ctile.background_type = ResourceId.water;
						ctile.foreground_type = ResourceId.bedrock;
						break;

					// ### other ####
					case 0xffff0000: // chest on gras
						ctile.background_type = ResourceId.gras;
						ctile.foreground_type = ResourceId.chest;
						break;
				}

				ctile.init();

				map[x][y] = ctile;
			}
		}

		return map;
	}
}
