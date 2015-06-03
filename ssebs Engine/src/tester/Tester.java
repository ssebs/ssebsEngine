package tester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import engine.AudioHelper;
import engine.LogicHelper;
import engine.RenderHelper;
import engine.URLDataHelper;

public class Tester
{
	private static Texture backgroundTexture, dargonTexture;

	private static float x = 1280 / 2 - 128;
	private static float y = 720 / 2 - 32;

	public static void main(String[] args)
	{
		RenderHelper.createDisplay(1280, 720, "ssebs Engine");
		AudioHelper.createAL();
		AudioHelper.playSound("Whoosh.wav");

		backgroundTexture = RenderHelper.loadTexture(backgroundTexture, "res/BackgroundTexture.png");
		dargonTexture = RenderHelper.loadTexture(dargonTexture, "res/Dargon.png");

		while (!Display.isCloseRequested())
		{
			long delta = (long) LogicHelper.getDelta();

			RenderHelper.texRender(backgroundTexture, 0, 0);
			gameplay(delta);

			RenderHelper.updateDisplay(60);
		}

		RenderHelper.closeDisplay();
		AudioHelper.closeAL();
	}

	private static void gameplay(long delta)
	{
		RenderHelper.texRender(dargonTexture, x, y);

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			x -= delta / 1.25;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			x += delta / 1.25;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
		{
			y -= delta / 1.25;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
		{
			y += delta / 1.25;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN))
		{
			System.out.println(URLDataHelper.getStringDataAsString("https://raw.githubusercontent.com/ssebs/ssebsEngine/master/ssebs%20Engine/Misc/serverTestFile.txt"));
		}

	}
}
