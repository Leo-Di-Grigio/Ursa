package game.data.objects.statics;

import game.data.objects.Obj;
import tools.Const;

import com.owlengine.resources.Assets;

public class Block extends Obj {

	public Block() {
		super(Const.OBJ_BLOCK);
		setTex(Assets.getTex(Const.TEX_BLOCK));
	}
}