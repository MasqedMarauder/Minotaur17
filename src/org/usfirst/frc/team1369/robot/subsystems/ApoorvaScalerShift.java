package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.ApoorvaMap;
import org.usfirst.frc.team1369.robot.commands.ApoorvaAutoUtils;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ApoorvaScalerShift {

	private DoubleSolenoid shift = ApoorvaMap.solScalerShift;
	public static Value climber = Value.kForward;
	public static Value drive = Value.kReverse;
	//private static boolean wasToggled = false;
	
	private long prevToggleTime;
	
	public ApoorvaScalerShift() {
		reset();
	}
	
	public void reset() {
		shift.set(drive);
		smartdashboard();
		prevToggleTime = 0;
	}
	
	public void smartdashboard() {
		SmartDashboard.putString("Scaler Shift", isDriveMode() ? "Drive Mode" : "Scaler Mode");
	}

	public boolean isDriveMode() {return shift.get() == drive;}
	
	public boolean isScalerMode() {return shift.get() == climber;}
	
	public void toggle() {
		// check if the current time is 1.5 seconds more than the previous toggle time
		if((System.nanoTime() - prevToggleTime) > nanoTimeConvert(1.5)) {
			prevToggleTime = System.nanoTime();
			shift.set(isScalerMode() ? drive : climber);
			smartdashboard();
		}
	}
	
	public double nanoTimeConvert(double sec) {
		return sec * (1E9);
	}
	
	public void teleop() {
		if(ApoorvaMap.gamepad.getRawButton(ApoorvaMap.JOY_ROPESHIFT)) {
			toggle();
		}
	}
	
}
