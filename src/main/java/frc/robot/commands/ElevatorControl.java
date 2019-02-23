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
  
  int controlSelected;

  public ElevatorControl(int buttonSelected) {
    requires(Robot.elevator);

    controlSelected = buttonSelected;
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //TODO: Make RobotMap constants for the different values. 
      //If user shifts HIGH
    if(controlSelected == 1){
      Robot.elevator.elevatorGearShift(true);
    }
      //If user shifts LOW
    else if(controlSelected == 2){
      Robot.elevator.elevatorGearShift(false);
    }

      //Button to drop the elevator FLAT.
    if(controlSelected == 3){
      Robot.elevator.setPoint(0);
      Robot.elevator.collapseElevator();
    }

      //Button to stand the elevator UP
    if(controlSelected == 4){
      Robot.elevator.setPoint(0);
      Robot.elevator.riseElevator();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
