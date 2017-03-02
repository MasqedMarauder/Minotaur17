package org.usfirst.frc.team1369.robot.subsystems;

import org.usfirst.frc.team1369.robot.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class MinoDoubleSol extends Subsystem implements Constants {

	private final DoubleSolenoid sol;
	
	private long prevToggleTime;
	
	public MinoDoubleSol(int forwardChannel, int reverseChannel) {
		sol = new DoubleSolenoid(forwardChannel, reverseChannel);
		prevToggleTime = 0L;
	}
	
	public boolean safetyTimeoutCleared() {
		if ((System.nanoTime() - prevToggleTime) > 1.5E9) {
			prevToggleTime = System.nanoTime();
			return true;
		}
		return false;
	}
	
	public void set(Value value) {
		if (sol.get() != value && safetyTimeoutCleared()) {
			sol.set(value);
			smartDashboard();
		}
	}
	
	public void toggle() {
		if (safetyTimeoutCleared()) {
			sol.set(sol.get() == Value.kForward ? Value.kReverse : Value.kForward);
			smartDashboard();
		}
	}
	
	public Value getValue() {return sol.get();}
	
	public abstract void smartDashboard();
	
	public void reset() {}

}
