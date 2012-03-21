package global.sprites;


import java.awt.Image;

public class AnimatedSprite extends Sprite{

	int total_frames;
	Image[] frames;
	int[] animation_script;
	int curframe = 0;
	
	public AnimatedSprite(){
		// do nothing
	}
	
	public AnimatedSprite(Image image, int x, int y) {
		frames = new Image[1];
		total_frames = 1;
	}
	
	public AnimatedSprite(Image[] multiple_images, int x, int y, int nFrames) {
		frames = multiple_images;
		pos_X = x;
		pos_Y = y;
		total_frames = nFrames;
		animation_script = new int[nFrames];
	}
	
	public void setAnimationScript(int[] script){
		animation_script = script;
	}
	
	public void advance_one_frame(){
		curframe = (curframe + 1) % total_frames; 
	}
	
	public Image getImage(){
		return frames[animation_script[curframe]];
	}
	
}