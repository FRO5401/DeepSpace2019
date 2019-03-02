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
		compressor = new Compressor(RobotMap.PCM_ID);	//makes a new instance of the Compressor class and has the location of the port from RobotMap
	}
	
    @Override
	public void initDefaultCommand() {
		setDefaultCommand(new CompressorToggle()); //"Attaches" this subsystem to the CompressorToggle command
	}
    
    public void startCompressor() {
    	compressor.setClosedLoopControl(true); //runs the compressor
    	compressor.start();
    }
    
    public void stopCompressor() {
		compressor.stop(); //stops the compressor from running
    }
    
    public void reportCompressorStatus(){
    	SmartDashboard.putBoolean("Compressor Enabled", compressor.enabled());
    	SmartDashboard.putBoolean("Compressor in Closed Looop", compressor.getClosedLoopControl());
    	SmartDashboard.putNumber("Compressor Current Value", compressor.getCompressorCurrent());
		SmartDashboard.putBoolean("Compressor Pressure Switch On/Off", compressor.getPressureSwitchValue());
		//reports different aspects of the compressor i.e if it's running, what loop control it's in, the electrical current the compressor is using, and the level of pressure
    }
    
    public boolean isEnabled(){
    	return compressor.enabled(); //sends a true/false value indicating whether or not the compressor is running or not
    }
}