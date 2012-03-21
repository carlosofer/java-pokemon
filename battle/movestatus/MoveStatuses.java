package battle.movestatus;

public class MoveStatuses{
	
	public static MoveStatus[] statuslist = new MoveStatus[20];
	static{
		statuslist[1] = new LittleStatus("%s is hurt by Wrap", "%s was released from Wrap!");
		statuslist[2] = new Toxic("%s is badly poisoned");
		statuslist[3] = new PerishSong();
		statuslist[4] = new LockOn();
	}
	
}