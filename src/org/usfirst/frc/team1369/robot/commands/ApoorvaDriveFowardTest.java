package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.ApoorvaMap;
import org.usfirst.frc.team1369.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ApoorvaDriveFowardTest extends Command{

	boolean isApoorva;
	
	public ApoorvaDriveFowardTest(){
		requires(Robot.driveTrain);
		isApoorva = false;
	}
	
	
	
	public void driveDistance(int pos) {
		ApoorvaMap.masterLeft.setEncPosition(0);
		
		Robot.driveTrain.setControlMode(TalonControlMode.PercentVbus);
		
		double previousError = 0;
		double integral = 0;
		
		double heading = Robot.driveTrain.gyro.getAngle();
		double dt = System.nanoTime();
		
		while(ApoorvaMap.masterLeft.getEncPosition() < pos) {
			double error = heading - Robot.driveTrain.gyro.getAngle();
			if(Math.abs(error) > 10) {
				dt = System.nanoTime();
				integral += (error * dt);
				double derivative = (error - previousError) / dt;
				
				double val = (0.1 * error) + (0.1 * integral) + (0.1 * derivative);
				
				previousError = error;
				
				ApoorvaMap.masterLeft.set(0.25);
				ApoorvaMap.masterRight.set(-0.25);	
			}
		}
		
		ApoorvaMap.masterLeft.set(0);
		ApoorvaMap.masterRight.set(0);
	}
	
	@Override
	protected void initialize() {
		Robot.resetRobot();
		ApoorvaAutoUtils.sleeper(500);
		//driveDistance(1024);
		
		Robot.driveTrain.driveDistance(6);
		//Robot.driveTrain.driveDistance(1);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Left Encoder", ApoorvaMap.masterLeft.getEncPosition());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isApoorva;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.resetRobot();
	}
	

}
