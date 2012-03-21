package battle.movestatus;

import pokemon.*;

public class MoveStatus{
	
	String message, message2;
	public int turn_style;
	
	public void applyStatus(Pokemon victim, int turns){
		// nothing here, this is just a template class for the individual statuses.
	}

	public void printMessage(String _name){
		System.out.format(message2 + "\n", _name);
	}
	
}