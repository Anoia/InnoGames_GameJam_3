package menu;

import game.Game;
import game.ResourceId;
import game.ResourceLoader;

import java.util.LinkedList;
import java.util.List;

import processing.core.PFont;

public class Menu
{
	private final int c_fontSize = 20;
	private final Game game;

	List<MenuItem> itemList = new LinkedList<MenuItem>();

	public Menu(final Game game)
	{
		this.game = game;

		// create font
		PFont font = game.loadFont("../fonts/Calibri-20.vlw");
		game.textFont(font, this.c_fontSize);

		int line = 130;

		// add menu items
		this.itemList.add(new MenuItem(830, line, 140, 30)
		{
			@Override
			public String getText()
			{
				return "Holz: " + game.player.carryWood + " | " + game.player.chestWood;
			}

			@Override
			public void action()
			{
				System.out.println("clicked " + this.getText());
			}
		});

		line += 40;
		this.itemList.add(new MenuItem(830, line, 140, 30)
		{
			@Override
			public String getText()
			{
				return "Stein: " + game.player.carryStone + " | " + game.player.chestStone;
			}

			@Override
			public void action()
			{
				System.out.println("clicked " + this.getText());
			}
		});

		line += 40;
		this.itemList.add(new MenuItem(830, line, 140, 30)
		{
			@Override
			public String getText()
			{
				return "Rucksack: 5 H";
			}

			@Override
			public void action()
			{
				System.out.println("clicked " + this.getText());
				if (game.player.capacity < 15)
				{
					game.player.upgradeBackpack();
				}
			}
		});

		line += 40;
		this.itemList.add(new MenuItem(830, line, 140, 30)
		{
			@Override
			public String getText()
			{
				return "Spitzhacke: 10 H";
			}

			@Override
			public void action()
			{
				System.out.println("clicked " + this.getText());
				if (!game.player.pickaxe)
				{
					game.player.buyPickaxe();
				}
			}
		});

		line += 40;
		this.itemList.add(new MenuItem(830, line, 140, 30)
		{
			@Override
			public String getText()
			{
				return "Ziel: 50H, 30S";
			}

			@Override
			public void action()
			{
				System.out.println("clicked " + this.getText());
				game.player.buyObjective();
			}
		});
	}

	public void tick()
	{
		this.drawBackground();
		this.drawTime();
		this.drawInventory();
		this.drawHearts();
	}

	private void drawHearts()
	{
		int health = this.game.player.health;
		switch (health)
		{
			case 0:
				this.game.image(ResourceLoader.getImg(ResourceId.heartEmpty), 840, 500);
				this.game.image(ResourceLoader.getImg(ResourceId.heartEmpty), 880, 500);
				this.game.image(ResourceLoader.getImg(ResourceId.heartEmpty), 920, 500);
				break;
			case 1:
				this.game.image(ResourceLoader.getImg(ResourceId.heart), 840, 500);
				this.game.image(ResourceLoader.getImg(ResourceId.heartEmpty), 880, 500);
				this.game.image(ResourceLoader.getImg(ResourceId.heartEmpty), 920, 500);
				break;
			case 2:
				this.game.image(ResourceLoader.getImg(ResourceId.heart), 840, 500);
				this.game.image(ResourceLoader.getImg(ResourceId.heart), 880, 500);
				this.game.image(ResourceLoader.getImg(ResourceId.heartEmpty), 920, 500);
				break;
			case 3:
				this.game.image(ResourceLoader.getImg(ResourceId.heart), 840, 500);
				this.game.image(ResourceLoader.getImg(ResourceId.heart), 880, 500);
				this.game.image(ResourceLoader.getImg(ResourceId.heart), 920, 500);
				break;
		}

	}

	private void drawBackground()
	{
		this.game.image(ResourceLoader.getImg(ResourceId.menubackground), 800, 0);
	}

	private void drawTime()
	{
		this.game.fill(206, 188, 165);

		long ticks = this.game.getTicksLeft();

		long seconds = (ticks / 60);
		long minutes = seconds / 60;
		seconds = seconds - minutes * 60;

		this.game.text(String.format("%02d:%02d", minutes, seconds), 850, 2 * this.c_fontSize);
	}

	private void drawInventory()
	{

		for (int i = 0; i < this.itemList.size(); i++)
		{
			if (i > 1)
			{
				if (this.game.chestOpen)
				{
					this.game.fill(206, 188, 165);
				}
				else
				{
					this.game.fill(97, 90, 86);
				}
			}
			MenuItem item = this.itemList.get(i);
			this.game.text(item.getText(), item.x, item.y + this.c_fontSize);
			// DEBUG make menus visible
			// this.game.stroke(255, 255, 0);
			// this.game.noFill();
			// this.game.rect(item.x, item.y, item.w, item.h);
		}
	}

	public void click(int mouseX, int mouseY)
	{
		for (MenuItem item : this.itemList)
		{
			if (item.check(mouseX, mouseY))
			{
				item.action();
				return;
			}
		}
	}
}
