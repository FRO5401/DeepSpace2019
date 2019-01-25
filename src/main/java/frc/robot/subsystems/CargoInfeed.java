/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.*;

/**
 * Add your docs here.
 */
public class CargoInfeed extends Subsystem {
  double armAngle;
  double armHeight;

  VictorSP feederMotors = new VictorSP(RobotMap.CARGO_FEED_ROLLERS);
  TalonSRX armTalon = new TalonSRX(RobotMap.ARM_TALON_CHANNEL);
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new FeedCargo());
  }

  public void armSetPoint(double desiredAngleNativeUnits){ 
    double desiredNativePoint = desiredAngleNativeUnits;
    armTalon.set(ControlMode.Position, desiredNativePoint);
    getArmAngle();
  }

  public void armOverrideMove(double armDirection){
    armTalon.set(ControlMode.PercentOutput, armDirection);
    getArmAngle();
  }

  public void armSetTalonNeutralMode(NeutralMode neutralMode){
    armTalon.setNeutralMode(neutralMode);
    SmartDashboard.putString("Neutral Mode", neutralMode.toString());
  }

  public void feedIn(){
    feederMotors.set(RobotMap.FEEDER_SPEED);
  }
  
  public void feedOut(){
    feederMotors.set(-1 * RobotMap.FEEDER_SPEED);
  }

  public void feedStop(){
    feederMotors.set(0);
  }

  public double getArmAngle(){
    armAngle = armTalon.getSensorCollection().getQuadraturePosition();
    SmartDashboard.putNumber("Arm Angle (Native)", armAngle);
    SmartDashboard.putNumber("Arm Angle (Degrees)", (armAngle * RobotMap.ARM_ANGLE_PER_PULSE));
    return armAngle;
  }

  public double getArmDistance(){
    armHeight = armTalon.getSensorCollection().getQuadraturePosition();
    SmartDashboard.putNumber("Arm Height (Height)", (armHeight * RobotMap.ARM_DISTANCE_PER_PULSE));
    return armHeight;
  }

}
