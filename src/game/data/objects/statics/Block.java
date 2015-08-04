package game.data.objects.statics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.data.objects.Obj;
import tools.Const;

public class Block extends Obj {

	private Sprite sprite;

	public Block() {
		super(Const.OBJ_BLOCK);
	}
	
	@Override
	public void setTex(Texture tex) {
		tex.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		sprite = new Sprite(tex, 0, 0, 154, 30);
		sprite.setSize(sizeX(), sizeY());
		sprite.translate(x() - sizeX()/2, y() - sizeY()/2);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
}