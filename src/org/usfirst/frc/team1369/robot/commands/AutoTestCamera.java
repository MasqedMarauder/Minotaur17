package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.subsystems.GearGrabber;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoTestCamera extends Auto {

	public AutoTestCamera() {
		super();
	}
	
	public void stop() {
		driveTrain.setControlMode(TalonControlMode.PercentVbus);
		driveTrain.setTarget(0);
		
	}
	
	public void auto() {
		try{
			Robot.isDisabled = false;
			Robot.speedShift.set(Mode.TORQUE);
			Utils.sleep(500);
			
			Utils.resetRobot();
			double totalDist = Robot.rangeSensor.getDistance();
			int totalSteps = 1;
			Robot.driveTrain.moveByGyroDistance(70, Direction.BACKWARD, 1200, 5, 1000);

			for(int i = 0; i < totalSteps; i++){
				
				Utils.sleep(100);
				double angle = Robot.camera.getAngle();
				while(Math.abs(angle) > 80){
					System.out.println("Problem");
					angle = Robot.camera.getAngle();

				}
				//Robot.driveTrain.turnP(angle/4, (angle < 0 ? Direction.COUNTERCLOCKWISE : Direction.CLOCKWISE), .5, 2, 2000);
				Utils.sleep(100);
				double dist = Robot.rangeSensor.getDistance();
				System.out.println("dist: " + dist);
				Robot.driveTrain.moveByGyroDistance(dist - 2, Direction.BACKWARD, 1200, 5, 1000);
				
				System.out.println("finished moving");
				
			}
			
			//Robot.driveTrain.turnP(360, Direction.CLOCKWISE, .2, 1, 100);
			Robot.driveTrain.moveByGyroDistance(4, Direction.BACKWARD, 1000, 5, 1000);
			
			Robot.gearGrabber.set(GearGrabber.Mode.OPEN);
			Utils.sleep(500);
			Robot.driveTrain.moveByGyroDistance(20, Direction.FORWARD, 500, 5, 1000);
			
			//Robot.speedShift.set(Mode.SPEED);
		}catch(Exception e){
			System.out.println("Auto Interrupted");
		}
	}
	
	public double convert(double inches) {
		return (inches * 14 * Math.PI) / 54;
	}
	
}
