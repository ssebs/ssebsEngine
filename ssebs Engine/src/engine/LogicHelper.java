package engine;

import org.lwjgl.Sys;

public class LogicHelper
{
	public static long _lastFrame;
	
	public static long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * @return Delta time between updates(redraw calls)
	 */
	public static double getDelta()
	{
		long currentTime = getTime();
		double delta = (double) currentTime - (double) _lastFrame;
		_lastFrame = getTime();
		return delta;
	}

	public static void crash()
	{
		System.err.println("ERROR: GAME CRASHED");
		RenderHelper.closeDisplay();
		System.exit(1);
	}
}
