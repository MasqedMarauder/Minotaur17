package org.usfirst.frc.team1369.robot.commands;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;

import com.ctre.CANTalon.TalonControlMode;



public class AutoTurn extends Auto {

	public AutoTurn() {
	super();
	}
	
	@Override
	public void auto() {
		this.threadLock = new Object();
		thread = new Thread(new Runnable() {
			public void run() {
				Utils.resetRobot();
				Utils.sleep(750);
				
				driveTrain.turnP(Robot.camera.getAngle(), Direction.CLOCKWISE, .5, threadLock);
				Utils.sleep(500);
			}
		});
		thread.start();
	}

	@Override
	public void stop() {
		threadLock = null;
		driveTrain.setControlMode(TalonControlMode.PercentVbus);
		driveTrain.setTarget(0);		
	}

}
