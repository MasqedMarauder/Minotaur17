package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1369.robot.subsystems.GearGrabber;

public abstract class Auto {

	public DriveTrain driveTrain;
	public GearGrabber grabber;
	
	public Auto() {
		this.driveTrain = Robot.driveTrain;
		this.grabber = Robot.gearGrabber;
	}
	
	public abstract void auto();
	public abstract void stop();
	
}