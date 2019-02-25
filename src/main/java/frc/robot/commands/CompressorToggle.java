package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;
// it toggles the compressor by toggling the compressor
/**
 *
 */
public class CompressorToggle extends Command {

    //creates a boolean that can toggle the compressor, hence the name compressorToggle
    private boolean compressorToggle; 
	
    public CompressorToggle() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.compressorsubsystem); //it calls to the CompressorSubsystem
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() { //starts the thing
        // if enabled, start compressor; if disable, stop compressor,
        if(Robot.compressorsubsystem.isEnabled() == false){ 
            Robot.compressorsubsystem.startCompressor();
        } else {
            Robot.compressorsubsystem.stopCompressor();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    /*if the back button is pressed and the subsystem is enabled -> stopcompressor, then if back button is pressed and the
    subsystem isn't enabled -> startcompressor */ 
	protected void execute() {
        compressorToggle = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_BACK);

        if(compressorToggle && Robot.compressorsubsystem.isEnabled()){
            Robot.compressorsubsystem.stopCompressor();
        }
        else if(compressorToggle && !(Robot.compressorsubsystem.isEnabled())){
            Robot.compressorsubsystem.startCompressor();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    	//end() does nothing because this command toggles the state
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    	Robot.compressorsubsystem.stopCompressor();
    	//System.out.println("CompressorToggle Interrupted");
    }
}
