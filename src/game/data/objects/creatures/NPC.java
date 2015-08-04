package game.data.objects.creatures;

import tools.Const;

import com.owlengine.tools.Log;

import cycle.GameAPI;
import game.data.location.Location;
import game.data.objects.Creature;
import game.data.objects.Obj;

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
	public void update(Location loc) {
		updateAI(loc);
	}
	
	public void updateAI(Location loc){
		if(canAttack()){
			attack(loc);
		}
		else{
			if(stairs.size() > 0){
				if((int)target.y() > (int)y()){
					moveUp();
				}
				else if((int)target.y() < (int)y()){
					moveDown();
				}
			}
			
			if(!stairMode){
				if((int)target.x() + Const.AI_CONTACT_RADIUS < (int)x()){
					
					if(isGrounded() && isStretched()){
						moveUp();
					}
					
					moveLeft();
				}
				else if((int)target.x() - Const.AI_CONTACT_RADIUS > (int)x()){
					
					if(isGrounded() && isStretched()){
						moveUp();
					}
					
					moveRight();
				}
			}
		}
	}
	
	private boolean canAttack() {
		return false;
	}

	private boolean isStretched() {
		if(blocks.size() > 0 ){
			
			for(Obj obj: blocks){
				if(((int)(obj.x() + obj.sizeX()/2) > (int)(x() + sizeX()/2) &&
				    (int)(obj.y() + obj.sizeY()/2) > (int)(y() - sizeY()/2)) // right
				   ||
				   ((int)(obj.x() - obj.sizeX()/2) < (int)(x() - sizeX()/2) &&
				    (int)(obj.y() + obj.sizeY()/2) > (int)(y() - sizeY()/2))) // left
				{
					return true;
				}
			}
			
			return false;
		}
		else{
			return false;
		}
	}
}