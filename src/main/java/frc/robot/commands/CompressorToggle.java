package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CompressorToggle extends Command {

    //Whether or not the compressor is on
    private boolean compressorToggle;
	
    public CompressorToggle() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.compressorsubsystem);
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
        //If the compressor is not already enabled,
        if(Robot.compressorsubsystem.isEnabled() == false){
            //Start the compressor
            Robot.compressorsubsystem.startCompressor();
        } else {
            //Stop the compressor
            Robot.compressorsubsystem.stopCompressor();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
        //Compressor is toggled using the XBox BACK Button
        compressorToggle = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_BACK);

        //Compressor is set to stop if it is already enabled
        if(compressorToggle && Robot.compressorsubsystem.isEnabled()){
            Robot.compressorsubsystem.stopCompressor();
        } //else, the compressor is set to run
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
        //Stops the compressor
    	Robot.compressorsubsystem.stopCompressor();
    	//System.out.println("CompressorToggle Interrupted");
    }
}
