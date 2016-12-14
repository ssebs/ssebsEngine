package tester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import engine.AudioHelper;
import engine.Game;
import engine.RenderHelper;
import engine.URLDataHelper;

public class Tester extends Game
{
	private static boolean  NOCOLLIDE;

	private  Texture txtr_background, txtr_face, txtr_trackpad;

	private float _x;
	private float _y;

	//TODO: Add timer to logger

	/* Architechture of Game/Inherited class:
	 * 	Constructor
	 * 		init()
	 * 	loop()		<-- called from init()
	 * 		render()
	 * 		input()
	 * 		gameplayLogic()
	 * 	
	 */
	
	public Tester()
	{
		super("res/fonts/UbuntuMono-R.ttf");
		
		txtr_background = RenderHelper.loadTexture(txtr_background, "res/engine/BackgroundTexture.png");
		txtr_face = RenderHelper.loadTexture(txtr_face, "res/face256.png");
		txtr_trackpad = RenderHelper.loadTexture(txtr_trackpad, "res/MoveChar.png");

		AudioHelper.playSound("Whoosh.wav");
		super.gameLoop();
		
	}// End constr

	@Override
	protected void render(long delta)
	{
		// Background
		RenderHelper.texRender(txtr_background, 0, 0);
		RenderHelper.texRender(txtr_trackpad, 0, HEIGHT - txtr_trackpad.getImageHeight());
		
		// Foreground
		RenderHelper.texRender(txtr_face, (int)_x, (int)_y);

		// Logging
		_logger.log("Left: " + _x + " Right: " + (txtr_face.getImageWidth() + _x));
		_logger.log("MOUSE = X: " + Mouse.getX() + ", Y: " + Mouse.getY(), _fontSize);
		_logger.log("FPS:" + RenderHelper.getFPS(), _fontSize * 2, new Color(0x41AFFF));

	}// End render
	
	@Override
	protected void input(long delta)
	{
		final float speed = 1.25f;

		// Keyboard
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

		// Mouse
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

	}// End input

	@Override
	protected void gameplayLogic(long delta)
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

	@Override
	protected void init()
	{
		super.init(); 	// Loads settings from file
		
		_x = WIDTH / 2 - 128;
		_y = HEIGHT / 2 - 32;
		_fontSize = 15;
		NOCOLLIDE = false;
	}// End init
	
	public static void main(String[] args)
	{
		new Tester();
	}

}// End class
