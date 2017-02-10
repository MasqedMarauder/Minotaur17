
package org.usfirst.frc.team1369.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1369.robot.commands.ApoorvaAutoUtils;
import org.usfirst.frc.team1369.robot.commands.ApoorvaDriveFowardTest;
import org.usfirst.frc.team1369.robot.commands.ApoorvaExampleCommand;
import org.usfirst.frc.team1369.robot.subsystems.ApoorvaDriveTrain;

import com.ctre.CANTalon.TalonControlMode;

import org.usfirst.frc.team1369.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static ApoorvaDriveTrain driveTrain;
	public static ApoorvaGearGrabber gearGrabber;
	public static ApoorvaScalerShift scalerShift;
	public static ApoorvaSpeedShift speedShift;
	public static ApoorvaIntake intake;
	//public static Collecter collecter;
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		driveTrain = new ApoorvaDriveTrain();
		gearGrabber = new ApoorvaGearGrabber();
		scalerShift = new ApoorvaScalerShift();
		speedShift = new ApoorvaSpeedShift();
		intake = new ApoorvaIntake();
		//collecter = new Collecter();
		
		chooser.addDefault("Default Auto", new ApoorvaExampleCommand());
		chooser.addObject("Apoorva Drive Forward", new ApoorvaDriveFowardTest());
		
		SmartDashboard.putData("Auto mode", chooser);
	
		
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(600,600);
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public static void resetRobot() {
		Robot.gearGrabber.reset();
		Robot.scalerShift.reset();
		Robot.speedShift.reset();
		Robot.intake.reset();
		
		driveTrain.reset();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		SmartDashboard.putNumber("Left P", 0.5);
		SmartDashboard.putNumber("Left I", 0.1);
		SmartDashboard.putNumber("Left D", 0.1);
		
		SmartDashboard.putNumber("Right P", 0.5);
		SmartDashboard.putNumber("Right I", 0.1);
		SmartDashboard.putNumber("Right D", 0.1);
		
		resetRobot();
	}

	/**
	 * This function is called periodically during operator control
	 */
	
	double error = 0;
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		SmartDashboard.putNumber("Left Encoder", ApoorvaMap.masterLeft.getEncPosition());
		
		if(!driveTrain.deadband(ApoorvaMap.gamepad.getRawAxis(1)) || !driveTrain.deadband(ApoorvaMap.gamepad.getRawAxis(5))){	
			//driveTrain.driveVelocity(500*ApoorvaMap.gamepad.getRawAxis(1), 500*ApoorvaMap.gamepad.getRawAxis(5)*.93);
			if(!driveTrain.deadband(ApoorvaMap.gamepad.getRawAxis(1)))
				driveTrain.setLeftSpeed(500 * ApoorvaMap.gamepad.getRawAxis(1));
			if(!driveTrain.deadband(ApoorvaMap.gamepad.getRawAxis(5)))
				driveTrain.setRightSpeed(500 * ApoorvaMap.gamepad.getRawAxis(5));
		}
		else
		{
			driveTrain.breakThisRobot();
		}

		SmartDashboard.putString("Speed 1",ApoorvaMap.masterLeft.getSpeed()+"");
		SmartDashboard.putString("Speed 2",ApoorvaMap.masterRight.getSpeed()+"");
		
		
		SmartDashboard.putNumber("LE", ApoorvaMap.masterLeft.getError());
		SmartDashboard.putNumber("RE", ApoorvaMap.masterRight.getError());


		ApoorvaMap.masterLeft.setP(SmartDashboard.getNumber("Left P", 0.5));
		ApoorvaMap.masterLeft.setI(SmartDashboard.getNumber("Left I", 0.1));
		ApoorvaMap.masterLeft.setD(SmartDashboard.getNumber("Left D", 0.1));

		ApoorvaMap.masterRight.setP(SmartDashboard.getNumber("Right P", 0.5));
		ApoorvaMap.masterRight.setI(SmartDashboard.getNumber("Right I", 0.1));
		ApoorvaMap.masterRight.setD(SmartDashboard.getNumber("Right D", 0.1));
		
		SmartDashboard.putNumber("Gyro Apoorva", ApoorvaDriveTrain.gyro.getAngle());
	
		
		gearGrabber.teleop();
		scalerShift.teleop();
		speedShift.teleop();
		intake.teleop();
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
