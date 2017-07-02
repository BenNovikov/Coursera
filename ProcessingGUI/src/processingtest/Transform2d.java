package processingtest;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

public class Transform2d extends PApplet {
	Robot robot1;
	Robot robot2;
	Robot robot3;
	PShape star;
	PImage img;
	int backColor;
	
	public static void main(String[] args) {
		PApplet.main("processingtest.Transform2d");	
	}

	public void settings() {
		size(1000, 400);
	}

	public void setup() {
		smooth();
		frameRate(30);
		
		//position and eyes color, center red by default
		robot1 = new Robot(width * 2/8, height * 4/8, new int[] {0, 255, 0});
		robot2 = new Robot();
		robot3 = new Robot(width * 6/8, height * 4/8, 255, 255, 0);
		
		starSetup();
		
		img = imageSetup();
		image(img, 0, 0);
	}

	public void draw() {
		background(255);
		image(img, 0, 0);
		
		robot1.draw();
		robot2.draw();
		robot3.draw();
		
		translate(mouseX, mouseY);
		scale((float)0.25);
		shape(star);
	}
	
	void starSetup() {
		star = createShape();
		star.beginShape();
		star.fill(255, 0, 0);
		star.stroke(255);
		star.strokeWeight(2);
		float vertices[][] = {
				{0, -50}, 
				{14, -20}, 
				{47, -15}, 
				{23, 7}, 
				{29, 40}, 
				{0, 25}, 
				{-29, 40}, 
				{-23, 7}, 
				{-47, -15}, 
				{-14, -20}};
		for(float each[] : vertices) {
			star.vertex(each[0], each[1]);
		}
		star.endShape(CLOSE);
	}	
	
	PImage imageSetup() {
		String imageSource = "";
		int hr = hour();
		switch(hr) {
			case 5: case 6: case 7: case 8: case 9: case 10:
				imageSource = "http://making-the-internet.s3.amazonaws.com/php-morning.png"; 
				backColor = color(0x865f86);
				break;
			case 11: case 12: case 13: case 14: case 15: case 16:	
				imageSource = "http://making-the-internet.s3.amazonaws.com/php-afternoon.png";
				backColor = color(0x2c87c8);
				break;
			case 17: case 18: case 19: case 20: case 21: case 22:	
				imageSource = "http://making-the-internet.s3.amazonaws.com/php-evening.png";
				backColor = color(0xc7b02f);
				break;
			case 23: case 0: case 1: case 2: case 3: case 4:	
				imageSource = "http://making-the-internet.s3.amazonaws.com/php-night.png";
				backColor = color(0x180829);
				break;
		}
		background(backColor);
		System.out.println(hr + " hours, Background: " + backColor);
		PImage imgSetup = loadImage(imageSource);
		imgSetup.resize(0, height);
		
		return imgSetup;
	}
	
	class Robot {
		//The robot's midpoint and arm pivot points
		static final int MIDPOINT_X = 39;
		static final int LEFT_PIVOT_X = 12;
		static final int RIGHT_PIVOT_X = 66;
		static final int PIVOT_Y = 32;
		//Where upper left of robot appears on screen
		int robotX;
		int robotY;
		int eyesWhite[] = {255, 255, 255};
		int eyesColor[] = eyesWhite.clone();
		float leftArmAngle = 0;
		float rightArmAngle = 0;
		
		Robot(int x, int y, int c0, int c1, int c2) {
			robotX = x;
			robotY = y;
			eyesColor[0] = constrain(c0, 0, 255);
			eyesColor[1] = constrain(c1, 0, 255);
			eyesColor[2] = constrain(c2, 0, 255);
		}	
		
		Robot() {
			this(width * 4/8, height * 4/8, 255, 0, 0);
		}	
		
		Robot(int x, int y, int[] c) {
			this(x, y, c[0], c[1], c[2]);
		}
		
		void draw() {
			//These variables are for mouseX and mouseY,
			//adjusted to be relative to the robot's coordinate system
			//instead of the window's coordinate system.		   
			float mX;
			float mY;

			pushMatrix();
			translate(robotX, robotY); 	//place robot so arms are always on screen		

			mX = mouseX - robotX;
			mY = mouseY - robotY;
			//check the robot's side
			if (mX < MIDPOINT_X) { 		
				leftArmAngle = atan2(mY - PIVOT_Y, mX - LEFT_PIVOT_X) - HALF_PI;
			} else {
				rightArmAngle = atan2(mY - PIVOT_Y, mX - RIGHT_PIVOT_X) - HALF_PI;
			}
			
			drawRobot(mousePressed ? eyesColor : eyesWhite);
			popMatrix();
		}		

		private void drawRobot(int[] eyes) {
			noStroke();
			fill(0, 0, 128);
			rect(20, 0, 38, 30); // head
			rect(14, 32, 50, 50); // body
			
			drawLeftArm();
			drawRightArm();
			
			rect(22, 84, 16, 50); // left leg
			rect(40, 84, 16, 50); // right leg
			
			fill(eyes[0], eyes[1], eyes[2]);
			ellipse(30, 12, 12, 12); // left eye
			ellipse(47, 12, 12, 12);  // right eye
		}

		private void drawLeftArm() {
			pushMatrix();
			translate(12, 32);
			rotate(leftArmAngle);
			rect(-12, 0, 12, 37); // left arm
			popMatrix();
		}

		private void drawRightArm() {
			pushMatrix();
			translate(66, 32);
			rotate(rightArmAngle);
			rect(0, 0, 12, 37); // right arm
			popMatrix();
		}
	}
}
