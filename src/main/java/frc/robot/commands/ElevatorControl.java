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
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class ElevatorControl extends Command {

    //DPad & Joysticks
  double dPadInput, rightJoystickOperator;
    
    //Buttons
  boolean left, right, theAButton, theXButton, theBButton, overrideButton, elevatorShiftLow, elevatorShiftHigh;
  
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

  public ElevatorControl() {
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
    Robot.elevator.setElevatorNeutralMode(NeutralMode.Brake);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      //Read DPad
    dPadInput = Robot.oi.xboxDPad(Robot.oi.xboxOperator);
      
      //Read Joysticks
    rightJoystickOperator = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_Y);

      //Read Buttons
    left = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_LEFT_BUMPER);
    right = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_RIGHT_BUMPER);
    theAButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_A);
    theXButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_X);
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_L3);
    elevatorShiftLow = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_BACK);
    elevatorShiftHigh = Robot.oi.xboxButton(Robot.oi.xboxDriver, RobotMap.XBOX_BUTTON_START);
    
      //Read Limit Switches
    topLimit = Robot.elevator.getLimitT();
    bottomLimit = Robot.elevator.getLimitB();

    /*** INPUT LOGIC ***/

      //If user shifts HIGH
    if(elevatorShiftHigh){
      Robot.elevator.elevatorGearShift(true);
    }
      //If user shifts LOW
    else if(elevatorShiftLow){
      Robot.elevator.elevatorGearShift(false);
    }

      //Button to drop the elevator FLAT.
    if(theXButton){
      Robot.elevator.setPoint(0);
      Robot.elevator.collapseElevator();
    }

      //Button to stand the elevator UP
    if(theBButton){
      Robot.elevator.setPoint(0);
      Robot.elevator.riseElevator();
    }

      //override control
    if(overrideButton){
      if((Robot.elevator.getLimitB() == false && Robot.elevator.getLimitT() == false)){
        Robot.elevator.overrideElevator(rightJoystickOperator); //Normal override Control
      }
      else if(Robot.elevator.getLimitB() == true){
        Robot.elevator.overrideElevator(Math.abs(rightJoystickOperator)); //Always go up
      }
      else if(Robot.elevator.getLimitT() == true){
        Robot.elevator.overrideElevator(Math.abs(rightJoystickOperator) * -1); //Always go down
      }
    }
    else { //PID Control
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
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevator.setPoint(0);
    Robot.elevator.collapseElevator();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.elevator.setPoint(0);
    Robot.elevator.collapseElevator();
  }
}
