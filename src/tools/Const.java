package tools;

public class Const {
	
	// UI
	public static final String UI_MENU = "interface/menu.json";
	public static final String UI_GAME = "interface/game.json";
	
	// Assets
	// texture
	public static final String TEX_NULL = "assets/tex/null.png";
	
	// textures location
	public static final String TEX_BLOCK = "assets/tex/location/block.png";
	public static final String TEX_BACKGROUND_WALL = "assets/tex/location/background_wall.png";
	public static final String TEX_STAIRS = "assets/tex/location/stairs.png";
	public static final String TEX_WATER = "assets/tex/location/water.png";
	
	// textures creatures
	public static final String TEX_PLAYER = "assets/tex/creatures/player.png";
	
	// font
	public static final String FONT_DEFAULT = "assets/font/font.ttf";
	
	// Editor
	public static final int GRID_STEP = 20;
	public static final int EDITOR_BACKGROUND_BLUEPRINT_COLOR = 0x2F4E81;
	
	// Objects type
	public static final int OBJ_TYPE_NULL = 0;
	public static final int OBJ_TYPE_OBJECT = 1;
	public static final int OBJ_TYPE_CREATURE = 2;
	
	// Objects ID's
	public static final int OBJ_NULL = 0;
	public static final int OBJ_PLAYER = 1;
	public static final int OBJ_BLOCK = 2;
	public static final int OBJ_BACKGROUND_WALL = 3;
	public static final int OBJ_STAIRS = 4;
	public static final int OBJ_WATER = 5;
	
	// Animation
	public static final int ANIMATION_DIRECT_LEFT = 0;
	public static final int ANIMATION_DIRECT_RIGHT = 1;
	
	public static final int ANIMATION_ARRAY_SIZE = 7;
	public static final int ANIMATION_IDLE_0 = 0;
	public static final int ANIMATION_MOVE_1 = 1;
	public static final int ANIMATION_MOVE_2 = 2;
	public static final int ANIMATION_MOVE_3 = 3;
	public static final int ANIMATION_MOVE_4 = 4;
	public static final int ANIMATION_MOVE_5 = 5;
	public static final int ANIMATION_MOVE_6 = 6;

	// Physic
	public static final int BODY_TYPE_STATIC = 0;
	public static final int BODY_TYPE_DYNAMIC = 1;
	public static final int BODY_TYPE_KINEMATIC = 2;
	
	public static final short BODY_FILTER_1 = 0x0001;
	public static final short BODY_FILTER_2 = 0x0002;
	public static final short BODY_FILTER_3 = 0x0004;
	public static final short BODY_FILTER_4 = 0x0008;
	public static final short BODY_FILTER_5 = 0x0010;
	public static final short BODY_FILTER_6 = 0x0020;
	public static final short BODY_FILTER_7 = 0x0040;
	public static final short BODY_FILTER_8 = 0x0080;
	public static final short BODY_FILTER_9 = 0x0100;
	public static final short BODY_FILTER_10 = 0x0200;
	public static final short BODY_FILTER_11 = 0x0400;
	public static final short BODY_FILTER_12 = 0x0800;
	public static final short BODY_FILTER_13 = 0x1000;
	public static final short BODY_FILTER_14 = 0x2000;
	public static final short BODY_FILTER_15 = 0x4000;
	
	public static final float PHYSICS_GRAVITY_X =  0.0f;
	public static final float PHYSICS_GRAVITY_Y = -0.4f;

	public static final float BEAR_SPEED = 30.0f;
	public static final float BEAR_SPEED_JUMP = 50.0f;
	public static final float BEAR_JUMP_IMPULSE = 2000.0f;
	
	// Map blocks
	public static final int MAP_NULL = 0;
	public static final int MAP_BLOCK = 1;
	
	public static int getBodyType(final String type) {
		
		switch (type) {
		
			case "dynamic":
				return Const.BODY_TYPE_DYNAMIC;
				
			case "kinematic":
				return Const.BODY_TYPE_KINEMATIC;
		
			case "static":
			default:
				return Const.BODY_TYPE_STATIC;
		}
	}

	public static int getObjType(final String type) {
		
		switch (type) {
			
			case "object":
				return Const.OBJ_TYPE_OBJECT;
				
			case "creature":
				return Const.OBJ_TYPE_CREATURE;

			case "null":
			default:
				return Const.OBJ_TYPE_NULL;
		}
	}
}