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
import frc.robot.commands.XboxMove;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;

/**
 * Add your docs here.
 */
public class DriveBase extends Subsystem {
  
  /*** Objects ***/
    //Motors
  private VictorSP leftDrive1;
  private VictorSP rightDrive1;
  private VictorSP leftDrive2;
  private VictorSP rightDrive2;

    //Solenoids
  private Solenoid gearShifter;

    //Sensors
  private Encoder leftEncoder;
  private Encoder rightEncoder;
  private AHRS navxGyro;

  public DriveBase(){
    leftDrive1 = new VictorSP(RobotMap.DRIVE_MOTOR_LEFT_1);
    rightDrive1 = new VictorSP(RobotMap.DRIVE_MOTOR_RIGHT_1);
    leftDrive2 = new VictorSP(RobotMap.DRIVE_MOTOR_LEFT_2);
    rightDrive2 = new VictorSP(RobotMap.DRIVE_MOTOR_RIGHT_2);

    gearShifter = new Solenoid(RobotMap.GEAR_SHIFTER);

    navxGyro = new AHRS(I2C.Port.kMXP);
    leftEncoder = new Encoder(RobotMap.DRIVE_ENC_LEFT_A, RobotMap.DRIVE_ENC_LEFT_B, true, EncodingType.k4X);
    rightEncoder = new Encoder(RobotMap.DRIVE_ENC_RIGHT_A, RobotMap.DRIVE_ENC_RIGHT_B, false, EncodingType.k4X);

    //Initial report of the gyro angle. 
    SmartDashboard.putNumber("NavX Angle", navxGyro.getAngle());
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new XboxMove());
  }

  public void drive(double leftDriveDesired, double rightDriveDesired){
    leftDrive1.set(leftDriveDesired);
    leftDrive2.set(leftDriveDesired);
    rightDrive1.set(rightDriveDesired);
    rightDrive2.set(rightDriveDesired);

    //Report gyro angle
    SmartDashboard.putNumber("NavX Angle", navxGyro.getAngle());
  }

  public void driveButton(double speed){
  }

  public void stopMotors(){
    leftDrive1.set(0);
    leftDrive2.set(0);
    rightDrive1.set(0);
    rightDrive2.set(0);
  }

  public void shiftHighToLow(){
    gearShifter.set(true);
    setDPPHighGear();
  }

  public void shiftLowToHigh(){
    gearShifter.set(false);
    setDPPLowGear();
  }

  public boolean getCurrentGear(){
    return gearShifter.get();
  }

  public void setDPPLowGear(){
    leftEncoder.setDistancePerPulse(RobotMap.LOW_GEAR_LEFT_DPP);
    rightEncoder.setDistancePerPulse(RobotMap.LOW_GEAR_RIGHT_DPP);
  }

  public void setDPPHighGear(){
    leftEncoder.setDistancePerPulse(RobotMap.HIGH_GEAR_LEFT_DPP);
    rightEncoder.setDistancePerPulse(RobotMap.HIGH_GEAR_RIGHT_DPP);
  }

    //For autonomous driving
  public double getEncoderDistance(int encoderNumber){
    double leftDistAdj = leftEncoder.getDistance();
    double rightDistAdj = rightEncoder.getDistance();  
    double avgDistance = (leftDistAdj + rightDistAdj) / 2;

    if(encoderNumber == 1){
      return leftDistAdj;
    }
    else if(encoderNumber == 2){
      return rightDistAdj;
    }
    else{
      return avgDistance;
    }
  }

    //Runs continuously while robot is on. 
  public void reportEncoders(){
    SmartDashboard.putNumber("Left Enc Raw", leftEncoder.get());
    SmartDashboard.putNumber("Right Enc Raw", rightEncoder.get());
    SmartDashboard.putNumber("Left Enc Adj", leftEncoder.getDistance());
    SmartDashboard.putNumber("Right Enc Adj", rightEncoder.getDistance());
  }

  public void reportGyro(){
    SmartDashboard.putBoolean("NavX Connection", navxGyro.isConnected());
    SmartDashboard.putNumber("NaxX Angle", navxGyro.getAngle());
    SmartDashboard.putNumber("NavX Pitch", navxGyro.getPitch());
    SmartDashboard.putNumber("NavX Yaw", navxGyro.getYaw());
  }

  public double getGyroAngle(){
    double currentAngle = navxGyro.getAngle();
    SmartDashboard.putBoolean("NavX Connected", navxGyro.isConnected());
    SmartDashboard.putNumber("NavX Angle", currentAngle);
    return currentAngle;
  }

  public void resetEncoders(){
    leftEncoder.reset();
    rightEncoder.reset();
  }

  //
  public void resetGyro(){
    navxGyro.reset();
  }

}
