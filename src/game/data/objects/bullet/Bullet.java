package game.data.objects.bullet;

import game.data.location.Location;
import game.data.objects.ObjData;
import tools.Const;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.owlengine.resources.Assets;

public class Bullet implements Poolable {
	
	private static int ID;
	public final int id;
	
	protected Body body;
	protected Fixture fixture;
	protected Sprite sprite;
	
	protected int lifeTimer;
	
	static {
		ID = 0;
	}
	
	public Bullet(World world) {
		this.id = ID++;
		this.sprite = new Sprite(Assets.getTex(Const.TEX_BULLET));
		this.sprite.setScale(0.08f);
		
		buildBody(world);
	}

	private void buildBody(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.gravityScale = 0.0f;
		bodyDef.bullet = true;
		bodyDef.fixedRotation = true;
		bodyDef.position.set(Float.MAX_VALUE, Float.MAX_VALUE);
		
        MassData massData = new MassData();
		massData.mass = 0.0f;
		
		this.body = world.createBody(bodyDef);
		this.body.setUserData(new ObjData(id, Const.OBJ_BULLET));
		this.body.setMassData(massData);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1.0f, 1.0f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = false;
		fixtureDef.friction = 0.0f;
		
		this.fixture = this.body.createFixture(fixtureDef);
		
		Filter filter = new Filter();
		filter.categoryBits = Const.CATEGORY_BULLET;
		filter.maskBits = Const.MASK_BULLET;
		
		this.fixture.setFilterData(filter);
		
		shape.dispose();
	}

	public void setPos(final float x, final float y){
		body.setTransform(x, y, 0.0f);
		sprite.setPosition(x - 8, y - 8);
	}
	
	public void setImpulse(final float px, final float py){
		body.setLinearVelocity(px, py);
	}
	
	@Override
	public void reset() {
		this.lifeTimer = 0;
		body.setLinearVelocity(0.0f, 0.0f);
		body.setTransform(Float.MAX_VALUE, Float.MAX_VALUE, 0.0f); // place bullet at maximum far position
	}

	public void update(Location loc) {
		this.lifeTimer++;
		
		if(lifeTimer > Const.BULLET_LIFETIME){
			loc.freeBullet(this.id);
		}
		else{
			sprite.setPosition(body.getPosition().x - 8, body.getPosition().y - 8);
		}
	}
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
}