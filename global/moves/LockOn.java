package global.moves;

import pokemon.*;

public class LockOn extends Move{
	
	public LockOn(){
		name = "Lock On";
		PP = 5;
	}
	
	public void useMove(Pokemon user, Pokemon enemy){
		
		// The next attack never misses.
		
		System.out.format("%s used Lock-on!%n", user.name);
		
		if(enemy.hasMoveStatus(4) == false){
			System.out.format("%s identified %s!%n", user.name, enemy.name);
			user.inflictMoveStatus(4);
		}else{
			System.out.println("But it failed!");
		}
		
	}
	
}