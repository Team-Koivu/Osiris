package rina.util;

// Minecraft.
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.*;

// OpenGL.
import org.lwjgl.opengl.GL11;

// Java.
import java.util.*;

// Turok.
import rina.util.TurokRect;

/**
 * @author Rina!
 *
 * Created by Rina in 27/07/2020.
 *
 **/
public class TurokRenderGL {
	public static void color(int r, int g, int b, int a) {
		GL11.glColor4f((float) r / 255, (float) g / 255, (float) b / 255, (float) a / 255);
	}

	public static void color(int r, int g, int b) {
		GL11.glColor3f((float) r / 255, (float) g / 255, (float) b / 255);
	}

	public static void drawOutlineRect(float x, float y, float width, float height) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glLineWidth(2f);

		GL11.glBegin(GL11.GL_LINE_LOOP);
		{
			GL11.glVertex2d(width, y);
			GL11.glVertex2d(x, y);
			GL11.glVertex2d(x, height);
			GL11.glVertex2d(width, height);
		}

		GL11.glEnd();
	}

	public static void drawOutlineRect(int x, int y, int width, int height) {
		drawOutlineRect((float) x, (float) y, (float) width, (float) height);
	}

	public static void drawOutlineRect(TurokRect rect) {
		drawOutlineRect((float) rect.x, (float) rect.y, (float) (rect.x + rect.width), (float) (rect.y + rect.height));
	}

	public static void drawSolidRect(float x, float y, float width, float height) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex2d(width, y);
			GL11.glVertex2d(x, y);
			GL11.glVertex2d(x, height);
			GL11.glVertex2d(width, height);
		}

		GL11.glEnd();
	}

	public static void drawSolidRect(int x, int y, int width, int height) {
		drawSolidRect((float) x, (float) y, (float) width, (float) height);
	}

	public static void drawSolidRect(TurokRect rect) {
		drawSolidRect((float) rect.x, (float) rect.y, (float) (rect.x + rect.width), (float) (rect.y + rect.height));
	}

	public static void prepareToRenderString() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
	}

	public static void releaseRenderString() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public static void fixScreen(float scaled_width, float scaled_height) {
		GL11.glPushMatrix();
		GL11.glTranslated(scaled_width, scaled_height, 0);
		GL11.glScaled(0.5, 0.5, 0.5);
		GL11.glPopMatrix();
	}

	public static void init2D() {
		GL11.glPushMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);

		GlStateManager.enableBlend();

		GL11.glPopMatrix();
	}

	public static void release2D() {
		GlStateManager.enableCull();
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.enableDepth();
	}
}