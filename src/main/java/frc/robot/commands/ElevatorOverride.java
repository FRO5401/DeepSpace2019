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
  double leftJoystickOperator;
    
    //Buttons
  boolean overrideButton;

    //Limit switches
  boolean topLimit;
  boolean bottomLimit;

  // Speed Adjustment
  double speedAdj;

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

    if(bottomLimit){
      speedAdj = 0;
    }

    /*** INPUT LOGIC ***/

      //override control
    if(overrideButton){
      if((Robot.elevator.getElevatorDeployedBottom()) && (Robot.elevator.getElevatorDeployedTop())){
        speedAdj = -.30;
      }
      else{
        speedAdj = 0;
      }
        //If BOTTOM and TOP are NOT tripped. 
      if((bottomLimit == false) && (topLimit == false)){
        if((leftJoystickOperator > RobotMap.AXIS_THRESHOLD) || (leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD))){
          Robot.elevator.overrideElevator(leftJoystickOperator+speedAdj); //Normal override Control
        }
          //If input is out of threshold.
        else{
          Robot.elevator.overrideElevator(speedAdj);
        }
      }
      //TODO: Elevator Logic is backwards, fixed in a wacky way... correct POST Hatboro. 
        //if BOTTOM is tripped but TOP is not.
      else if((bottomLimit == true) && (topLimit == false)){
        if(leftJoystickOperator > RobotMap.AXIS_THRESHOLD){
//          Robot.elevator.overrideElevator(0);//Makes it go down, 0'd to eliminate power down
          Robot.elevator.overrideElevator(leftJoystickOperator+speedAdj);
        }
          //If input is out of threshold.
        else{
          Robot.elevator.overrideElevator(speedAdj);
        }
      }
        //if TOP is tripped and BOTTOM is false.
      else if((topLimit == true) && (bottomLimit == false)){
        if(leftJoystickOperator < (-1 * RobotMap.AXIS_THRESHOLD)){
//          Robot.elevator.overrideElevator(0);
          Robot.elevator.overrideElevator(leftJoystickOperator+speedAdj);//This makes it go UP
        }
          //If input is out of threshold.
        else{
          Robot.elevator.overrideElevator(speedAdj);
        }
      }
    }
    else if(!overrideButton) {
      Robot.elevator.holdPoint();
      overrideFinished = true;
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
