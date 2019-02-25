/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
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

  @Override
  protected void initialize() {
    Robot.carriageinfeed.carriageSetTalonNeutralMode(NeutralMode.Brake);
  }

  @Override
  protected void execute() {
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_R3);
    feedIn = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_TRIGGER);
    feedOut = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_TRIGGER);

    carriageUpDown = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_Y);

    //when R3 is pressed
    if(overrideButton){
      //allows normal movement between maximum and minimum angles
      if(Robot.carriageinfeed.getCarriageAngle() >= 88 && Robot.carriageinfeed.getCarriageAngle() <= -43){
        Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
      }
      //allows only downward movement if at highest angle
      else if(Robot.carriageinfeed.getCarriageAngle() >= 88){
        if (carriageUpDown < 0){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
      }
      //allows only upward movement if at lowest angle
      else if(Robot.carriageinfeed.getCarriageAngle() <= -43){
        if (carriageUpDown > 0){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
      }
      //allows normal control if no previous conditions are met
      else{
        Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
      }  
    }
    //When right trigger is pressed passed threshold, feed in cargo  
    if(feedIn > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedIn();
    }
    //when left trigger is pressed passed threshold, feed cargo out
    else if(feedOut > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedOut();
    }
    //if nothing is pressed, do nothing
    else{
      Robot.carriageinfeed.feedStop();
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.carriageinfeed.feedStop();
    Robot.carriageinfeed.carriageOverrideMove(0);
  }

  @Override
  protected void interrupted() {
    Robot.carriageinfeed.feedStop();
    Robot.carriageinfeed.carriageOverrideMove(0);
  }
}
