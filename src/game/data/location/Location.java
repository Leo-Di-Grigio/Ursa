package game.data.location;

import game.data.objects.Obj;
import game.data.objects.ObjBuilder;
import game.data.sql.Database;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.owlengine.tools.Log;

public final class Location {

	private class DrawSort implements Comparator<Integer>{

		@Override
		public int compare(Integer val1, Integer val2) {
			
			if(val1 == val2){
				return 0;
			}
			else{
				if(val1 < val2){
					return -1;
				}
				else{
					return 1;
				}
			}
		}
	}
	
	// Data
	private HashMap<Integer, Obj> objects;
	private TreeMap<Integer, HashSet<Obj>> draw;
	
	// Tmp
	private Set<Obj> set;
	
	public Location() {
		objects = new HashMap<Integer, Obj>();
		draw = new TreeMap<Integer, HashSet<Obj>>(new DrawSort());
		
		// tmp
		set = new HashSet<Obj>();
	}
	
	public Obj addObj(World world, int type, float x, float y){
		Obj object = ObjBuilder.buildObj(world, type, x, y);
		
		if(object == null){
			Log.err("Location.addObj(): Null Obj loaded typeId: " + type);
			return null;
		}
		else{
			objects.put(object.id, object);
			
			// draw sort
			final int layer = Database.getObject(object.type).drawLayer;
					
			if(!draw.containsKey(layer)){
				draw.put(layer, new HashSet<Obj>());
			}
			
			draw.get(layer).add(object);
			
			//
			return object;	
		}
	}

	public void removeObj(World world, Obj object) {
		world.destroyBody(object.getBody());
		objects.remove(object.id);
		
		final int layer = Database.getObject(object.type).drawLayer;
		draw.get(layer).remove(object);
	}
	
	public Obj getObj(final int id) {
		return objects.get(id);
	}

	public Set<Obj> searchObj(float x, float y) {
		set.clear();
		
		for(Obj obj: objects.values()){
			
			if(x >= obj.x() - obj.sizeX()/2 &&
			   x <= obj.x() + obj.sizeX()/2 &&
			   y >= obj.y() - obj.sizeY()/2 &&
			   y <= obj.y() + obj.sizeY()/2)
			{
				set.add(obj);
			}
		}
		
		return set;
	}
	
	public void draw(SpriteBatch batch){
		for(HashSet<Obj> layer: draw.values()){
			for(Obj obj: layer){
				obj.draw(batch);
			}
		}
	}
}