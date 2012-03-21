package global.sprites;

import java.awt.Image;

public class PlayerSprite extends global.sprites.HumanSprite{
	
	public PlayerSprite(Image[][] multipleImages, int x, int y, int nFrames) {
		// do nothing
		frames = multipleImages; // can't use the super function because there are 2 dimensions
		posXF = x;
		posYF = y;
		total_frames = nFrames;
		animation_script = new int[nFrames];
	}
	
	public void setInitialLocation(int gridX, int gridY){
		// set current position in the new map
		cur_gridX = gridX;
		cur_gridY = gridY;
		posXF = cur_gridX * 16;
		posYF = cur_gridY * 16;
		global.Managers.GraphMan.move_screen((int)(posXF - 112), (int)(posYF - 88));
	}
	
	public void advance_one_frame(){
		
		if(walking_script.isEmpty()){
			// don't change the walking direction
		}else{
			walking_direction = walking_script.elementAt(0);
		}
		
		if(walking == true && global.Managers.GraphMan.text_playing == false){
			curframe = (curframe + walking_speed) % total_frames;
			if(blocked == false && jumping == false){
				switch(walking_direction){
					case 1:
						posXF -= walking_speed;
						global.Managers.GraphMan.nudge(-walking_speed, 0);
						break;
					case 2:
						posYF -= walking_speed;
						global.Managers.GraphMan.nudge(0, -walking_speed);
						break;
					case 3:
						posXF += walking_speed;
						global.Managers.GraphMan.nudge(+walking_speed, 0);
						break;
					case 4:
						posYF += walking_speed;
						global.Managers.GraphMan.nudge(0, +walking_speed);
						break;
				}
			} // if blocked, display the animation, but don't move an inch
			// if jumping, display the animation, but move according to a custom formula
			else if(blocked == false && jumping == true){
				// walking speed is always 2 when jumping
				switch(walking_direction){
				case 1:
					posXF -= walking_speed;
					global.Managers.GraphMan.nudge(-walking_speed, 0);
					break;
				case 2:
					posYF -= walking_speed;
					global.Managers.GraphMan.nudge(0, -walking_speed);
					break;
				case 3:
					posXF += walking_speed;
					global.Managers.GraphMan.nudge(+walking_speed, 0);
					break;
				case 4:
					posYF += walking_speed;
					global.Managers.GraphMan.nudge(0, +walking_speed);
					break;
				}
				posYF += (walking_distance - (16 - walking_speed / 2.0)) / 6.0;
			}
			
			walking_distance += walking_speed;
			
			walked_one_square:{ // this block of code is for when the player has finished walking one square.
			if(walking_distance >= 16){
				// set walking back to false
				if(blocked == false && jumping == false){
					++pedometer;
					switch(walking_direction){
					case 1:
						--cur_gridX;
						break;
					case 2:
						--cur_gridY;
						break;
					case 3:
						++cur_gridX;
						break;
					case 4:
						++cur_gridY;
						break;
					}
				}else if(walking_distance >= 32 && jumping == true){
					++pedometer;
					switch(walking_direction){
					case 1:
						cur_gridX -= 2; // add or subtract 2 simply because
						break;			// the jumped square must also be accounted for.
					case 2:
						cur_gridY -= 2;
						break;
					case 3:
						cur_gridX += 2;
						break;
					case 4:
						cur_gridY += 2;
						break;
					}
				}else if(jumping == true){
					break walked_one_square;
					// do nothing, don't do the stuff afterward either.
				}else{
					blocked = false;
				}
				walking = false;
				jumping = false;
				turning = true;
				walking_script.remove(0);
				walking_distance = 0;
				
				if(walking_script.size() > 0){
					walking = true;
					walking_direction = walking_script.elementAt(0); // take the next command 
				}else{
					// if there is a text-queue waiting, do that first.
					
					//if global.Managers.GraphMan.
					global.Managers.InputMan.key_input_enabled = true;
				}
				
				// do other actions here, for example, some squares may have actions associated with them.
				
				/*if(cur_gridX == global.Managers.GraphMan.curMap().UBoundX()){
					global.Managers.GraphMan.display_text("Better not go off the map...");
					walk(1);
				}else if(cur_gridX == global.Managers.GraphMan.curMap().LBoundX()){
					global.Managers.GraphMan.display_text("Better not go off the map...");
					walk(3);
				}else if(cur_gridY == global.Managers.GraphMan.curMap().LBoundY()){
					global.Managers.GraphMan.display_text("Better not go off the map...");
					walk(4);
				}else if(cur_gridY == global.Managers.GraphMan.curMap().UBoundY()){
					global.Managers.GraphMan.display_text("Better not go off the map...");
					walk(2);
				}*/
				
			}} // end walked_one_square
		}else if(turning == true){
			--curframe;
			if(curframe <= 0){
				global.Managers.InputMan.key_input_enabled = true;
				turning = false;
			}
		}else{
			curframe = 0;
		}
	}
	
	public void walk(int _direction){
		
		Integer direction = _direction;
		
		if(walking == false){switch(direction){
		case 1:
			if(global.Managers.GraphMan.curMap().accessible(cur_gridX - 1, cur_gridY) == false)
				if(global.Managers.GraphMan.curMap().jumpable(cur_gridX - 1, cur_gridY, 1) == false)
					blocked = true;
				else
					jumping = true;
			break;
		case 2:
			if(global.Managers.GraphMan.curMap().accessible(cur_gridX, cur_gridY - 1) == false)
				if(global.Managers.GraphMan.curMap().jumpable(cur_gridX, cur_gridY - 1, 2) == false)
					blocked = true;
				else
					jumping = true;
			break;
		case 3:
			if(global.Managers.GraphMan.curMap().accessible(cur_gridX + 1, cur_gridY) == false)
				if(global.Managers.GraphMan.curMap().jumpable(cur_gridX + 1, cur_gridY, 3) == false)
					blocked = true;
				else
					jumping = true;
			break;
		case 4:
			if(global.Managers.GraphMan.curMap().accessible(cur_gridX, cur_gridY + 1) == false)
				if(global.Managers.GraphMan.curMap().jumpable(cur_gridX, cur_gridY + 1, 4) == false)
					blocked = true;
				else
					jumping = true;
			break;
		}} // if the square you're trying to walk to is inaccessible, block it
		if(walking_direction == direction){
			walking = true;
			walking_script.addElement(direction);
			global.Managers.InputMan.key_input_enabled = false;
		}else if(turning == true){
			walking = true;
			turning = false;
			walking_script.addElement(direction);
			global.Managers.InputMan.key_input_enabled = false;
		}else if(walking == true){ // only for scripts
			walking_script.addElement(direction);
			global.Managers.InputMan.key_input_enabled = false;
		}else{
			turning = true;
			curframe = 4; // stop for 0.08 seconds
			walking_direction = direction;
			global.Managers.InputMan.key_input_enabled = false;
			// if the key is pressed, just turn in that direction. Don't add to the walking script.
		}
		
	} // Another difference is that the PlayerSprite is linked to the InputManager.
	
}