package global.moves;

import pokemon.*;

public class NightShade extends Move{
	
	public NightShade(){
		type = 7;
		PP = 15;
		name = "Night Shade";
		accuracy = 100;
		// Night Shade is its own move, so set all the variables here.
	}
	
	public void useMove(Pokemon user, Pokemon enemy){
	
		System.out.format("%s used %s! %n", user.name, name, user.Attack, enemy.Defense);	
		
		if(global.Formulas.attack_hit(user, enemy, accuracy) == false)
			return;
	
		enemy.inflictDamage(user.level);
		
		} // end physical formula
	
}