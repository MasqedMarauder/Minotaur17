package org.usfirst.frc.team1369.robot.commands;

public class AutoUtils {
	
	//sleep the current thread
	public static void sleeper(int ms) {
		try {
			Thread.sleep(ms);
		}
		catch(Exception e) { 
			e.printStackTrace();
		}
	}
	
	//will create a new thread to run code
	public static void executeMethod(Runnable run) {
		new Thread(run).start();
	}
	
	public static void drive(int distance, int speed) {
		
	}

	
	
}
