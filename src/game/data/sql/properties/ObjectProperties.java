package game.data.sql.properties;

import com.owlengine.resources.Assets;

public final class ObjectProperties {

	public final int typeId;
	public final String title;
	
	public final int objType;
	public final int bodyType;
	public final boolean interact;
	
	public final float dencity;
	public final float friction;
	public final float mass;
	
	public final int drawLayer;
	
	public final int sizex;
	public final int sizey;
	
	public final String texture;
	
	public final boolean go;
	public final boolean creature;
	public final boolean player;
	public final boolean npc;
	
	public final int maxHp;
	
	public final boolean planEditorIgnore;
	public final int planEditorColor;
	
	public ObjectProperties(int typeId, 
							String title,
							int objType, 
							int bodyType,
							boolean interact,
							float dencity, 
							float friction, 
							float mass, 
							int drawLayer, 
							int sizex, 
							int sizey, 
							String texture,
							boolean go,
							boolean player,
							boolean npc,
							int maxHp, 
							boolean planEditorIgnore,
							int planEditorA, 
							int planEditorR,
							int planEditorG, 
							int planEditorB)
	{
		this.typeId = typeId;
		this.title = title;
		this.objType = objType;
		this.bodyType = bodyType;
		this.interact = interact;
		this.dencity = dencity;
		this.friction = friction;
		this.mass = mass;
		this.drawLayer = drawLayer;
		this.sizex = sizex;
		this.sizey = sizey;
		this.texture = texture;
		
		this.go = go;
		this.player = player;
		this.npc = npc;
		
		if(this.npc || this.player){
			this.creature = true;
		}
		else{
			this.creature = false;
		}

		this.maxHp = maxHp;
		
		//
		Assets.loadTex(this.texture);
		
		// plan editor
		this.planEditorIgnore = planEditorIgnore;
		this.planEditorColor = (planEditorA << 24) | (planEditorR << 16) | (planEditorG << 8) | planEditorB;
	}
}
