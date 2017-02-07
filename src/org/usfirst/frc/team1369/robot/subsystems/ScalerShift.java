package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.RobotMap;
import org.usfirst.frc.team1369.robot.commands.AutoUtils;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ScalerShift {

	private DoubleSolenoid shift = RobotMap.solScalerShift;
	public static Value climber = Value.kForward;
	public static Value drive = Value.kReverse;
	private static boolean wasToggled = false;
	
	private long prevToggleTime;
	
	public ScalerShift() {
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
		if (!wasToggled) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					wasToggled = true;
					
					shift.set(isDriveMode() ? climber : drive);
					smartdashboard();
					
					AutoUtils.sleeper(1500);
					wasToggled = false;
				}
				
			}).start();
		}
		
		/*
		if((System.nanoTime() - prevToggleTime) > nanoTimeConvert(4)) {
			prevToggleTime = System.nanoTime();
			shift.set(isScalerMode() ? drive : climber);
			smartdashboard();
		}
		*/
	}
	
	public double nanoTimeConvert(double sec) {
		return sec * (10^9);
	}
	
	public void teleop() {
		if(RobotMap.gamepad.getRawButton(RobotMap.JOY_ROPESHIFT)) {
			toggle();
		}
	}
	
}
