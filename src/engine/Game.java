package engine;

import java.awt.Font;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.util.ResourceLoader;

public abstract class Game 
{
	protected int WIDTH, HEIGHT, MOUSEINVERT;
	protected float SENSITIVITY;
	protected boolean VSYNC;

	protected  int _fontSize, _targetFPS;
	protected  Logger _logger;
	
	
	public Game(String fontPath, int targetFPS) 
	{
		System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
		
		init(); // Gets settings from file then sets vars
		
		RenderHelper.createDisplay(WIDTH, HEIGHT, VSYNC, "ssebs Engine");
		AudioHelper.createAL();
		
		// Load fonts
		try
		{
			InputStream is = ResourceLoader.getResourceAsStream(fontPath);
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
		
		_targetFPS = targetFPS;
		
		
	}// End constructor
	
	protected void gameLoop()
	{
		// Main game loop
		while (!Display.isCloseRequested())
		{
			long delta = (long) LogicHelper.getDelta();
			render(delta);
			input(delta);
			gameplayLogic(delta);

			RenderHelper.updateDisplay(60);
		}

		RenderHelper.closeDisplay();
		AudioHelper.closeAL();
	}
	
	protected abstract void render(long delta);
	
	protected abstract void input(long delta);
	
	protected abstract void gameplayLogic(long delta);
	
	
	protected void init()
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

		_fontSize = 15;
		
	}//End init method

}
