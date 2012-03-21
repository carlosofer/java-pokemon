package global.maps;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileIndex{
	
	Image[] tiles;
	
	String prefix(int a){ // 4 digits
		String output = "" + a;
		while(output.length() < 4){
			output = "0" + output;
		}
		return output;
	}
	
	public TileIndex(){
		tiles = new Image[2969];
		File m = null;
		for(int a = 1; a <= 2968; ++a){ // the tiles are numbered from 1 to 2968 - 0 is a null tile.
			//System.out.println(a);
			try{
				m = new File("pictures/tiles/" + prefix(a) + ".png");
				tiles[a] = ImageIO.read(m);
			}catch(FileNotFoundException e){
				System.out.println("Error 404 - File not found");
			}catch(IOException e){
				// do nothing, I guess.
			}finally{
			}
		}
		
	}
	
	public Image getTile(int index){
		if(tiles[index] != null){
			return tiles[index];
		}else{
			System.out.println("Error 704 - Tile not found");
			return null;
		}
	}
	
	
}