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
    requires(Robot.carriageinfeed);//Links the carriageInfeed subsystem with this command
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
    //waits for any values from OI, once it receives a value, sets the variables equal to that value

    if(overrideButton){//if overrideButton is true, go to the next condition
      if(Robot.carriageinfeed.getCarriageAngle() >= 88 && /* should this operator be || (or)?*/ Robot.carriageinfeed.getCarriageAngle() <= -43){ //should the operators >= and <= be flipped.
        //if carriage anlge is meets the conditions, sets the output of the talon equal to the axis value
        Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
      }
      else if(Robot.carriageinfeed.getCarriageAngle() >= 88){
        if (carriageUpDown < 0){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
          //allow the talon controlling the carriage to receive axis value if it's greater than 88 degrees and carriageUpDown is less than 0
        }
      }
      else if(Robot.carriageinfeed.getCarriageAngle() <= -43){
        if (carriageUpDown > 0){
          Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);
          //allow the talon controlling the carriage to receive axis value if it's less than -43 degrees and carriageUpDown is greater than 0
        }
      }
      else{
        Robot.carriageinfeed.carriageOverrideMove(carriageUpDown);//allow the talon controlling the carriage to receive axis values if no conditions are met
      }  
    }
      
    if(feedIn > RobotMap.AXIS_THRESHOLD){
      //if the value of feedIn is greater than the threshold, run the infeed
      Robot.carriageinfeed.feedIn();
    }
    else if(feedOut > RobotMap.AXIS_THRESHOLD){
      //if the value of feedOut is greater than the threshold, run feed out
      Robot.carriageinfeed.feedOut();
    }
    else{
      Robot.carriageinfeed.feedStop();
      //stops the feed motors
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
