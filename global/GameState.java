package global;

public class GameState{
	
	// Where any global information about the current game is stored.
	
	static int player_x = 0, player_y = 0;
	private static int world_type = 0;
	
	// Index of world types
	// 0 = regular overworld
	// 1 = battle
	
	static Player player = new Player(2500);
	static Trainer opponent = new Trainer(2500, 1);
	
	static boolean key_enabled = true; // if this is false, do not allow keyboard input
	
	public GameState(){
		// do nothing
	}

	public static void setWorldType(int world_type) {
		GameState.world_type = world_type;
	}

	public static int getWorldType() {
		return world_type;
	}
	
}