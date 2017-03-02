package org.usfirst.frc.team1369.robot;

import edu.wpi.first.wpilibj.SPI;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// Hardware Ports
	public static int masterLeftPort = 1;
	public static int slaveLeftMidPort = 3;
	public static int slaveLeftPort = 2;
	public static int masterRightPort = 4;
	public static int slaveRightMidPort = 5;
	public static int slaveRightPort = 6;

	public static int gearGrabberForChan = 6;
	public static int gearGrabberRevChan = 7;

	public static int scalerShiftForChan = 4;
	public static int scalerShiftRevChan = 5;

	public static int speedShiftForChan = 3;
	public static int speedShiftRevChan = 2;

	public static int shootaurIPort = 0;
	public static int intakePort = 1;
	public static int shootaurIIPort = 2;

	public static SPI.Port gyroPort = SPI.Port.kOnboardCS0;

	public static int gamepadPort = 0;

	// Gamepad Axes and Buttons
	public static int LEFT_X_AXIS = 0;
	public static int LEFT_Y_AXIS = 1;
	public static int RIGHT_X_AXIS = 4;
	public static int RIGHT_Y_AXIS = 5;

}
