package global.sprites;

import java.awt.Image;

public class Sprite{
	
	// Default Sprite container
	// this class contains... an image and some coordinates, that's about it.
	
	Image sptImage;
	int pos_X, pos_Y;
	
	public Sprite(){
		sptImage = null;
		pos_X = 0;
		pos_Y = 0;
	}
	
	public Sprite(Image image){
		sptImage = image;
	}
	
	public Sprite(Image image, int x, int y){
		sptImage = image;
		pos_X = x;
		pos_Y = y;
	}
	
	public Image getImage(){
		return sptImage;
	}
	
	public int getX(){
		return pos_X;
	}
	
	public int getY(){
		return pos_Y;
	}
	
	public void update(){
		// do nothing, this is only a template
	}
	
}