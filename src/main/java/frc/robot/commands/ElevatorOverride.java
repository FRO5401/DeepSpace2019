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

public class ElevatorOverride extends Command {

  double dPadInput, leftJoystickOperator;
    
  boolean left, right, theAButton, theXButton, theBButton, overrideButton, elevatorShiftLow, elevatorShiftHigh;

  boolean topLimit;
  boolean bottomLimit;

  boolean overrideFinished;

  public ElevatorOverride() {
    requires(Robot.elevator);
    overrideFinished = false;
  }

  @Override
  protected void initialize() {
    Robot.elevator.setElevatorNeutralMode(NeutralMode.Brake);
  }

  @Override
  protected void execute() {
  
    leftJoystickOperator = Robot.oi.xboxAxis(Robot.oi.xboxOperator, RobotMap.XBOX_AXIS_LEFT_Y);

    overrideButton = Robot.oi.xboxButton(Robot.oi.xboxOperator, RobotMap.XBOX_BUTTON_L3);
    
    topLimit = Robot.elevator.getLimitB();
    bottomLimit = Robot.elevator.getLimitT();


    if(overrideButton){
      //when both limit switches are not pressed
      if((bottomLimit == false) && (topLimit == false)){
        //allows movement in both directions
        if((leftJoystickOperator > RobotMap.AXIS_THRESHOLD) || (leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD))){
          Robot.elevator.overrideElevator(leftJoystickOperator); 
        }
        //stops movement when input is between threshold
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
      //when bottom switch is pressed
      else if((bottomLimit == true) && (topLimit == false)){
        //only allows movement upwards
        if(leftJoystickOperator > RobotMap.AXIS_THRESHOLD){
          Robot.elevator.overrideElevator(leftJoystickOperator);
        }
        //stops movement when input is below threshold
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
      //when top switch is pressed
      else if((topLimit == true) && (bottomLimit == false)){
        //only allows movement downwards
        if(leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD)){
          Robot.elevator.overrideElevator(leftJoystickOperator);
        }
        //stops movement when input is above threshold
        else{
          Robot.elevator.overrideElevator(0);
        }
      }
      //stops movement when there is not input
      else{
        Robot.elevator.overrideElevator(0);
      }
    }
    //sets overrideFinished to true to know it's done
    else if(!overrideButton) {
      overrideFinished = true;
    }
  }

  @Override
  protected boolean isFinished() {
    return overrideFinished;
  }

  @Override
  protected void end() {
    Robot.elevator.overrideStopped();
  }

  @Override
  protected void interrupted() {
    Robot.elevator.overrideStopped();
  }
}
