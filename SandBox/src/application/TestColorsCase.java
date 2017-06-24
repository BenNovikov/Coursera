package application;

public class TestColorsCase {
	public static void main(String[] args) {
		String colorName = "RED";
		checkColor(colorName);
		checkColor("ORANGE");		
	}
	
	public static void checkColor(String col) {
		int color = 0;
				
		switch(col) {
		case "BLUE": 
			color = 1;
			break;
		case "RED" :
	        color = 2;
	        break;
		case "ORANGE" :
	        color = 3;
	        break;
		default : 
	        color = 0;
		};

		System.out.printf("Color: %s %d\n", col, color);
	}
}
