/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  TalonSRX elevatorSRX = new TalonSRX(RobotMap.ELEVATOR_TALON_SRX);
  Solenoid elevatorSolenoid = new Solenoid(RobotMap.ELEVATOR_SOLENOID);

  public void overrideElevator(double joystickSpeed){
      elevatorSRX.set(ControlMode.PercentOutput, joystickSpeed);
  }

  public void elevatorGearShift(int button){
    if(button == RobotMap.XBOX_BUTTON_BACK_OPERATOR){
      elevatorSolenoid.set(false);
    }else if(button == RobotMap.XBOX_BUTTON_START_OPERATOR){
      elevatorSolenoid.set(true);
    }else{
      System.out.print("Some error was thrown, start/back wasn't pressed");
    }
  }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
