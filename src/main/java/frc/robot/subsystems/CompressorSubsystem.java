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
	
	private Compressor compressor;

	public CompressorSubsystem() {
		compressor = new Compressor(RobotMap.PCM_ID);	// Instantiate the Compresser Variable
	}
	
    @Override
	public void initDefaultCommand() {	// 
		setDefaultCommand(new CompressorToggle());
	}
    
    public void startCompressor() {	// Here the Compresser Turns On
    	compressor.setClosedLoopControl(true);
    	compressor.start();
    }
    
    public void stopCompressor() {	// Here the Compresser Turns Off
		compressor.stop();
    }
    
    public void reportCompressorStatus(){
    	SmartDashboard.putBoolean("Compressor Enabled", compressor.enabled());
    	SmartDashboard.putBoolean("Compressor in Closed Looop", compressor.getClosedLoopControl());
    	SmartDashboard.putNumber("Compressor Current Value", compressor.getCompressorCurrent());
    	SmartDashboard.putBoolean("Compressor Pressure Switch On/Off", compressor.getPressureSwitchValue());
    }	

    public boolean isEnabled(){
    	return compressor.enabled();
    }
}