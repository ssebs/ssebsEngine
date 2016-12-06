package tester;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

import engine.AudioHelper;
import engine.Logger;
import engine.LogicHelper;
import engine.RenderHelper;
import engine.URLDataHelper;

public class Tester
{
	private static Texture backgroundTexture, dargonTexture;

	private static float _x = 1280 / 2 - 128;
	private static float _y = 720 / 2 - 32;

	private static Logger _logger;
	
	public static void main(String[] args)
	{
		RenderHelper.createDisplay(1280, 720, "ssebs Engine");
		AudioHelper.createAL();
		//AudioHelper.playSound("Whoosh.wav");

		backgroundTexture = RenderHelper.loadTexture(backgroundTexture, "res/BackgroundTexture.png");
		dargonTexture = RenderHelper.loadTexture(dargonTexture, "res/Dargon.png");

		try 
		{
			InputStream is = ResourceLoader.getResourceAsStream("res/fonts/UbuntuMono-R.ttf");
			Font fnt = Font.createFont(Font.TRUETYPE_FONT, is);
			fnt = fnt.deriveFont(16f);
			_logger = new Logger(fnt);
			System.out.println("Ubuntu");			
		} catch (Exception e) 
		{
			e.printStackTrace();
			_logger = new Logger(new Font("Comic Sans MS", Font.BOLD, 24));
			System.out.println("Comic");
		}
		
		
		/**
		 * FIX FONTS
		**/	
		
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
		RenderHelper.texRender(dargonTexture, _x, _y);

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			_x -= delta / 1.25;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			_x += delta / 1.25;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
		{
			_y -= delta / 1.25;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
		{
			_y += delta / 1.25;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN))
		{
			System.out.println(URLDataHelper.getStringDataAsString("https://raw.githubusercontent.com/ssebs/ssebsEngine/master/ssebs%20Engine/Misc/serverTestFile.txt"));
		}
		
		if (Mouse.isButtonDown(0))
		{
			String coords = "X: " + Mouse.getX() + ", Y: " + Mouse.getY();
			_logger.log("Left Mouse Click--" + coords);
		}
		if (Mouse.isButtonDown(1))
		{
			String coords = "X: " + Mouse.getX() + ", Y: " + Mouse.getY();
			_logger.log("Right Mouse Click--" + coords, 15);
		}

	}
	
}
