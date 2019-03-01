package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

/*
 * Command controls the Hatch Mechanism.
 * Either OPEN or CLOSE
 */

public class PopHatch extends Command {
  
  public PopHatch() {
    requires(Robot.hatchmechanism);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.hatchmechanism.openHatch();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean hatchInOut = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_Y);

    if(hatchInOut){
      Robot.hatchmechanism.closeHatch();
    }
    else{
      Robot.hatchmechanism.openHatch();
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
