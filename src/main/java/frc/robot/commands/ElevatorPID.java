/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ElevatorPID extends Command {

  //Controls on the XBox
  double dPadInput;
  boolean left, right, theAButton;
 
  //PID Setpoints
  double rocketHighBall, rocketHighHatch;
  double rocketMidBall, rocketMidHatch;
  double rocketLowBall, rocketLowHatch;
  double cargoShipBall;
  double cargoBallInfeed;
  double ballInfeedFloor; 
  double endGame;

  //Limit Switches to detect if the elevator has reached the upper or lower limit
  boolean topLimit;
  boolean bottomLimit;

  public ElevatorPID() {
    requires(Robot.elevator);

      rocketHighBall = 0; //TODO: change these to correct setpoints
      rocketHighHatch = 0;
      rocketMidBall = 0;
      rocketMidHatch = 0;
      rocketLowBall = 0;
      rocketLowHatch = 0;
      cargoShipBall = 0;
      cargoBallInfeed = 0;
      ballInfeedFloor = 0;

  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {

    dPadInput = Robot.oi.xboxDPad(Robot.oi.xboxOperator);
      
    left = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_LEFT_BUMPER);
    right = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_RIGHT_BUMPER);
    theAButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_A);

    topLimit = Robot.elevator.getLimitT();
    bottomLimit = Robot.elevator.getLimitB();

    if((topLimit != true) && left && dPadInput == 0){ 
      Robot.elevator.setPoint(rocketHighBall);
    }
    else if(right && dPadInput == 0){ 
      Robot.elevator.setPoint(rocketHighHatch);
    }
    else if(left && dPadInput == 270){ 
      Robot.elevator.setPoint(rocketMidBall);
    }
    else if(right && dPadInput == 270){
      Robot.elevator.setPoint(rocketMidHatch);
    }
    else if(left && dPadInput == 90){ 
      Robot.elevator.setPoint(rocketLowBall);
    }
    else if(right && dPadInput == 90){ 
      Robot.elevator.setPoint(rocketLowHatch);
    }
    else if(left && right && dPadInput == 135){ 
      Robot.elevator.setPoint(0);
    }
    else if(right && dPadInput == 180){ 
      Robot.elevator.setPoint(cargoShipBall);
    }
    else if(left && dPadInput == 180){
      Robot.elevator.setPoint(cargoBallInfeed);
    }
    else if((bottomLimit != true) && theAButton){
      Robot.elevator.setPoint(ballInfeedFloor);
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
