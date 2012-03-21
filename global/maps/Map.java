package global.maps;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Map{
	
	// file layout
	
	// header is simply a 4-byte string, representing the width and height.
	// afterwards, there are 3 * W * H bytes, representing the tile grid, accessibility grid, etc.
	
	// After that, there is a single byte determining the number of exits in the map.
	
	int[][] layer1; // the main grid of blocks
	int[][] layer2; // the grid on top. Sometimes blocks will overlay other blocks.
	int[][] solid; // determines whether the player can go onto this block or not.
	
	Dimension[] exits; // the places where one can go on or off a map.
	int exitmaps, exitcoords; // each exit has a map that it leads to.
	
	int n_exits;
	
	// index for "solid":
	// 		0 = walkable square
	//		1 = square occupied by a wall
	//		2 = square occupied by an interactive object
	
	//	101 - 104 - jumpable in the direction 1 to 4
	
	public Map(File infile) throws IOException{ // loading a map from a file
		n_exits = 0;
		FileInputStream reader = null;
		try {
			reader = new FileInputStream(infile);
			
			int width = 0, height = 0;
			
			width = reader.read() + reader.read() * 256;
			height = reader.read() + reader.read() * 256;
			
			layer1 = new int[height][width];
			layer2 = new int[height][width];
			solid = new int[height][width];
			
			System.out.format("%d %d %n", width, height);
			for(int a = 0; a < height; ++a){
				for(int b = 0; b < width; ++b){
					int read_data = (reader.read() + reader.read() * 256);
					layer1[b][a] = read_data;
				}
			}
			for(int a = 0; a < height; ++a){
				for(int b = 0; b < width; ++b){
					int read_data = (reader.read() + reader.read() * 256);
					layer2[b][a] = read_data;
				}
			}
			for(int a = 0; a < height; ++a){
				for(int b = 0; b < width; ++b){
					int read_data = (reader.read() + reader.read() * 256);
					solid[b][a] = read_data;
				}
			}
			
			// read the exits.
			
			n_exits = reader.read(); // from 0 to 255; no more than 255 exits per map.
			
			for(int a = 0; a < n_exits; ++a){
				
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Error 24 - Map file not found.");
		} finally {
			if(reader != null)
				reader.close();
			// close the stream.
		}
	}
	
	@SuppressWarnings("static-access")
	public void draw_map(Graphics g, int scrnX, int scrnY){
		if(global.GameState.getWorldType() == 0){
			for(int a = 0; a < layer1[0].length; ++a){
				for(int b = 0; b < layer1.length; ++b){
					g.drawImage(global.Managers.GraphMan.tiles.getTile(layer1[b][a]), b*16 - scrnX, a*16 - scrnY + 8, null);
					if(layer2[b][a] != 0)
					g.drawImage(global.Managers.GraphMan.tiles.getTile(layer2[b][a]), b*16 - scrnX, a*16 - scrnY + 8, null);
					// layers 1 and 2 use the same tile index.
				}
			}
		}
	}
	
	public int blocktype(int x, int y){
		return layer1[x][y];
	}
	
	public int solidblock(int x, int y){
		return solid[x][y];
	}
	
	public boolean accessible(int x, int y){
		if(global.Managers.GraphMan.player.get_coordinates().equals(new Dimension(x,y))) return false;
		
		// for the grid square the player is currently on, as well as the square the player is going towards
		
		if(x < 0) return false;
		if(y < 0) return false;
		if(x >= layer1[0].length) return false;
		if(y >= layer1.length) return false;		
		if(solid[x][y] == 0 || solid[x][y] >= 256){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean jumpable(int x, int y, int direction){
		if(x < 0) return false;
		if(y < 0) return false;
		if(x >= layer1[0].length) return false;
		if(y >= layer1.length) return false;
		if(solid[x][y] > 100 && solid[x][y] < 105){
			if(solid[x][y] == direction + 100) return true;
			else return false;
		}else{
			return false;
		}
	}
	
	public int LBoundX(){
		return 0;
	}
	public int LBoundY(){
		return 0;
	}
	public int UBoundX(){
		return layer1[0].length - 1;
	}
	public int UBoundY(){
		return layer1.length - 1;
	}
	
}