package global;

import global.moves.*;

public class MoveList{
	public static Move[] movelist = new Move[20];
	static {
		// movelist[0] = no move;
		movelist[1] = new PhysicalMove("Tackle", "Tackles the opponent", 35, (byte) 35, 100, 0);
		movelist[2] = new PhysicalMove("Peck", "Pecks the opponent", 35, (byte) 35, 100, 2);
		movelist[3] = new StatusMove("Growl", "Growls cutely to lower attack", (byte) 40, 2, 100, -1, 0, 0, 0, 0, 0, 0);
		movelist[4] = new StatusMove("Leer", "Leers cutely? to lower defense", (byte) 40, 2, 100, 0, -1, 0, 0, 0, 0, 0);
		movelist[5] = new PhysicalMove("Zen Headbutt", "Does something.", 80, (byte) 15, 90, 13); // Change this to a SecondaryMove later.
		movelist[6] = new EffectMove("Wrap", "Wraps tightly around the opponent", 40, (byte) 20, 100, 0, 1);
		movelist[7] = new HyperBeam("Hyper Beam", "Does sth.", 150, (byte) 5, 90, 0);
		movelist[8] = new Fly();
		movelist[9] = new NightShade();
		movelist[10] = new Toxic();
		movelist[11] = new PerishSong();
		movelist[12] = new PhysicalMove("Thunderbolt", "Shocks the opponent with a 100,000-volt bolt", 90, (byte) 10, 90, 0);
		movelist[13] = new DragonRage();
		movelist[14] = new LockOn();
		movelist[19] = new Struggle();
	}
}