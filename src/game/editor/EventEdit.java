package game.editor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

final class EventEdit implements Poolable {
	
	// data
	private int type;
	private int data;
	
	private Vector2 vec1;
	private Vector2 vec2;
	
	public EventEdit() {
		vec1 = new Vector2();
		vec2 = new Vector2();
	}

	@Override
	public void reset() {
		this.type = EventEditStack.NULL;
		this.data = EventEditStack.NULL;
		
		this.vec1.setZero();
		this.vec2.setZero();
	}

	public int type() {
		return type;
	}
	
	public int data() {
		return data;
	}
	
	public float v1x(){
		return vec1.x;
	}
	
	public float v1y(){
		return vec1.y;
	}
	
	public float v2x(){
		return vec2.x;
	}
	
	public float v2y(){
		return vec2.y;
	}

	//
	public void objAdd(int objId) {
		this.type = EventEditStack.OBJ_ADD;
		this.data = objId;
	}

	public void objDel(int objType, float x, float y) {
		this.type = EventEditStack.OBJ_DELETE;
		this.data = objType;
		this.vec1.set(x, y);
	}
}
