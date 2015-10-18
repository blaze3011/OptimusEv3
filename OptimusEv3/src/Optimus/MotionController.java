package Optimus;

import java.io.IOException;

import lejos.hardware.Button;

public class MotionController {
	private Pilot movePilot = new Pilot();
	private DetectMotion detMot = new DetectMotion();
	private CamDisplay camDisp = new CamDisplay();
	private MotionController moveControl = new MotionController();
	
	//To determine whether to detect motion or to move left or right
	private boolean startCase = true;
	
	public void start() throws IOException{
		//To exit the program when escape button is pressed
		while(Button.ESCAPE.isUp()) {
			//Checks which case is needed
			if (startCase == true){
				moveControl.detectDistance();
			}
			else {
				moveControl.leftRightMovement();
			}
		}
	}
	//Uses the DetectMotion class to detect the distance of the object 
	private void detectDistance (){
		int recDist = 0;
		
		//Receives command from detectMotion to move forward
		if (recDist == 1){
			movePilot.forward();
			startCase = true;
		}
		else {
			//Receives command from detectMotion to stop
			movePilot.stop();
			startCase = false;
		}	
	}
	
	//Uses the CamDisplay to check if the object is moving left or right
	private void leftRightMovement() throws IOException{
		int moveCom = 0;
		moveCom = camDisp.motionStart();
		
		//The instruction from CamDisplay is to move left
		if (moveCom == 1){
			movePilot.left();
			startCase = true;
			// move left
		} else if (moveCom == 2){
			movePilot.right();
			startCase = true;

			//move right
		} else if (moveCom == 0){
			movePilot.stop();
			startCase = false;
			//stop
		} else {
			movePilot.stop();
			startCase = false;
			//stop
		}
	}
}
