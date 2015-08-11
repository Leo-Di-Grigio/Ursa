package game.data;

import java.util.Set;

import tools.Const;
import tools.Version;
import main.Config;
import game.data.location.GameContactListener;
import game.data.location.Location;
import game.data.location.loader.LocationMng;
import game.data.objects.Obj;
import game.data.objects.ObjBuilder;
import game.data.objects.ObjData;
import game.data.objects.creatures.Player;
import game.data.sql.Database;
import game.data.sql.properties.ObjectProperties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import cycle.GameAPI;

public final class GameData implements Disposable {
	
	// GameObjets
	private Location loc;
	
	// Objects
	private Player player;
	
	// Physics
	private World world;

	// modes
	private boolean editMode;
	private ObjectProperties editObjProto = null;
	
	// mouse selecting
	private boolean editSelect;
	private boolean editSelectMapPart;
	private Vector3 select1;
	private Obj selectedObj;
	
	//
	private Box2DDebugRenderer debugRenderer;
	private Vector3 cameraResoultion;
	private ShapeRenderer shapeBatch;
	
	public GameData() {
		shapeBatch = new ShapeRenderer();
		cameraResoultion = new Vector3();
		select1 = new Vector3();
		
		//
		Box2D.init();
		new ObjBuilder();
		
		//
		world = new World(new Vector2(Const.PHYSICS_GRAVITY_X, Const.PHYSICS_GRAVITY_Y), true);
		world.setContactListener(new GameContactListener(this));
		
		if(Config.debug()){
			debugRenderer = new Box2DDebugRenderer();
		}
		
		//
		loc = LocationMng.loadLocation(world, Const.MAPS_FOLDER + "/" + Config.map());
		this.player = (Player)loc.addObj(world, Const.OBJ_PLAYER, 10, Database.getObject(Const.OBJ_PLAYER).sizey);
		GameAPI.camera().position.set(player.x(), player.y(), 0.0f);
	}
	
	public void update(OrthographicCamera camera) {
		if(!editMode){
			world.step(Gdx.graphics.getDeltaTime(), 8, 3);
			loc.update();
			
			float deltax = player.x() - camera.position.x;
			float deltay = player.y() - camera.position.y;
			camera.translate(deltax, deltay);
			loc.scroll(deltax, deltay);
		}
		else{
			shapeBatch.setProjectionMatrix(GameAPI.camera().combined);
			shapeBatch.begin(ShapeType.Line);
			drawEditGrid();
			shapeBatch.end();
		}
		
		if(Config.debug() && debugRenderer != null){
			debugRenderer.render(world, GameAPI.camera().combined);
		}
	}

	public void draw(SpriteBatch batch) {
		if(!editMode){
			loc.drawBackGround(batch);
		}
		
		loc.draw(batch);
	}
	
	public void drawHUD(BitmapFont font, SpriteBatch batch) {
		if(editMode){
			font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
		else{
			font.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		}
		
		font.draw(batch, Version.TITLE_VERSION, 5, 15);
		font.draw(batch, "Game", 5, 30);
		font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 5, 45);
		font.draw(batch, "Weapon: "+ player.getWeaponMain(), 5, 60);
	}
	
	public void postUpdate() {
		if(editMode){
			drawEdit();
		}
	}
	
	private void drawEdit() {
		shapeBatch.setProjectionMatrix(GameAPI.camera().combined);
		shapeBatch.begin(ShapeType.Line);

		if(editMode && editObjProto != null){
			drawEditObj();
		}
		else if(editSelectMapPart){
			drawEditSelectedMapPart();
		}
	
		shapeBatch.end();
	}

	private void drawEditGrid() {
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
	
	private void drawEditObj() {
		Gdx.gl.glLineWidth(1.0f);
		shapeBatch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		shapeBatch.rect((int)select1.x, (int)select1.y, editObjProto.sizex, editObjProto.sizey);
	}
	
	private void drawEditSelectedMapPart() {
		shapeBatch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		shapeBatch.rect(select1.x, select1.y, selectedObj.sizeX(), selectedObj.sizeY());
	}
	
	@Override
	public void dispose() {
		world.dispose();
		
		if(Config.debug() && debugRenderer != null){
			debugRenderer.dispose();
		}
	}
	
	public void interact(ObjData dataA, ObjData dataB, boolean value) {
		if(dataA.type == Const.OBJ_BULLET || dataB.type == Const.OBJ_BULLET){
			loc.bulletInteract(dataA, dataB, value); 
		}
		else if(dataA.type == Const.OBJ_STAIRS || dataB.type == Const.OBJ_STAIRS){
			loc.npcInteractStair(dataA, dataB, value);
		}
		else if(dataA.type == Const.OBJ_WATER || dataB.type == Const.OBJ_WATER){
			loc.npcInteractWater(dataA, dataB, value);
		}
		else if(dataA.type == Const.OBJ_BLOCK || dataB.type == Const.OBJ_BLOCK){
			loc.npcInteractBlock(dataA, dataB, value);
		}
		else if(dataA.type == Const.OBJ_BLOCK_CUBE || dataB.type == Const.OBJ_BLOCK_CUBE){
			loc.npcInteractBlock(dataA, dataB, value);
		}
	}
	
	// Player movement
	public void playerMoveUp() {
		if(editMode){
			GameAPI.camera().translate(0.0f, 1.0f);
		}
		else{
			player.moveUp();
		}
	}

	public void playerMoveDown() {
		if(editMode){
			GameAPI.camera().translate(0.0f, -1.0f);
		}
		else{
			player.moveDown();
		}
	}
	
	public void playerMoveLeft() {
		if(editMode){
			GameAPI.camera().translate(-1.0f, 0.0f);
		}
		else{
			player.moveLeft();
			player.setDirect(Const.ANIMATION_DIRECT_LEFT);
		}
	}
	
	public void playerMoveRight() {
		if(editMode){
			GameAPI.camera().translate(1.0f, 0.0f);
		}
		else{
			player.moveRight();
			player.setDirect(Const.ANIMATION_DIRECT_RIGHT);
		}
	}

	public void playerStopX() {
		player.moveStopX();
	}

	public void playerStopY() {
		player.moveStopY();
	}
	
	public void playerAttack() {
		loc.attack(player.id);
	}

	public void setEditMode(boolean value) {
		this.editMode = value;
		
		if(this.editMode){
			Gdx.gl.glClearColor(0.18431f, 0.30588f, 0.50588f, 1.0f); // blueprint color
			world.setGravity(world.getGravity().set(0.0f, 0.0f));
		}
		else{
			editSelectMapPart = false;
			editSelect = false;
			editObjProto = null;
			selectedObj = null;
			GameAPI.camera().zoom = 0.13f;
			GameAPI.camera().position.set(player.x(), player.y(), 0.0f);
			loc.setLocColor();
			world.setGravity(world.getGravity().set(Const.PHYSICS_GRAVITY_X, Const.PHYSICS_GRAVITY_Y));
		}
	}

	public void setEditObject(int typeId) {
		if(typeId == Const.OBJ_NULL){
			editObjProto = null;
		}
		else{
			editObjProto = Database.getObject(typeId);
		}
	}
	
	public void cameraZoom(int data) {
		if(this.editMode){
			final float zoom = GameAPI.camera().zoom + 0.01f*data;
			
			if(zoom > 0.01f && zoom < 10.0f){
				GameAPI.camera().zoom = zoom;
			}
		}
	}

	public void sceneEscape() {
		if(editMode && editObjProto != null){
			editSelect = false;
		}
	}
	
	public void sceneDel() {
		if(editMode && editSelectMapPart && selectedObj != null){
			loc.removeObj(world, selectedObj);
			editSelectMapPart = false;
			editSelect = false;
		}
	}

	public void sceneMouseMove() {
		if(editMode && editObjProto != null){
			pickPixel(select1);
		}
	}

	public void sceneLeftClick() {
		if(editMode){
			if(editObjProto != null && !editSelect){
				placeObj();
			}
			else{
				pickPixel(select1);
				
				Set<Obj> objects = loc.searchObj(select1.x, select1.y);
				
				if(objects.size() > 0){
					this.editSelectMapPart = true;

					for(Obj obj: objects){
						// pick last (upper) layer
						selectedObj = obj; 
					}
					
					select1.set(selectedObj.x() - selectedObj.sizeX()/2, selectedObj.y() -selectedObj.sizeY()/2, 0.0f);
				}
				else{
					this.editSelectMapPart = false;
				}
			}
		}
	}
	
	private void placeObj() {
		pickPixel(select1);
		
		final int posx = (int)select1.x;
		final int posy = (int)select1.y;
		
		final int sizex = editObjProto.sizex;
		final int sizey = editObjProto.sizey;
		
		loc.addObj(world, editObjProto.typeId, posx + sizex/2, posy + sizey/2);
	}

	public void sceneRightClick() {
		if(editMode){
			editObjProto = null;
			editSelect = false;
			editSelectMapPart = false;
		}
	}

	private void pickPixel(Vector3 vector){
        vector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        GameAPI.camera().unproject(vector);
	}

	public void setLocColor() {
		loc.setLocColor();
	}

	public Player getPlayer() {
		return player;
	}

	public void buildPlan() {
		loc.buildPlan();
	}

	public void saveLocation() {
		loc.saveLocation();
	}

	public void playerStand() {
		player.stand();
	}
}