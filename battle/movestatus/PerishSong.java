package battle.movestatus;

import pokemon.*;

public class PerishSong extends MoveStatus{ 
	
	public PerishSong(){
		turn_style = 2;
	}
	
	public void applyStatus(Pokemon victim, int turns){
		System.out.format("%s's Perish Song count is at %d!%n", victim.name, 10 - turns);
	}
	
}