package global;

import global.Trainer;
import pokemon.Pokemon;

public class Player extends Trainer{
	
	int trClass = 0;
	
	boolean badges[] = new boolean[32]; // 32 badges to collect, from four regions
	
	public Player(int money){
		this.money = money;
	}
	
	public Player(int money, short[] pokemon_species, int[] levels) {
		for(int a = 0; a < Math.min(6, Math.min(pokemon_species.length, levels.length)); ++a){
			party[a] = new Pokemon(pokemon_species[a], "", levels[a]);
		}
	}
	
	public void setBadges(boolean[] badge){
		for(int a = 0; a < 32; ++a){
			badges[a] = badge[a];
		}
	}
	
	public boolean[] getBadges(){
		return badges;
	}
	
}