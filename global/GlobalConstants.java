package global;

public class GlobalConstants{
	
	public static String[] statlabels = {"WTFstat", "Attack", "Defense", "Special Attack", "Special Defense", "Speed", "Accuracy", "Evasion"};
	public static String[] typenames = {"Normal", "Fighting", "Flying", "Poison", "Ground", "Rock", "Bug", "Ghost", "Steel", "Fire", "Water", "Grass", "Electric", "Psychic", "Ice", "Dragon", "Dark"};
	
	public static int GameMode = 0;
	// GameMode determines the formulas used for calculating stats and damage.
	// It also determines how much money the player loses.
	
	// 0 = Default (Java).
	// 1 = Red/Blue/Yellow
	// 2 = Gold/Silver/Crystal
	// 3 = Ruby/Sapphire/Emerald
	// 4 = Diamond/Pearl/Platinum
	
	/* Differences between versions */
	
	/* 0 = Default (Java)
	 * 
	 * The user loses money based on the total level of Pokemon defeated, not the highest level.
	 * The EV system is based on Gen.3 to 4, but the maximum EV's a Pokemon can have depends on the IV's.
	 * IV's can go anywhere from 0 to 255.
	 * 
	 */
	
	/* 1 = Red/Blue/Yellow
	 * 
	 * The user loses half his money after losing a battle.
	 * The EV system is based on Gen.1, where it grew quadratically.
	 * IV's can only go from 0 to 240, in multiples of 16. Sp.ATK and Sp.DEF are locked together.
	 * 
	 */
	
	/* 2 = Gold/Silver/Crystal
	 * 
	 * The user loses half his money after losing a battle.
	 * The EV system is revamped, to go up 1 for each 1024 EV points.
	 * IV's can only go from 0 to 240, in multiples of 16. Sp.ATK and Sp.DEF are locked together.
	 * 
	 */
	
	/* 3 = Ruby/Sapphire/Emerald
	 * 
	 * The user loses half his money after losing a battle.
	 * The EV system is revamped yet once more, to go up 1 for each 4 EV points.
	 * IV's can only go from 0 to 248, in multiples of 8.
	 * 
	 */
	
	/* 4 = Diamond/Pearl/Platinum
	 * 
	 * The user loses a fixed amount of money after losing a battle, based on the highest level.
	 * The EV system is the same as in Gen.3.
	 * IV's can only go from 0 to 248, in multiples of 8.
	 * 
	 */
	
}