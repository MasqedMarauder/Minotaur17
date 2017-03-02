package org.usfirst.frc.team1369.robot.auto;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.subsystems.GearGrabber;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

import com.ctre.CANTalon.TalonControlMode;

public class AutonomousGearBlueBoiler extends Autonomous {
	
	public void endExecution() {
		driveTrain.setControlMode(TalonControlMode.PercentVbus);
		driveTrain.setTarget(0);
		
	}
	
	public void startExecution() {
		try{
			Robot.speedShift.set(Mode.TORQUE);
			Utils.sleep(500);
			
			Utils.resetRobot();
			Utils.sleep(1000);
	
			//driveTrain.moveByDistance(4*Math.PI, Direction.BACKWARD, 0.5);
			Robot.driveTrain.resetEncoders();
			Robot.driveTrain.moveInches(convert(60), Direction.BACKWARD, 128);
			Utils.sleep(500);
			Robot.driveTrain.turnP(48, Direction.CLOCKWISE, .75, 10);
			Utils.sleep(500);
			Robot.driveTrain.resetEncoders();
			Robot.driveTrain.moveInches(convert(45), Direction.BACKWARD, 50);
			
			
			//Robot.speedShift.set(Mode.SPEED);
		}catch(Exception e){
			System.out.println("Auto Interrupted");
		}
	}
	
	public double convert(double inches) {
		return (inches * 14 * Math.PI) / 54;
	}
	
}
