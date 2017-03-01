package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.subsystems.GearGrabber;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoPIDTuning extends Auto {

	public AutoPIDTuning() {
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
			Robot.speedShift.set(Mode.TORQUE);
			Utils.sleep(500);
			
			Utils.resetRobot();
			Utils.sleep(1000);
	
			Robot.driveTrain.resetEncoders();
			//Robot.driveTrain.memeMove(SmartDashboard.getNumber("Distance 2 Move", 12), 25, 20);
			Robot.driveTrain.moveInches(convert(SmartDashboard.getNumber("Distance 2 Move", 12)), Direction.BACKWARD, 
					(int)SmartDashboard.getNumber("Allowable Error", 40));
			Robot.driveTrain.turnP(48, Direction.COUNTERCLOCKWISE, .75, 10);
			Robot.driveTrain.moveInches(convert(SmartDashboard.getNumber("Distance 2 Move", 12)), Direction.BACKWARD, 
					(int)SmartDashboard.getNumber("Allowable Error", 40));
			
			
			Utils.sleep(20);
			Robot.driveTrain.stopDrive();
			//Robot.speedShift.set(Mode.SPEED);
		}catch(Exception e){
			System.out.println("Auto Interrupted");
		}
	}
	
	public double convert(double inches) {
		return (inches * 14 * Math.PI) / 53.0;
	}
	
}
