package org.usfirst.frc.team1369.robot.auto;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain;

public abstract class Autonomous extends Thread {

	public DriveTrain driveTrain = Robot.driveTrain;
	
	public abstract void endExecution();
	public abstract void startExecution();
	
	@Override
	public void run() {
		this.startExecution();
	}

}
