package Optimus;

import java.io.IOException;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.video.Video;
import lejos.robotics.RegulatedMotor;

public class CamDisplay {

	private static final int WIDTH = 160;
    private static final int HEIGHT = 120;
    //private static final int NUM_PIXELS = WIDTH * HEIGHT;
    
    private int [][] luminanceFrame = new int[HEIGHT][WIDTH];
    private int threshold = 145;
    private MotionMap aMotMap = new MotionMap();
    
    public CamDisplay() {
		// Initialize luminance frame
    	for (int x=0; x<WIDTH; x += 1) {
    		for (int y=0; y<HEIGHT; y += 1) {
    			luminanceFrame[y][x] = 0;
    		}
    	}
	}
    
    public int motionStart() throws IOException{
    	EV3 ev3 = (EV3) BrickFinder.getLocal();
        Video video = ev3.getVideo();
        video.open(WIDTH, HEIGHT);
        int diff = 0;
        byte[] frame = video.createFrame();
         
        //escape sequence 
        while(Button.ESCAPE.isUp()) {
            video.grabFrame(frame);
            // y1: pos 0; u: pos 1; y2: pos 2; v: pos 3.
            // Create a frame of luminance values
            extractLuminanceValues(frame);
            // Motion processing
            aMotMap.addFrame(luminanceFrame);
            aMotMap.compMotion();
            aMotMap.compLeftRight();
            
            //System.out.println("Max motion: " + aMotMap.compMaxMotion());
            System.out.println("L:" + (int) aMotMap.leftMotion + " R:" + (int) aMotMap.rightMotion);
           
            //gets a difference value to start the movement loop
            diff = (int) aMotMap.leftMotion - (int) aMotMap.rightMotion;
            	
            
            //1 is left 0 is stop 2 is right
            while (Math.abs(diff) > 15){
	            if ((int) aMotMap.leftMotion > (int) aMotMap.rightMotion){
	            	diff = 0;
	            	return 1;
	            } else if ((int) aMotMap.leftMotion == (int) aMotMap.rightMotion){
	            	diff = 0;
	            	return 0;
	            } else if ((int) aMotMap.leftMotion < (int) aMotMap.rightMotion){
	            	diff = 0;
	            	return 2;
	            } else {
	            	diff = 0;
	            	return 0;
	            }
            }
            
            // Display the frame or motion
            //dispFrame();
            //dispMotion();
            
            // Adjust threshold?
            if (Button.UP.isDown()) {
                threshold +=5;
                if (threshold > 255)
                    threshold = 255;
            }
            else if (Button.DOWN.isDown()) { 
            	threshold -=5;
                if (threshold < 0)
                    threshold = 0;
            }
        }
        video.close();
        //wrong
		return diff;
    }
    
    // DO: Improve this possibly by combining with chrominance values.
    public void extractLuminanceValues(byte [] frame) {
    	int x,y;
    	int doubleWidth = 2*WIDTH; // y1: pos 0; u: pos 1; y2: pos 2; v: pos 3.
    	int frameLength = frame.length;
    	for(int i=0;i<frameLength;i+=2) {
    		x = (i / 2) % WIDTH;
    		y = i / doubleWidth;
    		luminanceFrame[y][x] = frame[i] & 0xFF;   		
    	}
    }
    
    public void dispFrame() {
    	for (int y=0; y<HEIGHT; y++) {
    		for (int x=0; x<WIDTH; x++) {
    			if (luminanceFrame[y][x] <= threshold) {
    				LCD.setPixel(x, y, 1);
    			}
    			else {
    				LCD.setPixel(x, y, 0);
    			}	
    		}
    	}
    	
    }
    
    public void dispMotion() {
    	if (aMotMap.motionMap != null) {
    		for (int y=0; y<HEIGHT; y++) {
    			for (int x=0; x<WIDTH; x++) {
    				if (aMotMap.motionMap[y][x] <= threshold) {
    					LCD.setPixel(x, y, 0);
    				}
    				else {
    					LCD.setPixel(x, y, 1);
    				}	
    			}
    		}
    	}
    }
    
}


