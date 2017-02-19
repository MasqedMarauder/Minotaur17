package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;
import org.usfirst.frc.team1369.robot.subsystems.GearGrabber.Mode;

import com.ctre.CANTalon.TalonControlMode;

public class AutoGearTest extends Auto {

	public AutoGearTest() {
		super();
	}
	
	public void stop() {
		driveTrain.setControlMode(TalonControlMode.PercentVbus);
		driveTrain.setTarget(0);
		
	}
	
	public void auto() {
		Utils.resetRobot();
		Utils.sleep(1000);

		driveTrain.moveByDistance(10, Direction.FORWARD, 0.5);
		Utils.sleep(500);
		
		grabber.set(Mode.OPEN);
		Utils.sleep(500);
		
		driveTrain.moveByDistance(10, Direction.BACKWARD, 0.5);
	}
	
}
