/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * Add your docs here.
 */
public class FlywheelShooter extends Subsystem {
TalonSRX _talonMaster;
TalonSRX _talonSlave;

private double PIDMotorSpeed = -19900;
private double feedForward;
private double kP, kI, kD;

private double MOTOR_SPEED = PIDMotorSpeed;

private boolean pidEnabled;
private boolean compressorEnabled;

private int THRESH;
private int Izone;

public FlywheelShooter(){
  _talonMaster = new TalonSRX(0);
  _talonSlave =  new TalonSRX(1);

  _talonMaster.set(ControlMode.Velocity,0);
  _talonSlave.set(ControlMode.Follower, _talonMaster.getDeviceID());

  SmartDashboard.putNumber("Motor Speed", MOTOR_SPEED);

  _talonMaster.getSensorCollection().getQuadraturePosition();
  SmartDashboard.putNumber("Position", _talonMaster.getSensorCollection().getQuadraturePosition());
  _talonMaster.getSensorCollection().getQuadratureVelocity();
  SmartDashboard.putNumber("Velocity", _talonMaster.getSensorCollection().getQuadratureVelocity());

  feedForward = 0.33;


  kP = 0.1;
  kI = 0.00005;
  kD = 2;
  Izone = 0;

  pidEnabled = true;
  SmartDashboard.putBoolean("PID Enabled", pidEnabled);

  SmartDashboard.putNumber("Feed Forward", feedForward);
  SmartDashboard.putNumber("kP", kP);
  SmartDashboard.putNumber("kI", kI);
  SmartDashboard.putNumber("kD", kD);

 // _talonMaster.configureSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
 _talonMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
  THRESH = 200;
}

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
    public void startMotors(){
      if(pidEnabled){
        _talonMaster.config_kP(0, kP, 10);
        _talonMaster.config_kI(0, kI, 10);
        _talonMaster.config_kD(0, kD, 10);
        _talonMaster.config_kF(0, feedForward, 10);
        _talonMaster.config_IntegralZone(0, Izone, 10);
        _talonMaster.set(ControlMode.Velocity, 0);
      } else {
        _talonMaster.set(ControlMode.PercentOutput, MOTOR_SPEED);

        compressorEnabled = true;
      }
    }

      public void stopMotors(){
        _talonMaster.set(ControlMode.PercentOutput, 0);
        compressorEnabled = false;
      }

      public void reset(){
        stopMotors();
        SmartDashboard.putBoolean("AutoTargeting", false);
        SmartDashboard.putBoolean("Shooter OnOff", false);
      }

      public double getTargetSpeed(){
        return MOTOR_SPEED;
      }

      public double getVelocity(){
        return _talonMaster.getSensorCollection().getQuadratureVelocity();
      }

      public void switchState(){
        if(compressorEnabled){
          reset();
        } else {
          startMotors();
        }
      }

      public void shootOverrideSwitchState(){
        if(pidEnabled){
          _talonMaster.set(ControlMode.PercentOutput, MOTOR_SPEED);
          pidEnabled = false;
        } else {
          _talonMaster.set(ControlMode.Velocity, MOTOR_SPEED);
          pidEnabled = true;
        }
        SmartDashboard.putBoolean("PID Enabled", pidEnabled);
      }

      public void printReadyToShoot(){
        if (_talonMaster.getSensorCollection().getQuadraturePosition() < MOTOR_SPEED + THRESH || _talonMaster.getSensorCollection().getQuadraturePosition() > MOTOR_SPEED - THRESH){//.getQuadraturePostion() > MOTOR_SPEED -THRESH) {
          SmartDashboard.putBoolean("Ready to shoot", true);
        } else {
          SmartDashboard.putBoolean("Ready to shoot", false);
      }
      }
    }

