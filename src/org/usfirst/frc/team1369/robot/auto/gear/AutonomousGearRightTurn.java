package org.usfirst.frc.team1369.robot.auto.gear;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.auto.Autonomous;
import org.usfirst.frc.team1369.robot.subsystems.GearGrabber;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

import com.ctre.CANTalon.TalonControlMode;

public class AutonomousGearRightTurn extends Autonomous {
	
	public void endExecution() {
		driveTrain.setControlMode(TalonControlMode.PercentVbus);
		driveTrain.setTarget(0);
		
	}
	
	public void startExecution() {
		try{

			Robot.speedShift.set(Mode.TORQUE);
			Utils.sleep(10);
			
			Utils.resetRobot();
	
			//driveTrain.moveByDistance(4*Math.PI, Direction.BACKWARD, 0.5);
			Robot.driveTrain.resetEncoders();
			Robot.driveTrain.moveByGyroDistance(50, Direction.BACKWARD, 1.0);
			Utils.sleep(300);
			Robot.driveTrain.turnP(49, Direction.CLOCKWISE, .75, 10);
			Utils.sleep(300);
			Robot.driveTrain.resetEncoders();
			Robot.driveTrain.moveByGyroDistance(84, Direction.BACKWARD, 1.0);
			Utils.sleep(300);
			Robot.gearGrabber.set(GearGrabber.Mode.OPEN);
			Utils.sleep(100);
			Robot.driveTrain.moveByGyroDistance(30, Direction.FORWARD, 1.0);
			
			
			//Robot.speedShift.set(Mode.SPEED);
			
		}catch(Exception e){
			System.out.println("Auto Interrupted");
		}
	}
	
}

