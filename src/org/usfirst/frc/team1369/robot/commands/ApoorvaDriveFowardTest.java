package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ApoorvaDriveFowardTest extends Command{

	public ApoorvaDriveFowardTest(){
		requires(Robot.driveTrain);
	}
	
	@Override
	protected void initialize() {
		
		Robot.driveTrain.driveDistance(12);
		ApoorvaAutoUtils.sleeper(1000);
		Robot.driveTrain.turn(90);
		
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}
	

}
