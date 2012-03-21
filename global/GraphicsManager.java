package global;

import global.actions.GraphAction;
import global.actions.TextDisplay;
import global.actions.YesNo;
import global.maps.Map;
import global.maps.TileIndex;
import global.sprites.AnimatedSprite;
import global.sprites.PlayerSprite;
import global.sprites.Sprite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GraphicsManager{
	
	// TODO: Create an Action Queue that queues all actions.
	Vector<GraphAction> action_queue = new Vector<GraphAction>();
	// TODO: Use this Action Queue.
	
	BufferedImage screen = new BufferedImage(240, 180, 2); // TYPE_INT_ARGB
	
	public boolean waiting_for_yesno = false;
	public boolean on_yes = false;
	Vector<Integer> yesno_queue = new Vector<Integer>();
	
	// GraphMan - the main Graphics Manager. This is where all the graphics layering and stuff goes.
	
	/* OVERWORLD TILES */
	
	public PlayerSprite player = null;
	Vector<AnimatedSprite> people = new Vector<AnimatedSprite>();
	
	Map currentmap = global.Managers.AllMaps.getMap(0);
	
	public boolean text_playing = false;
	String displayed_text = "";
	Vector<String> text_queue = new Vector<String>(); // if I can't create a thread, I'll create a queue instead.
	double text_scroll = 0;
	Sprite textbox = null, yesnobox = null, selector = null;
	
	public static TileIndex tiles = new TileIndex();
	
	int screen_X = 0, screen_Y = 0; 
	// ALWAYS draw the back ones first, then the front ones.
	
	/* BATTLE ENGINE GRAPHICS */
	
	Sprite pkmn1, pkmn2;
	
	public GraphicsManager(){
		// do nothing
		
		BufferedImage TextBox = null, YesNoBox = null, Selector = null, PlayerImage[][] = new BufferedImage[4][3];
		try{
			// TODO: put this in the graphics loader later.
			PlayerImage[0][0] = ImageIO.read(new File("pictures/PlayerSprite/ml1.png"));
			PlayerImage[0][1] = ImageIO.read(new File("pictures/PlayerSprite/ml2.png"));
			PlayerImage[0][2] = ImageIO.read(new File("pictures/PlayerSprite/ml3.png"));
			PlayerImage[1][0] = ImageIO.read(new File("pictures/PlayerSprite/mb1.png"));
			PlayerImage[1][1] = ImageIO.read(new File("pictures/PlayerSprite/mb2.png"));
			PlayerImage[1][2] = ImageIO.read(new File("pictures/PlayerSprite/mb3.png"));
			PlayerImage[2][0] = ImageIO.read(new File("pictures/PlayerSprite/mr1.png"));
			PlayerImage[2][1] = ImageIO.read(new File("pictures/PlayerSprite/mr2.png"));
			PlayerImage[2][2] = ImageIO.read(new File("pictures/PlayerSprite/mr3.png"));
			PlayerImage[3][0] = ImageIO.read(new File("pictures/PlayerSprite/m1.png"));
			PlayerImage[3][1] = ImageIO.read(new File("pictures/PlayerSprite/m2.png"));
			PlayerImage[3][2] = ImageIO.read(new File("pictures/PlayerSprite/m3.png"));
			TextBox = ImageIO.read(new File("pictures/textbox.png"));
			YesNoBox = ImageIO.read(new File("pictures/yesnobox.png"));
			Selector = ImageIO.read(new File("pictures/selector.png"));
		}catch (IOException e){
			// do nothing
			System.out.println("Error 23 - File not found?");
		}
		
		player = new PlayerSprite(PlayerImage, 112, 80, 32);
		int[] script = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2};
		player.setAnimationScript(script);
		
		textbox = new Sprite(TextBox, 0, 130);
		yesnobox = new Sprite(YesNoBox, 180, 90);
		selector = new Sprite(Selector, 182, 92);
	}
	
	public void move_screen(int X, int Y){
		screen_X = X;
		screen_Y = Y;
	}
	
	public void update_screen(){
		
		Graphics g = screen.getGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, 240, 180);
		
		if(global.GameState.getWorldType() == 0){
		
			// draw the map
			currentmap.draw_map(g, screen_X, screen_Y);
			
			g.drawImage(player.getImage(), player.getX() - screen_X, player.getY() - screen_Y, null);
	
		}else if(global.GameState.getWorldType() == 1){
			
			// draw the Pokemon 'n' stuffs
			
			
			
		}
		
		/* The text box is global - it applies everywhere. */
		
		if(text_playing){
			g.drawImage(textbox.getImage(), textbox.getX(), textbox.getY(), null);
			g.drawString(((TextDisplay) action_queue.elementAt(0)).message().substring(0, (int)(text_scroll)), textbox.getX() + 4, textbox.getY() + 16);
		}if(waiting_for_yesno){
			// draw the Yes-No dialog.
			g.drawImage(yesnobox.getImage(), yesnobox.getX(), yesnobox.getY(), null);
			g.drawString("Yes", 188, 106);
			g.drawString("No", 188, 123);
			if(on_yes)
				g.drawImage(selector.getImage(), 184, 94, null);
			else
				g.drawImage(selector.getImage(), 184, 111, null);
		}
		
		global.Managers.WindowMan.redraw_image();
		
	}
	
	public void advance_one_frame(){
		
		if(global.GameState.getWorldType() == 0){ // regular overworld
			
			// eventually, there will be menus and dialogs here.
			
			if(text_playing == false){ // if nothing is currently in the way
	
				player.advance_one_frame();
				
				if(global.Managers.InputMan.key_input_enabled == true){
					if(player.isWalking() == false){
						if(global.Managers.InputMan.arrow_keydown[0] == true){
							player.walk(1);
						}else if(global.Managers.InputMan.arrow_keydown[1] == true){
							player.walk(2);
						}else if(global.Managers.InputMan.arrow_keydown[2] == true){
							player.walk(3);
						}else if(global.Managers.InputMan.arrow_keydown[3] == true){
							player.walk(4);
						}else if(player.isStopped() == false){
							player.stop();
						}else{ /* do nothing */ }
					}
				}
			
			}
			
		}else if (global.GameState.getWorldType() == 1){
			
			// 
			
		}
		
		/* Once again, the text box is global. */
		
		if(waiting_for_yesno){
			// check the state of the yes-no box
		}else if(text_playing == true){
			if(global.Managers.InputMan.A_keydown == true){
				text_scroll = Math.min(text_scroll + 1.0, ((TextDisplay) action_queue.elementAt(0)).message().length()); // 60 CPS
			}else{
				text_scroll = Math.min(text_scroll + 0.4, ((TextDisplay) action_queue.elementAt(0)).message().length()); // 24 CPS
			}
			if(text_scroll == ((TextDisplay) action_queue.elementAt(0)).message().length()){
				if(((TextDisplay) action_queue.elementAt(0)).TDID == 2){ // if there is a yesno in the queue, do that instead.
					waiting_for_yesno = true;
					on_yes = true; // set default to Yes
				}
			}
		}
		
		update_screen();
		
	}

	public void send_A_key_signal(){
		if(waiting_for_yesno){
			text_scroll = 0;
			waiting_for_yesno = false;
			if(on_yes)
				((YesNo) action_queue.elementAt(0)).doYes();
			else
				((YesNo) action_queue.elementAt(0)).doNo();
			action_queue.remove(0);
			if(action_queue.size() != 0)
				action_queue.elementAt(0).performAction();
			/*text_queue.remove(0);
			yesno_queue.remove(0);
			if(text_queue.size() == 0)
				text_playing = false; // get rid of the text.
			else
				;// do nothing*/
		}else if(text_playing == true && text_scroll == ((TextDisplay) action_queue.elementAt(0)).message().length()){
			text_scroll = 0;
			action_queue.remove(0);
			text_playing = false;
			if(action_queue.size() != 0)
				action_queue.elementAt(0).performAction();
			/*text_queue.remove(0);
			yesno_queue.remove(0);
			if(text_queue.size() == 0)
				text_playing = false; // get rid of the text.
			else
				;// do nothing*/
		}
	}
	
	public void send_B_key_signal(){
		// do nothing
		
		// DEBUG: display messages.
		if(overworld()){
			display_text("This is a text box.");
			display_yesno("Select Yes or No.", 1);
		}else if(waiting_for_yesno){
			// do the rest of the stuff too.
			text_scroll = 0;
			waiting_for_yesno = false;
			global.ActionCollection.performNo(yesno_queue.elementAt(0));
			text_queue.remove(0);
			yesno_queue.remove(0);
			if(text_queue.size() == 0)
				text_playing = false; // get rid of the text.
			else
				;// do nothing
		}
		
	}
	
	public void send_arrowkey_signal(int keynum){
		if(waiting_for_yesno)
			on_yes = !on_yes; // if an arrow key is being pressed...
	}
	
	public boolean overworld(){
		return text_playing == false && waiting_for_yesno == false;
	}
	
	public void display_text(String text){
		
		action_queue.add(new TextDisplay(text));
		action_queue.elementAt(0).performAction();
		
		// clear all the currently held-down keys - they don't matter anymore.
		/*global.Managers.InputMan.clear_all_keys();
		
		text_playing = true;
		text_queue.add(text);
		yesno_queue.add((Integer)0);*/
	}
	
	public void display_yesno(String text, int yesno){
		
		action_queue.add(global.ActionCollection.yesnos[yesno]);
		action_queue.elementAt(0).performAction();
		
		// clear all the currently held-down keys - they don't matter anymore.
		/*global.Managers.InputMan.clear_all_keys();
		
		text_playing = true;
		text_queue.add(text);
		yesno_queue.add((Integer)yesno);*/
	}
	
	public BufferedImage getRenderedPicture(){
		return screen;
	}
	
	public Dimension getLocation(){
		Dimension output = new Dimension(screen_X, screen_Y);
		return output;
	}
	
	public void setLocation(int X, int Y){
		screen_X = X;
		screen_Y = Y;
		update_screen();
	}
	
	public void setLocation(Dimension input){
		screen_X = input.width;
		screen_Y = input.height;
		update_screen();
	}
	
	public void nudge(int X, int Y){
		screen_X += X;
		screen_Y += Y;
		update_screen();
	}
	
	public Map curMap(){
		return currentmap;
	}
	
}

