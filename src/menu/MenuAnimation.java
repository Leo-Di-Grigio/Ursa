package menu;

import tools.Const;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.owlengine.resources.Assets;

final class MenuAnimation {

	private static final float ANGLE_ROTATE = 1.0f;
	private static final float ANIM_STEP = 8.0f;
	private static final float SCALE_DISK = 1.4f;
	private static final float SCALE_BEAR = 0.95f;
	private static final float SCALE_FLAME = 0.9f;

	// 124x82
	private Sprite bear;
	private Sprite disk;
	private Sprite [] flame;
	
	private float angle;
	private int timer;
	
	public MenuAnimation() {
		Assets.loadTex(Const.TEX_MENU_ANIMATION_BEAR);
		Assets.loadTex(Const.TEX_MENU_ANIMATION_BACK_CIRCLE);
		Assets.loadTex(Const.TEX_MENU_ANIMATION_FLAMES_ATLAS);
		
		//
		disk = new Sprite(Assets.getTex(Const.TEX_MENU_ANIMATION_BACK_CIRCLE));
		disk.setScale(SCALE_DISK);
		
		disk.translate( Gdx.graphics.getWidth()/2 - disk.getWidth()/2, 
						Gdx.graphics.getHeight()/2 - disk.getHeight()/2 + 150);
		disk.setOrigin(disk.getWidth()/2, disk.getHeight()/2);
		
		//
		bear = new Sprite(Assets.getTex(Const.TEX_MENU_ANIMATION_BEAR));
		bear.setScale(SCALE_BEAR);
		
		bear.translate( Gdx.graphics.getWidth()/2 - bear.getWidth()/2,
						Gdx.graphics.getHeight()/2 - bear.getHeight()/2 + 150);
		bear.setOrigin(bear.getWidth()/2, bear.getHeight()/2);
		
		//
		Texture tex = Assets.getTex(Const.TEX_MENU_ANIMATION_FLAMES_ATLAS);
		flame = new Sprite[5];
		
		for(int i = 0; i < flame.length; ++i){
			// internal ring
			flame[i] = new Sprite(new TextureRegion(tex, i*124, 0, 124, 82));
			flame[i].setScale(SCALE_FLAME);
			flame[i].setOrigin(flame[i].getWidth()/2, flame[i].getHeight()/2 - 120);
			
			flame[i].translate( Gdx.graphics.getWidth()/2 - bear.getWidth()/2 - 15,
								Gdx.graphics.getHeight()/2 - bear.getHeight()/2 + 280);
		}
	}
	
	public void draw(SpriteBatch batch, final float delta){	
		this.timer++;
		this.angle = ANGLE_ROTATE*delta;
		
		disk.rotate( 10.0f * delta);
		bear.rotate(-10.0f * delta);
		
		disk.draw(batch);
		bear.draw(batch);
		
		int frame = (int)(timer/ANIM_STEP);
		if(frame == flame.length){
			frame = 0;
			timer = 0;
		}
		
		for(int i = 0; i < 6; ++i){
			flame[frame].rotate(60.0f + angle);
			flame[frame].draw(batch);
		}
	}
}
