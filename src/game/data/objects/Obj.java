package game.data.objects;

import game.data.sql.Database;
import game.data.sql.properties.ObjectProperties;
import tools.Const;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

abstract public class Obj {

	private static int ID;

	public final int id;
	public final int type;
	
	// Physics
	private float sizex;
	private float sizey;
	protected Body body;
	protected Fixture fixture;
	
	// Graphics
	protected Texture tex;
	
	static {
		ID = 0;
	}
	
	public Obj(final int type) {
		this.id = ID++;
		this.type = type;
	}

	public final void setBody(Body body, float sizex, float sizey){
		this.body = body;
		this.body.setUserData(new ObjData(id, type));
		
		PolygonShape shape = new PolygonShape();
		
		this.sizex = sizex;
		this.sizey = sizey;
		shape.setAsBox(this.sizex/2, this.sizey/2);
		
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        
        ObjectProperties property = Database.getObject(type);
        fixtureDef.density = property.dencity;
        fixtureDef.friction = property.friction;
        fixtureDef.isSensor = property.interact;
        
        fixture = this.body.createFixture(fixtureDef);
        
		shape.dispose();
	}
	
	public Body getBody() {
		return body;
	}
	
	public final void setPos(float x, float y){
		body.getPosition().set(x, y);
	}
	
	public final float x() {
		return body.getPosition().x;
	}
	
	public final float y() {
		return body.getPosition().y;
	}
	
	public final float sizeX() {
		return sizex;
	}

	public final float sizeY() {
		return sizey;
	}
	
	public void moveUp() {
		if(isGrounded()){
			body.setLinearVelocity(body.getLinearVelocity().x, Const.BEAR_SPEED_JUMP);
		}
	}
	
	public void moveDown(){
		
	}

	public void moveLeft() {
		body.setLinearVelocity(-Const.BEAR_SPEED, body.getLinearVelocity().y);
	}

	public void moveRight() {
		body.setLinearVelocity( Const.BEAR_SPEED, body.getLinearVelocity().y);
	}

	public final void moveStopX(){
		body.setLinearVelocity(   0.0f, body.getLinearVelocity().y);
	}
	
	public boolean isGrounded(){		
		return false;
	}
	
	public final Vector2 getSpeed(){
		return body.getLinearVelocity();
	}

	public void setTex(Texture tex) {
		this.tex = tex;
	}

	public void draw(SpriteBatch batch) {
		batch.draw(tex, x() - sizeX()/2, y() - sizeY()/2, sizeX(), sizeY());
	}
}