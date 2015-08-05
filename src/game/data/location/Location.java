package game.data.location;

import game.data.location.loader.LocationMng;
import game.data.objects.Creature;
import game.data.objects.Obj;
import game.data.objects.ObjBuilder;
import game.data.objects.ObjData;
import game.data.objects.bullet.Bullet;
import game.data.sql.Database;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import tools.Const;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.owlengine.resources.Assets;
import com.owlengine.tools.Log;

public final class Location {

	private float colorR = 183.0f;
	private float colorG = 207.0f;
	private float colorB = 208.0f;
	
	// Pools
	private Pool<Bullet> bulletsPool;
	
	// Data
	private HashMap<Integer, Obj> objects;
	private HashMap<Integer, Creature> creatures;
	private TreeMap<Integer, HashSet<Obj>> draw;
	
	private HashMap<Integer, Bullet> activeBullets;
	private Array<Bullet> drawBullets;
	private HashSet<Bullet> freeBullets;
	
	// Background
	private ScrollBackground [] background;
	
	// Tmp
	private Set<Obj> tmpSet;
	
	private class DrawSort implements Comparator<Integer>{

		@Override
		public int compare(Integer val1, Integer val2) {
			
			if(val1 == val2){
				return 0;
			}
			else{
				if(val1 < val2){
					return -1;
				}
				else{
					return 1;
				}
			}
		}
	}
	
	public Location(World world) {
		// pools
		bulletsPool = new Pool<Bullet>(100, 100) {
			
			@Override
			protected Bullet newObject() {
				return new Bullet(world);
			}
		};
		
		// data
		objects = new HashMap<Integer, Obj>();
		creatures = new HashMap<Integer, Creature>();
		draw = new TreeMap<Integer, HashSet<Obj>>(new DrawSort());
		
		drawBullets = new Array<Bullet>();
		freeBullets = new HashSet<Bullet>();
		activeBullets = new HashMap<Integer, Bullet>();
		
		// background
		Assets.loadTex(Const.TEX_BACKGROUND_LAYER_1);
		Assets.loadTex(Const.TEX_BACKGROUND_LAYER_2);
		
		background = new ScrollBackground[2];
		background[0] = new ScrollBackground(Const.TEX_BACKGROUND_LAYER_1, 0.85f, 0.0f, 0.1f);
		background[1] = new ScrollBackground(Const.TEX_BACKGROUND_LAYER_2, 0.76f, 0.0f, 0.1f);
		
		// tmp
		tmpSet = new HashSet<Obj>();
	}
	
	public Obj addObj(World world, int type, float x, float y){
		Obj object = ObjBuilder.buildObj(world, type, x, y);
		
		if(object == null){
			Log.err("Location.addObj(): Null Obj loaded typeId: " + type);
			return null;
		}
		else{
			objects.put(object.id, object);
			
			if(object.creature){
				creatures.put(object.id, (Creature)object);
			}
			
			// draw sort
			final int layer = Database.getObject(object.type).drawLayer;
					
			if(!draw.containsKey(layer)){
				draw.put(layer, new HashSet<Obj>());
			}
			
			draw.get(layer).add(object);
			
			//
			return object;	
		}
	}

	public void removeObj(World world, Obj object) {
		world.destroyBody(object.getBody());
		objects.remove(object.id);
		
		if(object.creature){
			creatures.remove(object.id);
		}
		
		final int layer = Database.getObject(object.type).drawLayer;
		draw.get(layer).remove(object);
	}
	
	public Obj getObj(final int id) {
		return objects.get(id);
	}
	
	public Creature getCreature(final int id) {
		return creatures.get(id);
	}

	public Set<Obj> searchObj(float x, float y) {
		tmpSet.clear();
		
		for(Obj obj: objects.values()){
			
			if(x >= obj.x() - obj.sizeX()/2 &&
			   x <= obj.x() + obj.sizeX()/2 &&
			   y >= obj.y() - obj.sizeY()/2 &&
			   y <= obj.y() + obj.sizeY()/2)
			{
				tmpSet.add(obj);
			}
		}
		
		return tmpSet;
	}
	
	public void setLocColor() {
		Gdx.gl.glClearColor(colorR/255.0f, colorG/255.0f, colorB/255.0f, 1.0f);
	}

	public void scroll(float deltax, float deltay) {
		for(int i = 0; i < background.length; ++i){
			background[i].scroll(deltax, deltay);
		}
	}

	public void drawBackGround(SpriteBatch batch){
		for(int i = 0; i < background.length; ++i){
			background[i].draw(batch);
		}
	}
	
	public void npcInteractBlock(ObjData idA, ObjData idB, boolean value) {
		Obj objA = getObj(idA.id);
		
		if(objA.creature){
			creatures.get(idA.id).interactBlock(value, getObj(idB.id));
		}
		else{
			creatures.get(idB.id).interactBlock(value, getObj(idA.id));
		}
	}
	
	public void npcInteractStair(ObjData idA, ObjData idB, boolean value) {
		Obj objA = getObj(idA.id);
		
		if(objA.creature){
			creatures.get(idA.id).interactStair(value, getObj(idB.id));
		}
		else{
			creatures.get(idB.id).interactStair(value, getObj(idA.id));
		}
	}

	public void npcInteractWater(ObjData idA, ObjData idB, boolean value) {
		Obj objA = getObj(idA.id);
		
		if(objA.creature){
			creatures.get(idA.id).interactWater(value, getObj(idB.id));
		}
		else{
			creatures.get(idB.id).interactWater(value, getObj(idA.id));
		}
	}
	
	public void bulletInteract(ObjData dataA, ObjData dataB, boolean value) {
		if(dataA.type == Const.OBJ_BULLET){
			freeBullet(dataA.id);
		}
		else{
			freeBullet(dataB.id);
		}
	}

	public void attack(final int id) {
		Creature attacker = getCreature(id);
		
		if(attacker == null){
			Log.err("Location.attack(): attacker id: " + id + " is null");
		}
		else{
			if(attacker.canAttack()){
				attacker.attack();
				
				Bullet bullet = bulletsPool.obtain();
				
				if(attacker.direct() == Const.ANIMATION_DIRECT_RIGHT){
					bullet.setPos(attacker.x() + attacker.sizeX()/2 + 3.0f, attacker.y());
					bullet.setImpulse( Const.WEAPON_BULLET_IMPULSE_X, 0.0f);
				}
				else if(attacker.direct() == Const.ANIMATION_DIRECT_LEFT){
					bullet.setPos(attacker.x() - attacker.sizeX()/2 - 3.0f, attacker.y());
					bullet.setImpulse(-Const.WEAPON_BULLET_IMPULSE_X, 0.0f);
				}
				
				drawBullets.add(bullet);
				activeBullets.put(bullet.id, bullet);
			}
		}
	}
	
	public void freeBullet(final int id) {
		Bullet bullet = activeBullets.remove(id);
		
		if(bullet != null){
			freeBullets.add(bullet);
		}
	}
	
	public void update(){
		for(Creature unit: creatures.values()){
			unit.update();
		}
		
		// free Bullets
		if(freeBullets.size() > 0){
			for(Bullet bullet: freeBullets){
				if(bullet == null){
					Log.err("Location.freeBullet(): Null bullet");
				}
				else{
					drawBullets.removeValue(bullet, true);
					bulletsPool.free(bullet);
				}
			}
			
			freeBullets.clear();
		}
	}
	
	public void draw(SpriteBatch batch){
		for(HashSet<Obj> layer: draw.values()){
			for(Obj obj: layer){
				obj.draw(batch);
			}
		}
		
		for(Bullet bullet: drawBullets){
			bullet.update(this);
			bullet.draw(batch);
		}
	}

	public void buildPlan() {
		File foler = new File(Const.PLANS_FOLDER);
		
		if(!foler.exists()){
			foler.mkdirs();
		}
		
		LocationMng.buildPlan(draw, Const.PLANS_FOLDER + "/" + Const.PLANS_FILE);
	}
}