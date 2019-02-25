package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class PopHatch extends Command {
  
  public PopHatch() {
    requires(Robot.hatchmechanism);
  }

  @Override
  protected void initialize() {
    Robot.hatchmechanism.openHatch();
  }

  @Override
  protected void execute() {
    boolean hatchInOut = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_Y);

    //if Y button is pressed, close hatch
    if(hatchInOut){
      Robot.hatchmechanism.closeHatch();
    }
    //if nothing is pressed, open hatch (default position)
    else{
      Robot.hatchmechanism.openHatch();
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
