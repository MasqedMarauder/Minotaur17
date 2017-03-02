package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.commands.ModGearGrabber;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearGrabber extends MinoDoubleSol {

	public enum Mode {
		OPEN (Value.kForward),
		CLOSED (Value.kReverse),
		DISABLED (Value.kOff),
		TOGGLE;
		
		private final Value value;
		Mode(Value val) {value = val;}
		Mode() {value = null;}
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
	
	public void set(Mode mode) {
		if (mode == Mode.TOGGLE) toggle();
		else set(mode.value);
	}
	
	public boolean isOpen() {return getValue() == Mode.OPEN.value;}
	public boolean isClosed() {return getValue() == Mode.CLOSED.value;}
	public boolean isDisabledMode() {return getValue() == Mode.DISABLED.value;}
	
	public void smartDashboard() {
		SmartDashboard.putString("GearGrabber", isDisabledMode() ? "Disabled" : (isOpen() ? "Open" : "Closed"));
	}

	public void initDefaultCommand() {
		setDefaultCommand(new ModGearGrabber(Mode.CLOSED));
	}

}
