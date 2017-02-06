package org.usfirst.frc.team1369.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static Joystick gamepad = new Joystick(0);
	
	//CANTalons
	public static CANTalon masterLeft = new CANTalon(1);
	public static CANTalon slaveLeftMiddle = new CANTalon(2);
	public static CANTalon slaveLeft = new CANTalon(3);

	public static CANTalon masterRight = new CANTalon(4);
	public static CANTalon slaveRightMiddle = new CANTalon(5);
	public static CANTalon slaveRight = new CANTalon(6);
		
	//solenoids
	public static DoubleSolenoid solScalerShift = new DoubleSolenoid(4,5);
	
	public static DoubleSolenoid solSpeedShift = new DoubleSolenoid(6,7);
	
	public static DoubleSolenoid solGearGrabber = new DoubleSolenoid(2,3);
	
	
	//button mapping
	public static int JOY_ROPESHIFT = 2;
	public static int JOY_SPDSHIFT = 3;
	
	public static int JOY_GRGRB_OPEN = 5;
	public static int JOY_GRGRB_CLOSED = 7;
}
