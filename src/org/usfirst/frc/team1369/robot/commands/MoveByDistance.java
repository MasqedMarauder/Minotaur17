package org.usfirst.frc.team1369.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1369.robot.Constants;
import org.usfirst.frc.team1369.robot.Robot;

/**
 *
 */
public class MoveByDistance extends Command implements Constants {
	
	private static final double p_gain = 0.05;
	
	private final int targetClicks;
	private final Direction direction;
	private final double speed;
	
	private int clicksRemaining;
	private double inchesRemaining;
	private double power;
	
	public MoveByDistance(double inches, Direction direction, double speed) {
		requires(Robot.driveTrain);
		targetClicks = (int) (inches * CLICKS_PER_INCH);
		this.direction = direction;
		this.speed = speed;
	}
	
	@Override
	protected void initialize() {
		Robot.driveTrain.resetEncoders();
	}

	@Override
	protected void execute() {
			clicksRemaining = targetClicks - Math.abs(Robot.driveTrain.getRightTalon().getEncPosition());
			inchesRemaining = clicksRemaining / CLICKS_PER_INCH;
			power = direction.value * speed * inchesRemaining * p_gain;
			Robot.driveTrain.setTarget(power);
	}

	@Override
	protected boolean isFinished() {
		return inchesRemaining > 3;
	}

	@Override
	protected void end() {
		Robot.driveTrain.stopDrive();
	}

	@Override
	protected void interrupted() {
		Robot.driveTrain.stopDrive();
	}
}
