package global.moves;

// Move class

// Default class - for physical, damaging moves

import pokemon.*;

public class Move{
	
	public String name = "";
	String description = "";
	public int power = 0, accuracy = 0;
	public byte PP = 0;
	public int type = 0;
	
	public void loadStats(String _name, String _description, int _power, byte _PP, int _accuracy, int _type){
		name = _name;
		description = _description;
		power = _power;
		PP = _PP;
		accuracy = _accuracy;
		type = _type;
	}
	
	
	public void useMove(Pokemon user, Pokemon enemy){
	}
	
}