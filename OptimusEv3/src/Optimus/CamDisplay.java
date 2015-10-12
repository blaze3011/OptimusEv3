package Optimus;

import java.io.IOException;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.video.Video;

public class CamDisplay {

	private static final int WIDTH = 160;
    private static final int HEIGHT = 120;
    //private static final int NUM_PIXELS = WIDTH * HEIGHT;
    
    private static int [][] luminanceFrame = new int [HEIGHT][WIDTH];
    private static int threshold = 70;
    
     
    public CamDisplay() {
		// Initialize luminance frame
    	for (int x=0; x<WIDTH; x += 1) {
    		for (int y=0; y<HEIGHT; y += 1) {
    			luminanceFrame[y][x] = 0;
    		}
    	}
	}
    
    public void CamStart() throws IOException{
    	 EV3 ev3 = (EV3) BrickFinder.getLocal();
         Video video = ev3.getVideo();
         video.open(WIDTH, HEIGHT);
         byte[] frame = video.createFrame();
          
         while(Button.ESCAPE.isUp()) {
             video.grabFrame(frame);
             // y1: pos 0; u: pos 1; y2: pos 2; v: pos 3.
             // Create a frame of luminance values
             extractLuminanceValues(frame);
             // Display the frame
             dispFrame();
             
             // Adjust threshold?
             if (Button.UP.isDown()) {
                 threshold +=1;
                 if (threshold > 255)
                     threshold = 255;
             }
             else if (Button.DOWN.isDown()) { 
             	threshold -=1;
                 if (threshold < 0)
                     threshold = 0;
             }
         }
         video.close();
    }
     
    /*private static void play(File file) {
        long now = System.currentTimeMillis();
         
        if (now - lastPlay > 2000) {
            System.out.println("Playing " + file.getName());
            Sound.playSample(file);
            lastPlay = now;
        }
    }*/
    
    
//    public static void extractLuminanceValues(byte [] frame) {
//    	int x,y;
//    	int quadWidth = 4*WIDTH; // y1: pos 0; u: pos 1; y2: pos 2; v: pos 3.
//    	for(int i=0;i<frame.length;i+=4) {
//    		x = (i % quadWidth)/4;
//    		y = i / quadWidth;
//    		luminanceFrame[y][x] = frame[i];
//    	}	
//    }
    
    // DO: Improve this possibly by combining with chrominance values.
/*    public static void extractLuminanceValues(byte [] frame) {
    	int x,y;
    	int doubleWidth = 2*WIDTH; // y1: pos 0; u: pos 1; y2: pos 2; v: pos 3.
    	int frameLength = frame.length;
    	for(int i=0;i<frameLength;i+=2) {
    		x = (i / 2) % WIDTH;
    		y = i / doubleWidth;
    		luminanceFrame[y][x] = frame[i];
    	}
    }*/
    
    public static void extractLuminanceValues(byte [] frame) {
    	int x,y;
    	int doubleWidth = 2*WIDTH; // y1: pos 0; u: pos 1; y2: pos 2; v: pos 3.
    	int frameLength = frame.length;
    	for(int i=0;i<frameLength;i+=2) {
    		x = (i / 2) % WIDTH;
    		y = i / doubleWidth;
    		//luminanceFrame[y][x] = 2*Math.abs((int) frame[i]);
    		luminanceFrame[y][x] = frame[i] & 0xFF;   		
    	}
    }
    
    public static void dispFrame() {
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
      
//    private static int convertYUVtoARGB(int y, int u, int v) {
//        int c = y - 16;
//        int d = u - 128;
//        int e = v - 128;
//        int r = (298*c+409*e+128)/256;
//        int g = (298*c-100*d-208*e+128)/256;
//        int b = (298*c+516*d+128)/256;
//        r = r>255? 255 : r<0 ? 0 : r;
//        g = g>255? 255 : g<0 ? 0 : g;
//        b = b>255? 255 : b<0 ? 0 : b;
//        return 0xff000000 | (r<<16) | (g<<8) | b;
//    }


}
