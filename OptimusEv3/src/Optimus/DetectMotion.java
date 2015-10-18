package Optimus;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.MoveController;
import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class DetectMotion {
	//Initializes the ultrasonic sensor and specifies the port
	private EV3UltrasonicSensor ultraSensor = new EV3UltrasonicSensor(SensorPort.S3);
	
	//Method to return the distance of an object in flot[] 
	public int detectObject(){
		float[] onDist = new float[10];
		boolean detection = true;
		
		//enables the ultrasonic sensor
		ultraSensor.enable();
		//loop to check if ultrasonic sensor is enabled
		if (ultraSensor.isEnabled()){
			while (detection){
				ultraSensor.getDistanceMode().fetchSample(onDist, 0);
				
				//the sample is always in the first variable in the array
				if (onDist[0] > 0.10){
					//gives signal to controller to move
					return 1;
				} 
				else {
					//Distance to object is too near tell controller to stop.
					return 0;
				}	
			}
		}
		else{
			//display error message
				LCD.drawString("Ultrasonic Sensor not enabled", 0, 4);
				Delay.msDelay(5000);
		}
		//stop by default
			return 0;
	}
}