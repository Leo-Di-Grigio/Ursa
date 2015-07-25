package game.data.location;

import tools.Const;
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
		ObjData dataA = (ObjData)contact.getFixtureA().getBody().getUserData();
		
		if(dataA.type == Const.OBJ_PLAYER){
			gamedata.playerInteract(contact.getFixtureB(), (ObjData)contact.getFixtureB().getBody().getUserData(), true);
		}
	}

	@Override
	public void endContact(Contact contact) {
		ObjData dataA = (ObjData)contact.getFixtureA().getBody().getUserData();
		
		if(dataA.type == Const.OBJ_PLAYER){
			gamedata.playerInteract(contact.getFixtureB(), (ObjData)contact.getFixtureB().getBody().getUserData(), false);
		}
	}
	
	@Override
	public void preSolve(Contact contact, Manifold mainfold) {
		
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
}
