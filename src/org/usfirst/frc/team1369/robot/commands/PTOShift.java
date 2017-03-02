package org.usfirst.frc.team1369.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.subsystems.ClimberShift.Mode;

/**
 *
 */
public class PTOShift extends Command {
	
	private final Mode mode;
	private boolean isFinished;
	
	public PTOShift(Mode mode) {
		requires(Robot.climberShift);
		this.mode = mode;
	}

	@Override
	protected void initialize() {
		isFinished = false;
	}

	@Override
	protected void execute() {
		Robot.climberShift.set(mode);
		isFinished = true;
	}

	@Override
	protected boolean isFinished() {
		return isFinished;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
