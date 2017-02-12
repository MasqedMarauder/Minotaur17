package org.usfirst.frc.team1369.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearGrabber extends MinoDoubleSol {

	public enum Mode {
		OPEN (Value.kReverse),
		CLOSED (Value.kForward),
		DISABLED (Value.kOff);
		
		private final Value value;
		
		Mode(Value val) {value = val;}
		
		public Value value() {return value;}
	}
	
	public GearGrabber() {
		super(gearGrabberForChan, gearGrabberRevChan);
		set(Mode.CLOSED);
	}
	
	public void set(Mode mode) {set(mode.value);}
	
	public boolean isOpen() {return getValue() == Mode.OPEN.value;}
	public boolean isClosed() {return getValue() == Mode.CLOSED.value;}
	public boolean isDisabledMode() {return getValue() == Mode.DISABLED.value;}
	
	public void smartDashboard() {
		SmartDashboard.putString("GearGrabber", isDisabledMode() ? "Disabled" : (isOpen() ? "Open" : "Closed"));
	}

	public void teleop(Joystick gamepad) {
		if (gamepad.getRawButton(BTN_LB)) {set(Mode.OPEN);}
		else {set(Mode.CLOSED);}
	}

}
