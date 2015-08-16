package game.editor;

import game.data.objects.Obj;

import java.util.Stack;

import com.badlogic.gdx.utils.Pool;

final class EventEditStack {

	private static final int INIT_VALUE = 30;
	
	// opcodes
	public static final int NULL = 0;
	public static final int OBJ_ADD = 1;
	public static final int OBJ_DELETE = 2;
	public static final int AREA_ADD = 3;
	public static final int AREA_DELETE = 4;
	
	// data
	private Pool<EventEdit> eventPool;
	private Stack<EventEdit> eventStack;
	
	public EventEditStack() {
		
		eventPool = new Pool<EventEdit>(INIT_VALUE) {
			
			@Override
			protected EventEdit newObject() {
				return new EventEdit();
			}
		};
		
		eventStack = new Stack<EventEdit>();
	}
	
	public void free(EventEdit event){
		eventPool.free(event);
	}
	
	public EventEdit pop(){
		return eventStack.pop();
	}

	public boolean isEmpty() {
		return eventStack.isEmpty();
	}

	//
	private EventEdit getClear(){
		return eventPool.obtain();
	}
	
	private void add(EventEdit event){
		eventStack.push(event);
	}
	
	// Edit events
	public void objAdd(Obj object) {
		EventEdit event = getClear();
		event.objAdd(object.id);
		add(event);
	}
	
	public void objDel(Obj object) {
		EventEdit event = getClear();
		event.objDel(object.type, object.x(), object.y());
		add(event);
	}
}
