package game.data.objects.creatures;

import tools.Const;

import com.owlengine.tools.Log;

import cycle.GameAPI;
import game.data.objects.Creature;

abstract public class NPC extends Creature {

	private Player target;

	public NPC(int type) {
		super(type);
		target = GameAPI.getPlayer();
		
		if(target == null){
			Log.err("NPC build err: target is null");
		}
	}
	
	public void updateAI(){
		if(target.y() > y()){
			moveUp();
		}
		
		if(target.x() + Const.AI_CONTACT_RADIUS < x()){
			moveLeft();
		}
		else if(target.x() - Const.AI_CONTACT_RADIUS > x()){
			moveRight();
		}
	}
}
