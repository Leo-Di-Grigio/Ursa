package game.data.objects;

import game.data.sql.Database;
import tools.Const;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public final class ObjBuilder {

	private static BodyDef dynamicDef;
	private static BodyDef staticDef;
	private static BodyDef kinematicDef;
	
	public ObjBuilder() {
		dynamicDef = new BodyDef();
		dynamicDef.type = BodyType.DynamicBody;
		dynamicDef.fixedRotation = true;
		
		kinematicDef = new BodyDef();
		kinematicDef.type = BodyType.KinematicBody;
		kinematicDef.fixedRotation = true;
		
		staticDef = new BodyDef();
		staticDef.type = BodyType.StaticBody;
		staticDef.fixedRotation = true;
	}
	
	public static void buildObj(World world, Obj object, float x, float y, float sizex, float sizey){
		BodyDef def = null;
		
		switch (Database.getObject(object.type).bodyType) {
			case Const.BODY_TYPE_DYNAMIC:
				def = dynamicDef;
				break;
			
			case Const.BODY_TYPE_KINEMATIC:
				def = kinematicDef;
				break;
				
			case Const.BODY_TYPE_STATIC:
			default:
				def = staticDef;
				break;
		}
		
		def.position.set(x, y);
		object.setBody(world.createBody(def), sizex, sizey);
	}
}
