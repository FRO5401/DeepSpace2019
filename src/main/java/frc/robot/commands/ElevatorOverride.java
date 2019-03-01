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

/*
 * Command controls the Override moving, it is called from OI
 */
public class ElevatorOverride extends Command {

    //DPad & Joysticks
  double dPadInput, leftJoystickOperator;
    
    //Buttons
  boolean left, right, theAButton, theXButton, theBButton, overrideButton, elevatorShiftLow, elevatorShiftHigh;

    //Limit switches
  boolean topLimit;
  boolean bottomLimit;

    //Override Finished
  boolean overrideFinished;

  public ElevatorOverride() {
    requires(Robot.elevator);
    overrideFinished = false;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevator.setElevatorNeutralMode(NeutralMode.Brake);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  
      //Read Joysticks
    leftJoystickOperator = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_Y);

      //Read Buttons
    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_L3);
    
      //Read Limit Switches
    topLimit = Robot.elevator.getLimitB();
    bottomLimit = Robot.elevator.getLimitT();

    /*** INPUT LOGIC ***/

      //override control
    if(overrideButton){
        //If BOTTOM and TOP are NOT tripped. 
      if((bottomLimit == false) && (topLimit == false)){
        if((leftJoystickOperator > RobotMap.AXIS_THRESHOLD) || (leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD))){
          Robot.elevator.overrideElevator(leftJoystickOperator); //Normal override Control
        }
          //If input is out of threshold.
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
      //TODO: Elevator Logic is backwards, fixed in a wacky way... correct POST Hatboro. 
        //if BOTTOM is tripped but TOP is not.
      else if((bottomLimit == true) && (topLimit == false)){
        if(leftJoystickOperator > RobotMap.AXIS_THRESHOLD){
          Robot.elevator.overrideElevator(leftJoystickOperator);
        }
          //If input is out of threshold.
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
        //if TOP is tripped and BOTTOM is false.
      else if((topLimit == true) && (bottomLimit == false)){
        if(leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD)){
          Robot.elevator.overrideElevator(leftJoystickOperator);
        }
          //If input is out of threshold.
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
        //If there is an unexpected Limit Switch combo.
      else{
        Robot.elevator.overrideElevator(0);
      }
    }
    else if(!overrideButton) {
      overrideFinished = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return overrideFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevator.overrideStopped();
    //new ElevatorPID();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.elevator.overrideStopped();
  }
}
