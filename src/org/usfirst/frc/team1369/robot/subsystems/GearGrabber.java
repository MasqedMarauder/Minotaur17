package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearGrabber extends Subsystem {
	
	private DoubleSolenoid gearGrabber = RobotMap.solGearGrabber;
	public static Value open = Value.kReverse;
	public static Value closed = Value.kForward;

	public GearGrabber() {
		reset();
	}
	
	public void reset() {
		gearGrabber.set(closed);
		smartdashboard();
	}
	
	public void smartdashboard() {
		SmartDashboard.putString("Gear Grabber", isOpen() ? "Open" : "Closed");
	}

	public boolean isOpen() {return gearGrabber.get() == open;}

	public boolean isClosed() {return gearGrabber.get() == closed;}
	
	public void teleop() {
		if(RobotMap.gamepad.getRawButton(RobotMap.JOY_GRGRB_OPEN)) {
			gearGrabber.set(open);
		}
		else{
			gearGrabber.set(closed);
		}
		smartdashboard();
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}

}
