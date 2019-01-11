/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

import com.kauailabs.*;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;

/**
 * Add your docs here.
 */
public class DriveBase extends Subsystem {
  //Motors
  private VictorSP leftDrive1;
  private VictorSP rightDrive1;
  private VictorSP leftDrive2;
  private VictorSP rightDrive2;

    //Solenoids
  private DoubleSolenoid gearShifter;

    //Sensors
  private Encoder leftEncoder;
  private Encoder rightEncoder;
  private AHRS navxGyro;

  public DriveBase(){
    leftDrive1 = new VictorSP(RobotMap.DRIVE_MOTOR_LEFT_1);
    rightDrive1 = new VictorSP(RobotMap.DRIVE_MOTOR_RIGHT_1);
    leftDrive2 = new VictorSP(RobotMap.DRIVE_MOTOR_LEFT_2);
    rightDrive2 = new VictorSP(RobotMap.DRIVE_MOTOR_RIGHT_2);

    gearShifter = new DoubleSolenoid(RobotMap.GEAR_SHIFTER_IN, RobotMap.GEAR_SHIFTER_OUT);

    navxGyro = new AHRS(I2C.Port.kMXP);
    leftEncoder = new Encoder(RobotMap.DRIVE_ENC_LEFT_A, RobotMap.DRIVE_ENC_LEFT_B, true, EncodingType.k4X);
    rightEncoder = new Encoder(RobotMap.DRIVE_ENC_RIGHT_A, RobotMap.DRIVE_ENC_RIGHT_B, false, EncodingType.k4X);

    SmartDashboard.putNumber("Left Enc Raw", leftEncoder.get());
    SmartDashboard.putNumber("Right Enc Raw", rightEncoder.get());
    SmartDashboard.putNumber("Left Enc Adj", leftEncoder.getDistance());
    SmartDashboard.putNumber("Right Enc Adj", rightEncoder.getDistance());
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
