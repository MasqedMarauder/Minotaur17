package org.usfirst.frc.team1369.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class SleepCommand extends Command
{
	
	private boolean isFinished;

	@Override
	protected void initialize() {
		isFinished = false;
	}

	@Override
	protected void execute() {
		try {
			Thread.sleep(3000);
		}
		catch(Exception e) {
			
		}
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
