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
		compressor = new Compressor(RobotMap.PCM_ID);	//basically, you make a new compressor in the compressorsubsystem
	}
	
    @Override
	public void initDefaultCommand() {
		setDefaultCommand(new CompressorToggle()); //creates the command
	}
    //closes a loop and starts the compressor
    public void startCompressor() {
    	compressor.setClosedLoopControl(true); //turns on compressor
    	compressor.start(); //runs the compressor
    }
    //stops the compressor by turning it off
    public void stopCompressor() {
		compressor.stop();
    }
    //calls to smartdashboard and displayed messages depending on what is enabled or changed. 
    public void reportCompressorStatus(){
    	SmartDashboard.putBoolean("Compressor Enabled", compressor.enabled());
    	SmartDashboard.putBoolean("Compressor in Closed Looop", compressor.getClosedLoopControl());
    	SmartDashboard.putNumber("Compressor Current Value", compressor.getCompressorCurrent());
    	SmartDashboard.putBoolean("Compressor Pressure Switch On/Off", compressor.getPressureSwitchValue());
    }
    // returns the value of the enabled compressor so that if its enabled, you can actually, you know, use it. 
    public boolean isEnabled(){
    	return compressor.enabled();
    }
}