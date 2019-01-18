package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

//variables
  

public class CargoRollers extends Command {

  //buttons
  boolean in;
  boolean out;

  //motor speed
  double speed = RobotMap.SPT_MOTOR_SPEED;

  public void SpinSPT() {
    requires(Robot.spt);
  }

  @Override
  protected void initialize() {
    //set arm to starting position
  }

  @Override //called repeatedly
  protected void execute() {
    //Read Buttons
    in = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_A_OPERATOR);
    out = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_B_OPERATOR);

    //*** SPT Rollers ***/
    if (in){
      double left = speed;
      double right = speed * -1;

      Robot.spt.infeedMotors(left, right);
    }
    else if (out){
      double left = speed * -1;
      double right = speed;

      Robot.spt.infeedMotors(left, right);
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
