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
		//instantiates compressor
		compressor = new Compressor(RobotMap.PCM_ID);	
	}
	
    @Override
	public void initDefaultCommand() {
		//default command is CompressorToggle
		setDefaultCommand(new CompressorToggle());
	}

	//starts compressor
    public void startCompressor() {
    	compressor.setClosedLoopControl(true);
    	compressor.start();
    }
	
	//stops compressor
    public void stopCompressor() {
		compressor.stop();
    }
	
	//reports values to smart dashboard relating to the compressor
    public void reportCompressorStatus(){
    	SmartDashboard.putBoolean("Compressor Enabled", compressor.enabled());
    	SmartDashboard.putBoolean("Compressor in Closed Looop", compressor.getClosedLoopControl());
    	SmartDashboard.putNumber("Compressor Current Value", compressor.getCompressorCurrent());
    	SmartDashboard.putBoolean("Compressor Pressure Switch On/Off", compressor.getPressureSwitchValue());
    }
	
	//tells if compressor is enabled or not
    public boolean isEnabled(){
    	return compressor.enabled();
    }
}