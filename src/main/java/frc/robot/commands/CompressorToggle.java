package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

/**
This command is meant to control the compressor
 */
public class CompressorToggle extends Command {

    private boolean compressorToggle;
	
    public CompressorToggle() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.compressorsubsystem);
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
        if(Robot.compressorsubsystem.isEnabled() == false){
            Robot.compressorsubsystem.startCompressor();
        } else {
            Robot.compressorsubsystem.stopCompressor();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
        //Button used for turning the compressor on and off based off of a when-pressed system
        compressorToggle = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_BACK);
        
        //When the button is pressed and the compressor is already enabled, the compressor will be disabled
        if(compressorToggle && Robot.compressorsubsystem.isEnabled()){
            Robot.compressorsubsystem.stopCompressor();
        }
        //When the button is pressed and the compressor is not enabled, the compressor will be disabled
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
        //When the code is interrupted, the compressor will automatically stop
    	Robot.compressorsubsystem.stopCompressor();
    	//System.out.println("CompressorToggle Interrupted");
    }
}
