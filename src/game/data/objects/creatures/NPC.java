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
	
	@Override
	public void update() {
		updateAI();
	}
	
	public void updateAI(){
		if((int)target.y() > (int)y()){
			moveUp();
		}
		
		if((int)target.x() + Const.AI_CONTACT_RADIUS < (int)x()){
			moveLeft();
		}
		else if((int)target.x() - Const.AI_CONTACT_RADIUS > (int)x()){
			moveRight();
		}
	}
}
