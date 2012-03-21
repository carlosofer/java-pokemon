package battle.movestatus;

import pokemon.*;

public class LittleStatus extends MoveStatus{
	
	public LittleStatus(String _message, String _message2){
		message = _message;
		message2 = _message2;
		turn_style = 1;
	}
	
	public void applyStatus(Pokemon victim, int turns){
		System.out.format(message + " and took %d damage!%n", victim.name, victim.stats[0] / 16); // e.g. "Dratini was hurt by Wrap!"
		victim.HP -= victim.stats[0] / 16; // moves like Wrap, Bind, etc. make the user lose 1/16 max.HP each turn.
	}
	
}