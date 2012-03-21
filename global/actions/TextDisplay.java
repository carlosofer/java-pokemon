package global.actions;

public class TextDisplay extends GraphAction{

	public int TDID = 0;
	String message = "";
	
	public TextDisplay(){
		TDID = 1;
	}
	
	public TextDisplay(String _message){
		TDID = 1;
		message = _message;
	}
	
	public void performAction(){
		global.Managers.GraphMan.text_playing = true;
	}
	
	public String message(){
		return message;
	}
	
}
