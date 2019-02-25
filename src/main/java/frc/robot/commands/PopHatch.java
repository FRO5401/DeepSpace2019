package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
//this entire command is used to open and close the mechanism that picks up and releases the hatch. 
public class PopHatch extends Command {
  
  public PopHatch() {
    requires(Robot.hatchmechanism); //calls to the subsystem for hatchmechanism so that you can actually run this command
  }

  @Override
  protected void initialize() { //opens the hatchRelease function when its initialized. 
    Robot.hatchmechanism.openHatch();
  }

  @Override
  //if the y button is pressed, closehatch(); and if the y button is pressed again, openhatch();
  protected void execute() {
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
