package game.data;

import tools.Const;
import cycle.GameAPI;
import game.data.location.Location;
import game.data.objects.creatures.Player;

public final class PlayerHandler {

	private GameData gamedata;
	private Location loc;
	private Player player;
	
	public PlayerHandler(GameData gamedata, Location loc, Player player) {
		this.gamedata = gamedata;
		this.loc = loc;
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void moveUp() {
		if(gamedata.editMode()){
			GameAPI.camera().translate(0.0f, 1.0f);
		}
		else{
			player.moveUp();
		}
	}

	public void moveDown() {
		if(gamedata.editMode()){
			GameAPI.camera().translate(0.0f, -1.0f);
		}
		else{
			player.moveDown();
		}
	}
	
	public void moveLeft() {
		if(gamedata.editMode()){
			GameAPI.camera().translate(-1.0f, 0.0f);
		}
		else{
			player.moveLeft();
			player.setDirect(Const.ANIMATION_DIRECT_LEFT);
		}
	}
	
	public void moveRight() {
		if(gamedata.editMode()){
			GameAPI.camera().translate(1.0f, 0.0f);
		}
		else{
			player.moveRight();
			player.setDirect(Const.ANIMATION_DIRECT_RIGHT);
		}
	}

	public void moveStopX() {
		player.moveStopX();
	}

	public void moveStopY() {
		player.moveStopY();
	}
	
	public void attack() {
		loc.attack(player.id);
	}

	public void playerStand() {
		player.stand();
	}

	public float x() {
		return player.x();
	}

	public float y() {
		return player.y();
	}

	public int getWeaponMain() {
		return player.getWeaponMain();
	}
}
