package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class PopHatch extends Command {
  
  //A command to pop a hatch from the robot
  public PopHatch() {
    requires(Robot.hatchmechanism);
  }

  @Override
  protected void initialize() {
    Robot.hatchmechanism.openHatch();
  }

  @Override
  protected void execute() {
    //Y-button is attached to the hatch mechanism
    boolean hatchInOut = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_Y);

    //If the button is pressed,
    if(hatchInOut){
      //close the hatch release
      Robot.hatchmechanism.closeHatch();
    }
    else{
      //open the hatch release
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
