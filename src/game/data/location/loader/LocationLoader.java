package game.data.location.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import tools.Const;

import com.badlogic.gdx.physics.box2d.World;
import com.owlengine.tools.Log;

import game.data.location.Location;
import game.data.sql.Database;

final class LocationLoader {

	public static Location loadLocation(World world, String filePath) {
		Location loc = new Location(world);
		readLocationFile(world, loc, filePath);
		return loc;
	}

	private static void readLocationFile(World world, Location loc, String filePath) {
		File file = new File(filePath);
		
		if(!file.exists()){
			loadDefaultLocation(world, loc);
		}
		else{
			try {
				Scanner in = new Scanner(file);
				String data = in.nextLine();
				
				// parse
				String [] arr = data.split(";");
				
				for(int i = 0; i < arr.length; i += 3){
					int type = Integer.parseInt(arr[i]);
					int x = Integer.parseInt(arr[i + 1]);
					int y = Integer.parseInt(arr[i + 2]);
					
					loc.addObj(world, type, x, y);
				}
				
				in.close();
			}
			catch (FileNotFoundException e) {
				Log.err("Location loader.readLocationFile(): IOException - load default location data");
				loadDefaultLocation(world, loc);
			}
		}
	}
	
	private static void loadDefaultLocation(World world, Location loc){
		// load default location data
		loc.addObj(world, Const.OBJ_BLOCK, Database.getObject(Const.OBJ_BLOCK).sizex/2, -2.0f);		
	}
}
