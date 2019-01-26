/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
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
  private double armAngle;
  private int loopIndex, slotIndex;

  VictorSP feederMotors;
  TalonSRX armTalon;

  public CargoInfeed(){
    feederMotors = new VictorSP(RobotMap.CARGO_FEED_ROLLERS);
    armTalon = new TalonSRX(RobotMap.ARM_TALON_CHANNEL);

    loopIndex = 0;
    slotIndex = 0;

      //Setting the sensor and the threshold for error.
    armTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, loopIndex, RobotMap.TIMEOUT_LIMIT_MS);
    armTalon.configAllowableClosedloopError(slotIndex, RobotMap.ARM_PID_THRESHOLD, RobotMap.TIMEOUT_LIMIT_MS);
    
      //Setting Max and Min values. 
    armTalon.configNominalOutputForward(0, 	RobotMap.TIMEOUT_LIMIT_MS);
    armTalon.configNominalOutputReverse(0, 	RobotMap.TIMEOUT_LIMIT_MS);
    armTalon.configPeakOutputForward(1, 	RobotMap.TIMEOUT_LIMIT_MS);
    armTalon.configPeakOutputReverse(-1, 	RobotMap.TIMEOUT_LIMIT_MS);
    
      //Setting the PID values.
    armTalon.config_kF(slotIndex, RobotMap.ARM_kF, RobotMap.TIMEOUT_LIMIT_MS);
    armTalon.config_kP(slotIndex, RobotMap.ARM_kP, RobotMap.TIMEOUT_LIMIT_MS);
    armTalon.config_kI(slotIndex, RobotMap.ARM_kI, RobotMap.TIMEOUT_LIMIT_MS);
    armTalon.config_kD(slotIndex, RobotMap.ARM_kD, RobotMap.TIMEOUT_LIMIT_MS);   
  }
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new FeedCargo());
  }

    //Moves the arm to the desired angle (native units)
  public void armSetPoint(double desiredAngleNativeUnits){ 
    armTalon.set(ControlMode.Position, desiredAngleNativeUnits);
  }

    //Moves the arm manually, given velocity
  public void armOverrideMove(double armVelocity){
    armTalon.set(ControlMode.PercentOutput, armVelocity);
  }

    //Sets the neutral mode of the Talons (Coast or Brake), post to Dashboard
  public void armSetTalonNeutralMode(NeutralMode neutralMode){
    armTalon.setNeutralMode(neutralMode);
    SmartDashboard.putString("Neutral Mode", neutralMode.toString());
  }

    //Set motors to feed in
  public void feedIn(){
    feederMotors.set(RobotMap.FEEDER_SPEED);
  }
  
    //Set motors to feed out
  public void feedOut(){
    feederMotors.set(-1 * RobotMap.FEEDER_SPEED);
  }

    //Set motors to stop feeding
  public void feedStop(){
    feederMotors.set(0);
  }

    //Get talon encoder value, post vals to Dashboard.
  public double getArmAngle(){
    armAngle = armTalon.getSensorCollection().getQuadraturePosition();
    SmartDashboard.putNumber("Arm Angle (Native)", armAngle);
    SmartDashboard.putNumber("Arm Angle (Degrees)", (armAngle * RobotMap.ARM_ANGLE_PER_PULSE));
    return armAngle;
  }
}
