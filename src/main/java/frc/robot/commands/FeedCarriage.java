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
  boolean overrideButton; //that one overrides the button by toggling it on and off
  double feedIn;
  double feedOut;

  double carriageUpDown;
  double carriageReset;
  double carriageMiddle;
  double carriageGround;
  
  public FeedCarriage() {
    requires(Robot.carriageinfeed); //uses the subsystem carriageinfeed to run the command.
  }

  @Override
  protected void initialize() {
    Robot.carriageinfeed.carriageSetTalonNeutralMode(NeutralMode.Brake); //sets the value of the carriage infeed to neutralmode.brake which just stops the carriage if its running.
  }

  @Override
  protected void execute() {
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_R3); //R3 overrides the the carriage
    feedIn = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_TRIGGER); //right trigger starts the cargo infeed
    feedOut = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_TRIGGER); //left trigger expels the cargo

    carriageUpDown = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_RIGHT_Y); //controls the vertical movement of the carriage

    //activates when the overrideButton is pressed
    if(overrideButton){
      //if the carriage angle is less than/equal to 88 and greater than/equal to -43, then set the overrideMove to carriageUpDown's value
      if(Robot.carriageinfeed.getCarriageAngle() >= 88 && Robot.carriageinfeed.getCarriageAngle() <= -43){
        Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
      }
      //if the carriage angle is less greater than 88, then do the next if
      else if(Robot.carriageinfeed.getCarriageAngle() >= 88){
        //if carriageUpDown is less than 0, then set the overrideMove to carriageUpDown
        if (carriageUpDown < 0){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
      }
      //if the carriage angle is less than -43, then do the next if
      else if(Robot.carriageinfeed.getCarriageAngle() <= -43){
        //if carriageUpDown is greater than 0, then set the overrideMove to carriageUpDown
        if (carriageUpDown > 0){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
        }
      }
      //else, just set the overrideMove to carriageUpDown
      else{
        Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
      }  
    }
      
    //if feedIn crosses the axis threshold, then it can feed in cargo into the carriage
    if(feedIn > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedIn();
    }
    //if feedOut crosses its axis threshold, then it can expel the cargo out from the carriage
    else if(feedOut > RobotMap.AXIS_THRESHOLD){
      Robot.carriageinfeed.feedOut();
    }
    //if none of the above is completed, then stop the feeder because it is not needed.
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
