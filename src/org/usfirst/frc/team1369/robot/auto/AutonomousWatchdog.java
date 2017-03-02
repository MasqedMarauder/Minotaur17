package org.usfirst.frc.team1369.robot.auto;

public class AutonomousWatchdog extends Thread {

	public Autonomous autonomous;
	
	public AutonomousWatchdog(Autonomous auto) {
		super();
		
		this.autonomous = auto;
	}
	
	@Override
	public void run() {
		this.autonomous.start();
		try {
			Thread.sleep(15000);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(autonomous.isAlive()) {
				this.autonomous.interrupt();
			}
			this.autonomous.endExecution();
		}
	}
	
}
