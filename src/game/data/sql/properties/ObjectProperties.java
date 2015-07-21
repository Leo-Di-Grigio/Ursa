package game.data.sql.properties;

public final class ObjectProperties {

	public final int id;
	public final String title;
	
	public final int bodyType;
	public final boolean interact;
	
	public final float dencity;
	public final float friction;
	public final float mass;
	
	public ObjectProperties(int id, String title, 
							int bodyType, boolean interact, 
							float dencity, float friction, float mass)
	{
		this.id = id;
		this.title = title;
		
		this.bodyType = bodyType;
		this.interact = interact;
		this.dencity = dencity;
		this.friction = friction;
		this.mass = mass;
	}
}
