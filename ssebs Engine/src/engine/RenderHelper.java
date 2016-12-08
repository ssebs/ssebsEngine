package engine;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class RenderHelper
{
	private static Texture loadingTexture;

	public static void createDisplay(final int WIDTH, final int HEIGHT, String title)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle(title);
			Display.setInitialBackground(0, 0, 0);
			try
			{
				Display.setIcon(new ByteBuffer[] { new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/ssebsEngineIcon16.png")), false, false, null),
						new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/ssebsEngineIcon32.png")), false, false, null) });
				
				Display.create();

				loadingTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/LoadingTexture.png"));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
//		glMatrixMode(GL_PROJECTION);
//		glLoadIdentity();
//		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
//		glMatrixMode(GL_MODELVIEW);
//		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		 GL11.glEnable(GL11.GL_TEXTURE_2D);               
         
	        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
	         
	            // enable alpha blending
	            GL11.glEnable(GL11.GL_BLEND);
	            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	         
	            GL11.glViewport(0,0,WIDTH,HEIGHT);
	        GL11.glMatrixMode(GL11.GL_MODELVIEW);
	 
	        GL11.glMatrixMode(GL11.GL_PROJECTION);
	        GL11.glLoadIdentity();
	        GL11.glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
	        GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
				
		texRender(loadingTexture, 0, 0);
		Display.update();
	}

	public static void updateDisplay(final int FPS)
	{
		Display.sync(FPS);
		Display.update();
	}

	public static void closeDisplay()
	{
		Display.destroy();
	}

	public static Texture loadTexture(Texture texture, String filename)
	{
		try
		{
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(filename));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return texture;
	}

	public static void texRender(Texture tex, int x, int y)
	{
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.40f);
		
		Color.white.bind();
		tex.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(tex.getWidth(), 0);
			glVertex2f(x + tex.getTextureWidth(), y);
			glTexCoord2f(tex.getWidth(), tex.getHeight());
			glVertex2f(x + tex.getTextureWidth(), y + tex.getTextureHeight());
			glTexCoord2f(0, tex.getHeight());
			glVertex2f(x, y + tex.getTextureHeight());
		glEnd();
		
		GL11.glDisable(GL11.GL_ALPHA_TEST);
	}

	public static void texRender(Texture tex, float x, float y)
	{
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.40f);
		
		Color.white.bind();
		tex.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(tex.getWidth(), 0);
			glVertex2f(x + tex.getTextureWidth(), y);
			glTexCoord2f(tex.getWidth(), tex.getHeight());
			glVertex2f(x + tex.getTextureWidth(), y + tex.getTextureHeight());
			glTexCoord2f(0, tex.getHeight());
			glVertex2f(x, y + tex.getTextureHeight());
		glEnd();
		
		GL11.glDisable(GL11.GL_ALPHA_TEST);
	}
}
