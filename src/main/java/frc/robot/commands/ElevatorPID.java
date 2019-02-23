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

    //DPad & Joysticks
  double dPadInput;
    
    //Buttons
  boolean left, right, theAButton;
 
    //Constants
  double rocketHighBall, rocketHighHatch;
  double rocketMidBall, rocketMidHatch;
  double rocketLowBall, rocketLowHatch;
  double cargoShipBall;
  double cargoBallInfeed;
  double ballInfeedFloor; 
  double endGame;

    //Limit switches
  boolean topLimit;
  boolean bottomLimit;

  public ElevatorPID() {
    requires(Robot.elevator);

      //Setpoints
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

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    dPadInput = Robot.oi.xboxDPad(Robot.oi.xboxOperator);
      
      //Read Buttons
    left = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_LEFT_BUMPER);
    right = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_RIGHT_BUMPER);
    theAButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_A);

      //Read Limit Switches
    topLimit = Robot.elevator.getLimitT();
    bottomLimit = Robot.elevator.getLimitB();

    if((topLimit != true) && left && dPadInput == 0){ //UP //Rocket High Ball
      Robot.elevator.setPoint(rocketHighBall);
    }
    else if(right && dPadInput == 0){ //UP //Rocket High Hatch
      Robot.elevator.setPoint(rocketHighHatch);
    }
    else if(left && dPadInput == 270){ //LEFT //Rocket Mid Ball
      Robot.elevator.setPoint(rocketMidBall);
    }
    else if(right && dPadInput == 270){ //LEFT //Rocket Mid Hatch
      Robot.elevator.setPoint(rocketMidHatch);
    }
    else if(left && dPadInput == 90){ //RIGHT //Rocket Low Ball
      Robot.elevator.setPoint(rocketLowBall);
    }
    else if(right && dPadInput == 90){ //RIGHT //Hatch Feed Station
      Robot.elevator.setPoint(rocketLowHatch);
    }
    else if(left && right && dPadInput == 135){ //RIGHT and DOWN //GAME RESET
      Robot.elevator.setPoint(0);
    }
    else if(right && dPadInput == 180){ //DOWN //Cargo Ship Ball
      Robot.elevator.setPoint(cargoShipBall);
    }
    else if(left && dPadInput == 180){ //DOWN //Ball Infeed Station
      Robot.elevator.setPoint(cargoBallInfeed);
    }
    else if((bottomLimit != true) && theAButton){ //Ball Infeed Floor
      Robot.elevator.setPoint(ballInfeedFloor);
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
