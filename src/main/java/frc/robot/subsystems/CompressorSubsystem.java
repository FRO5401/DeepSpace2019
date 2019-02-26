package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.CompressorToggle;

/**
 *
 */
public class CompressorSubsystem extends Subsystem {
	
	//A compressor in the subsystem
	private Compressor compressor;

	public CompressorSubsystem() {
		compressor = new Compressor(RobotMap.PCM_ID);	
	}
	
    @Override
	public void initDefaultCommand() {
		setDefaultCommand(new CompressorToggle());
	}
		
	//Starts the compressor
    public void startCompressor() {
    	compressor.setClosedLoopControl(true);
    	compressor.start();
    }
		
		//Stops the compressor from running
    public void stopCompressor() {
		compressor.stop();
    }
		
		//Checks the status of the compressor
    public void reportCompressorStatus(){
    	SmartDashboard.putBoolean("Compressor Enabled", compressor.enabled());
    	SmartDashboard.putBoolean("Compressor in Closed Looop", compressor.getClosedLoopControl());
    	SmartDashboard.putNumber("Compressor Current Value", compressor.getCompressorCurrent());
    	SmartDashboard.putBoolean("Compressor Pressure Switch On/Off", compressor.getPressureSwitchValue());
    }
		
		//Checks if the compressor is enabled
    public boolean isEnabled(){
    	return compressor.enabled();
    }
}