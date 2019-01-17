package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

public class PopHatch extends Command {
  private boolean input;

  public PopHatch(boolean oiInput) {
    requires(Robot.hatchmechansim);
    input = oiInput;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    boolean hatchInOut = Robot.oi.xboxButton(RobotMap.XBOX_BUTTON_(put xbox button here), Robot.oi.xboxController_Operator);

    if(input == true){
      Robot.hatchmechanism.openHatch();
    } else if(input == false){
      Robot.hatchmechanism.closeHatch();
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
