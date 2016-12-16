package engine;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class Logger 
{
	private TrueTypeFont _font;
	private boolean _timerDone;
	/**
	 * Logger Constructor, this class will display strings on the display
	 * @param awtFont
	 */
	public Logger(Font awtFont)
	{		
		_font = new TrueTypeFont(awtFont, true);
		_timerDone = true;
	}
	
	public void timeLog(String str, long millis)
	{
		Thread thd = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				long timer = 0;
				
				while(timer <= millis)
				{
					_timerDone = false;
					try
					{
						Thread.sleep(2);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					timer++;
				}
				_timerDone = true;
			}
		});
		thd.start();
		
		while(!_timerDone)
		{
			log(str, 15*5, Color.red);
		}
		
	}
	
		
	public void log(String logTxt)
	{
		_font.drawString(10, 10, logTxt, Color.black);
	}
	
	public void log(String logTxt, Color c)
	{
		_font.drawString(10, 10, logTxt, c);
	}
	
	public void log(String logTxt, int yOff)
	{
		_font.drawString(10, 10+yOff, logTxt, Color.black);
	}
	
	public void log(String logTxt, int yOff, Color c)
	{
		_font.drawString(10, 10+yOff, logTxt, c);
	}
	
	public void log(String logTxt, int x, int y)
	{
		_font.drawString(x, y, logTxt, Color.black);
	}
	
	public void log(String logTxt, int x, int y, Color c)
	{
		_font.drawString(x, y, logTxt, c);
	}

	
}
