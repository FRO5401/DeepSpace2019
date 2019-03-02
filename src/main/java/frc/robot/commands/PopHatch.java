package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class PopHatch extends Command {
  
  public PopHatch() {
    requires(Robot.hatchmechanism);//Links the subsystem to the command
  }

  @Override
  protected void initialize() {
    Robot.hatchmechanism.openHatch();//pushes out the actuator on initialization of the robot
  }

  @Override
  protected void execute() {
    boolean hatchInOut = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_Y);
    //allows the hatchInOut variable to receive values from OI

    if(hatchInOut){
      Robot.hatchmechanism.closeHatch();
      //if hatchInOut is true, retract the acuator
    }
    else{
      Robot.hatchmechanism.openHatch();//if hatchInOut is false, push the actuator out
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
