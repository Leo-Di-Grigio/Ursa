package tools;

public class Const {
	
	// UI
	public static final String UI_MENU = "interface/menu.json";
	public static final String UI_GAME = "interface/game.json";
	
	// Assets
	// texture
	public static final String TEX_NULL = "assets/tex/null.png";
	
	public static final String TEX_BEAR_1 = "assets/tex/creatures/bear.png";
	
	// font
	public static final String FONT_DEFAULT = "assets/font/font.ttf";
	
	// Objects type
	public static final int CREATURE_NULL = 0;
	
	public static final int CREATURE_BEAR_1 = 1;
	public static final int CREATURE_BEAR_2 = 2;
	public static final int CREATURE_BEAR_3 = 3;
	public static final int CREATURE_BEAR_4 = 4;
	public static final int CREATURE_BEAR_5 = 5;
	public static final int CREATURE_BEAR_6 = 6;
	public static final int CREATURE_BEAR_7 = 7;
	public static final int CREATURE_BEAR_8 = 8;
	public static final int CREATURE_BEAR_9 = 9;
	
	public static final int CREATURE_CHILD = 10;
	public static final int CREATURE_MOTHER = 11;
	public static final int CREATURE_FATHER = 12;
	public static final int CREATURE_POLICE = 13;
	public static final int CREATURE_SWAT = 14;
	
	public static final int OBJ_BOX = 100;
	
	// Animation
	public static final int ANIMATION_ARRAY_SIZE = 7;
	public static final int ANIMATION_IDLE_0 = 0;
	public static final int ANIMATION_MOVE_1 = 1;
	public static final int ANIMATION_MOVE_2 = 2;
	public static final int ANIMATION_MOVE_3 = 3;
	public static final int ANIMATION_MOVE_4 = 4;
	public static final int ANIMATION_MOVE_5 = 5;
	public static final int ANIMATION_MOVE_6 = 6;
	
	// Navigation	
	public static final int MAP_NODE_PIXEL_SIZE = 32;
	public static final int MAP_NODE_SIZE_X = 128;
	public static final int MAP_NODE_SIZE_Y = 32;
	
	public static final int MAP_PIXEL_SIZE_X = Const.MAP_NODE_PIXEL_SIZE * Const.MAP_NODE_SIZE_X;
	public static final int MAP_PIXEL_SIZE_Y = Const.MAP_NODE_PIXEL_SIZE * Const.MAP_NODE_SIZE_Y;
	
	// Physic
	public static final float PHYSICS_GRAVITY_X =  0.0f;
	public static final float PHYSICS_GRAVITY_Y = -0.4f;

	public static final float BEAR_SPEED = 30.0f;
	public static final float BEAR_SPEED_JUMP = 50.0f;
	
	// Map blocks
	public static final int MAP_NULL = 0;
	public static final int MAP_BLOCK = 1;
}