package game.data.objects;

import game.data.BackgroundWall;
import game.data.objects.creatures.NpcWoman;
import game.data.objects.creatures.Player;
import game.data.objects.statics.Block;
import game.data.objects.statics.Stairs;
import game.data.objects.statics.Water;
import game.data.sql.Database;
import game.data.sql.properties.ObjectProperties;
import tools.Const;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.owlengine.resources.Assets;

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
	
	public static Obj buildObj(World world, final int type, final float x, final float y){
		Obj object = null;
		
		switch(type) { 
		
			case Const.OBJ_PLAYER:
				object = new Player();
				break;
				
			case Const.OBJ_BLOCK:
			case Const.OBJ_BLOCK_VERTICAL:
			case Const.OBJ_BLOCK_CUBE:
				object = new Block();
				break;
			
			case Const.OBJ_BACKGROUND_WALL:
			case Const.OBJ_BACKGROUND_HOUSE1:
				object = new BackgroundWall();
				break;
				
			case Const.OBJ_STAIRS:
				object = new Stairs();
				break;
				
			case Const.OBJ_WATER:
				object = new Water();
				break;
				
			case Const.OBJ_NPC_WOMAN:
				object = new NpcWoman();
				break;
				
			case Const.OBJ_NULL:
			default:
				object = null;
				break;
		}
		
		if(object == null){
			return null;
		}
		else{
			BodyDef def = null;
			final ObjectProperties property = Database.getObject(type);
			
			switch(property.bodyType) {
			
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
			
			object.setTex(Assets.getTex(property.texture));
			object.setBody(world.createBody(def), property.sizex, property.sizey);
			
			return object;
		}
	}
}
