package battle;

// import pokemon.*;

public class StatModifiers{
	
	public static double stage(double coeff){
		if(coeff < 1){
			return 2.0 - (2.0/coeff);
		}else if(coeff > 1){
			return (coeff*2.0) - 2.0;
		}
		return 0;
	}
	
	public static double fromStage(double stage){
		if(stage < 0){
			return 2.0 / (2.0 - stage);
		}else if(stage > 0){
			return (stage + 2.0) / 2.0; 
		}
		return 1.0;
	}
	
	public static double fromAEStage(double stage){
		if(stage < 0){
			return 3.0 / (3.0 - stage);
		}else if(stage > 0){
			return (stage + 3.0) / 3.0;
		}
		return 1.0;
	}
	
}