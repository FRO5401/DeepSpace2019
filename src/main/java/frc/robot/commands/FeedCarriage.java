/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import javax.naming.spi.NamingManager;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class FeedCarriage extends Command {
  boolean overrideButton;
  double feedIn;
  double feedOut;

  double carriageUpDown;
  double carriageReset;
  double carriageMiddle;
  double carriageGround;
  
  public FeedCarriage() {
    requires(Robot.carriageinfeed);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.carriageinfeed.carriageSetTalonNeutralMode(NeutralMode.Brake);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      //Read buttons
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_L3_OPERATOR);
    feedIn = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_TRIGGER);
    feedOut = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_TRIGGER);

      //Read axis
    carriageUpDown = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_Y);

      //carriage Move logic.
    if(overrideButton){
      //Forces elevator to go down/up when too high/low
      if(Robot.carriageinfeed.getCarriageAngle() == 88){
        Robot.carriageinfeed.carriageOverrideMove(Math.abs(carriageUpDown) * -1);

      }
      else if(Robot.carriageinfeed.getCarriageAngle() == -43){
        Robot.carriageinfeed.carriageOverrideMove(Math.abs(carriageUpDown));
        }  
      Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
    }
      
      //Feeder Logic
    if(feedIn > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedIn();
    }
    else if(feedOut > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedOut();
    }
    else{
      Robot.carriageinfeed.feedStop();
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
    Robot.carriageinfeed.feedStop();
    Robot.carriageinfeed.carriageOverrideMove(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.carriageinfeed.feedStop();
    Robot.carriageinfeed.carriageOverrideMove(0);
  }
}
