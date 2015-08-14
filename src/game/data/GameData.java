package game.data;

import tools.Const;
import tools.Version;
import main.Config;
import game.data.location.GameContactListener;
import game.data.location.Location;
import game.data.location.loader.LocationMng;
import game.data.objects.ObjBuilder;
import game.data.objects.ObjData;
import game.data.objects.creatures.Player;
import game.data.sql.Database;
import game.editor.Editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import cycle.GameAPI;

public final class GameData implements Disposable {
	
	// GameObjets
	private Location loc;
	
	// Physics
	private World world;

	// modes
	private boolean editMode;
	
	public GameData() {
		//
		Box2D.init();
		new ObjBuilder();
		
		//
		world = new World(new Vector2(Const.PHYSICS_GRAVITY_X, Const.PHYSICS_GRAVITY_Y), true);
		world.setContactListener(new GameContactListener(this));

		//
		loc = LocationMng.loadLocation(world, Const.MAPS_FOLDER + "/" + Config.map());
	}
	
	public PlayerHandler buildPlayerHandler(){
		Player player = (Player)loc.addObj(world, Const.OBJ_PLAYER, 10, Database.getObject(Const.OBJ_PLAYER).sizey);
		GameAPI.camera().position.set(player.x(), player.y(), 0.0f);
		
		return new PlayerHandler(this, loc, player);
	}
	
	public void update(OrthographicCamera camera, PlayerHandler player) {
		if(editMode){
			Editor.update(this, camera);
			Editor.drawPhysicDebug(world);
		}
		else{
			world.step(Gdx.graphics.getDeltaTime(), 8, 3);
			loc.update(world);
			
			final float deltax = player.x() - camera.position.x;
			final float deltay = player.y() - camera.position.y;
			camera.translate(deltax, deltay);
			loc.scroll(deltax, deltay);
		}
	}

	public void draw(SpriteBatch batch) {
		if(editMode){
			Editor.draw(this, loc, batch);
		}
		else{
			loc.drawBackGround(batch);
			loc.draw(batch);
		}
	}
	
	public void drawHUD(BitmapFont font, SpriteBatch batch, PlayerHandler player) {
		if(editMode){
			font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
		else{
			font.setColor(0.0f, 0.0f, 0.0f, 1.0f);
		}
		
		font.draw(batch, Version.TITLE_VERSION, 5, 15);
		font.draw(batch, "Game", 5, 30);
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 5, 45);
		font.draw(batch, "Weapon: "+ player.getWeaponMain(), 5, 60);
	}
	
	public void postUpdate() {
		if(editMode){
			Editor.postUpdate();
		}
	}
	
	public void collision(ObjData dataA, ObjData dataB, boolean value) {
		if(dataA.type == Const.OBJ_BULLET || dataB.type == Const.OBJ_BULLET){
			loc.bulletCollision(dataA, dataB, value); 
		}
		else if(dataA.type == Const.OBJ_STAIRS || dataB.type == Const.OBJ_STAIRS){
			loc.npcCollisionStair(dataA, dataB, value);
		}
		else if(dataA.type == Const.OBJ_WATER || dataB.type == Const.OBJ_WATER){
			loc.npcCollisionWater(dataA, dataB, value);
		}
		else if(dataA.type == Const.OBJ_BLOCK || dataB.type == Const.OBJ_BLOCK){
			loc.npcCollisionBlock(dataA, dataB, value);
		}
		else if(dataA.type == Const.OBJ_BLOCK_CUBE || dataB.type == Const.OBJ_BLOCK_CUBE){
			loc.npcCollisionBlock(dataA, dataB, value);
		}
	}
	
	// Modes
	public boolean editMode() {
		return editMode;
	}
	
	public void setEditMode(boolean value, PlayerHandler player) {
		editMode = value;
		
		if(editMode){
			Editor.begin(world);
		}
		else{
			Editor.reset();
			setGameMode(player);
		}
	}
	
	public void setGameMode(PlayerHandler player) {
		GameAPI.camera().zoom = 0.13f;
		GameAPI.camera().position.set(player.x(), player.y(), 0.0f);
		loc.setLocColor();
		world.setGravity(world.getGravity().set(Const.PHYSICS_GRAVITY_X, Const.PHYSICS_GRAVITY_Y));
	}

	// Events
	public void eventMouseScroll(int data) {
		if(editMode){
			Editor.eventMouseScroll(data);
		}
	}

	public void eventEscape() {
		if(editMode){
			Editor.eventEscape();
		}
	}
	
	public void eventDel() {
		if(editMode){
			Editor.eventDel(loc, world);
		}
	}

	public void sceneMouseMove() {
		if(editMode){
			Editor.eventMouseMove();
		}
	}

	public void eventLeftClick() {
		if(editMode){
			Editor.eventLeftClick(loc, world);
		}
	}
	
	public void eventRightClick() {
		if(editMode){
			Editor.eventRightClick();
		}
	}

	public void setLocColor() {
		loc.setLocColor();
	}

	public void buildPlan() {
		loc.buildPlan();
	}

	public void saveLocation() {
		loc.saveLocation();
	}
	
	@Override
	public void dispose() {
		world.dispose();
	}
}