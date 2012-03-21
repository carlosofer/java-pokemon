package global.actions;

public class YesNo extends TextDisplay{
	
	protected String message = "";
	
	public YesNo(){
		TDID = 2;
	}
	
	public YesNo(String _message){
		TDID = 2;
		message = _message;
	}
	
	public String message(){
		return null;
	}
	
	public void doYes(){
		// do nothing
	}
	
	public void doNo(){
		// do nothing
	}
	
}