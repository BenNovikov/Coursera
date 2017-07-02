package processingtest;

import processing.core.PApplet;

public class Lesson1 extends PApplet {
	
    public static void main(String[] args) {
        PApplet.main("processingtest.Lesson1");
    }
    
	public void settings() {
		size(400, 400);
	}
	
	public void setup() {
		smooth();
		frameRate(30);
		setBackground();	
	}
	
	public void draw() {
		setBackground();
		// Set ellipses and rects to CENTER mode
		ellipseMode(CENTER);
		rectMode(CENTER);
		// Draw Zoog's body
		stroke(0);
		fill(175);
		rect(mouseX, mouseY, 20, 100);
		// Draw Zoog's head
		stroke(0);
		fill(255);
		ellipse(mouseX, mouseY - 30, 60, 60);
		// Draw Zoog's eyes
		fill(mouseX, 0, mouseY);
		ellipse(mouseX - 19, mouseY - 30, 16, 32);
		ellipse(mouseX + 19, mouseY - 30, 16, 32);
		// Draw Zoog's legs
		stroke(0);
		line(mouseX - 10, mouseY + 50, mouseX - 20, mouseY + 60);
		line(mouseX + 10, mouseY + 50, mouseX + 20, mouseY + 60);
	}

	public void mousePressed() {
		stroke(0);
		fill(84);
		rectMode(CENTER);
		rect(mouseX, mouseY - 10, 16, 16);
	}
	
	public void keyPressed() {
		background(255);
	}
		
	public void setBackground() {
		background(255);
		noStroke();
		
		rectMode(CORNER);
		fill(255,0,0);
		rect(0, 0, 300, 300);
		
		colorMode(RGB,255,255,255,255);
		ellipseMode(CORNER);
		fill(150, 0, 0, 127);
		ellipse(100, 100, 300, 300);	
	}
}
