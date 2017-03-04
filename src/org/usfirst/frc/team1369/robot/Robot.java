
package org.usfirst.frc.team1369.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1369.robot.auto.Autonomous;
import org.usfirst.frc.team1369.robot.auto.AutonomousWatchdog;
import org.usfirst.frc.team1369.robot.auto.gear.AutonomousGearRightTurn;
import org.usfirst.frc.team1369.robot.auto.gear.AutonomousPIDTest;
import org.usfirst.frc.team1369.robot.auto.gear.AutonomousGearLeftTurn;
import org.usfirst.frc.team1369.robot.auto.gear.AutonomousCameraTest;
import org.usfirst.frc.team1369.robot.auto.gear.AutonomousGearCenter;
import org.usfirst.frc.team1369.robot.commands.Auto;
import org.usfirst.frc.team1369.robot.commands.AutoGearBlueBoiler;
import org.usfirst.frc.team1369.robot.commands.AutoGearBlueLoader;
import org.usfirst.frc.team1369.robot.commands.AutoGearCenter;
import org.usfirst.frc.team1369.robot.commands.AutoPIDTuning;
import org.usfirst.frc.team1369.robot.commands.AutoTestCamera;
import org.usfirst.frc.team1369.robot.commands.AutoTestCamera2;
import org.usfirst.frc.team1369.robot.commands.AutoTestMotion;
import org.usfirst.frc.team1369.robot.subsystems.*;
import org.usfirst.frc.team1369.robot.subsystems.DriveTrain.Direction;
import org.usfirst.frc.team1369.robot.subsystems.SpeedShift.Mode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static Servo indexerServo = new Servo(3);
	
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

	public static Joystick gamepad;

	public static DriveTrain driveTrain;
	public static SpeedShift speedShift;
	public static ScalerShift scalerShift;
	public static GearGrabber gearGrabber;
	public static Intake intake;
	public static Shootaur shootaur;
	public static MinoRangeSensor rangeSensor;

	public static boolean isTeleop = false;
	public static boolean isDisabled = false;

	Command autonomousCommand;
	SendableChooser<Autonomous> chooser = new SendableChooser<>();

	public static Camera camera;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.z
	 */
	@Override
	public void robotInit() {
		gamepad = new Joystick(Constants.gamepadPort);

		SmartDashboard.putNumber("Tune P", 0);
		SmartDashboard.putNumber("Tune I", 0);
		SmartDashboard.putNumber("Tune D", 0);
		SmartDashboard.putNumber("Tune Damper", 0);
		
		
		/*
		 * SmartDashboard.putNumber("Left PID Constant P",
		 * Constants.kpDriveTrainVbus);
		 * SmartDashboard.putNumber("Left PID Constant I",
		 * Constants.kiDriveTrainVbus);
		 * SmartDashboard.putNumber("Left PID Constant D",
		 * Constants.kdDriveTrainVbus);
		 * SmartDashboard.putNumber("Left PID Constant F",
		 * Constants.kfDriveTrainVbus);
		 * 
		 * SmartDashboard.putNumber("Right PID Constant P",
		 * Constants.kpDriveTrainVbus);
		 * SmartDashboard.putNumber("Right PID Constant I",
		 * Constants.kiDriveTrainVbus);
		 * SmartDashboard.putNumber("Right PID Constant D",
		 * Constants.kdDriveTrainVbus);
		 * SmartDashboard.putNumber("right PID Constant F",
		 * Constants.kfDriveTrainVbus);
		 */

		if (camera == null) {
			camera = new Camera();
			camera.start();
		}
		
		driveTrain = new DriveTrain();
		speedShift = new SpeedShift();
		scalerShift = new ScalerShift();
		gearGrabber = new GearGrabber();
		intake = new Intake();
		shootaur = new Shootaur();
		rangeSensor = new MinoRangeSensor(0);
		oi = new OI();

		chooser.addDefault("Nothing", null);
		chooser.addObject("Gear - Center", new AutonomousGearCenter());
		chooser.addObject("Gear - Left Setup - Right Turn", new AutonomousGearRightTurn());
		chooser.addObject("Gear - Right Setup - Left Turn", new AutonomousGearLeftTurn());
		chooser.addObject("Auto Camera Test", new AutonomousCameraTest());
		chooser.addObject("Auto PID Test", new AutonomousPIDTest());
		
		/*
		 * chooser.addDefault("Nothing", null); chooser.addObject("GearTest",
		 * new AutoGearCenter()); chooser.addObject("BlueLoader", new
		 * AutoGearBlueLoader()); chooser.addObject("BlueBoiler", new
		 * AutoGearBlueBoiler()); chooser.addObject("CameraTest", new
		 * AutoTestCamera()); chooser.addObject("CameraDriving", new
		 * AutoTestCamera2()); chooser.addObject("PID TUning", new
		 * AutoPIDTuning()); chooser.addObject("StraightLineTest", new
		 * AutoTestMotion()); // chooser.addDefault("Default Auto", new
		 * ExampleCommand());
		 */
		// chooser.addObject("My Auto", new GearAuto());
		SmartDashboard.putData("Choose tf upppp 100", chooser);
		
		

		Robot.speedShift.set(Mode.TORQUE);

		Utils.resetRobot();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Robot.isTeleop = false;
		Robot.isDisabled = true;
		SmartDashboard.putNumber("Disabled Init Ran", 1);
	}

	@Override
	public void disabledPeriodic() {
		Robot.isDisabled = true;
		SmartDashboard.putNumber("Disabled Init Ran", 2);
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
		Robot.isDisabled = false;
		SmartDashboard.putNumber("Disabled Init Ran", 0);
		
		camera.setExposure(1);

		try {
			Autonomous auto = chooser.getSelected();
			auto = auto.getClass().newInstance();// - Might work for testing purposes 
			
			if (auto != null) {
				AutonomousWatchdog doggo = new AutonomousWatchdog(auto);
				doggo.start();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		// AutonomousGearBlueBoiler auto = null;
		// AutonomousWatchdog watch = new AutonomousWatchdog(auto);

		// watch.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

		Scheduler.getInstance().run();

		SmartDashboard.putNumber("Left Encoder Value", driveTrain.getLeftTalon().getEncPosition());
		SmartDashboard.putNumber("Right Encoder Value", driveTrain.getRightTalon().getEncPosition());
		
		SmartDashboard.putNumber("Distance:  ", camera.getDistance());
		SmartDashboard.putNumber("Angle:  ", camera.getAngle());

		SmartDashboard.putNumber("LCurrent", driveTrain.getLeftTalon().getOutputCurrent());
		SmartDashboard.putNumber("RCurrent", driveTrain.getRightTalon().getOutputCurrent());

		SmartDashboard.putNumber("GyroAngle", driveTrain.getGyroAngle());

		SmartDashboard.putNumber("Ultra Distance", Robot.rangeSensor.getDistance());

	}

	@Override
	public void teleopInit() {
		isTeleop = true;
		isToggled = false;	
		//camera.setExposure(50);
		camera.setExposure(1);
		Utils.resetRobot();	
	}

	//30 - open
	//50 - closed
	
	boolean isToggled = false;
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		/*
		 * Servos
		 */
		
		driveTrain.teleop(gamepad);
		speedShift.teleop(gamepad);
		scalerShift.teleop(gamepad);
		gearGrabber.teleop(gamepad);
		intake.teleop(gamepad);
		shootaur.teleop(gamepad);

		
		SmartDashboard.putNumber("Distance form egeg", rangeSensor.getVoltage());
		SmartDashboard.putNumber("Left Encoder Value", driveTrain.getLeftTalon().getEncPosition());
		SmartDashboard.putNumber("Right Encoder Value", driveTrain.getRightTalon().getEncPosition());

		SmartDashboard.putNumber("Speed", driveTrain.getRightTalon().getSpeed());

		SmartDashboard.putNumber("Left motor", driveTrain.getLeftTalon().get());
		SmartDashboard.putNumber("Right motor", driveTrain.getRightTalon().get());

		SmartDashboard.putNumber("GyroAngle", driveTrain.getGyroAngle());

		SmartDashboard.putNumber("Distance:  ", camera.getDistance());
		SmartDashboard.putNumber("Angle:  ", camera.getAngle());

		SmartDashboard.putNumber("LCurrent", driveTrain.getLeftTalon().getOutputCurrent());
		SmartDashboard.putNumber("RCurrent", driveTrain.getRightTalon().getOutputCurrent());

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

}
