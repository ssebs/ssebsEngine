package tester;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
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
	
	private static Texture txtr_background, txtr_dargon, txtr_trackpad;

	private static float _x;
	private static float _y;
	private static int _fontSize;
	
	private static Logger _logger;
	
	public static void main(String[] args)
	{
		init();	// Gets settings from file then sets vars
		
		RenderHelper.createDisplay(WIDTH, HEIGHT, "ssebs Engine");
		AudioHelper.createAL();
		
		AudioHelper.playSound("Whoosh.wav");
		
		//Load in the textures
		txtr_background = RenderHelper.loadTexture(txtr_background, "res/BackgroundTexture.png");
		txtr_dargon = RenderHelper.loadTexture(txtr_dargon, "res/Dargon.png");
		txtr_trackpad = RenderHelper.loadTexture(txtr_trackpad, "res/MoveChar.png");
		
		
		try 
		{
			InputStream is = ResourceLoader.getResourceAsStream("res/fonts/UbuntuMono-R.ttf");
			Font fnt = Font.createFont(Font.TRUETYPE_FONT, is);
			fnt = fnt.deriveFont((float)_fontSize);
			_logger = new Logger(fnt);
			System.out.println("Ubuntu");			
		} catch (Exception e) 
		{
			e.printStackTrace();
			_logger = new Logger(new Font("Comic Sans MS", Font.BOLD, _fontSize));
			System.out.println("Comic");
		}
		
		while (!Display.isCloseRequested())
		{
			long delta = (long) LogicHelper.getDelta();
			gameplay(delta);

			RenderHelper.updateDisplay(60);
		}

		RenderHelper.closeDisplay();
		AudioHelper.closeAL();
	}

	private static void gameplay(long delta)
	{
		//Vars
		final float speed = 1.25f;
		
		//Render
		RenderHelper.texRender(txtr_background, 0, 0);
		RenderHelper.texRender(txtr_trackpad, 0, HEIGHT-txtr_trackpad.getImageHeight());

		RenderHelper.texRender(txtr_dargon, _x, _y);
						
		_logger.log("Left: " + _x +  " Right: " + (txtr_dargon.getImageWidth() + _x));
		_logger.log("MOUSE = X: " + Mouse.getX() + ", Y: " + Mouse.getY(), _fontSize);

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
			_logger.log(URLDataHelper.getStringDataAsString("https://raw.githubusercontent.com/ssebs/ssebsEngine/master/ssebs%20Engine/Misc/serverTestFile.txt"), _fontSize*2);
		
		
		
		if (Mouse.isButtonDown(0)) 
		{
			if (Mouse.getX() <= txtr_trackpad.getImageWidth() && Mouse.getY() <= txtr_trackpad.getImageHeight())
			{
				_x += SENSITIVITY * Mouse.getDX();
				_y += SENSITIVITY * MOUSEINVERT * Mouse.getDY(); // Invert y
			}
		}
		
		if (Mouse.isButtonDown(2))
		{
			_x +=  Mouse.getDX();
			_y += MOUSEINVERT * Mouse.getDY(); // Invert y
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
			
			if (s.startsWith("MOUSEINVERT"))
			{
				if(s.endsWith("FALSE"))
					MOUSEINVERT = -1;
				else if (s.endsWith("TRUE"))
					MOUSEINVERT = 1;
				else 
					MOUSEINVERT = -1; System.out.println(s);
			}
		}
		settings = null;
		
		_x = WIDTH / 2 - 128;
		_y = HEIGHT / 2 - 32;
		_fontSize = 15;
	}
	
	
}// End class
