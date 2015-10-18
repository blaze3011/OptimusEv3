package Optimus;

import java.io.IOException;

public class OptimusMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MotionController motCont = new MotionController();
		try {
			motCont.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
