package game.data.objects.statics;

import com.owlengine.resources.Assets;

import tools.Const;
import game.data.objects.Obj;

public class Stairs extends Obj {

	public Stairs() {
		super(Const.OBJ_STAIRS);
		setTex(Assets.getTex(Const.TEX_STAIRS));
	}
}
