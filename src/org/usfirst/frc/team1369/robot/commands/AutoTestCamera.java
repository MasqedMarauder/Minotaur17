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
			Utils.sleep(1000);
			
			double angle = Robot.camera.getAngle();
			Direction dir;
			if(angle < 0){
				dir = Direction.COUNTERCLOCKWISE;
			}
			else{
				dir = Direction.CLOCKWISE;
			}
			Robot.driveTrain.turnP(angle/2, dir, .75, 10);

			
			
			//Robot.speedShift.set(Mode.SPEED);
		}catch(Exception e){
			System.out.println("Auto Interrupted");
		}
	}
	
	public double convert(double inches) {
		return (inches * 14 * Math.PI) / 54;
	}
	
}
