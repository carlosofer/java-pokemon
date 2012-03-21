package global.yesno;

import global.actions.YesNo;

public class YesNo1 extends YesNo{
	
	public YesNo1(String _message) {
		super(_message);
	}
	
	public String message(){
		return message;
	}

	public YesNo1() {
		message = "Select Yes or No.";
	}

	public void doYes(){
		global.Managers.GraphMan.display_text("You selected Yes.");
	}
	
	public void doNo(){
		global.Managers.GraphMan.display_text("You selected No. Please select Yes.");
		global.Managers.GraphMan.display_yesno("You selected No. Please select Yes.", 1);
	}
	
}
