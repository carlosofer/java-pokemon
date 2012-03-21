package battle;
import java.util.Scanner;

import pokemon.*;
import global.*;
import global.sprites.Sprite;

// Integrating the battle engine is the next step.

public class BattleEngine{
	
	static boolean waiting_for_animation = false;
	
	static Trainer trPlayer;
	static Trainer trOpponent;
	
	static Pokemon previous_firstmover;
	static Pokemon previous_secondmover;
	
	static Sprite pkmn1;
	static Sprite pkmn2;
	
	public static void Battle(Trainer trainer1, Trainer trainer2){
		
		trPlayer = trainer1;
		trOpponent = trainer2;
		
		initBattle();
		
		if(trainer2.getTrainerClass() == -1){
			if(global.Startup.isCLI == 0)
				System.out.format("Wild %s appeared! %n", pokemon.Names.defaultName[trainer2.getActive().getSpecies()]);
			else
				global.Managers.GraphMan.display_text("Wild " + pokemon.Names.defaultName[trainer2.getActive().getSpecies()] + "appeared!");
		}else{
			if(global.Startup.isCLI == 0)
				System.out.format("%s wants to battle! %n", trainer2.name); // include the classes later
			else
				global.Managers.GraphMan.display_text(trainer2.name + " wants to battle!");
			if(global.Startup.isCLI == 0)
				System.out.format("%s sent out %s! %n", trainer2.name, pokemon.Names.defaultName[trainer2.getActive().getSpecies()]);
			else
				global.Managers.GraphMan.display_text(trainer2.name + " sent out " + pokemon.Names.defaultName[trainer2.getActive().getSpecies()] + "!");
		}
		if(global.Startup.isCLI == 0)
			System.out.format("Go, %s! %n", trainer1.getActive().name);
		else
			global.Managers.GraphMan.display_text("Go, " + trainer1.getActive().name + "!");
		
		if(global.Startup.isCLI == 0){
			while(trPlayer.getActive().HP > 0 && trOpponent.getActive().HP > 0){
				wait_for_input();
			}
		}
		
	}
	
	public static void initBattle(){
		global.GameState.setWorldType(1);
		// reset the stats of all Pokemon
		for(int a = 0; a < trPlayer.getPartySize(); ++a)
			trPlayer.getParty()[a].resetStats();
		for(int a = 0; a < trOpponent.getPartySize(); ++a)
			trOpponent.getParty()[a].resetStats();
	}
	
	public static void wait_for_input(){

		Scanner inp1 = new Scanner(System.in);
		// does exactly what it says - waits for input.

			int m = 0, n, action;
			
			// have command-line input for now
			
			if(trPlayer.getActive().user_status != 0){
				// If the user of an attack has something to do, do not let the player do anything
				m = -1;
				// A bunch of different things here will affect whether an attack can be used or not.
				if(trPlayer.getActive().user_status == 1){                                 
					System.out.format("%s must recharge! %n", trPlayer.getActive().name);
				}
			}
			else{
				// TODO: implement a "menu state".
				
				System.out.format("What should [trainer] do? (1 = Fight 2 = Switch) ");
				action = inp1.nextInt();
				if(action == 1){
					if(trPlayer.getActive().PP[0] == 0 &&
						trPlayer.getActive().PP[1] == 0 &&
						trPlayer.getActive().PP[2] == 0 &&
						trPlayer.getActive().PP[3] == 0){
						m = 19; // Struggle
					}else{
						System.out.format("What should %s do? (0 = %s 1 = %s 2 = %s 3 = %s) ", trPlayer.getActive().name,
										global.MoveList.movelist[trPlayer.getActive().moves[0]].name,
										global.MoveList.movelist[trPlayer.getActive().moves[1]].name,
										global.MoveList.movelist[trPlayer.getActive().moves[2]].name,
										global.MoveList.movelist[trPlayer.getActive().moves[3]].name);
						m = inp1.nextInt(); // choose a move
						while (trPlayer.getActive().PP[m] == 0){
							System.out.println("There's no PP left for this move!");
							m = inp1.nextInt();
						}
					}
				}/*else if(action == 2){
					if(trPlayer.getActive() == pokemon.PokemonCollection.Dratina)
						trPlayer.getActive() = pokemon.PokemonCollection.Kompras;
					else
						trPlayer.getActive() = pokemon.PokemonCollection.Dratina;
					System.out.println("The trainer switched Pokemon!");
					m = -1;
				}*/
				// TODO: Implement proper switching some time
			}
			
			if(trOpponent.getActive().user_status != 0){
				n = -1;
				if(trOpponent.getActive().user_status == 1){                                 
					System.out.format("%s must recharge! %n", trOpponent.getActive().name);
				}
			}
			else if(trOpponent.getActive().PP[0] == 0 &&
					trOpponent.getActive().PP[1] == 0 &&
					trOpponent.getActive().PP[2] == 0 &&
					trOpponent.getActive().PP[3] == 0){
				n = 19; // Struggle
			}else{
				n = (int)(Math.random() * 4);
				while(trOpponent.getActive().PP[n] == 0){
					n = (int)(Math.random() * 4);
				} // use a random move that still has PP left
			}
			
			pass_one_turn(trPlayer.getActive(), trOpponent.getActive(), m, n);
			
			// if the trainer's Pokemon faints, switch one out
			
			if(trPlayer.getActive().HP <= 0){
				// the player must switch another Pokemon out
				if (trPlayer.unfainted() > 0){
					System.out.print("Switch another Pokemon out! ( ");
					for(int a = 0; a < trPlayer.getPartySize(); ++a){
						if(trPlayer.getParty()[a].HP > 0)
							System.out.format("%d = %s ", a, trPlayer.getParty()[a].name);
					}
					System.out.print(")");
					int switchout = inp1.nextInt();
					while(trPlayer.getParty()[switchout].HP <= 0){
						System.out.println("There's no will to battle!");
						switchout = inp1.nextInt();
					}
					trPlayer.switchPokemon(switchout);
					System.out.format("Go, %s! %n", trPlayer.getActive().name);
				} else {
					System.out.println("The trainer is out of usable Pokemon!");
					System.out.format("%s: Hahaha, that'll teach you to mess with me!%n", trOpponent.name);
					System.out.format("The trainer paid P%d in prize money!%n", 24 * trPlayer.getActive().level);
					System.out.println("The trainer whited out!");
				}
			}
			
			if(trOpponent.getActive().HP <= 0){
				// the opponent will switch another Pokemon out, but the program asks the player first
				
				if (trOpponent.unfainted() > 0){
					
					// the other trainer switches in another random Pokemon
					int opponent_swo = 0;
					while(trOpponent.getParty()[opponent_swo].HP <= 0){
						opponent_swo = (int)(Math.random() * trOpponent.getPartySize());
					}
					trOpponent.switchPokemon(opponent_swo);
					System.out.format("%s is about to send out %s.%n", trOpponent.name, trOpponent.getActive().name);
					
					// do all this mumbo-jumbo
					if (trPlayer.unfainted() > 0){
						System.out.print("Switch another Pokemon out? ( ");
						for(int a = 0; a < trPlayer.getPartySize(); ++a){
							if(trPlayer.getParty()[a].HP > 0)
								System.out.format("%d = %s ", a, trPlayer.getParty()[a].name);
						}
						System.out.print("-1 = [Cancel] )");
						int switchout = inp1.nextInt();
						
						if(switchout == -1){
							// do nothing
						}else{
							while(trPlayer.getParty()[switchout].HP <= 0){
								System.out.println("There's no will to battle!");
								switchout = inp1.nextInt();
							}
						}
						
						// TODO: add in the animations here
						
						System.out.format("%s sent out %s! %n", trOpponent.name, pokemon.Names.defaultName[trOpponent.getActive().getSpecies()]);
						
						if(switchout == -1){
							// do nothing
						}else{
							trPlayer.switchPokemon(switchout);
							System.out.format("Go, %s! %n", trPlayer.getActive().name);
						}
						
					} else {
						System.out.println("The trainer is out of usable Pokemon!");
						System.out.println("The trainer whited out!");
					}
				} else { // if the other trainer's all out of Pokemon
					System.out.format("The trainer defeated %s! %n", trOpponent.name);
					System.out.format("%s: I bit the dust.%n", trOpponent.name);
					System.out.format("The trainer got P%d for winning! %n", 24 * trOpponent.getActive().level);
				}
			}
			
	}
	
	public static void end_battle(){
		
	}
	
	public static void pass_one_turn(Pokemon pokemon1, Pokemon pokemon2, int move1, int move2){

		// if move1 or move2 is < -1, it means "use an item"
		// if it's -1, it means "do nothing"
		
		Pokemon Mover1;
		Pokemon Mover2;
		int firstmove = 0;
		int secondmove = 0;
		
		// in the case of using Fly
		if(pokemon1.user_status == 2 || pokemon2.user_status == 2){
			// whoever moved first moves first again.
			Mover1 = previous_firstmover; Mover2 = previous_secondmover;
			if(previous_firstmover == pokemon1)
				{firstmove = move1; secondmove = move2;}
			else if(previous_firstmover == pokemon2)
				{firstmove = move2; secondmove = move1;}
		}else{
			if(pokemon1.Speed >= pokemon2.Speed){
				Mover1 = pokemon1; Mover2 = pokemon2;
				firstmove = move1; secondmove = move2;
			}else{
				Mover1 = pokemon2; Mover2 = pokemon1;
				firstmove = move2; secondmove = move1;
			}
		}
	
battle_turn:{
		
		// Mover1 moves first, then Mover2
		
		previous_firstmover = Mover1;
		previous_secondmover = Mover2;
		
		if(Mover1.user_status == 2){
			global.MoveList.movelist[8].useMove(Mover1, Mover2);
			Mover1.user_status = 0;
			// Automatically use Fly in this scenario
		}else if(firstmove == -1){
			// do nothing
		}else if(firstmove == 19){ // Struggle
			global.MoveList.movelist[19].useMove(Mover1, Mover2);
		}else{
			battle.Attack.useAttack(Mover1, firstmove, Mover2);
		}
		
		if(Mover2.HP <= 0){
			System.out.format("%s fainted!%n", Mover2.name);
		}
		if(Mover1.HP <= 0){
			System.out.format("%s fainted!%n", Mover1.name);
		}
		if(Mover1.HP <= 0 || Mover2.HP <= 0){
			break battle_turn;
		} // end the turn early
		
		// next Pokemon's turn
		
		if(Mover2.user_status == 2){
			global.MoveList.movelist[8].useMove(Mover2, Mover1);
			Mover2.user_status = 0;
			// Automatically use Fly in this scenario
		}else if (secondmove == -1){
			// do nothing
		}else if(firstmove == 19){ // Struggle
			global.MoveList.movelist[19].useMove(Mover2, Mover1);
		}else{
			battle.Attack.useAttack(Mover2, secondmove, Mover1);
		}
		
		if(Mover1.HP <= 0){
			System.out.format("%s fainted!%n", Mover1.name);
		}
		if(Mover2.HP <= 0){
			System.out.format("%s fainted!%n", Mover2.name);
		}

} // :battle_turn
		
		if(Mover1.user_status != 2 && Mover2.user_status != 2){ // During Fly, no statuses are counted
			if(Mover1.HP > 0)
				Mover1.applyAllStatuses();
			if(Mover2.HP > 0)
				Mover2.applyAllStatuses();
		}
		
		System.out.println();
			
	}
	
}