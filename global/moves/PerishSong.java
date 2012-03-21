package global.moves;

import pokemon.Pokemon;

public class PerishSong extends Move{

	public PerishSong(){
		name = "Perish Song";
		PP = 5;
		type = 7; // 3 = Poison
		accuracy = 0;
	}
	
	public void useMove(Pokemon user, Pokemon enemy){
		
		// This attack never misses.
		
		System.out.format("%s used Perish Song!%n", user.name);
		
		int failure = 1;
		
		inflict_guard1:{
			for(int a = 0; a < user.move_statuses; ++a){
				if(user.move_statusID[a] == 3){
					break inflict_guard1;
				}
				// if the status is already there, do nothing
			}
			user.inflictMoveStatus(3);
			failure = 0;
		}
		
		inflict_guard2:{
			for(int a = 0; a < enemy.move_statuses; ++a){
				if(enemy.move_statusID[a] == 3){
					break inflict_guard2;
				}
				// if the status is already there, do nothing
			}
			enemy.inflictMoveStatus(3);			
			failure = 0;
		}
		
		if(failure == 1){
			System.out.println("But it failed!");
		}
		
	}
}