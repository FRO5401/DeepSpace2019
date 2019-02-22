package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
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
