package Optimus;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class Pilot {
	
	private RegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
	private RegulatedMotor motorC = new EV3LargeRegulatedMotor(MotorPort.C);

	public void forward(){
		motorB.forward();
		motorC.forward();
	}
	
	public void reverse(){
		motorB.backward();
		motorC.backward();
	}
	
	public void left(){
		motorB.rotate(120);
    	motorC.rotate(240);
	}
	
	public void right(){
		motorB.rotate(240);
    	motorC.rotate(120);
	}
	
	public void stop(){
		
		motorB.stop();
		motorC.stop();
	}
}
