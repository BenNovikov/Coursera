package processingtest;

import processing.core.PApplet;
import processing.core.PShape;

public class Transform2d extends PApplet {
	//Where upper left of robot appears on screen
	final int ROBOT_X = 150;
	final int ROBOT_Y = 150;
	//The robot's midpoint and arm pivot points
	final int MIDPOINT_X = 39;
	final int LEFT_PIVOT_X = 12;
	final int RIGHT_PIVOT_X = 66;
	final int PIVOT_Y = 32;

	float leftArmAngle = 0;
	float rightArmAngle = 0;
	
	PShape star;

	public static void main(String[] args) {
		PApplet.main("processingtest.Transform2d");	
	}

	public void settings() {
		size(400, 400);
	}

	public void setup() {
		smooth();
		frameRate(30);
		
		starSetup();
	}

	public void draw() {
		//These variables are for mouseX and mouseY,
		//adjusted to be relative to the robot's coordinate system
		//instead of the window's coordinate system.		   
		float mX;
		float mY;

		background(255);

		pushMatrix();
		translate(ROBOT_X, ROBOT_Y); 	//place robot so arms are always on screen		

		mX = mouseX - ROBOT_X;
		mY = mouseY - ROBOT_Y;
		//check the robot's side
		if (mX < MIDPOINT_X) { 		
			leftArmAngle = atan2(mY - PIVOT_Y, mX - LEFT_PIVOT_X) - HALF_PI;
		} else {
			rightArmAngle = atan2(mY - PIVOT_Y, mX - RIGHT_PIVOT_X) - HALF_PI;
		}
		drawRobot();
		popMatrix();
		
		translate(mouseX, mouseY);
		scale((float)0.25);
		shape(star);
	}

	void drawRobot() {
		noStroke();
		fill(38, 38, 200);
		rect(20, 0, 38, 30); // head
		rect(14, 32, 50, 50); // body
		
		drawLeftArm();
		drawRightArm();
		
		rect(22, 84, 16, 50); // left leg
		rect(40, 84, 16, 50); // right leg
		
		if (mousePressed) {
			fill(255, 0, 0);
		} else {
			fill(255, 255, 255);
		}		
		ellipse(30, 12, 12, 12); // left eye
		ellipse(47, 12, 12, 12);  // right eye
	}

	void drawLeftArm() {
		pushMatrix();
		translate(12, 32);
		rotate(leftArmAngle);
		rect(-12, 0, 12, 37); // left arm
		popMatrix();
	}

	void drawRightArm() {
		pushMatrix();
		translate(66, 32);
		rotate(rightArmAngle);
		rect(0, 0, 12, 37); // right arm
		popMatrix();
	}
	
	void starSetup() {
		// First create the shape
		star = createShape();
		star.beginShape();
		// You can set fill and stroke
		star.fill(255, 0, 0);
		star.stroke(255);
		star.strokeWeight(2);
		// Here, we are hardcoding a series of vertices
		star.vertex(0, -50);
		star.vertex(14, -20);
		star.vertex(47, -15);
		star.vertex(23, 7);
		star.vertex(29, 40);
		star.vertex(0, 25);
		star.vertex(-29, 40);
		star.vertex(-23, 7);
		star.vertex(-47, -15);
		star.vertex(-14, -20);
		star.endShape(CLOSE);
	}
}
