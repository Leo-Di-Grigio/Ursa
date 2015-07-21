package game.data.location;

import game.data.objects.Obj;
import game.data.objects.ObjBuilder;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public final class Location {

	// Data
	private HashMap<Integer, Obj> objects;
	
	public Location() {
		objects = new HashMap<Integer, Obj>();
	}
	
	public void draw(SpriteBatch batch){
		for(Obj obj: objects.values()){
			obj.draw(batch);
		}
	}

	public void addObj(World world, Obj object, float x, float y, float sizex, float sizey) {
		ObjBuilder.buildObj(world, object, x, y, sizex, sizey);
		objects.put(object.id, object);
	}
}
