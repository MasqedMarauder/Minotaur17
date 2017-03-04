package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.subsystems.GearGrabber;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.AutoInterruptedException;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoTestCamera2 extends Auto {

	public AutoTestCamera2() {
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
			
			while(true){
				turnToTarget();
				driveToTarget();
				if(Robot.isDisabled) {
					throw new AutoInterruptedException();
				}
			}

			
			
			//Robot.speedShift.set(Mode.SPEED);
		}catch(Exception e){
			System.out.println("Auto Interrupted");
		}
	}
	
	public void turnToTarget(){
		try{
		Utils.resetRobot();
		Utils.sleep(1000);
		
		double angle = Robot.camera.getAngle();
		Direction dir;
		Robot.driveTrain.turnP(angle/2, angle < 0 ? Direction.COUNTERCLOCKWISE : Direction.CLOCKWISE, .75, 10);
		}catch(Exception e){
		}
	}
	
	public void driveToTarget(){
		try{
		//Robot.driveTrain.moveInches(convert(12), Direction.BACKWARD, (int)SmartDashboard.getNumber("Alllowable Error", 20));
		}catch(Exception e){
			
		}
	}
	public double convert(double inches) {
		return (inches * 14 * Math.PI) / 54;
	}
	
}
