package game.data.location.loader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import com.owlengine.tools.Log;

import tools.Const;
import game.data.location.Location;
import game.data.objects.Obj;
import game.data.sql.Database;
import game.data.sql.properties.ObjectProperties;

final class LocationWriter {

	public static void writeLocation(Location loc, HashMap<Integer, Obj> objects, String filePath) {
		File file = new File(Const.MAPS_FOLDER + "/" + filePath);
		
		Log.debug("\n-----------------------------");
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			
			PrintWriter out = new PrintWriter(file);
			
			writeObjects(out, objects);
			
			out.flush();
			out.close();
			
			Log.debug("Done. Location saved at \"" + file.getAbsolutePath() + "\"");
		}
		catch (IOException e) {
			Log.err("LocationWriter.writeLocation(): IOException");
		}
	    Log.debug("\n-----------------------------\n");
	}

	private static void writeObjects(PrintWriter out, HashMap<Integer, Obj> objects) {
		ObjectProperties property = null;
		
		for(Obj obj: objects.values()){
			property = Database.getObject(obj.type);
			
			if(property.editorWrite){
				out.print("" + obj.type + ";" + (int)(obj.x()) + ";" + (int)(obj.y()) + ";");
			}
		}
	}
}