package battle.movestatus;

import pokemon.*;

public class Toxic extends MoveStatus{
	
	public Toxic(String _message){
		message = _message;
		message2 = "WTF, %s isn't poisoned anymore?"; // Debug.
		turn_style = 0;
	}
	
	public void applyStatus(Pokemon victim, int turns){
		victim.HP -= (victim.stats[0] * Math.min(turns, 15)) / 16; // the number of turns that Toxic lasted.
		System.out.format(message + " and took %d damage! %n", victim.name, (victim.stats[0] * Math.min(turns, 15)) / 16); // e.g. "Dratini was hurt by Wrap!"
	}
	
}