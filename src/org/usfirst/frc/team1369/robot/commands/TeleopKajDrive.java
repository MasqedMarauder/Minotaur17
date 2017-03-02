package org.usfirst.frc.team1369.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1369.robot.Constants;
import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.RobotMap;

/**
 *
 */
public class TeleopKajDrive extends Command implements Constants{
	
	private double left_y;
	private double right_y;
	private double right_x;
	
	//double left_y = Robot.driveTrain.deadband(Robot.gamepad.getRawAxis(LEFT_Y_AXIS));
	//double right_y = Robot.driveTrain.deadband(Robot.gamepad.getRawAxis(RIGHT_Y_AXIS));
	//double right_x = Robot.driveTrain.deadband(Robot.gamepad.getRawAxis(RIGHT_X_AXIS));

	public TeleopKajDrive() {
		requires(Robot.driveTrain);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		/*if (!Robot.scalerShift.isScaleMode()) {
			double leftPower = left_y - right_x;
			double rightPower = left_y + right_x;
	
			if (Math.abs(leftPower) > 1) {
				leftPower /= Math.abs(leftPower);
				rightPower /= Math.abs(leftPower);
			}
			if (Math.abs(rightPower) > 1) {
				leftPower /= Math.abs(rightPower);
				rightPower /= Math.abs(rightPower);
			}
			Robot.driveTrain.driveVelocity(1250 * leftPower, 1250 * rightPower);
		} else {
			Robot.driveTrain.driveVbus(-left_y, left_y);
		}
		*/
	}

	@Override
	protected boolean isFinished() {return false;}

	@Override
	protected void end() {}

	@Override
	protected void interrupted() {Robot.driveTrain.stopDrive();}
}
