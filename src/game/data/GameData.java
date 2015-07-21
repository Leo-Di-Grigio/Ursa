package game.data;

import tools.Const;
import main.Config;
import game.data.location.Location;
import game.data.objects.Block;
import game.data.objects.ObjBuilder;
import game.data.objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public final class GameData implements Disposable {
	
	//
	private Location loc;
	
	// Objects
	private Player player;
	
	//
	private Box2DDebugRenderer debugRenderer;
	private World world;
	
	public GameData() {
		Box2D.init();
		new ObjBuilder();
		
		//
		world = new World(new Vector2(0.0f, -140f), true);
		
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
		loc.addObj(world, new Block(), -20, 20, 40, 4);
		loc.addObj(world, new Block(), 12, 6, 10, 4);
		loc.addObj(world, new Block(), 30, 12, 10, 4);
		loc.addObj(world, new Block(), -80, 20, 40, 4);
		loc.addObj(world, new Block(), -102, 116, 4, 200);
		loc.addObj(world, new Block(), -102, -96, 4, 200);
	}
	
	public void update(OrthographicCamera camera) {
		world.step(Gdx.graphics.getDeltaTime(), 6, 4);
		camera.position.set(player.x(), player.y(), 0.0f);
		
		if(Config.debug() && debugRenderer != null){
			debugRenderer.render(world, camera.combined);
		}
	}

	public void draw(SpriteBatch batch) {
		loc.draw(batch);
	}

	@Override
	public void dispose() {
		world.dispose();
		
		if(Config.debug() && debugRenderer != null){
			debugRenderer.dispose();
		}
	}

	// Player movement
	public void playerMoveUp() {
		player.moveUp();
	}

	public void playerMoveDown() {
		player.moveDown();
	}
	
	public void playerMoveLeft() {
		player.moveLeft();
		player.setDirect(Const.ANIMATION_DIRECT_LEFT);
	}
	
	public void playerMoveRight() {
		player.moveRight();
		player.setDirect(Const.ANIMATION_DIRECT_RIGHT);
	}

	public void playerStopX() {
		player.moveStopX();
	}
}