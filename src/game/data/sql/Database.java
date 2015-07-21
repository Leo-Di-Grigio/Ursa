package game.data.sql;

import game.data.sql.properties.ObjectProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import tools.Const;

import com.owlengine.tools.Log;

public final class Database {

	private static Connection connection;
	
	private static HashMap<Integer, ObjectProperties> objects;
	
	public Database() {
		connect();
		
		// local
		loadObjects();
		
		// close
		disconnect();
	}

	// Get
	public static ObjectProperties getObject(final int id) {
		return objects.get(id);
	}
	
	// Load
	private static void loadObjects() {
		objects = new HashMap<Integer, ObjectProperties>();
		
		try {
			Statement state = connection.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM OBJECTS;");
			
			while(result.next()) {
				int id = result.getInt("id");
				String title = result.getString("title");
				int bodyType = Const.getBodyType(result.getString("body_type"));
				boolean interact = result.getBoolean("interact");
				float dencity = result.getFloat("dencity");
				float friction = result.getFloat("friction");
				float mass = result.getFloat("mass");
				
				//
				ObjectProperties property = new ObjectProperties(id, title, bodyType, interact, dencity, friction, mass);
				objects.put(property.id, property);
			}
			
			state.close();
			Log.debug("SQLite: table \"data/database/objects\" successful readed");
		}
		catch (SQLException e) {
			Log.err("SQLite error on load (DB:Locations)");
			e.printStackTrace();
		}
	}

	// SQL
	private void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			
			try {
				String classpath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
				
				if(!classpath.startsWith(".")){
					classpath = classpath.substring(0, classpath.length() - 4); // remove /bin in classpath
				}
				
				String path = "jdbc:sqlite:" + classpath + "data/gamedata.db";
				connection = DriverManager.getConnection(path);
				connection.setAutoCommit(false);
				
				Log.debug("SQLite connection succesful");
			}
			catch (SQLException e) {
				Log.err("SQLite connection error");
			}
		}
		catch (ClassNotFoundException e) {
			Log.err("SQLite driver error");
		}
	}
	
	private void disconnect() {
		try {
			connection.close();
			Log.debug("SQLite connection closed normaly");
		} 
		catch (SQLException e) {
			Log.err("SQLite connection close error");
		}
	}
}
