package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.subsystems.Shootaur;

import edu.wpi.first.wpilibj.command.Command;

public class IndexerToggle extends Command {

	private Shootaur.IndexerMode mode;
	private boolean isFinished;
	
	public IndexerToggle(Shootaur.IndexerMode mode) {
		requires(Robot.shootaur);
		this.mode = mode;
	}
	
	@Override
	protected void initialize() {
		isFinished = false;
	}
	
	@Override
	protected void execute() {
		Robot.shootaur.indexer.set(mode.position());
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
