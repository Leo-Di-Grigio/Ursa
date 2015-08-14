package game.data.location;

import game.data.GameData;
import game.data.objects.ObjData;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameContactListener implements ContactListener {

	private GameData gamedata;

	public GameContactListener(GameData gamedata) {
		this.gamedata = gamedata;
	}
	
	@Override
	public void beginContact(Contact contact) {
		gamedata.collision((ObjData)contact.getFixtureA().getBody().getUserData(),
						  (ObjData)contact.getFixtureB().getBody().getUserData(),
						  true);
	}

	@Override
	public void endContact(Contact contact) {
		gamedata.collision((ObjData)contact.getFixtureA().getBody().getUserData(),
				  		  (ObjData)contact.getFixtureB().getBody().getUserData(),
				  		  false);
	}
	
	@Override
	public void preSolve(Contact contact, Manifold mainfold) {
		
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
}
