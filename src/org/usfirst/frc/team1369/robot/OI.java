package org.usfirst.frc.team1369.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team1369.robot.commands.ExampleCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements Constants{
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	public static final Joystick gamepad = new Joystick(RobotMap.gamepadPort);
	public static int BTN_A = 1;
	public static int BTN_B = 2;
	public static int BTN_X = 3;
	public static int BTN_Y = 4;
	public static int BTN_LB = 5;
	public static int BTN_RB = 6;
	public static int BTN_BACK = 7;
	public static int BTN_START = 8;
	public static int BTN_LEFT_JOYSTICK = 9;
	public static int BTN_RIGHT_JOYSTICK = 10;

	Button buttonA = new JoystickButton(gamepad, BTN_A),
			buttonB = new JoystickButton(gamepad, BTN_B),
			buttonX = new JoystickButton(gamepad, BTN_X),
			buttonY = new JoystickButton(gamepad, BTN_Y),
			buttonLB = new JoystickButton(gamepad, BTN_LB),
			buttonRB = new JoystickButton(gamepad, BTN_RB),
			buttonBack = new JoystickButton(gamepad, BTN_BACK),
			buttonStart = new JoystickButton(gamepad, BTN_START);
			
	
	public OI(){
		
	}
	
	public double getLeftY(){
		return gamepad.getRawAxis(LEFT_Y_AXIS);
	}
	
	public double getRightX(){
		return gamepad.getRawAxis(LEFT_X_AXIS);
	}
	
	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
