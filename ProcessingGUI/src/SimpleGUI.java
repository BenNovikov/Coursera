import processing.core.PApplet;
import processing.core.PImage;

public class SimpleGUI extends PApplet {
	PImage img;
	int[] color = new int[4];
	
    public static void main(String[] args) {
        PApplet.main("SimpleGUI");
    }
    
    public void settings() {
    	  size(300, 500);
    	}
	
	public void setup(){
		background(240);
		noStroke();
//		stroke(0);
		img = loadImage("https://www.google.com.ua/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
		img.resize(width, height);
		image(img, 0, 0);
	}
	
	public void draw(){
		color = objectColor(second(), color[3]);
		fill(color[0], color[1], color[2]);
		ellipse(mouseX, mouseY, width/6, height/6);
	}
	
	public int[] objectColor(float seconds, int previous){
		int[] rgb = new int[4];
		float divider = 36;
		float freq = (seconds - divider)/divider;
		float ratio = Math.abs(freq - Math.round(freq));		
		
		rgb[0] = (int)(255*ratio);
		rgb[1] = (int)(255*ratio);
		rgb[2] = (int)(255*ratio);
		int current = rgb[0] + rgb[1] + rgb[2];
//		System.out.printf("current:%d previous:%d ", current, previous);
		if(current != previous){
			System.out.printf("seconds:%05.2f ", seconds);
//			System.out.printf("previous:%04d current:%04d ", previous, current);
			System.out.printf("r:%3d g:%3d b:%3d\n", rgb[0], rgb[1], rgb[2]);
		}
		rgb[3] = current;				
		
		return rgb;
	}

}
