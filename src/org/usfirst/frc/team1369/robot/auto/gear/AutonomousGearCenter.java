package org.usfirst.frc.team1369.robot.auto.gear;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.auto.Autonomous;
import org.usfirst.frc.team1369.robot.subsystems.GearGrabber;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

import com.ctre.CANTalon.TalonControlMode;

public class AutonomousGearCenter extends Autonomous {
	
	public void endExecution() {
		driveTrain.setControlMode(TalonControlMode.PercentVbus);
		driveTrain.setTarget(0);
		
	}
	
	public void startExecution() {
		try{

			System.out.println("Started");
			Robot.isDisabled = false;
			Robot.speedShift.set(Mode.TORQUE);
			Utils.sleep(100);
			
			Utils.resetRobot();
			
			//driveTrain.moveByDistance(4*Math.PI, Direction.BACKWARD, 0.5);
			System.out.println("Range Sensors: " + Robot.rangeSensor.getDistance());
			System.out.println("In loop 1");
			if(Robot.rangeSensor.getDistance() > 4){
				Robot.driveTrain.moveByGyroDistance(Robot.rangeSensor.getDistance() - 40, Direction.BACKWARD, .75);
			}
			
			Utils.sleep(100);
			/*
			double angle = Robot.camera.getAngle();
			while(Math.abs(angle) > 80){
				Utils.sleep(100);
				System.out.println("problem");
				angle = Robot.camera.getAngle();
			}
			
			Direction dir = (angle > 0 ? Direction.COUNTERCLOCKWISE : Direction.CLOCKWISE);
			*/
			
			//Robot.driveTrain.turnP(angle, dir, .5, 1);
			
			Utils.sleep(200);
			System.out.println(Robot.rangeSensor.getDistance());
			System.out.println("In loop 2");
			Robot.driveTrain.moveByGyroDistance(Robot.rangeSensor.getDistance()-8, Direction.BACKWARD, 0.5);
			Utils.sleep(100);
			Robot.gearGrabber.set(GearGrabber.Mode.OPEN);
			Utils.sleep(100);
			Robot.driveTrain.resetEncoders();
			
			Robot.driveTrain.moveByGyroDistance(20, Direction.FORWARD, 0.5);
			
			//Robot.speedShift.set(Mode.SPEED);
		}catch(Exception e){
			System.out.println("Auto Interrupted");
		}
	}
	
}
