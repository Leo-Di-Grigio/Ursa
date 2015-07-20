package game.data;

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
		
		debugRenderer = new Box2DDebugRenderer();
		
		//
		loc = new Location();
		
		// objects
		this.player = new Player();
		loc.addObj(world, player, 0, 0, 6, 4);
		
		// location test
		loc.addObj(world, new Block(), 0, -2, 100, 2);
		loc.addObj(world, new Block(), 0, 20, 100, 2);
		loc.addObj(world, new Block(), -102, 115, 2, 100);
		loc.addObj(world, new Block(), -102, -95, 2, 100);
		
		loc.addObj(world, new Block(), 15, 15, 2, 2);
		loc.addObj(world, new Block(), 10, 6, 2, 2);
		loc.addObj(world, new Block(), 20, 6, 2, 2);
	}
	
	public void update(OrthographicCamera camera) {
		world.step(Gdx.graphics.getDeltaTime(), 1, 1);
		camera.position.set(player.x(), player.y(), 0.0f);
		debugRenderer.render(world, camera.combined);
	}

	public void draw(SpriteBatch batch) {
		loc.draw(batch);
	}

	@Override
	public void dispose() {
		debugRenderer.dispose();
		world.dispose();
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
	}
	
	public void playerMoveRight() {
		player.moveRight();
	}

	public void playerStopX() {
		player.moveStopX();
	}
}