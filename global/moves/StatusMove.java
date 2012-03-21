// Second type of move - 

package global.moves;

import pokemon.*;

public class StatusMove extends Move{
	
	int target; int[] deltastats = {0, 0, 0, 0, 0, 0, 0, 0};
	
	public StatusMove(String _name, String _description, byte _PP, int _target, int _accuracy, int deltastat1, int deltastat2, int deltastat3, int deltastat4, int deltastat5, int deltastat6, int deltastat7){
		name = _name;
		description = _description;
		target = _target;
		PP = _PP;
		accuracy = _accuracy;
		deltastats[1] = deltastat1;
		deltastats[2] = deltastat2;
		deltastats[3] = deltastat3;
		deltastats[4] = deltastat4;
		deltastats[5] = deltastat5;
		deltastats[6] = deltastat6;
		deltastats[7] = deltastat7;
	}
	
	// Target index:
	// 1 = user
	// 2 = enemy
	
	// later, implement:
	// 3 = both allies
	// 4 = both enemies
	// 5 = everyone except for user
	
	// Index:
	// 0 = nothing (HP, technically)
	// 1 = Attack
	// 2 = Defense
	// 3 = Sp.Atk
	// 4 = Sp.Def
	// 5 = Speed
	// 6 = Accuracy
	// 7 = Evasion

	public void useMove(Pokemon user, Pokemon enemy){
	
		System.out.format("%s used %s!%n", user.name, name);	
		
		if(global.Formulas.attack_hit(user, enemy, accuracy) == false)
			return;
		
		// no damage formula here, stats are lowered instead
		
		switch(target){
			case 1:
				applyStats(user); break;
			case 2:
				applyStats(enemy); break;
			case 3:
				// applyStats to both allies
			case 4:
				// applyStats to both enemies
			default:
				break;
		}
		
	}
	
	void applyStats(Pokemon target){
		target.changeStats(deltastats);
	}
	
}