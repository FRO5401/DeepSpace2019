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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.*;

/**
 * Add your docs here.
 */
public class CarriageInfeed extends Subsystem {
  private double carriageAngle;
  private int loopIndex, slotIndex;

  private double CARRIAGE_kF = 0;
  private double CARRIAGE_kP = 0;
  private double CARRIAGE_kI = 0;
  private double CARRIAGE_kD = 0;
  private int TIMEOUT_LIMIT_MS = 10;
  private int CARRIAGE_PID_THRESHOLD = 2;
  
  private double resetAngle = 88;
  private double groundAngle = -43;
  private double midAngle = 0;

  VictorSP feederMotors;
  TalonSRX carriageTalon;
  DigitalInput carriageLimitTop;


  public CarriageInfeed(){
    feederMotors = new VictorSP(RobotMap.CARRIAGE_FEED_ROLLERS);
    carriageTalon = new TalonSRX(RobotMap.CARRIAGE_TALON_CHANNEL);
    carriageLimitTop = new DigitalInput(RobotMap.C_STOP_T);

    loopIndex = 0;
    slotIndex = 0;

      //Setting the sensor and the threshold for error.
    carriageTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, loopIndex, TIMEOUT_LIMIT_MS);
    carriageTalon.configAllowableClosedloopError(slotIndex, CARRIAGE_PID_THRESHOLD, TIMEOUT_LIMIT_MS);
    
      //Setting Max and Min values. 
    carriageTalon.configNominalOutputForward(0, TIMEOUT_LIMIT_MS);
    carriageTalon.configNominalOutputReverse(0, TIMEOUT_LIMIT_MS);
    carriageTalon.configPeakOutputForward(1, TIMEOUT_LIMIT_MS);
    carriageTalon.configPeakOutputReverse(-1, TIMEOUT_LIMIT_MS);
    
      //Setting the PID values.
    carriageTalon.config_kF(slotIndex, CARRIAGE_kF, TIMEOUT_LIMIT_MS);
    carriageTalon.config_kP(slotIndex, CARRIAGE_kP, TIMEOUT_LIMIT_MS);
    carriageTalon.config_kI(slotIndex, CARRIAGE_kI, TIMEOUT_LIMIT_MS);
    carriageTalon.config_kD(slotIndex, CARRIAGE_kD, TIMEOUT_LIMIT_MS);   
  }
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new FeedCarriage());
  }

  //PID methods for carriage to run when elevator is moving
  public void setMidCarriageAngle(){
    carriageTalon.set(ControlMode.Position, midAngle);
  }

  public void setGroundCarriageAngle(){
    carriageTalon.set(ControlMode.Position, groundAngle);
  }

  public void resetCarriageAngle(){
  //Reset to 90 degrees (default)
    carriageTalon.set(ControlMode.Position, resetAngle);
  }
    //Moves the carriage to the desired angle (native units)
  public void carriageSetPoint(double desiredAngleNativeUnits){ 
    //solve for a.p.p later
    carriageTalon.set(ControlMode.Position, desiredAngleNativeUnits);
  }

    //Moves the carriage manually, given velocity
  public void carriageOverrideMove(double carriagePercentSpeed){
    carriageTalon.set(ControlMode.PercentOutput, carriagePercentSpeed * -1);
  }

    //Sets the neutral mode of the Talons (Coast or Brake), post to Dashboard
  public void carriageSetTalonNeutralMode(NeutralMode neutralMode){
    carriageTalon.setNeutralMode(neutralMode);
    SmartDashboard.putString("Neutral Mode", neutralMode.toString());
  }

    //Set motors to feed in
  public void feedIn(){
    feederMotors.set(RobotMap.CARRIAGE_FEEDER_SPEED);
  }
  
    //Set motors to feed out
  public void feedOut(){
    feederMotors.set(-1 * RobotMap.CARRIAGE_FEEDER_SPEED);
  }

    //Set motors to stop feeding
  public void feedStop(){
    feederMotors.set(0);
  }

    //Get talon encoder value, post vals to Dashboard.
  public double getCarriageAngle(){
    carriageAngle = carriageTalon.getSensorCollection().getQuadraturePosition();
    return carriageAngle;
  }

  public boolean getLimitTop(){
    return !carriageLimitTop.get();
  }

  public void reportCarriageInfeedSensors(){
    SmartDashboard.putBoolean("Top Limit Infeed", getLimitTop());
    SmartDashboard.putNumber("Infeed Direction", feederMotors.getSpeed());
    SmartDashboard.putNumber("Carriage Angle (Native)", getCarriageAngle());
    SmartDashboard.putNumber("Carriage Angle (Degrees)", (getCarriageAngle() * RobotMap.CARRIAGE_ANGLE_PER_PULSE));
  }
}
