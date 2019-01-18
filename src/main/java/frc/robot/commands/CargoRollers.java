package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

//variables
  

public class CargoRollers extends Command {
  public void SpinSPT() {
    requires(Robot.spt);
  }

  @Override
  protected void initialize() {
    //set arm to starting position
  }

  @Override //called repeatedly
  protected void execute() {
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
