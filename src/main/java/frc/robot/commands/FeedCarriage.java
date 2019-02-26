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

  //For overriding PID commands
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

    //Used to measure how far up the carriage infeed is moving
    carriageUpDown = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_Y);

    //If the override button is pressed and...
    if(overrideButton){
      //the robot carriage angle is between the reset angle and the ground angle
      if(Robot.carriageinfeed.getCarriageAngle() >= 88 && Robot.carriageinfeed.getCarriageAngle() <= -43){
        //Override the movement of the carriage
        Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
      }
      //the robot carriage angle is no less than the reset angle,
      else if(Robot.carriageinfeed.getCarriageAngle() >= 88){
        //and the angle is decreasing,
        if (carriageUpDown < 0){
          //Override the movement of the carriage
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
      }
      //the robot carriage angle is no more than the ground angle,
      else if(Robot.carriageinfeed.getCarriageAngle() <= -43){
        //and the angle is increasing
        if (carriageUpDown > 0){
          //Override the movement of the carriage
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
      }
      else{ //Can be deleted, this cannot be met
        Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
      }  
    }
    //Code for feeding in the carriages
    if(feedIn > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedIn();
    }
    //Code for ejecting carriages
    else if(feedOut > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedOut();
    }
    else{
      Robot.carriageinfeed.feedStop();
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  //Stops the carriage infeed once end() or interrupted() is called
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
