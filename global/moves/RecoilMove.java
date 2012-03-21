package global.moves;

import pokemon.Pokemon;

public class RecoilMove extends Move{

	int recoil_coeff = 0;
	
	// Physical move that does recoil damage. All moves that do recoil damage are physical, so...
	
	public RecoilMove(String _name, String _description, int _power, byte _PP, int _accuracy, int _type, int recoilpct){
		name = _name;
		description = _description;
		power = _power;
		PP = _PP;
		accuracy = _accuracy;
		type = _type;
		recoil_coeff = recoilpct;
	}
	
	public void useMove(Pokemon user, Pokemon enemy){

		// Once again, kudos to Bulbapedia for the formula.
	
		System.out.format("%s used %s! %n", user.name, name, user.Attack, enemy.Defense);	
			
		if(global.Formulas.attack_hit(user, enemy, accuracy) == false)
			return;
		
		enemy.inflictDamage(global.Formulas.move_damage(user, power, type, enemy));
		user.inflictDamage(global.Formulas.move_damage(user, power, type, enemy) / recoil_coeff);
		
		} // end physical formula
	
}