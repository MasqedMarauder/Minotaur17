package org.usfirst.frc.team1369.robot;

import edu.wpi.first.wpilibj.SPI;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public interface Constants {
	
	//Hardware Ports	
	int masterLeftPort		= 1;
	int slaveLeftMidPort	= 3;
	int slaveLeftPort		= 2;
	int masterRightPort		= 4;
	int slaveRightMidPort	= 5;
	int slaveRightPort		= 6;
	
	int gearGrabberForChan	= 6;
	int gearGrabberRevChan	= 7;
	
	int scalerShiftForChan	= 4;
	int scalerShiftRevChan	= 5;
	
	int speedShiftForChan	= 2;
	int speedShiftRevChan	= 3;
	
	int intakePort			= 2;
	
	SPI.Port gyroPort = SPI.Port.kOnboardCS0;
	
	int gamepadPort = 0;
	
	//Gamepad Axes and Buttons
	int LEFT_X_AXIS		= 0;
	int LEFT_Y_AXIS		= 1;
	int RIGHT_X_AXIS	= 4;
	int RIGHT_Y_AXIS	= 5;
	
	int BTN_A 				= 1;
	int BTN_B				= 2;
	int BTN_X				= 3;
	int BTN_Y				= 4;
	int BTN_LB				= 5;
	int BTN_RB				= 6;
	int BTN_BACK			= 7;
	int BTN_START			= 8;
	int BTN_LEFT_JOYSTICK	= 9;
	int BTN_RIGHT_JOYSTICK	= 10;
	
	int DPAD_NOT_PRESSED = -1;
	
	int DPAD_UP		= 0;
	int DPAD_RIGHT	= 90;
	int DPAD_DOWN	= 180;
	int DPAD_LEFT	= 270;
	
	int DPAD_NORTH		= 0;
	int DPAD_NORTHEAST	= 45;
	int DPAD_EAST		= 90;
	int DPAD_SOUTHEAST	= 135;
	int DPAD_SOUTH		= 180;
	int DPAD_SOUTHWEST	= 225;
	int DPAD_WEST		= 270;
	int DPAD_NORTHWEST	= 315;
	
	
	double kfDriveTrainVbus = .18514;
	double kpDriveTrainVbus = 0.3;
	double kiDriveTrainVbus = 0.0;
	double kdDriveTrainVbus = 0.0;
	
	
	float NOMINAL_OUTPUT_VOLTAGE	= 0.0f;
	float PEAK_OUTPUT_VOLTAGE		= 12.0f;
	

	int ENCODER_PPR = 256;
	double WHEEL_DIAMETER = 4.0;
	double CLICKS_PER_INCH = (ENCODER_PPR * 4) / (WHEEL_DIAMETER * Math.PI);
	
}
