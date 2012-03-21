/* Pokemon's "use move" method */

package battle;

import pokemon.*;

public class Attack{

	public static void useAttack(Pokemon user, int ID, Pokemon enemy){
		
		if(ID == 19) // Struggle always works
			global.MoveList.movelist[19].useMove(user, enemy);
		
		int absMoveIndex = user.moves[ID];
			if(user.PP[ID] == 0){
				System.out.println("The move is out of PP!");
				return; // the move is not executed
			}
			else{
				global.MoveList.movelist[absMoveIndex].useMove(user, enemy);
				--user.PP[ID];
			}
	
	}
	
}
