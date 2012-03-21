package global;

import global.actions.YesNo;
import global.yesno.*;

public class ActionCollection {
	
// YesNo collection
	
	static YesNo[] yesnos = new YesNo[20];
	
	static{
		yesnos[1] = new YesNo1();
	}
	
	static void performYes(int a){
		yesnos[a].doYes();
	}
	static void performNo(int a){
		yesnos[a].doNo();
	}
	
}
