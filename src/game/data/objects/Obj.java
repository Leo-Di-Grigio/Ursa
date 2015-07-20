package game.data.objects;

import tools.Const;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.Array;

abstract public class Obj {

	private static int ID;

	private final int id;
	private final int type;
	
	// Physics
	private Body body;
	private Fixture fixture;
	
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
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(sizex, sizey);
		
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        fixture = body.createFixture(fixtureDef);
		
		shape.dispose();
	}
	
	public final int id(){
		return id;
	}
	
	public final int type(){
		return type;
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
	
	public int sizeX() {
		return tex.getWidth();
	}

	public int sizeY() {
		return tex.getHeight();
	}
	
	public final void moveUp() {
		if(isGrounded()){
			body.setLinearVelocity(body.getLinearVelocity().x, Const.BEAR_SPEED_JUMP);
		}
	}
	
	public final void moveDown(){
		
	}

	public final void moveLeft() {
		body.setLinearVelocity(-Const.BEAR_SPEED, body.getLinearVelocity().y);
	}

	public final void moveRight() {
		body.setLinearVelocity( Const.BEAR_SPEED, body.getLinearVelocity().y);
	}

	public final void moveStopX(){
		body.setLinearVelocity(   0.0f, body.getLinearVelocity().y);
	}
	
	public boolean isGrounded(){
		Array<Contact> contacts = body.getWorld().getContactList();
		
		for(int i = 0; i < contacts.size; ++i){
			Contact contact = contacts.get(i);
		    
			if(contact.isTouching() && contact.getFixtureA() == fixture){
				WorldManifold worldManifold = contact.getWorldManifold();
				Vector2 pos = body.getPosition();
			        
				boolean below = true;
			        
				for(int j = 0; j < worldManifold.getNumberOfContactPoints(); ++j){
					below &= worldManifold.getPoints()[j].y < pos.y;
				}
			        
				if(below){
					if(contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals("p")) {
						body = (Body)contact.getFixtureA().getBody().getUserData();                     
					}
				   
					if(contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals("p")) {
						body = (Body)contact.getFixtureB().getBody().getUserData();
					}
				
					return true;
				}
				
				return false;
			}
		}
		
		return false;
	}
	
	public final void setTex(Texture tex) {
		this.tex = tex;
	}

	public void draw(SpriteBatch batch) {

	}
}