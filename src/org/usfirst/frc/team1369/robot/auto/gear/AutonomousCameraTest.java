package org.usfirst.frc.team1369.robot.auto.gear;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.auto.Autonomous;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

import com.ctre.CANTalon.TalonControlMode;

public class AutonomousCameraTest extends Autonomous {
	
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
	
			Robot.driveTrain.turnP(Robot.camera.getAngle(), Robot.camera.getAngle() < 0 ? Direction.CLOCKWISE : Direction.COUNTERCLOCKWISE, .75, 10);
			Utils.sleep(500);
			Robot.driveTrain.resetEncoders();
			Robot.driveTrain.moveByGyroDistance(Robot.rangeSensor.getDistance()-8, Direction.BACKWARD, 0.5);
			
			
			//Robot.speedShift.set(Mode.SPEED);
			
		}catch(Exception e){
			System.out.println("Auto Interrupted");
		}
	}
	
}
