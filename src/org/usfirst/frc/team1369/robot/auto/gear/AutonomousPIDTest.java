package org.usfirst.frc.team1369.robot.auto.gear;

import org.usfirst.frc.team1369.robot.Robot;
import org.usfirst.frc.team1369.robot.Utils;
import org.usfirst.frc.team1369.robot.auto.Autonomous;
import org.usfirst.frc.team1369.robot.subsystems.GearGrabber;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousPIDTest extends Autonomous {
	
	public void endExecution() {
		driveTrain.setControlMode(TalonControlMode.PercentVbus);
		driveTrain.setTarget(0);
		
	}
	
	public void startExecution() {
		try{

			System.out.println("Started");
			Robot.isDisabled = false;
			Robot.speedShift.set(Mode.TORQUE);
			Utils.sleep(100);
			
			Utils.resetRobot();

			Robot.driveTrain.turnPID(90, Direction.COUNTERCLOCKWISE, 1.0, SmartDashboard.getNumber("Tune P", 0),
					SmartDashboard.getNumber("Tune I", 0),
					SmartDashboard.getNumber("Tune D", 0),
					SmartDashboard.getNumber("Tune Damper", 0));
		}catch(Exception e){
			System.out.println("Auto Interrupted");
		}
	}
	
}
