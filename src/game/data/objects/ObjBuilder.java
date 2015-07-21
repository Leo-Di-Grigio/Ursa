package game.data.objects;

import tools.Const;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public final class ObjBuilder {

	private static BodyDef dynamicDef;
	private static BodyDef staticDef;
	
	public ObjBuilder() {
		dynamicDef = new BodyDef();
		dynamicDef.type = BodyType.DynamicBody;
		dynamicDef.fixedRotation = true;
		
		staticDef = new BodyDef();
		staticDef.type = BodyType.StaticBody;
		staticDef.fixedRotation = true;
	}
	
	public static void buildObj(World world, Obj object, float x, float y, float sizex, float sizey){
		BodyDef def = null;
		
		if(object.type == Const.CREATURE_BEAR_1){
			def = dynamicDef;		
		}
		
		if(object.type == Const.OBJ_BOX){
			def = staticDef;
		}
		
		def.position.set(x, y);
		object.setBody(world.createBody(def), sizex, sizey);
	}
}
