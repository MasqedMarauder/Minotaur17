package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.RobotMap;
import org.usfirst.frc.team1369.robot.commands.AutoUtils;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpeedShift {

	public static Value speedMode = Value.kReverse;
	public static Value torqueMode = Value.kForward;
	
	private DoubleSolenoid shift = RobotMap.solSpeedShift;
	
	private boolean wasToggled = false;
	
	public SpeedShift() {
		reset();
	}
	
	public void reset() {
		shift.set(speedMode);
		smartdashboard();
		wasToggled = false;
	}
	
	public void smartdashboard() {
		SmartDashboard.putString("Speed Shift", shift.get() == speedMode ? "Speed Mode" : "Torque Mode");
	}
	
	public boolean isSpeedMode() {
		return shift.get() == speedMode;
	}
	
	public void toggle() {
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
	}
	
	public void teleop() {
		if(RobotMap.gamepad.getRawButton(RobotMap.JOY_SPDSHIFT)) {
			toggle();
		}
	}
	
}
