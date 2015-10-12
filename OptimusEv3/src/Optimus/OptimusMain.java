package Optimus;

import java.io.IOException;

public class OptimusMain {

	public static void main(String[] args) {
			// TODO Auto-generated method stub
		DetectMotion detectMot = new DetectMotion();
		CamDisplay camDisp = new CamDisplay();
		
		try {
			camDisp.CamStart();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//detectMot.detectObject();
	
	}
}
