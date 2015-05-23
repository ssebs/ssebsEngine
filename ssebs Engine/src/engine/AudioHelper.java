package engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

public class AudioHelper
{
	public static void createAL()
	{
		try
		{
			AL.create();
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}

	public static void closeAL()
	{
		AL.destroy();
	}

	public static void playSound(final String fileName)
	{
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				SoundFile.fileName = fileName;
				SoundFile.execute();
			}
		});
		thread.start();
	}
}
