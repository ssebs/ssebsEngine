package tester;

import java.awt.Font;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.ResourceLoader;

import engine.AudioHelper;
import engine.IOUtil;
import engine.Logger;
import engine.LogicHelper;
import engine.RenderHelper;
import engine.URLDataHelper;

public class Tester
{
	private static int WIDTH, HEIGHT, MOUSEINVERT;
	private static float SENSITIVITY;
	private static boolean VSYNC, NOCOLLIDE;

	private static Texture txtr_background, txtr_face, txtr_trackpad;

	private static float _x;
	private static float _y;
	private static int _fontSize;

	private static Logger _logger;

	//TODO: Add timer to logger
	//TODO: Add game class and make Tester override it, similar to Android Activities

	public static void main(String[] args)
	{
		System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
		init(); // Gets settings from file then sets vars

		RenderHelper.createDisplay(WIDTH, HEIGHT, VSYNC, "ssebs Engine");
		AudioHelper.createAL();

		//Load in the textures
		txtr_background = RenderHelper.loadTexture(txtr_background, "res/engine/BackgroundTexture.png");
		txtr_face = RenderHelper.loadTexture(txtr_face, "res/face256.png");
		txtr_trackpad = RenderHelper.loadTexture(txtr_trackpad, "res/MoveChar.png");

		// Load fonts
		try
		{
			InputStream is = ResourceLoader.getResourceAsStream("res/fonts/UbuntuMono-R.ttf");
			Font fnt = Font.createFont(Font.TRUETYPE_FONT, is);
			fnt = fnt.deriveFont((float) _fontSize);
			_logger = new Logger(fnt);
			System.out.println("Ubuntu font loaded");
		} catch (Exception e)
		{
			e.printStackTrace();
			_logger = new Logger(new Font("Comic Sans MS", Font.BOLD, _fontSize));
			System.out.println("Comic Sans font used as backup");
		}

		
		AudioHelper.playSound("Whoosh.wav");
		
		// Main game loop
		while (!Display.isCloseRequested())
		{
			long delta = (long) LogicHelper.getDelta();
			render(delta);
			input(delta);
			gameplay(delta);

			RenderHelper.updateDisplay(60);
		}

		RenderHelper.closeDisplay();
		AudioHelper.closeAL();
	}

	private static void render(long delta)
	{
		RenderHelper.texRender(txtr_background, 0, 0);
		RenderHelper.texRender(txtr_trackpad, 0, HEIGHT - txtr_trackpad.getImageHeight());

		RenderHelper.texRender(txtr_face, (int)_x, (int)_y);

		_logger.log("Left: " + _x + " Right: " + (txtr_face.getImageWidth() + _x));
		_logger.log("MOUSE = X: " + Mouse.getX() + ", Y: " + Mouse.getY(), _fontSize);
		_logger.log("FPS:" + RenderHelper.getFPS(), _fontSize * 2, new Color(0x41AFFF));

	}

	private static void input(long delta)
	{
		final float speed = 1.25f;

		//Input
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			_x -= delta / speed;

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			_x += delta / speed;

		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			_y -= delta / speed;

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			_y += delta / speed;

		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN))
			_logger.log(
					URLDataHelper.getStringDataAsString(
							"https://raw.githubusercontent.com/ssebs/ssebsEngine/master/ssebs%20Engine/Misc/serverTestFile.txt"),
					_fontSize * 2);

		if (Mouse.isButtonDown(0))
		{
			// in "trackpad" area
			if (Mouse.getX() <= txtr_trackpad.getImageWidth() && Mouse.getY() <= txtr_trackpad.getImageHeight())
			{
				_x += SENSITIVITY * Mouse.getDX();
				_y += SENSITIVITY * MOUSEINVERT * Mouse.getDY(); // Invert y
			}
		}

		if (Mouse.isButtonDown(1))
		{
			NOCOLLIDE = true;
			_x = Mouse.getX();
			_y = HEIGHT - Mouse.getY();
		} else
		{
			NOCOLLIDE = false;
		}

		if (Mouse.isButtonDown(2))
		{
			_x += Mouse.getDX();
			_y += -1 * Mouse.getDY(); // Dont want to invert for this as it matched screenspace
		}

	}

	private static void gameplay(long delta)
	{
		int distance = 15;

		if (!NOCOLLIDE)
		{
			// Colliding with edges
			if (_x <= distance || _x >= (WIDTH - txtr_face.getImageWidth()) - distance)
			{
				_logger.log("COLLIDING", _fontSize * 3, Color.red);
				_x = (WIDTH / 2) - txtr_face.getImageWidth() / 2;
				_y = (HEIGHT / 2) - txtr_face.getImageHeight() / 2;
			}
			if (_y <= distance || _y >= (HEIGHT - txtr_face.getImageHeight()) - distance)
			{
				_logger.log("COLLIDING", _fontSize * 3, Color.red);
				_x = (WIDTH / 2) - txtr_face.getImageWidth() / 2;
				_y = (HEIGHT / 2) - txtr_face.getImageHeight() / 2;
			}
		}

	} // End gameplay

	private static void init()
	{
		ArrayList<String> settings = IOUtil.readFileToArrayList("settings.ini");

		for (String s : settings)
		{
			if (!s.startsWith("#"))
				System.out.print("");
			if (s.startsWith("WIDTH"))
				WIDTH = Integer.parseInt(s.substring("WIDTH=".length()));
			if (s.startsWith("HEIGHT"))
				HEIGHT = Integer.parseInt(s.substring("HEIGHT=".length()));
			if (s.startsWith("SENSITIVITY"))
				SENSITIVITY = Float.parseFloat(s.substring("SENSITIVITY=".length()));
			if (s.startsWith("VSYNC"))
				VSYNC = Boolean.getBoolean(s.substring("VSYNC".length()));

			if (s.startsWith("MOUSEINVERT"))
				MOUSEINVERT = (s.endsWith("TRUE")) ? 1 : -1;
		}
		settings = null;

		_x = WIDTH / 2 - 128;
		_y = HEIGHT / 2 - 32;
		_fontSize = 15;
		NOCOLLIDE = false;
	}

}// End class
