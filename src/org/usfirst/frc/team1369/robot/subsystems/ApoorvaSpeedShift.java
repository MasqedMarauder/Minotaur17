package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.ApoorvaMap;
import org.usfirst.frc.team1369.robot.commands.ApoorvaAutoUtils;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ApoorvaSpeedShift {

	public static Value speedMode = Value.kReverse;
	public static Value torqueMode = Value.kForward;
	
	//private static boolean wasToggled = false;
	
	private DoubleSolenoid shift = ApoorvaMap.solSpeedShift;
	
	private long prevToggleTime;
	
	public ApoorvaSpeedShift() {
		reset();
	}
	
	public void reset() {
		shift.set(speedMode);
		smartdashboard();
		prevToggleTime = 0;
	}
	
	public void smartdashboard() {
		SmartDashboard.putString("Speed Shift", isSpeedMode() ? "Speed Mode" : "Torque Mode");
	}
	
	public boolean isSpeedMode() {return shift.get() == speedMode;}

	public boolean isTorqueMode() {return shift.get() == torqueMode;}
	
	public void toggle() {
		/*
		if (!wasToggled) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					wasToggled = true;
					
					shift.set(isSpeedMode() ? torqueMode : speedMode);
					smartdashboard();
					
					AutoUtils.sleeper(1500);
					wasToggled = false;
				}
				
			}).start();
		}
		*/
		
		if((System.nanoTime() - prevToggleTime) > nanoTimeConvert(1.5)) {
			prevToggleTime = System.nanoTime();
			shift.set(isSpeedMode() ? torqueMode : speedMode);
			smartdashboard();
		}

	}
	
	public void teleop() {
		if(ApoorvaMap.gamepad.getRawButton(ApoorvaMap.JOY_SPDSHIFT)) {
			toggle();
		}
	}
	
	public double nanoTimeConvert(double sec) {
		return sec * (1E9);
	}
	
}
