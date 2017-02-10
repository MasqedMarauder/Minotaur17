package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.ApoorvaMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ApoorvaIntake extends Subsystem{

	
	
	public ApoorvaIntake() {
		
	}

	public void teleop() {
		if(ApoorvaMap.gamepad.getRawButton(6)) {
			ApoorvaMap.victorIntake.set(-1);
		} else if(ApoorvaMap.gamepad.getRawButton(8)) {
			ApoorvaMap.victorIntake.set(1);
		} else {
			ApoorvaMap.victorIntake.set(0);
		}
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}

	public void reset() {
		ApoorvaMap.victorIntake.set(0);
	}
	
}
