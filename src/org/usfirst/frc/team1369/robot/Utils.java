
package org.usfirst.frc.team1369.robot;

public class Utils {

	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void resetRobot() {
		Robot.driveTrain.reset();
		Robot.speedShift.reset();
		Robot.scalerShift.reset();
		Robot.gearGrabber.reset();
		Robot.intake.reset();

		Robot.driveTrain.getLeftTalon().setEncPosition(0);
		Robot.driveTrain.getRightTalon().setEncPosition(0);
	}

}
