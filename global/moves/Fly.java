package global.moves;

import pokemon.Pokemon;

public class Fly extends Move{
	
	String premove;
	
	public Fly(){
		name = "Fly";
		premove = "flew up high";
		description = "Flies up in the air to strike the enemy later.";
		power = 70;
		PP = 15;
		accuracy = 100;
		type = 2; // Flying
	}
	
	public void useMove(Pokemon user, Pokemon enemy){
		
		if(user.user_status == 0){
	
			System.out.format("%s %s! %n", user.name, premove);	
			
			// the user will then have a status
			
			user.temp_enemy = enemy;
			
			user.user_status = 2;
			user.user_status_turns = 1;
			
		}else if(user.user_status == 2){
			
			System.out.format("%s used %s! %n", user.name, name);

			if(global.Formulas.attack_hit(user, enemy, accuracy) == false)
				return;
			
			enemy.inflictDamage(global.Formulas.move_damage(user, power, type, enemy));
		}
		
	}
	
}