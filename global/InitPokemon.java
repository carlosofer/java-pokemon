package global;
class InitPokemon{
	
	public static void initPokemon(){

		// Initialize the Pokemon's stats
		
		pokemon.PokemonCollection.Dratina.IV[0] = 6;
		pokemon.PokemonCollection.Dratina.IV[1] = 19;
		pokemon.PokemonCollection.Dratina.IV[2] = 28;
		pokemon.PokemonCollection.Dratina.IV[3] = 27;
		pokemon.PokemonCollection.Dratina.IV[4] = 15;
		pokemon.PokemonCollection.Dratina.IV[5] = 23;
		
		pokemon.PokemonCollection.Suzume.IV[0] = 31;
		pokemon.PokemonCollection.Suzume.IV[1] = 25;
		pokemon.PokemonCollection.Suzume.IV[2] = 16;
		pokemon.PokemonCollection.Suzume.IV[3] = 28;
		pokemon.PokemonCollection.Suzume.IV[4] = 27;
		pokemon.PokemonCollection.Suzume.IV[5] = 25;
		
		pokemon.PokemonCollection.Dratina.EV[0] = 49;
		pokemon.PokemonCollection.Dratina.EV[1] = 36;
		pokemon.PokemonCollection.Dratina.EV[2] = 18;
		pokemon.PokemonCollection.Dratina.EV[3] = 24;
		pokemon.PokemonCollection.Dratina.EV[4] = 19;
		pokemon.PokemonCollection.Dratina.EV[5] = 25;
		
		pokemon.PokemonCollection.Suzume.EV[0] = 0;
		pokemon.PokemonCollection.Suzume.EV[1] = 0;
		pokemon.PokemonCollection.Suzume.EV[2] = 0;
		pokemon.PokemonCollection.Suzume.EV[3] = 0;
		pokemon.PokemonCollection.Suzume.EV[4] = 0;
		pokemon.PokemonCollection.Suzume.EV[5] = 0;
		
		pokemon.PokemonCollection.Kompras.IV[0] = 31;
		pokemon.PokemonCollection.Kompras.IV[1] = 25;
		pokemon.PokemonCollection.Kompras.IV[2] = 16;
		pokemon.PokemonCollection.Kompras.IV[3] = 28;
		pokemon.PokemonCollection.Kompras.IV[4] = 27;
		pokemon.PokemonCollection.Kompras.IV[5] = 25;
		
		pokemon.PokemonCollection.Kompras.EV[0] = 0;
		pokemon.PokemonCollection.Kompras.EV[1] = 0;
		pokemon.PokemonCollection.Kompras.EV[2] = 0;
		pokemon.PokemonCollection.Kompras.EV[3] = 0;
		pokemon.PokemonCollection.Kompras.EV[4] = 0;
		pokemon.PokemonCollection.Kompras.EV[5] = 0;

		pokemon.PokemonCollection.Dratina.moves[0] = 5;
		pokemon.PokemonCollection.Dratina.moves[1] = 6;
		pokemon.PokemonCollection.Dratina.moves[2] = 7;
		pokemon.PokemonCollection.Dratina.moves[3] = 13;
		
		pokemon.PokemonCollection.Suzume.moves[0] = 9;
		pokemon.PokemonCollection.Suzume.moves[1] = 10;
		pokemon.PokemonCollection.Suzume.moves[2] = 11;
		pokemon.PokemonCollection.Suzume.moves[3] = 12;
		
		pokemon.PokemonCollection.Kompras.moves[0] = 5;
		pokemon.PokemonCollection.Kompras.moves[1] = 7;
		pokemon.PokemonCollection.Kompras.moves[2] = 9;
		pokemon.PokemonCollection.Kompras.moves[3] = 14;
		
		for(int a = 0; a < 4; ++a){
			if(pokemon.PokemonCollection.Dratina.moves[a] != 0){
				pokemon.PokemonCollection.Dratina.PP[a] = (byte)(global.MoveList.movelist[pokemon.PokemonCollection.Dratina.moves[a]].PP - 3);
			}
			if(pokemon.PokemonCollection.Suzume.moves[a] != 0){
				pokemon.PokemonCollection.Suzume.PP[a] = (byte)(global.MoveList.movelist[pokemon.PokemonCollection.Suzume.moves[a]].PP - 3);
			}
			if(pokemon.PokemonCollection.Kompras.moves[a] != 0){
				pokemon.PokemonCollection.Kompras.PP[a] = (byte)(global.MoveList.movelist[pokemon.PokemonCollection.Kompras.moves[a]].PP - 3);
			}
		}
		
		int absIndex1 = 0, absIndex2 = 0, absIndex3 = 0;
		
		if (pokemon.PokemonCollection.Dratina.forme == 0){
			absIndex1 = pokemon.PokemonCollection.Dratina.getSpecies(); 
		}else{
			switch(pokemon.PokemonCollection.Dratina.getSpecies()){
				case 386: // #396 Deoxys
					absIndex1 = 493 + pokemon.PokemonCollection.Dratina.forme; break;
				case 412: // #412 Burmy
					absIndex1 = 412; break; // Burmy does not change itself with the formes.
				case 413: // #413 Wormadam
					absIndex1 = 496 + pokemon.PokemonCollection.Dratina.forme; break;
				case 422:
					absIndex1 = 422; break; // Shellos
				case 423:
					absIndex1 = 423; break; // Gastrodon
				case 479: // #479 Rotom
					absIndex1 = 498 + 1; break; // Rotom has 5 more formes, but they all have the same base stats.
				case 487: // #487 Giratina
					absIndex1 = 499 + pokemon.PokemonCollection.Dratina.forme; break;
				case 494: // #494 Shaymin
					absIndex1 = 500 + pokemon.PokemonCollection.Dratina.forme; break;
			}
		}
		if (pokemon.PokemonCollection.Suzume.forme == 0){
			absIndex2 = pokemon.PokemonCollection.Suzume.getSpecies(); 
		}else{
			switch(pokemon.PokemonCollection.Suzume.getSpecies()){
				case 386: // #396 Deoxys
					absIndex2 = 493 + pokemon.PokemonCollection.Suzume.forme; break;
				case 412: // #412 Burmy
					absIndex2 = 412; // Burmy does not change itself with the formes.
				case 413: // #413 Wormadam
					absIndex2 = 496 + pokemon.PokemonCollection.Suzume.forme; break;
				case 422:
					absIndex2 = 422; break;
				case 423:
					absIndex2 = 423; break;
				case 479: // #479 Rotom
					absIndex2 = 498 + 1; break; // Rotom has 5 more formes, but they all have the same base stats.
				case 487: // #487 Giratina
					absIndex2 = 499 + pokemon.PokemonCollection.Suzume.forme; break;
				case 494: // #494 Shaymin
					absIndex2 = 500 + pokemon.PokemonCollection.Suzume.forme; break;
			}
		}
		if (pokemon.PokemonCollection.Kompras.forme == 0){
			absIndex3 = pokemon.PokemonCollection.Kompras.getSpecies(); 
		}else{
			switch(pokemon.PokemonCollection.Kompras.getSpecies()){
				case 386: // #396 Deoxys
					absIndex3 = 493 + pokemon.PokemonCollection.Kompras.forme; break;
				case 412: // #412 Burmy
					absIndex3 = 412; // Burmy does not change itself with the formes.
				case 413: // #413 Wormadam
					absIndex3 = 496 + pokemon.PokemonCollection.Kompras.forme; break;
				case 422:
					absIndex3 = 422; break;
				case 423:
					absIndex3 = 423; break;
				case 479: // #479 Rotom
					absIndex3 = 498 + 1; break; // Rotom has 5 more formes, but they all have the same base stats.
				case 487: // #487 Giratina
					absIndex3 = 499 + pokemon.PokemonCollection.Kompras.forme; break;
				case 494: // #494 Shaymin
					absIndex3 = 500 + pokemon.PokemonCollection.Kompras.forme; break;
			}
		}
		
		for(int a = 0; a < 6; ++a){
			// Formula from Bulbapedia: http://bulbapedia.bulbagarden.net/
			
			// Stat = ((Base x 2) + (EV / 4) + IV + 5) * nature * Level / 100 (nature is not implemented yet, let's pretend their natures are both Bashful.)
			
			pokemon.PokemonCollection.Dratina.stats[a] = (int)((global.BaseStats.BaseStats[absIndex1][a] * 2 + (pokemon.PokemonCollection.Dratina.EV[a] / 4.0) + pokemon.PokemonCollection.Dratina.IV[a] + 5) * (pokemon.PokemonCollection.Dratina.level / 100.0));
			pokemon.PokemonCollection.Suzume.stats[a] = (int)((global.BaseStats.BaseStats[absIndex2][a] * 2 + (pokemon.PokemonCollection.Suzume.EV[a] / 4.0) + pokemon.PokemonCollection.Suzume.IV[a] + 5) * (pokemon.PokemonCollection.Suzume.level / 100.0));
			pokemon.PokemonCollection.Kompras.stats[a] = (int)((global.BaseStats.BaseStats[absIndex3][a] * 2 + (pokemon.PokemonCollection.Kompras.EV[a] / 4.0) + pokemon.PokemonCollection.Kompras.IV[a] + 5) * (pokemon.PokemonCollection.Kompras.level / 100.0));
			
		}
		
		pokemon.PokemonCollection.Dratina.stats[0] += pokemon.PokemonCollection.Dratina.level + 5; // HP gets an additional bonus
		pokemon.PokemonCollection.Suzume.stats[0] += pokemon.PokemonCollection.Suzume.level + 5; // HP gets an additional bonus
		pokemon.PokemonCollection.Kompras.stats[0] += pokemon.PokemonCollection.Kompras.level + 5; // HP gets an additional bonus
		
		/*pokemon.PokemonCollection.Dratina.stats[0] += 500; // DEBUG: make their HP's really high.
		pokemon.PokemonCollection.Suzume.stats[0] += 800; // DEBUG: make their HP's really high.
		pokemon.PokemonCollection.Kompras.stats[0] += 500; // DEBUG: make their HP's really high.*/
		
	}
}