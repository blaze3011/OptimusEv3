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
	private EV3UltrasonicSensor ultraSensor = new EV3UltrasonicSensor(SensorPort.S2);
	private Pilot movePilot = new Pilot();
	
	public void detectObject(){

		float[] onDist = new float[10];
		boolean detection = true;
		ultraSensor.enable();
			if (ultraSensor.isEnabled()){
				while (detection){
					int count = 0;

					ultraSensor.getDistanceMode().fetchSample(onDist, 0);

					//Movement Code
					if (onDist[count] > 0.10){
						movePilot.forward();
					} 
					else {
						movePilot.stop();
					}	
				}
			} else {
				LCD.drawString("Ultrasonic Sensor not enabled", 0, 4);
				Delay.msDelay(5000);
			}
	}
}