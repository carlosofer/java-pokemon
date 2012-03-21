package global;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import pokemon.*;

public class Loader{
	
	public static Pokemon loadPokemon(File inp) throws IOException{
		Pokemon final_output = new Pokemon();

		FileInputStream fin = new FileInputStream(inp);
		// read 80 bytes of code
		int[] read_data = new int[80];
		
		
		try{
			for(int a = 0; a < 80; ++a){
				read_data[a] = fin.read();
			}
		}finally{
            if (fin != null) {
                fin.close();
            }
        }
		
		// interpret the data here

		// bytes 0 - 1: Pokemon's species
		final_output.setSpecies((short)(read_data[1] * 256 + read_data[0]));
		// System.out.println("This Pokemon's species is " + pokemon.Names.defaultName[final_output.getSpecies()]);
		// bytes 2 - 11: Pokemon's name
		String inp_name = "";
		for(int a = 2; a < 12; ++a){
			if(read_data[a] != 0)
				inp_name += (char)read_data[a];
		}
		final_output.name = inp_name;
		// System.out.println("This Pokemon's name is " + inp_name);
		// bytes 12 - 15: OT ID
		final_output.OT_ID[0] = (int)(read_data[12] + read_data[13] * 256);
		final_output.OT_ID[1] = (int)(read_data[14] + read_data[15] * 256);
		// System.out.format("This Pokemon's OT ID is: %d %d %n", final_output.OT_ID[0], final_output.OT_ID[1]);
		// bytes 16 - 19: Personality Value
		final_output.personality_var[0] = (int)(read_data[16] + read_data[17] * 256);
		final_output.personality_var[1] = (int)(read_data[18] + read_data[19] * 256);
		// System.out.format("This Pokemon's personality value is: %d %d %n", final_output.personality_var[0], final_output.personality_var[1]);		
		// bytes 20 - 21: Item Held
		final_output.item_held = (short)(read_data[20] + read_data[21] * 256);
		// System.out.format("This Pokemon is holding item %d %n", final_output.item_held);
		// bytes 22 - 24: Experience Points
		final_output.experience_points = (int)(read_data[22] + read_data[23] * 256 + read_data[24] * 65536);
		// System.out.format("This Pokemon has %d experience points %n", final_output.experience_points);
		// bytes 25 - 32: Moves known
		final_output.moves[0] = (short)(read_data[25] + read_data[26]);
		final_output.moves[1] = (short)(read_data[27] + read_data[28]);
		final_output.moves[2] = (short)(read_data[29] + read_data[30]);
		final_output.moves[3] = (short)(read_data[31] + read_data[32]);
		// System.out.format("This Pokemon knows: ");
		// bytes 33 - 36: PP
		final_output.PP[0] = (byte)(read_data[33] % 64); 
		final_output.PP[1] = (byte)(read_data[34] % 64);
		final_output.PP[2] = (byte)(read_data[35] % 64);
		final_output.PP[3] = (byte)(read_data[36] % 64);
		final_output.maxPP[0] = (byte)Math.min(global.MoveList.movelist[final_output.moves[0]].PP / 5 * (5 + (read_data[33] / 64)), 63); // to avoid overflows, maximum is 63, not 64 
		final_output.maxPP[1] = (byte)Math.min(global.MoveList.movelist[final_output.moves[1]].PP / 5 * (5 + (read_data[34] / 64)), 63);
		final_output.maxPP[2] = (byte)Math.min(global.MoveList.movelist[final_output.moves[2]].PP / 5 * (5 + (read_data[35] / 64)), 63);
		final_output.maxPP[3] = (byte)Math.min(global.MoveList.movelist[final_output.moves[3]].PP / 5 * (5 + (read_data[36] / 64)), 63);
		for(int a = 0; a < 4; ++a){
			// if(final_output.moves[a] != 0)
				// System.out.format("%s (%d/%d)  ", global.MoveList.movelist[final_output.moves[a]].name, final_output.PP[a], final_output.maxPP[a]);
		}
		// System.out.println();
		
		for(int a = 0; a < 6; ++a){
			// bytes 37 - 42: IV's
			final_output.IV[a] = (short) read_data[a + 37];
			// bytes 43 - 48: EV's
			final_output.EV[a] = (short) read_data[a + 43];
		}
		// System.out.format("This Pokemon's IV's are: %d %d %d %d %d %d %n", final_output.IV[0], final_output.IV[1], final_output.IV[2], final_output.IV[3], final_output.IV[4], final_output.IV[5]);
		// System.out.format("This Pokemon's EV's are: %d %d %d %d %d %d %n", final_output.EV[0], final_output.EV[1], final_output.EV[2], final_output.EV[3], final_output.EV[4], final_output.EV[5]);
		
		// byte 49: level
		final_output.level = (byte) read_data[49];
		// System.out.format("This Pokemon is level %d %n", final_output.level);
		
		// bytes 50 - 51: HP 
		final_output.HP = (short) (read_data[50] + read_data[51] * 256);
		// bytes 52 - 63: other stats
		for(int a = 0; a < 6; ++a){
			final_output.stats[a] = (short) (read_data[52 + a*2] + read_data[53 + a*2] * 256);
			// don't initialize Attack, Defense, etc. until in battle mode
		}
		// System.out.format("This Pokemon's current stats are: %d/%d HP, %d Attack, %d Defense, %d Special Attack, %d Special Defense, %d Speed %n",
				// final_output.HP, final_output.stats[0], final_output.stats[1], final_output.stats[2],
								 // final_output.stats[3], final_output.stats[4], final_output.stats[5]);
		
		// bytes 64 - 65: shiny var
		final_output.shinyvar = (int) (read_data[64] + read_data[65] * 256);
		// determine shininess depending on the game
		/*switch(global.GlobalConstants.GameMode){
			case 0:
				if((final_output.shinyvar ^ final_output.OT_ID[1]) < 8)
					System.out.format("This Pokemon is shiny. Congratulations!%n");
				else
					System.out.format("This Pokemon is not shiny.%n");
				break;
			case 1: case 2:
				// The shininess formula comes from the IV's in GSC.
				if(	(final_output.IV[1] & 0x20) == 0x20 &&
					(final_output.IV[2] & 0xF0) == 0xA0 &&
					(final_output.IV[3] & 0xF0) == 0xA0 &&
					(final_output.IV[4] & 0xF0) == 0xA0)
					System.out.format("This Pokemon is shiny. Congratulations!%n");
				else
					System.out.format("This Pokemon is not shiny.%n");
				break;
			case 3: case 4:
				if(	(final_output.personality_var[0] ^ final_output.personality_var[1] ^
					final_output.OT_ID[0] ^ final_output.OT_ID[1]) < 8)	
					System.out.format("This Pokemon is shiny. Congratulations!%n");
				else
					System.out.format("This Pokemon is not shiny.%n");
				break;
			default:
				System.out.format("This Pokemon is not shiny.%n");
		}*/ // No point in shininess just yet.
		
		// byte 66: status
		final_output.status = (byte) read_data[66];
		// System.out.format("This Pokemon's has status %d%n", final_output.status);
		// byte 67: ability
		final_output.ability = read_data[67];
		// System.out.format("This Pokemon's has ability %d%n", final_output.ability);
		// byte 68: happiness
		final_output.happiness = read_data[68];
		// System.out.format("This Pokemon's has happiness %d%n", final_output.happiness);
		// byte 69: pokerus status
		final_output.pokerus_status = (byte) read_data[69];
		/* switch(final_output.pokerus_status){
			case 0:
				System.out.format("This Pokemon has never had the Pokerus%n"); break;
			case 1:
				System.out.format("This Pokemon has the Pokerus%n"); break;
			case 2:
				System.out.format("This Pokemon has had the Pokerus%n"); break;
			case 3:
				System.out.format("This Pokemon has never the Pokerus%n"); break; // For shits and giggles.
			default:
				System.out.format("This Pokemon has never done the Polka Dance.%n"); // In case of a bug.
		}*/
		
		// bytes 70 to 79: OT name
		inp_name = "";
		for(int a = 70; a < 80; ++a){
			if(read_data[a] != 0)
				inp_name += (char)read_data[a];
		}
		final_output.OT_name = inp_name;
		// System.out.println("This Pokemon's OT name is " + inp_name);
		
		
		// System.out.println();
		
		return final_output;
	}
	
}