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
							String texture)
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
		
		//
		Assets.loadTex(this.texture);
	}
}
