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

public class XboxMove extends Command {
  /*** Variables ***/
    //Input Axes
  double turn;
  double throttle;
  double reverse;

    //Input Buttons
  boolean rotate; 
  boolean brake;
  boolean precision;
  boolean gearShiftHigh;
  boolean gearShiftLow;
  boolean resetSensors;
  boolean speedConstant1;
  boolean speedConstant2;
  boolean speedConstant3;

    //Instance Vars
  double left;
  double right; 
  double sensitivity;

  public XboxMove() {
    requires(Robot.drivebase);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.drivebase.shiftHighToLow();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    /*** Read Inputs ***/
      //Axes
    turn = Robot.oi.xboxAxis(Robot.oi.xboxDriver, RobotMap.XBOX_AXIS_LEFT_X);
    throttle = Robot.oi.xboxAxis(Robot.oi.xboxDriver, RobotMap.XBOX_AXIS_RIGHT_TRIGGER);
    reverse = Robot.oi.xboxAxis(Robot.oi.xboxDriver, RobotMap.XBOX_AXIS_LEFT_TRIGGER);
    
      //Buttons
    rotate = Robot.oi.xboxButton(Robot.oi.xboxDriver, RobotMap.XBOX_BUTTON_L3_DRIVER);
    brake = Robot.oi.xboxButton(Robot.oi.xboxDriver, RobotMap.XBOX_BUTTON_LEFT_BUMPER_DRIVER);
    precision = Robot.oi.xboxButton(Robot.oi.xboxDriver, RobotMap.XBOX_BUTTON_RIGHT_BUMPER_DRIVER);
    gearShiftHigh = Robot.oi.xboxButton(Robot.oi.xboxDriver, RobotMap.XBOX_BUTTON_START_DRIVER);
    gearShiftLow = Robot.oi.xboxButton(Robot.oi.xboxDriver, RobotMap.XBOX_BUTTON_BACK_DRIVER);
    resetSensors = Robot.oi.xboxButton(Robot.oi.xboxDriver, RobotMap.XBOX_BUTTON_Y_DRIVER);
    speedConstant1 = Robot.oi.xboxButton(Robot.oi.xboxDriver, RobotMap.XBOX_BUTTON_X_DRIVER);
    speedConstant2 = Robot.oi.xboxButton(Robot.oi.xboxDriver, RobotMap.XBOX_BUTTON_A_DRIVER);
    speedConstant3 = Robot.oi.xboxButton(Robot.oi.xboxDriver, RobotMap.XBOX_BUTTON_B_DRIVER);

    //Resets Sensors
    if(resetSensors){
      Robot.drivebase.resetEncoders();
      Robot.drivebase.resetGyro();
    }    
    
    /*** Gear Shifting ***/
      //Press for High Gear
    if(gearShiftHigh){
      Robot.drivebase.shiftLowToHigh();
    }
      //Press for Low Gear
    else if(gearShiftLow){
      Robot.drivebase.shiftHighToLow();
    }

    /*** Precision ***/
      //Hold for Precision Speed
    if(precision){
      sensitivity = RobotMap.DRIVE_SENSITIVITY_PRECISION;
    }
      //Release for Regular Speed
    else{
      sensitivity = RobotMap.DRIVE_SENSITIVITY_DEFAULT;
    }

    /*** Driving ***/
      //Braking
    if(brake){
      Robot.drivebase.stopMotors();
      left = 0;
      right = 0;
    }
      //Drives at constant speed
    else if(speedConstant1){
      left = (1.0 / 3);
      right = (1.0 / 3);
    }
    else if(speedConstant2){
      left = (2.0 / 3);
      right = (2.0 / 3);
    }
    else if(speedConstant3){
      left = (1.0);
      right = (1.0);
    }
      //Not Braking
    else{
        //Pirouetting (Turn in place). 
      if(rotate){
          //If the joystick is pushed passed the threshold. 
        if(Math.abs(turn) > RobotMap.AXIS_THRESHOLD){
            //Sets it to spin the desired direction.
          left = RobotMap.SPIN_SENSITIVITY * turn;
          right = RobotMap.SPIN_SENSITIVITY * (turn * -1);
        }
      }
        //Not pirouetting (Not turning in place).
      else{
          //Turning right
        if(turn > RobotMap.AXIS_THRESHOLD){
            //Makes left slow down by a factor of how far the axis is pushed. 
          left = (throttle - reverse) * sensitivity;
          right = (throttle - reverse) * sensitivity * (1 - turn);
        }
          //Turning left
        else if(turn < (-1 * RobotMap.AXIS_THRESHOLD)){
            //Makes right speed up by a factor of how far the axis is pushed. 
          left = (throttle - reverse) * sensitivity * (1 + turn);
          right = (throttle - reverse) * sensitivity;
        }
          //Driving straight 
        else{
            //No joystick manipulation. 
          left = (throttle - reverse) * sensitivity;
          right = (throttle - reverse) * sensitivity;
        }
      }
    }
      //After speed manipulation, send to drivebase. 
    Robot.drivebase.drive(left, right);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.drivebase.stopMotors();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Robot.drivebase.stopMotors();
  }
}
