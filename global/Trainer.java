package global;

import pokemon.Pokemon;

public class Trainer{
	
	Pokemon party[] = new Pokemon[6];
	public String name;
	int partysize = 0, active = 0;
	long money = 0;
	short ID[] = {0, 0};
	int trClass = 0; // a trClass of -1 means a wild Pokemon, 0 means a Player.
	// items will be included later
	
	public Trainer(){
		// do nothing
	}
	
	public Trainer(int money, int trainer_class){
		this.money = money;
		trClass = trainer_class;
	}
	
	public Trainer(int money, short[] pokemon_species, int[] levels, int trainer_class){
		partysize = Math.min(6, Math.min(pokemon_species.length, levels.length));
		for(int a = 0; a < partysize; ++a){
			party[a] = new Pokemon(pokemon_species[a], "", levels[a]);
		}
		this.money = money;
		trClass = trainer_class;
		active = 0;
	}
	
	public int getTrainerClass(){
		return trClass;
	}
	
	public int getPartySize(){
		return partysize;
	}
	
	public Pokemon[] getParty(){
		return party;
	}
	
	public Pokemon getActive(){
		return party[active];
	}
	
	public void setName(String input){
		name = input;
	}
	
	public String getName(){
		return name;
	}
	
	public int unfainted(){
		int output = 0;
		for(int a = 0; a < partysize; ++a){
			if(party[a].HP > 0){
				++output;
			}
		}
		return output;
	}
	
	public void addPokemon(Pokemon added){
		party[partysize++] = added;
	}
	
	public void addPokemon(short species, short level){
		Pokemon newb = new Pokemon();
		newb.level = level;
		newb.setSpecies(species);
		party[partysize++] = newb;
	}
	
	public void removePokemon(int a){
		if(a < partysize){
			party[a] = null;
			for(int b = 0; b < partysize - 1; ++b){
				party[b] = party[b+1];
			}
			party[partysize--] = null;
		}
	}
	
	public void switchPokemon(int a){
		if(a < partysize)
			if(party[a].HP > 0)
				active = a;
	}
	
}