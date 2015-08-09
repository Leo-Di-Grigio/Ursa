package game.data.location.loader;

import game.data.location.Location;
import game.data.objects.Obj;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import com.badlogic.gdx.physics.box2d.World;

public final class LocationMng {

	public static Location loadLocation(World world, String filePath){
		return LocationLoader.loadLocation(world, filePath);
	}
	
	public static void writeLocation(Location loc, HashMap<Integer, Obj> objects, String filePath){
		LocationWriter.writeLocation(loc, objects, filePath);
	}
	
	public static void buildPlan(TreeMap<Integer, HashSet<Obj>> draw, String filePath) {
		LocationPlanBuilder.buildPlan(draw, filePath);
	}
}
