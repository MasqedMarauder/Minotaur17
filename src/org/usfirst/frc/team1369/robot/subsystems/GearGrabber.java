package org.usfirst.frc.team1369.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearGrabber extends MinoDoubleSol {

	public enum Mode {
		OPEN (Value.kForward),
		CLOSED (Value.kReverse),
		DISABLED (Value.kOff);
		
		private final Value value;
		Mode(Value val) {value = val;}
		public Value value() {return value;}
	}
	
	public GearGrabber() {
		super(gearGrabberForChan, gearGrabberRevChan);
		set(Mode.CLOSED);
		smartDashboard();
	}
	
	@Override
	public void reset() {
		super.reset();
		set(Mode.CLOSED);
		smartDashboard();
	}
	
	public void set(Mode mode) {set(mode.value);}
	
	public boolean isOpen() {return getValue() == Mode.OPEN.value;}
	public boolean isClosed() {return getValue() == Mode.CLOSED.value;}
	public boolean isDisabledMode() {return getValue() == Mode.DISABLED.value;}
	
	public void smartDashboard() {
		SmartDashboard.putString("GearGrabber", isDisabledMode() ? "Disabled" : (isOpen() ? "Open" : "Closed"));
	}

	public void initDefaultCommand() {}

}
