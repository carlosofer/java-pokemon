package global.maps;

import java.io.File;
import java.io.IOException;

public class MapCollection{
	
	// do nothing
	
	Map[] maps;
	
	public MapCollection(){
		loadMaps();
	}
	
	public void loadMaps(){
		maps = new Map[1];
		try {
			maps[0] = new Map(new File("maps/map1.map"));
		} catch (IOException e) {
			// do nothing?
		}
	}
	
	public Map getMap(int a){
		return maps[a];
	}
	
}