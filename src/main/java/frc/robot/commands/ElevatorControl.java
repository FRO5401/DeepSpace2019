/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ElevatorControl extends Command {
  
  int controlSelected;

  public ElevatorControl(int buttonSelected) {
    requires(Robot.elevator);//retrieves the subsystem that this command calls from

    controlSelected = buttonSelected;//sets the controlSelected equal to the value that is retrieved from OI
  }

  @Override
  protected void initialize() {
    if(controlSelected == 1){
      Robot.elevator.elevatorGearShift(true);//if the value of controlSelected is 1, shift gear 
    }
    else if(controlSelected == 2){
      Robot.elevator.elevatorGearShift(false);//if the value of controlSelected is 2, don't shift the gear
    }

    if(controlSelected == 3){
      Robot.elevator.setPoint(0);
      Robot.elevator.collapseElevator();//if the value is 3, collapses the elevator
    }

    if(controlSelected == 4){
      Robot.elevator.setPoint(0);
      Robot.elevator.riseElevator();// if the value is 4, make the elevator rise
    }
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
