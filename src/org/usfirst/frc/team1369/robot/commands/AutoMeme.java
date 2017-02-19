package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;

import edu.wpi.first.wpilibj.command.Command;

public class AutoMeme extends Command {

	private DriveTrain driveTrain;

	public AutoMeme() {
		requires(Robot.driveTrain);
		this.driveTrain = Robot.driveTrain;
	}

	/**
	 *
	 */
	private boolean shouldFinish = false;

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Utils.resetRobot();
		Utils.sleep(750);

		//driveTrain.turnP(Robot.camera.getAngle(), Direction.CLOCKWISE, .5);
		Utils.sleep(500);
		shouldFinish = true;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		driveTrain.stopDrive();
		Utils.resetRobot();

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		driveTrain.stopDrive();
		Utils.resetRobot();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return shouldFinish;
	}

}
