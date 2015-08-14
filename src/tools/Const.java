package tools;

public class Const {
	
	// UI
	public static final String UI_MENU = "interface/menu.json";
	public static final String UI_GAME = "interface/game.json";
	
	// Assets
	// texture
	public static final String TEX_NULL = "assets/tex/null.png";
	
	// textures menu animation
	public static final String TEX_MENU_ANIMATION_BEAR = "assets/tex/menu/bear.png";
	public static final String TEX_MENU_ANIMATION_BACK_CIRCLE = "assets/tex/menu/back_circle.png";
	public static final String TEX_MENU_ANIMATION_FLAMES_ATLAS = "assets/tex/menu/flame_atlas.png";
	
	// textures location
	public static final String TEX_BLOCK = "assets/tex/location/block.png";
	public static final String TEX_BACKGROUND_WALL = "assets/tex/location/background_wall.png";
	public static final String TEX_STAIRS = "assets/tex/location/stairs.png";
	public static final String TEX_WATER = "assets/tex/location/water.png";

	// textures background
	public static final String TEX_BACKGROUND_LAYER_1 = "assets/tex/location/back/bg-1.png";
	public static final String TEX_BACKGROUND_LAYER_2 = "assets/tex/location/back/bg-2.png";
	
	// textures creatures
	public static final String TEX_PLAYER = "assets/tex/creatures/player.png";
	public static final String TEX_PLAYER_STAND = "assets/tex/creatures/player_stand.png";
	public static final String TEX_PLAYER_STAIRS = "assets/tex/creatures/player_stairs.png";
	
	// textures ammo
	public static final String TEX_BULLET = "assets/tex/bullet.png";
	
	// font
	public static final String FONT_DEFAULT = "assets/font/font.ttf";
	
	// OST
	public static final String OST_MENU = "assets/music/menu.ogg";
	
	// Editor
	public static final int GRID_STEP = 20;
	public static final int EDITOR_BACKGROUND_BLUEPRINT_COLOR = 0x2F4E81;
	
	// Objects type
	public static final int OBJ_TYPE_NULL = 0;
	public static final int OBJ_TYPE_OBJECT = 1;
	public static final int OBJ_TYPE_CREATURE = 2;
	
	// Objects ID's
	public static final int OBJ_BULLET = -1;
	public static final int OBJ_NULL = 0;
	public static final int OBJ_PLAYER = 1;
	public static final int OBJ_BLOCK = 2;
	public static final int OBJ_BACKGROUND_WALL = 3;
	public static final int OBJ_STAIRS = 4;
	public static final int OBJ_WATER = 5;
	public static final int OBJ_BLOCK_VERTICAL = 6;
	public static final int OBJ_BLOCK_CUBE = 7;
	public static final int OBJ_NPC_WOMAN = 8;
	public static final int OBJ_BACKGROUND_HOUSE1 = 9;
	public static final int OBJ_GRASS = 10;
	public static final int OBJ_GRASS_L = 11;
	public static final int OBJ_GRASS_R = 12;
	public static final int OBJ_GRASS_LR = 13;
	
	// Animation
	public static final int ANIMATION_DIRECT_LEFT = 0;
	public static final int ANIMATION_DIRECT_RIGHT = 1;
	
	// Player animation normal movement
	public static final int ANIMATION_ARRAY_SIZE = 7;
	public static final int ANIMATION_IDLE_0 = 0;
	public static final int ANIMATION_MOVE_1 = 1;
	public static final int ANIMATION_MOVE_2 = 2;
	public static final int ANIMATION_MOVE_3 = 3;
	public static final int ANIMATION_MOVE_4 = 4;
	public static final int ANIMATION_MOVE_5 = 5;
	public static final int ANIMATION_MOVE_6 = 6;

	// Player animation stair
	public static final int ANIMATION_STAIR_ARRAY_SIZE = 5;
	public static final int ANIMATION_STAIR_STEP_0 = 0;
	public static final int ANIMATION_STAIR_STEP_1 = 1;
	public static final int ANIMATION_STAIR_STEP_2 = 2;
	public static final int ANIMATION_STAIR_STEP_3 = 3;
	public static final int ANIMATION_STAIR_STEP_4 = 4;
	
	// Player animation stand movement and upping
	public static final int ANIMATION_STAND_MOVEMENT_ARRAY_SIZE = 8;
	public static final int ANIMATION_STAND_GET_UP_ARRAY_SIZE = 4;
	public static final int ANIMATION_STAND_ARRAY_SIZE = ANIMATION_STAND_MOVEMENT_ARRAY_SIZE + ANIMATION_STAND_GET_UP_ARRAY_SIZE;
	
	public static final int ANIMATION_STAND_MOVEMENT_IDLE_0 = 0;
	public static final int ANIMATION_STAND_MOVEMENT_1 = 1;
	public static final int ANIMATION_STAND_MOVEMENT_2 = 2;
	public static final int ANIMATION_STAND_MOVEMENT_3 = 3;
	public static final int ANIMATION_STAND_MOVEMENT_4 = 4;
	public static final int ANIMATION_STAND_MOVEMENT_5 = 5;
	public static final int ANIMATION_STAND_MOVEMENT_6 = 6;
	public static final int ANIMATION_STAND_MOVEMENT_7 = 7;
	
	public static final int ANIMATION_STAND_GET_UP_0 = 8;
	public static final int ANIMATION_STAND_GET_UP_1 = 9;
	public static final int ANIMATION_STAND_GET_UP_2 = 10;
	public static final int ANIMATION_STAND_GET_UP_3 = 11;
	
	// Physic
	public static final int BODY_TYPE_STATIC = 0;
	public static final int BODY_TYPE_DYNAMIC = 1;
	public static final int BODY_TYPE_KINEMATIC = 2;
	
	// Collide FileterCategory
	public static final short CATEGORY_NULL = 0x0000;
	public static final short CATEGORY_PLAYER_NORMAL = 0x0001;
	public static final short CATEGORY_PLAYER_GHOST = 0x0002;
	public static final short CATEGORY_NPC_NORMAL = 0x0004;
	public static final short CATEGORY_NPC_GHOST = 0x0008;
	public static final short CATEGORY_BLOCK = 0x0010;
	public static final short CATEGORY_INTERACT = 0x0020;
	public static final short CATEGORY_BULLET = 0x0040;
	public static final short CATEGORY_CORPSE = 0x0080;
	public static final short CATEGORY_9 = 0x0100;
	public static final short CATEGORY_10 = 0x0200;
	public static final short CATEGORY_11 = 0x0400;
	public static final short CATEGORY_12 = 0x0800;
	public static final short CATEGORY_13 = 0x1000;
	public static final short CATEGORY_14 = 0x2000;
	public static final short CATEGORY_15 = 0x4000;
	
	// Collide FilterMask
	public static final short MASK_NULL          = CATEGORY_NULL;
	public static final short MASK_PLAYER_NORMAL = CATEGORY_BLOCK | CATEGORY_INTERACT | CATEGORY_NPC_NORMAL | CATEGORY_BULLET;
	public static final short MASK_PLAYER_GHOST  = CATEGORY_INTERACT | CATEGORY_NPC_NORMAL | CATEGORY_BULLET;
	public static final short MASK_NPC_NORMAL    = CATEGORY_PLAYER_NORMAL | CATEGORY_PLAYER_GHOST | CATEGORY_INTERACT | CATEGORY_BULLET | CATEGORY_BLOCK;
	public static final short MASK_NPC_GHOST     = CATEGORY_PLAYER_NORMAL | CATEGORY_PLAYER_GHOST | CATEGORY_INTERACT | CATEGORY_BULLET;
	public static final short MASK_BLOCK         = CATEGORY_PLAYER_NORMAL | CATEGORY_NPC_NORMAL   | CATEGORY_BULLET   | CATEGORY_CORPSE;
	public static final short MASK_INTERACT      = CATEGORY_PLAYER_NORMAL | CATEGORY_PLAYER_GHOST | CATEGORY_NPC_NORMAL | CATEGORY_NPC_GHOST;
	public static final short MASK_BULLET        = CATEGORY_PLAYER_NORMAL | CATEGORY_PLAYER_GHOST | CATEGORY_NPC_NORMAL | CATEGORY_NPC_GHOST | CATEGORY_BLOCK;
	public static final short MASK_CORPSE        = CATEGORY_BLOCK;
	
	// Location gravity
	public static final float PHYSICS_GRAVITY_X =  0.0f;
	public static final float PHYSICS_GRAVITY_Y = -140.0f;

	// Bear data
	public static final float BEAR_SPEED = 30.0f;
	public static final float BEAR_SPEED_JUMP = 50.0f;
	
	// Map blocks
	public static final int MAP_NULL = 0;
	public static final int MAP_BLOCK = 1;
	
	// Weapon
	public static final int WEAPON_MAIN_NONE = 0;
	public static final int WEAPON_MAIN_PISTOL_SINGLE = 1;
	public static final int WEAPON_MAIN_PISTOL_DOUBLE = 2;
	public static final int WEAPON_MAIN_REVOLVER = 3;
	public static final int WEAPON_MAIN_SHOTGUN = 4;
	public static final int WEAPON_MAIN_MACHINE_GUN = 5;
	
	public static final int WEAPON_USABLE_NONE = 0;
	public static final int WEAPON_MAIN_RAKE = 1;
	public static final int WEAPON_USABLE_GRENADE = 2;
	public static final int WEAPON_USABLE_MOLOTOV = 3;
	public static final int WEAPON_USABLE_BAZOOKA = 4;
	public static final int WEAPON_USABLE_SLOWMO = 5;
	public static final int WEAPON_USABLE_MEDICINE = 6;
	public static final int WEAPON_USABLE_DEAD_RING = 7;
	public static final int WEAPON_USABLE_FIRE_PILLS = 8;

	// Shoot delay
	public static final long WEAPON_SHOOT_DELAY = 100;
	
	// Bullet impulse
	public static final float WEAPON_BULLET_IMPULSE_X = 300.0f;

	// Bullets lifetime
	public static final int BULLET_LIFETIME = 100;
	
	// AI
	public static final int AI_CONTACT_RADIUS = 12;
	
	// Editor plan-save folder
	public static final String PLANS_FOLDER = "plans";
	public static final String PLANS_FILE = "levelPlan";
	
	public static final String MAPS_FOLDER = "maps";
	
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