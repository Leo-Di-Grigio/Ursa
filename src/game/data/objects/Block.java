package game.data.objects;

import tools.Const;

import com.owlengine.resources.Assets;

public class Block extends Obj {

	public Block() {
		super(Const.OBJ_BOX);
		setTex(Assets.getTex(Const.TEX_BLOCK));
	}
}