package global;

import java.io.File;
import java.io.IOException;

public class InputManager{
	
	public boolean[] arrow_keydown = new boolean[4]; // left, up, right, down
	public boolean A_keydown = false;
	public boolean B_keydown = false;
	public boolean key_input_enabled = true; // if this is false, key input is temporarily disabled
	
	public void Form_KeyDown(int keycode){
		if(keycode >= 37 && keycode <= 40){
			if(arrow_keydown[keycode - 37] == false)
				global.Managers.GraphMan.send_arrowkey_signal(keycode - 37);
			arrow_keydown[keycode - 37] = true;
		}else if(keycode == 32){
			if(A_keydown == false) // new keypress, not just a repeat
				global.Managers.GraphMan.send_A_key_signal();
			A_keydown = true;
		}else if(keycode == (int)'X'){
			if(B_keydown == false) // new keypress, not just a repeat
				global.Managers.GraphMan.send_B_key_signal();
			B_keydown = true;
		}else if(keycode == (int)'A'){
			// DEBUG: initialize the battle engine
			
			global.GameState.opponent.setName("Pokemon Trainer Bill");
		
			global.GameState.player.addPokemon(pokemon.PokemonCollection.Kompras);
			global.GameState.player.addPokemon(pokemon.PokemonCollection.Dratina);
			
			global.GameState.opponent.addPokemon(pokemon.PokemonCollection.Suzume);
			try {
				global.GameState.opponent.addPokemon(global.Loader.loadPokemon(new File("testing.pkmn")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			battle.BattleEngine.Battle(global.GameState.player, global.GameState.opponent);
		}
	}
	
	public void Form_KeyUp(int keycode){
		if(keycode >= 37 && keycode <= 40){
			arrow_keydown[keycode - 37] = false;
		}else if(keycode == 32){
			A_keydown = false;
		}else if(keycode == (int)'X'){
			B_keydown = false;
		}
	}
	
	public void clear_all_keys(){
		for(int a = 0; a < 4; ++a){
			arrow_keydown[a] = false;
		}A_keydown = false;
	}
	
}