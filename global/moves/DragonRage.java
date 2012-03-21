package global.moves;

import pokemon.*;

public class DragonRage extends Move{
	
	public DragonRage(){
		type = 15;
		PP = 10;
		accuracy = 100;
		name = "Dragon Rage";
	}
	
	public void useMove(Pokemon user, Pokemon enemy){
	
		System.out.format("%s used %s! %n", user.name, name);	
		
		if(global.Formulas.attack_hit(user, enemy, accuracy) == false)
			return;
	
		enemy.inflictDamage(40);
		
	}
	
}