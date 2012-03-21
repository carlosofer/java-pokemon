package global.moves;

import pokemon.Pokemon;

public class Struggle extends Move{
	
	int recoil_coeff = 0;
	
	// Physical move that does recoil damage. All moves that do recoil damage are physical, so...
	
	public Struggle(){
		name = "Struggle";
		description = "A last-ditch move every Pokemon knows. Hits with recoil.";
		power = 50;
		PP = 1;
		accuracy = 100;
		type = 0;
		recoil_coeff = 4;
	}
	
	public void useMove(Pokemon user, Pokemon enemy){

		// Once again, kudos to Bulbapedia for the formula.
	
		System.out.format("%s used %s! %n", user.name, name, user.Attack, enemy.Defense);	
			
		// Struggle always deals x1 damage, unless the user is burned.
		
		double damage = 0;
		damage = ((user.level * 2 + 10) / 210.0) * (power) * (user.Attack / (double)enemy.Defense); // calculation step 

		if(user.status == 3){ // if the user is burned
			damage *= 0.5;
		}
		
		damage *= (0.85 + Math.random() * 0.15); // apply a random multiplier, from 0.85 to 1.00
		
		int disp_damage = Math.max(1, (int)(damage));
		
		enemy.inflictDamage(disp_damage);
		user.inflictDamage(disp_damage / recoil_coeff);
		
		} // end physical formula
	
}