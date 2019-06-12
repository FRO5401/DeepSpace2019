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
		compressor = new Compressor(RobotMap.PCM_ID);	
	}
	
    @Override
	public void initDefaultCommand() {
		//Default command not needed, called from OI.
	}
	
		//Turn the compressor on.
    public void startCompressor() {
    	compressor.setClosedLoopControl(true);
    	compressor.start();
    }
	
		//Turn the compressor off. 
    public void stopCompressor() {
		compressor.stop();
    }

		//Report all available sensors for smart dashboard.
    public void reportCompressorStatus(){
    	SmartDashboard.putBoolean("Compressor Enabled", compressor.enabled());
    	SmartDashboard.putBoolean("Compressor in Closed Looop", compressor.getClosedLoopControl());
    	SmartDashboard.putNumber("Compressor Current Value", compressor.getCompressorCurrent());
    	SmartDashboard.putBoolean("Compressor Pressure Switch On/Off", compressor.getPressureSwitchValue());
    }
	
		//Returns if the compressor is enabled or not. (Used for compressor toggle.)
    public boolean isEnabled(){
    	return compressor.enabled();
    }
}