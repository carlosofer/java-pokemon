package global;

import global.maps.MapCollection;

import java.io.File;
import java.io.IOException;

import battle.BattleEngine;

public class Startup{
	
	public static int isCLI = 0; // change this to 1 to switch between command-line and GUI modes.
	
	static Thread animation;
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException{
		
		if(isCLI == 0){
			
		   	Player player = new Player(2500);
			Trainer opponent = new Trainer(2500, 1);
			
			opponent.setName("Pokemon Trainer Bill");
			
			InitPokemon.initPokemon();
		
			player.addPokemon(pokemon.PokemonCollection.Kompras);
			player.addPokemon(pokemon.PokemonCollection.Dratina);
			
			opponent.addPokemon(pokemon.PokemonCollection.Suzume);
			opponent.addPokemon(global.Loader.loadPokemon(new File("testing.pkmn")));
			
			BattleEngine.Battle(player, opponent);
			
		}else{

			global.Managers.AllMaps = new MapCollection();
			global.Managers.GraphMan = new GraphicsManager();
			global.Managers.WindowMan = new Window("Java Pokemon");
			global.Managers.InputMan = new InputManager();
	
			global.Managers.WindowMan.setVisible(true); // initialize the main window
			
			global.Managers.GraphMan.update_screen();
			
			global.Managers.GraphMan.player.setInitialLocation(7, 5); // This has to go here. Seriously.
			
			while(true){
				try {
					animation.sleep(15); // 60 FPS
					global.Managers.GraphMan.advance_one_frame();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			// destroy animation later on.
			
		}
		
	}
	
	
}
