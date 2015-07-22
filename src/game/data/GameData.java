package game.data;

import tools.Const;
import tools.Version;
import main.Config;
import game.data.location.GameContactListener;
import game.data.location.Location;
import game.data.objects.Obj;
import game.data.objects.ObjBuilder;
import game.data.objects.ObjData;
import game.data.objects.creatures.Player;
import game.data.objects.statics.Block;
import game.data.objects.statics.Stairs;

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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import cycle.GameAPI;

public final class GameData implements Disposable {
	
	//
	private Location loc;
	
	// Objects
	private Player player;
	
	//
	private Box2DDebugRenderer debugRenderer;
	private World world;

	// modes
	private boolean editMode;
	private int editObject = Const.OBJ_NULL;
	
	// mouse selecting
	private boolean editSelect;
	private Vector3 select1;
	private Vector3 select2;
	
	// 
	private Vector3 cameraResoultion;
	private ShapeRenderer shapeBatch;
	
	public GameData() {
		shapeBatch = new ShapeRenderer();
		cameraResoultion = new Vector3();
		
		select1 = new Vector3();
		select2 = new Vector3();
		
		//
		Box2D.init();
		new ObjBuilder();
		
		//
		world = new World(new Vector2(0.0f, -140f), true);
		world.setContactListener(new GameContactListener(this));
		
		if(Config.debug()){
			debugRenderer = new Box2DDebugRenderer();
		}
		
		//
		loc = new Location();
		
		// objects
		this.player = new Player();
		loc.addObj(world, player, 0, 0, 12, 8);
		
		// location test
		loc.addObj(world, new Block(), 0, -2, 200, 4);
	}
	
	public void update(OrthographicCamera camera) {
		world.step(Gdx.graphics.getDeltaTime(), 8, 3);
		
		if(!editMode){
			camera.position.set(player.x(), player.y(), 0.0f);
		}
		
		if(Config.debug() && debugRenderer != null){
			debugRenderer.render(world, camera.combined);
		}
		
		if(editMode){
			drawEdit();
		}
	}

	public void draw(SpriteBatch batch) {
		loc.draw(batch);
	}

	private void drawEdit() {
		shapeBatch.setProjectionMatrix(GameAPI.camera().combined);
		

		shapeBatch.begin(ShapeType.Line);

		drawEditGrid();
		
		if(editSelect){
			drawEditObj();
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
		
		final int x1 = (int)select1.x;
		final int y1 = (int)select1.y;
		
		final int x2 = (int)select2.x;
		final int y2 = (int)select2.y;
		
		final int sizex = (int)Math.abs(x1 - x2);
		final int sizey = (int)Math.abs(y1 - y2);
		
		shapeBatch.rect(Math.min(x1, x2), Math.min(y1, y2), sizex, sizey);
	}

	@Override
	public void dispose() {
		world.dispose();
		
		if(Config.debug() && debugRenderer != null){
			debugRenderer.dispose();
		}
	}

	// Player interact
	public void playerInteract(Fixture objectFixture, ObjData data, boolean value) {
		
		switch (data.type) {
			case Const.OBJ_STAIRS:
				player.interactStair(value);
				break;
				
			case Const.OBJ_BLOCK:
				player.interactBlock(value, objectFixture, loc.getObj(data.id));
				break;

			default:
				break;
		}
	}
	
	// Player movement
	public void playerMoveUp() {
		if(editMode){
			GameAPI.camera().position.add(0.0f, 1.0f, 0.0f);
		}
		else{
			player.moveUp();
		}
	}

	public void playerMoveDown() {
		if(editMode){
			GameAPI.camera().position.add(0.0f, -1.0f, 0.0f);
		}
		else{
			player.moveDown();
		}
	}
	
	public void playerMoveLeft() {
		if(editMode){
			GameAPI.camera().position.add(-1.0f, 0.0f, 0.0f);
		}
		else{
			player.moveLeft();
			player.setDirect(Const.ANIMATION_DIRECT_LEFT);
		}
	}
	
	public void playerMoveRight() {
		if(editMode){
			GameAPI.camera().position.add(1.0f, 0.0f, 0.0f);
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

	public void setEditMode(boolean value) {
		this.editMode = value;
		
		if(this.editMode){
			Gdx.gl.glClearColor(0.18431f, 0.30588f, 0.50588f, 1.0f);
		}
		else{
			editSelect = false;
			editObject = Const.OBJ_NULL;
			GameAPI.camera().zoom = 0.13f;
			Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	public void setEditObject(int objId) {
		this.editObject = objId;
	}
	
	public void cameraZoom(int data) {
		if(this.editMode){
			final float zoom = GameAPI.camera().zoom + 0.01f*data;
			
			if(zoom > 0.01f && zoom < 10.0f){
				GameAPI.camera().zoom = zoom;
			}
		}
	}

	public void sceneEnter() {
		if(editMode && editObject != Const.OBJ_NULL){
			commitEdit();
			editSelect = false;
		}
	}

	public void sceneEscape() {
		if(editMode && editObject != Const.OBJ_NULL){
			cancelEdit();
			editSelect = false;
		}
	}

	private void commitEdit() {
		Obj obj = null;
		
		switch (editObject) {
			
			case Const.OBJ_BLOCK:
				obj = new Block();
				break;
				
			case Const.OBJ_STAIRS:
				obj = new Stairs();
				break;
		}
		
		if(obj != null){
			final int posx = (int)select1.x;
			final int posy = (int)select1.y;
			
			final int sizex = Math.abs((int)select2.x - posx);
			final int sizey = Math.abs((int)select2.y - posy);
			
			if(sizex != 0 && sizey != 0){
				if(select1.x < select2.x){
					if(select1.y < select2.y){
						loc.addObj(world, obj, posx + sizex/2, posy + sizey/2, sizex, sizey);
					}
					else{
						loc.addObj(world, obj, posx + sizex/2, posy - sizey/2, sizex, sizey);
					}
				}
				else{
					if(select1.y < select2.y){
						loc.addObj(world, obj, posx - sizex/2, posy + sizey/2, sizex, sizey);
					}
					else{
						loc.addObj(world, obj, posx - sizex/2, posy - sizey/2, sizex, sizey);
					}
				}	
			}
		}
	}
	
	private void cancelEdit() {
		
	}

	public void sceneClick() {
		if(editMode && editObject != Const.OBJ_NULL && !editSelect){
			// first click (first part)
			pickPixel(select1);
			editSelect = true;
		}
	}
	
	public void sceneDrag() {
		pickPixel(select2);
	}

	private void pickPixel(Vector3 vector){
        vector.set(Gdx.input.getX() , Gdx.input.getY(), 0);
        GameAPI.camera().unproject(vector);
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
	}
}