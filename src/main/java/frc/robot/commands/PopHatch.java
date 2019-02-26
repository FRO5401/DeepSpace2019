package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class PopHatch extends Command { // Command to pick up or release hatches
  
  public PopHatch() {
    requires(Robot.hatchmechanism); // Uses HatchMechanism Subsystem
  }

  @Override
  protected void initialize() {
    Robot.hatchmechanism.openHatch(); // Initialized with Open (Default)
  }

  @Override
  protected void execute() {  // Pressing Y on the Operator Controller closes and then opens the mechanism
    boolean hatchInOut = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_Y);

    if(hatchInOut){
      Robot.hatchmechanism.closeHatch();
    }
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
