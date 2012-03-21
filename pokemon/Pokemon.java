package pokemon;

import battle.StatModifiers;
import battle.movestatus.*;

public class Pokemon{

// At first, the values will be unencrypted. But later, the values will be encrypted with a key.

	short species = 0; // There are a total of 493 species. 0 = Missingno., a placeholder. The rest are in Ndex order.
	public short[] IV = {0, 0, 0, 0, 0, 0}; // There are a total of 6 IV's, from 0 to 31.
	public short[] EV = {0, 0, 0, 0, 0, 0}; // The limit for this is 510 at max IV's.
	public int[] personality_var = {0, 0}; // determines nature, gender, etc.
	public int shinyvar = 0; // my engine will have a separate identifier for determining shininess.
	
	// The chance will still be 1/8192 to get a shiny Pokemon. How the value is calculated:
	// A random number from 0 to 65535 is calculated and simply xor'd with the OT's hidden ID. If this value is less than 8, the Pokemon is shiny. No need to get personality values into it.
	
	public String OT_name; // Trainer's name, up to 10 bytes
	public String name; // nickname
	public int[] OT_ID = new int[2]; // The trainer's ID.
	
	public int gender = 0; // 0 = female, 1 = male, not determined in data file
	
	public int type1 = 0, type2 = 0; // types, just because they're too tedious to actually program into the game engine.
	
	public int experience = 0, level = 0, happiness = 0;
	public int HP = 0;
	public int[] stats = {0, 0, 0, 0, 0, 0}; // HP, ATK, DEF, SAK, SDF, SPD
	public int[] stage = {0, 0, 0, 0, 0, 0, 0, 0}; // null, ATK, DEF, SAK, SDF, SPD, ACC, EVN
	
	public int Attack = 0, Defense = 0, spAttack = 0, spDefense = 0, Speed = 0; // use this to calculate the multipliers
	public double Accuracy = 1, Evasion = 1;
	
	public short item_held = 0; // item held, implement this LATER
	
	public int experience_points = 0;
	
	public short[] moves = {0, 0, 0, 0};
	public byte[] PP = {0, 0, 0, 0};
	public byte[] maxPP = {0, 0, 0, 0};
	
	public byte status = 0; // status ailment
		// List of permanent status ailments
			// 1 = Poison
			// 2 = Paralysis
			// 3 = Burn
			// 4 = Freeze
			// 5 = Sleep
	public byte pokerus_status = 0;
	
	public byte move_statuses = 0; // number of move status currently in effect
	public MoveStatus[] move_status = new MoveStatus[4];
	public int[] move_statusTurns = {0, 0, 0, 0};
	public int[] move_statusTotTurns = {0, 0, 0, 0};
	public int[] move_statusID = {0, 0, 0, 0};
	
	public int ability = 0;
	
	public int user_status = 0, user_status_turns = 0;
	public Pokemon temp_enemy; // This is used for moves such as Fly, Dive, etc.
	// user-inflicted statuses like Protect and Endure never occur more than once at a time.
	// This variable also keeps track of stuff like Hyper Beam.
	
	public int substitute = 0;
	// if a Substitute is currently in play, this will be the substitute's HP count.
	
	public byte forme = 0; // Forme will always be at 0 for single-form Pokemon.

	// methods
	
	public Pokemon(){
		// do nothing, everything is already initialized.
	}
	
	public Pokemon(short inp_species, String inp_name, int inp_level){
		if(inp_name.length() > 10){
			name = inp_name.substring(0,9);
		}else if(inp_name.length() == 0){
			name = pokemon.Names.defaultName[inp_species];
		}else{
			name = inp_name;
		}
		setSpecies(inp_species); // even within a class, I'll be using the get and set methods.
		level = inp_level;
	}
	
	public short getSpecies(){
		return species;
	}
	
	public void setSpecies(short input){
		if(input > 0 && input <= 493) // we don't want any 494's, now do we? I'm not going to implement Zoroark or Zorua.
			species = input;
		else
			species = 0; // any number not from 1 to 493 is a ketsuban.
		type1 = global.BaseStats.basetype[species][0];
		type2 = global.BaseStats.basetype[species][1];
	}
	
	public void setSpecies(short input, byte forme){
		if(input > 0 && input <= 493){ // we don't want any 494's, now do we? I'm not going to implement Zoroark or Zorua.
			species = input;
			this.forme = forme;
		}
		else
			species = 0; // any number not from 1 to 493 is a ketsuban.
		type1 = global.BaseStats.basetype[species][0];
		type2 = global.BaseStats.basetype[species][1];
	}
	
	public void applyAllStatuses(){
		// Poison and Burn
		check:{ // Check for Toxic - if there, don't deduct damage for poison
			if(status == 1){
				for(int a = 0; a < 4; ++a){
					if(move_statusID[a] == 2)
						break check;
				}
				HP -= stats[0] / 8;
			}
		}
		
		// User Statuses
	
		if(user_status_turns > 0){
			--user_status_turns;
		}
		else if(user_status_turns == 0){
			switch(user_status){
				case 0: break; // no user status
				default:
					user_status = 0;
					break;
			}
		}
		
		// Move Statuses
		for(int a = 0; a < move_statuses; ++a){
			++move_statusTurns[a];
			move_status[a].applyStatus(this, move_statusTurns[a]);
			
			if (move_statusTurns[a] == move_statusTotTurns[a]){
			
				switch(move_statusID[a]){
					case 3: // Perish Song
						HP = 0; // the Pokemon then faints. No message is printed.
						break;
					default:
						// remove the move status
						move_status[a].printMessage(name);
				}
				
				for(int b = a; b < move_statuses - 1; ++b){
					move_status[b] = move_status[b+1]; // only the pointers are replaced, silly me.
					move_statusID[b] = move_statusID[b+1];
					move_statusTurns[b] = move_statusTurns[b+1];
					move_statusTotTurns[b] = move_statusTotTurns[b+1];
				}
				--move_statuses;
				
				// reset the last status
				move_status[move_statuses] = null;
				move_statusID[move_statuses] = 0;
				move_statusTurns[move_statuses] = 0;
				move_statusTotTurns[move_statuses] = 0;
				
				--a; 	// Prevent it from skipping over the next status...
						// That was a stupid error.
			}
		}
		
		// If the Pokemon faints, do it here, not in the BattleEngine
		
		if(HP <= 0){
			System.out.format("%s fainted!%n", name);
		} // check for fainting again after the status
	}
	
	public void inflictMoveStatus(int statusID){
		for(int a = 0; a < move_statuses; ++a){
			if(move_statusID[a] == statusID){
				return;
			}
			// if the status is already there, do nothing
		}
		move_status[move_statuses] = battle.movestatus.MoveStatuses.statuslist[statusID];
		move_statusID[move_statuses] = statusID;
		
		switch(move_status[move_statuses].turn_style){
			case 1: // Wrap, Bind, Whirlpool, Fire Spin, etc.
				move_statusTotTurns[move_statuses] = 3; // keep this at 3 for now, until I want to make it real.
				break;
			case 2: // Perish Song
				move_statusTotTurns[move_statuses] = 4; // This is actually supposed to be 3, but one is deducted on the turn of, so set it to 4.
				break;
			case 3:
				move_statusTotTurns[move_statuses] = 2; // only lasts for the next turn
		}
		++move_statuses;
	}
	
	public boolean hasMoveStatus(int status){
		for(int a = 0; a < 4; ++a){
			if(move_statusID[a] == status)
				return true;
		}
		return false;
	}
	
	public void inflictStatus(int new_status){
		// inflicts status
		if(status != 0){ // only if the target doesn't already have a status ailment
			switch(new_status){
				case 1: // Poison
					if(type1 == 3 || type1 == 8 || type2 == 3 || type2 == 8){ // Poison or Steel types
						return; // do nothing if the target is Poison-type or Steel-type
					}else{
						status = 1;
					}break;
				default: // All other statuses
					status = (byte) new_status;
			}
		}
	}
	
	public void inflictDamage(int damage){
		damage = Math.min(damage, HP);
		HP -= damage;
		System.out.format("%s took %d damage! HP is now at %d/%d.%n", name, damage, HP, stats[0]);
	}
	
	public void resetStats(){
		for(int a = 0; a < 8; ++a){
			stage[a] = 0;
		}
		HP = stats[0]; // take this one out later
		Attack = stats[1];
		Defense = stats[2];
		spAttack = stats[3];
		spDefense = stats[4];
		Speed = stats[5];
		Accuracy = 1.0;
		Evasion = 1.0;
	}
	
	public void changeStats(int[] deltastats){
		for(int a = 1; a < 8; ++a){
			int[] temp_dstats = {0, 0, 0, 0, 0, 0, 0, 0};
			for(int m = 0; m < 8; ++m)
				temp_dstats[m] = deltastats[m];
			if(stage[a] == -6 && temp_dstats[a] < 0){
				System.out.format("%s\'s %s won't go any lower! %n", name, global.GlobalConstants.statlabels[a]); return;
			}else if(stage[a] + temp_dstats[a] <= -6){
				temp_dstats[a] = -1; // if a move that would lower the stat by 2 stages meets a -5 stage, lower it by 1 stage instead.
			}else if(stage[a] == 6 && temp_dstats[a] > 0){
				System.out.format("%s\'s %s won't go any higher! %n", name, global.GlobalConstants.statlabels[a]); return;
			}else if(stage[a] + temp_dstats[a] >= 6){
				temp_dstats[a] = 1; // if a move that would raise the stat by 2 stages meets a +5 stage, raise it by 1 stage instead.
			}
			stage[a] += temp_dstats[a];
			if(temp_dstats[a] == -2)
				System.out.format("%s\'s %s fell harshly! %n", name, global.GlobalConstants.statlabels[a]);
			else if(temp_dstats[a] == -1)
				System.out.format("%s\'s %s fell!%n", name, global.GlobalConstants.statlabels[a]);
			else if(temp_dstats[a] == 1)	
				System.out.format("%s\'s %s rose!%n", name, global.GlobalConstants.statlabels[a]);
			else if(temp_dstats[a] == 2)
				System.out.format("%s\'s %s rose sharply!%n", name, global.GlobalConstants.statlabels[a]);
		}
		updateStats();
	}
	
	public void updateStats(){
		Attack = Math.max(1,(int) (stats[1] * StatModifiers.fromStage(stage[1])));
		if(status == 3){ // burn
			Attack = Math.max(1, Attack / 2);
		}
		Defense = Math.max(1,(int) (stats[2] * StatModifiers.fromStage(stage[2]))); 
		spAttack = Math.max(1,(int) (stats[3] * StatModifiers.fromStage(stage[3]))); 
		spDefense = Math.max(1,(int) (stats[4] * StatModifiers.fromStage(stage[4]))); 
		Speed = Math.max(1,(int) (stats[5] * StatModifiers.fromStage(stage[5])));
		if(status == 2){// paralysis
			Speed = Math.max(1, Speed / 4);
		}
		Accuracy = Math.max(1,(StatModifiers.fromAEStage(stage[6]))); 
		Evasion = Math.max(1,(StatModifiers.fromAEStage(stage[7])));
	}
	
}