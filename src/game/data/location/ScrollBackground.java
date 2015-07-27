package game.data.location;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.owlengine.resources.Assets;

public final class ScrollBackground {

	private Texture tex;
	private float scale;
	
	private float speedX;
	private float speedY;
	
	private float scrollX;
	private float scrollY;
	
	public ScrollBackground(final String path, final float speedX, final float speedY, final float scale){
		this.tex = Assets.getTex(path);
		this.speedX = speedX;
		this.speedY = speedY;
		this.scale = scale;
	}
	
	public void scroll(final float deltax, final float deltay) {
		this.scrollX += deltax*speedX;
		this.scrollY += deltay*speedY;
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(tex, scrollX, scrollY, tex.getWidth()*scale, tex.getHeight()*scale);
	}
}