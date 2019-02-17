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

    //Sets victors to desired speed giving from XboxMove.
  public void drive(double leftDriveDesired, double rightDriveDesired){
      //Left inverted in accordance to physical wiring.
    leftDrive1.set(leftDriveDesired);
    leftDrive2.set(leftDriveDesired);
    rightDrive1.set(-1 * rightDriveDesired);
    rightDrive2.set(-1 * rightDriveDesired);

    reportEncoders();

    //Report gyro angle
    SmartDashboard.putNumber("NavX Angle", navxGyro.getAngle());
  }

    //Sets Victors to 0.
  public void stopMotors(){
    leftDrive1.set(0);
    leftDrive2.set(0);
    rightDrive1.set(0);
    rightDrive2.set(0);
  }

    //Set shifter to LOW. 
  public void shiftHighToLow(){
    gearShifter.set(true);
    setDPPLowGear();
  }

    //Set shifter to HIGH.
  public void shiftLowToHigh(){
    gearShifter.set(false);
    setDPPHighGear();
  }

    //Sets DPP for LOW gear. 
  public void setDPPLowGear(){
    leftEncoder.setDistancePerPulse(RobotMap.LOW_GEAR_LEFT_DPP);
    rightEncoder.setDistancePerPulse(RobotMap.LOW_GEAR_RIGHT_DPP);
  }

    //Sets DPP for HIGH gear.
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
  
  //Get the current shifter value. 
  public boolean getCurrentGear(){
    return gearShifter.get();
  }

    //Gets Gyro Angle for Auto. 
  public double getGyroAngle(){
    double currentAngle = navxGyro.getAngle();
    SmartDashboard.putBoolean("NavX Connected", navxGyro.isConnected());
    SmartDashboard.putNumber("NavX Angle", currentAngle);
    return currentAngle;
  }

    //Gets Gyro Pitch
  public double getGyroPitch(){
    double pitch = navxGyro.getPitch();
    return pitch;
  }  

    //Posts the encoder values to SmartDashboard.
  public void reportEncoders(){
    SmartDashboard.putNumber("Left Enc Raw", leftEncoder.get());
    SmartDashboard.putNumber("Right Enc Raw", rightEncoder.get());
    SmartDashboard.putNumber("Left Enc Adj", leftEncoder.getDistance());
    SmartDashboard.putNumber("Right Enc Adj", rightEncoder.getDistance());
  }

    //Posts Gyro Values to SmartDashboard.
  public void reportGyro(){
    //SmartDashboard.putBoolean("NavX Connection", navxGyro.isConnected());
    //SmartDashboard.putNumber("NaxX Angle", navxGyro.getAngle());
    SmartDashboard.putNumber("NavX Pitch", navxGyro.getPitch());
    SmartDashboard.putNumber("NavX Yaw", navxGyro.getYaw());
  }

    //Resets the Encoders. 
  public void resetEncoders(){
    leftEncoder.reset();
    rightEncoder.reset();
  }

    //Resets the Gyro. 
  public void resetGyro(){
    navxGyro.reset();
  }

    //Gets the victor speeds
  public void getVictorSpeed(){
    SmartDashboard.putNumber("Left VSP1 Speed", leftDrive1.getSpeed());
    SmartDashboard.putNumber("Left VSP2 Speed", leftDrive2.getSpeed());
    SmartDashboard.putNumber("Right VSP1", rightDrive1.getSpeed());
    SmartDashboard.putNumber("Right VSP2", rightDrive2.getSpeed());
  }
}
