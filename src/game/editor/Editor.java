package game.editor;

import java.util.Set;

import tools.Const;
import game.data.GameData;
import game.data.location.Location;
import game.data.objects.Obj;
import game.data.sql.Database;
import game.data.sql.properties.ObjectProperties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import cycle.GameAPI;

public final class Editor {

	// Render
	private static Box2DDebugRenderer debugRenderer;
	private static ShapeRenderer shapeBatch;
	private static Vector3 cameraResoultion;
	
	// Render layers
	private static boolean editorLayerGraphics;
	private static boolean editorLayerPhysics;
	
	//
	private static ObjectProperties editObjProto = null;
	
	// mouse selecting
	private static boolean editSelect;
	private static boolean editSelectMapPart;
	
	// tmp data
	private static Obj selectedObj;
	private static Vector3 select1;
	
	public Editor() {
		debugRenderer = new Box2DDebugRenderer();
		shapeBatch = new ShapeRenderer();
		
		// temp
		cameraResoultion = new Vector3();
		select1 = new Vector3();
	}
	
	public static void update(GameData gameData, OrthographicCamera camera){
		shapeBatch.setProjectionMatrix(GameAPI.camera().combined);
		
		// grid layer
		shapeBatch.begin(ShapeType.Line);
		drawEditGrid();
		shapeBatch.end();
	}

	public static void postUpdate() {
		shapeBatch.setProjectionMatrix(GameAPI.camera().combined);
		shapeBatch.begin(ShapeType.Line);

		if(editObjProto != null){
			drawEditObj();
		}
		else if(editSelectMapPart){
			drawEditSelectedMapPart();
		}
	
		shapeBatch.end();
	}
	
	private static void drawEditObj() {
		Gdx.gl.glLineWidth(1.0f);
		shapeBatch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		shapeBatch.rect((int)select1.x, (int)select1.y, editObjProto.sizex, editObjProto.sizey);
	}
	
	private static void drawEditSelectedMapPart() {
		shapeBatch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		shapeBatch.rect(select1.x, select1.y, selectedObj.sizeX(), selectedObj.sizeY());
	}
	
	private static void drawEditGrid() {
		Gdx.gl.glLineWidth(1.0f);
		shapeBatch.setColor(0.5f, 0.5f, 0.5f, 1.0f);
		
		cameraResoultion.set(0.0f, 0.0f, 0.0f);
		GameAPI.camera().unproject(cameraResoultion);
		final int minx = ((int)(cameraResoultion.x)/Const.GRID_STEP * Const.GRID_STEP) - Const.GRID_STEP;
		final int miny = ((int)(cameraResoultion.y)/Const.GRID_STEP * Const.GRID_STEP) + Const.GRID_STEP;

		cameraResoultion.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0.0f);
		GameAPI.camera().unproject(cameraResoultion);
		final int maxx = ((int)(cameraResoultion.x)/Const.GRID_STEP * Const.GRID_STEP) + Const.GRID_STEP;
		final int maxy = ((int)(cameraResoultion.y)/Const.GRID_STEP * Const.GRID_STEP) - Const.GRID_STEP;
		
		for(int i = minx, j = miny; i < maxx; i += Const.GRID_STEP, j -= Const.GRID_STEP){
			shapeBatch.line(i, miny, i, maxy);
			shapeBatch.line(minx, j, maxx, j);
		}
	}

	public static void draw(GameData gameData, Location loc, SpriteBatch batch){
		if(editorLayerGraphics){
			loc.draw(batch);
		}
	}

	public static void drawPhysicDebug(World world) {
		if(editorLayerPhysics){
			debugRenderer.render(world, GameAPI.camera().combined);
		}
	}

	public static void dispose() {
		debugRenderer.dispose();
	}

	public static void setEditObject(int typeId) {
		if(typeId == Const.OBJ_NULL){
			editObjProto = null;
		}
		else{
			editObjProto = Database.getObject(typeId);
		}
	}
	
	public static void begin(World world) {
		Gdx.gl.glClearColor(0.18431f, 0.30588f, 0.50588f, 1.0f); // blueprint color
		world.setGravity(world.getGravity().set(0.0f, 0.0f));
	}
	
	public static void reset() {
		editSelectMapPart = false;
		editSelect = false;
		editObjProto = null;
		selectedObj = null;
	}
	
	public static void setEditorShowModeGraphics(boolean value) {
		editorLayerGraphics = value;
	}
	
	public static void setEditorShowModePhysics(boolean value) {
		editorLayerPhysics = value;
	}

	private static void placeObj(Location loc, World world) {
		pickPixel(select1);
		
		final int posx = (int)select1.x;
		final int posy = (int)select1.y;
		
		final int sizex = editObjProto.sizex;
		final int sizey = editObjProto.sizey;
		
		loc.addObj(world, editObjProto.typeId, posx + sizex/2, posy + sizey/2);
	}

	private static void pickPixel(Vector3 vector){
        vector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        GameAPI.camera().unproject(vector);
	}
	
	// Events
	public static void eventLeftClick(Location loc, World world) {
		if(editObjProto != null && !editSelect){
			placeObj(loc, world);
		}
		else{
			pickPixel(select1);
			
			Set<Obj> objects = loc.searchObj(select1.x, select1.y);
			
			if(objects.size() > 0){
				editSelectMapPart = true;

				for(Obj obj: objects){
					// pick last (upper) layer
					selectedObj = obj; 
				}
				
				select1.set(selectedObj.x() - selectedObj.sizeX()/2, selectedObj.y() -selectedObj.sizeY()/2, 0.0f);
			}
			else{
				editSelectMapPart = false;
			}
		}
	}
	
	public static void eventRightClick() {
		editObjProto = null;
		editSelect = false;
		editSelectMapPart = false;
	}

	public static void eventEscape() {
		if(editObjProto != null){
			editSelect = false;
		}
	}

	public static void eventMouseScroll(int data) {
		final float zoom = GameAPI.camera().zoom + 0.01f*data;
		
		if(zoom > 0.01f && zoom < 10.0f){
			GameAPI.camera().zoom = zoom;
		}
	}

	public static void eventDel(Location loc, World world) {
		if(editSelectMapPart && selectedObj != null){
			loc.removeObj(world, selectedObj);
			editSelectMapPart = false;
			editSelect = false;	
		}
	}

	public static void eventMouseMove() {
		if(editObjProto != null){
			pickPixel(select1);
		}
	}
}
