package game.data;

import com.owlengine.resources.Assets;

import tools.Const;
import game.data.objects.Obj;

public class BackgroundWall extends Obj {

	public BackgroundWall() {
		super(Const.OBJ_BACKGROUND_WALL);
		setTex(Assets.getTex(Const.TEX_BACKGROUND_WALL));
	}
}
