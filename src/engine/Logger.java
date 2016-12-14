package engine;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class Logger 
{
	TrueTypeFont _font;
	
	/**
	 * Logger Constructor, this class will display strings on the display
	 * @param awtFont
	 */
	public Logger(Font awtFont)
	{		
		_font = new TrueTypeFont(awtFont, true);
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
