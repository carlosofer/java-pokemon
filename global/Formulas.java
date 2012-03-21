package global;

import pokemon.*;

public class Formulas{
	
	public static int move_damage(Pokemon user, int power, int type, Pokemon enemy){
		double[] effect_mult = {0, 0.5, 1, 2};
		
		double damage = 0;
		double multiplier = 1;
		damage = ((user.level * 2 + 10) / 210.0) * (power) * (user.Attack / (double)enemy.Defense); // calculation step 

		// in the original formula, the "210.0" is actually "250.0". There's also a +2 at the end that I don't account for
		
		multiplier = (effect_mult[global.moves.Effectiveness.effectiveness[type][enemy.type1] + 2]);
		
		if(enemy.type2 != -1){
			multiplier *= effect_mult[global.moves.Effectiveness.effectiveness[type][enemy.type2] + 2]; // effectiveness
		}
			
		damage *= multiplier;
		
		if(type == user.type1 || type == user.type2){
			damage *= 1.5; // STAB
		}
		
		if(user.status == 3){ // if the user is burned
			damage *= 0.5;
		}
		
		damage *= (0.85 + Math.random() * 0.15); // apply a random multiplier, from 0.85 to 1.00
		
		int disp_damage = Math.max(1, (int)(damage));
		
		System.out.format("%s vs. %s", global.GlobalConstants.typenames[type], global.GlobalConstants.typenames[enemy.type1]);
		if(enemy.type2 != -1){
			System.out.format(", %s", global.GlobalConstants.typenames[enemy.type2]);
		}
		System.out.format(": ");
		
		if(multiplier > 1){
			System.out.println("It's super-effective!");
		}else if(multiplier < 1 && multiplier > 0){
			System.out.println("It's not very effective...");
		}else if(multiplier == 0){
			System.out.format("It doesn't affect %s!%n", enemy.name);
			disp_damage = 0;
		}
		
		return disp_damage;
		
	}
	
	public static boolean attack_hit(Pokemon user, Pokemon enemy, int accuracy){
		if(enemy.hasMoveStatus(4))
			return true; // if the enemy has been Locked-On, the attack will NEVER miss (although
						 // it might be blocked by Detect or Protect), even with Fly, Dig, or Dive.
		
		if(enemy.user_status == 3){
			System.out.format("%s protected itself!", enemy.name);
			return false; // if the enemy is using Protect or Detect
		}// Later, implement the moves that cannot be stopped by Protect or Detect
		
		if(Math.random() * 100 >= accuracy * user.Accuracy / enemy.Evasion || enemy.user_status == 2){
			System.out.format("The attack missed!%n");
			return false;
		}
		
		return true;
	}
	
}