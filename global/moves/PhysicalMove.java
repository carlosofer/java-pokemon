package global.moves;

import pokemon.*;

public class PhysicalMove extends Move{
	
	public PhysicalMove(String _name, String _description, int _power, byte _PP, int _accuracy, int _type){
		name = _name;
		description = _description;
		power = _power;
		PP = _PP;
		accuracy = _accuracy;
		type = _type;
	}
	
	public void useMove(Pokemon user, Pokemon enemy){
	
		System.out.format("%s used %s! %n", user.name, name, user.Attack, enemy.Defense);	
		
		if(global.Formulas.attack_hit(user, enemy, accuracy) == false)
			return;
		
		enemy.inflictDamage(global.Formulas.move_damage(user, power, type, enemy));
		
	}
	
}