package org.usfirst.frc.team1369.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class MinoRangeSensor extends AnalogInput implements Constants {
	
	public MinoRangeSensor(int channel){
		super(channel);
	}

	public double getDistance(){
		return getMillimeters() / 25.4;
	}
	
	public double getMillimeters(){
		return getVoltage() / VOLTS_PER_MM;
	}
	
}