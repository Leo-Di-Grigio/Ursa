package game.data.location.loader;

import game.data.location.Location;
import game.data.objects.Obj;

import java.util.HashSet;
import java.util.TreeMap;

public final class LocationMng {

	public static Location loadLocation(String filePath){
		return LocationLoader.loadLocation(filePath);
	}
	
	public static void writeLocation(Location loc, String filePath){
		LocationWriter.writeLocation(loc, filePath);
	}
	
	public static void buildPlan(TreeMap<Integer, HashSet<Obj>> draw, String filePath) {
		LocationPlanBuilder.buildPlan(draw, filePath);
	}
}
