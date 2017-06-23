package processingtest;

import processing.core.PApplet;
import processing.core.PShape;

public class Transform2d extends PApplet {
	Robot robot1;
	Robot robot2;
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
		
		robot1 = new Robot(50, 150, new int[] {0, 255, 0});
		robot2 = new Robot();
		
		starSetup();
	}

	public void draw() {
		background(255);
		
		robot1.draw();
		robot2.draw();
		
		translate(mouseX, mouseY);
		scale((float)0.25);
		shape(star);
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
			this(150, 150, 255, 0, 0);
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
			fill(38, 38, 200);
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
