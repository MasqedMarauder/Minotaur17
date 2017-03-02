package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.subsystems.GearGrabber;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoTestMotion extends Auto {

	public AutoTestMotion() {
		super();
		SmartDashboard.putNumber("Distance 2 Move", 12);
		SmartDashboard.putNumber("Allowable Error", 40);
	}
	
	public void stop() {
		driveTrain.setControlMode(TalonControlMode.PercentVbus);
		driveTrain.setTarget(0);
		
	}
	
	public void auto() {
		try{
			Robot.isDisabled = false;
			Robot.speedShift.set(Mode.SPEED);
			Utils.sleep(500);
			
			Utils.resetRobot();
			Utils.sleep(1000);
			
			Robot.driveTrain.moveByGyroDistance(30, Direction.FORWARD, 0.4);
			
		}catch(Exception e){
			System.out.println("Auto Interrupted");
		}
	}
	
	public double convert(double inches) {
		return (inches * 14 * Math.PI) / 53.0;
	}
	
}
