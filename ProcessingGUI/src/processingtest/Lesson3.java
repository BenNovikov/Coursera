package processingtest;

import processing.core.PApplet;

public class Lesson3 extends PApplet {

	public static void main(String[] args) {
		PApplet.main("processingtest.Lesson3");
	}
	Zoog zoog;

	public void settings() {
		size(200, 200);
	}
	public void setup() {
		smooth();
		zoog = new Zoog(100, 125, 60, 60, 16);
	}
	public void draw() {
		background(255);
		// mouseX position determines speed factor
		float factor = constrain(mouseX/10, 0, 5);
		zoog.jiggle(factor);
		zoog.display();
	}

	class Zoog {
		// Zoog's variables
		float x, y, w, h, eyeSize;

		// Zoog constructor
		Zoog(float tempX, float tempY, float tempW, float tempH, float tempEyeSize) {
			x = tempX;
			y = tempY;
			w = tempW;
			h = tempH;
			eyeSize = tempEyeSize;
		}
		
		public void jiggle(float speed) {
			x = constrain(x + random(-1, 1) * speed, 20, width - 20);
			y = constrain(y + random(-1, 1) * speed, 20, height - 20);			
			println(x + " w:" + width + " " + y + "h:" + height);
		}
		
		public void display() {
			ellipseMode(CENTER);
			rectMode(CENTER);
			
			// Draw Zoog's arms with a for loop
			for (float i = y - h/3; i < y + h/2; i += 10) {
			stroke(0);
			line(x-w/4, i, x + w/4, i);
			}
			// Draw Zoog's body
			stroke(0);
			fill(175);
			rect(x, y, w/6, h);
			// Draw Zoog's head
			stroke(0);
			fill(255);
			ellipse(x, y-h, w, h);
			// Draw Zoog's eyes
			fill(0);
			ellipse(x - w/3, y - h, eyeSize, eyeSize*2);
			ellipse(x + w/3, y - h, eyeSize, eyeSize*2);
			// Draw Zoog's legs
			stroke(0);
			line(x - w/12, y + h/2, x - w/4, y + h/2 + 10);
			line(x + w/12, y + h/2, x + w/4, y + h/2 + 10);
		}
	}


}
