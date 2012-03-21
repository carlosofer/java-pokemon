package global.moves;

import pokemon.*;

public class Toxic extends Move{
	
	public Toxic(){
		name = "Toxic";
		PP = 10;
		type = 3; // 3 = Poison
		accuracy = 85;
	}
	
	public void useMove(Pokemon user, Pokemon enemy){
		
		System.out.format("%s used Toxic!%n", user.name);
		
		if(global.Formulas.attack_hit(user, enemy, accuracy) == false)
			return;
		
		enemy.inflictMoveStatus(2); // badly poisons the opponent
	}
	
}