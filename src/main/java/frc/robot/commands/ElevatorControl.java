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

public class ElevatorControl extends Command {

  double dPadInput, rightJoystickOperator;
  boolean left, right, theAButton, overrideButton;
  double rocketHighBall, rocketHighHatch;
  double rocketMidBall, rocketMidHatch;
  double rocketLowBall, rocketLowHatch;
  double cargoShipBall;
  double cargoBallInfeed;
  double ballInfeedFloor; 
  double endGame;

  public ElevatorControl() {
    requires(Robot.elevator);

    dPadInput = Robot.oi.xboxDPad(Robot.oi.xboxOperator);
    rightJoystickOperator = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_Y);
    left = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_LEFT_BUMPER_OPERATOR);
    right = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_RIGHT_BUMPER_OPERATOR);
    theAButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_A_OPERATOR);
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_L3_OPERATOR);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      if(overrideButton){
        if((Robot.elevator.getLimitB() == false && Robot.elevator.getLimitT() == false)){
          Robot.elevator.overrideElevator(rightJoystickOperator); //Normal override Control
        }else if(Robot.elevator.getLimitB() == true){
          Robot.elevator.overrideElevator(Math.abs(rightJoystickOperator)); //Always go up
        }else if(Robot.elevator.getLimitT() == true){
          Robot.elevator.overrideElevator(Math.abs(rightJoystickOperator) * -1); //Always go down
        }
      } else { //PID Control
        if(left && dPadInput == 0){ //UP //Rocket High Ball
          Robot.elevator.setPoint(rocketHighBall);
        }else if(right && dPadInput == 0){ //UP //Rocket High Hatch
          Robot.elevator.setPoint(rocketHighHatch);
        }else if(left && dPadInput == 270){ //LEFT //Rocket Mid Ball
          Robot.elevator.setPoint(rocketMidBall);
        }else if(right && dPadInput == 270){ //LEFT //Rocket Mid Hatch
          Robot.elevator.setPoint(rocketMidHatch);
        }else if(left && dPadInput == 90){ //RIGHT //Rocket Low Ball
          Robot.elevator.setPoint(rocketLowBall);
        }else if(right && dPadInput == 90){ //RIGHT //Hatch Feed Station
          Robot.elevator.setPoint(rocketLowHatch);
        }else if(left && right && dPadInput == 135){ //RIGHT and DOWN //GAME RESET
          Robot.elevator.setPoint(0);
        }else if(right && dPadInput == 180){ //DOWN //Cargo Ship Ball
          Robot.elevator.setPoint(cargoShipBall);
        }else if(left && dPadInput == 180){ //DOWN //Ball Infeed Station
          Robot.elevator.setPoint(cargoBallInfeed);
        }else if(){ //END GAME
          Robot.elevator.setPoint(0);
        }else if(theAButton){ //Ball Infeed Floor
          Robot.elevator.setPoint(ballInfeedFloor);
        }
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
