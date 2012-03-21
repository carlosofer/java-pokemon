package battle.movestatus;

import pokemon.Pokemon;

public class LockOn extends MoveStatus{ 
	
	public LockOn(){
		turn_style = 3;
	}
	
	public void applyStatus(Pokemon victim, int turns){
		// do nothing
	}
	
}