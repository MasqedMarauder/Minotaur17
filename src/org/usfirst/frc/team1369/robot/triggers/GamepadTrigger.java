package org.usfirst.frc.team1369.robot.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class GamepadTrigger extends Button{

	private int axis;
	private Joystick gamepad;
	
	public GamepadTrigger(Joystick gamepad, int axis){
		this.axis = axis;
		this.gamepad = gamepad;
	}
	
	@Override
	public boolean get() {
		if((gamepad.getRawAxis(axis) > .85)){
			return true;
		}else{
			return false;
		}
	}

}
