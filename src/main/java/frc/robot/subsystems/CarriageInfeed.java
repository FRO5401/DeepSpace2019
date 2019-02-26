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
public class CarriageInfeed extends Subsystem {

  //Carriage Angle
  private double carriageAngle;

  //Indices for the motor PID controller
  private int loopIndex, slotIndex;

  //PID constants
  private double CARRIAGE_kF = 0;
  private double CARRIAGE_kP = 0;
  private double CARRIAGE_kI = 0;
  private double CARRIAGE_kD = 0;
  private int TIMEOUT_LIMIT_MS = 10;
  private int CARRIAGE_PID_THRESHOLD = 2;
  
  //Angle setpoints
  private double resetAngle = 88;
  private double groundAngle = -43;
  private double midAngle = 0;

  //Motors for the carriages
  VictorSP feederMotors;
  TalonSRX carriageTalon;

  public CarriageInfeed(){
    //Mechanism to put the carriages in and out
    feederMotors = new VictorSP(RobotMap.CARRIAGE_FEED_ROLLERS);

    //Mechanism to transport the carriages
    carriageTalon = new TalonSRX(RobotMap.CARRIAGE_TALON_CHANNEL);

    //Loop index and slot index configures the PID 
    loopIndex = 0;
    slotIndex = 0;

    //Configures PID in the carriage
    carriageTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, loopIndex, TIMEOUT_LIMIT_MS);
    carriageTalon.configAllowableClosedloopError(slotIndex, CARRIAGE_PID_THRESHOLD, TIMEOUT_LIMIT_MS);
    
    //Configures maximum and minimum output
    carriageTalon.configNominalOutputForward(0, TIMEOUT_LIMIT_MS);
    carriageTalon.configNominalOutputReverse(0, TIMEOUT_LIMIT_MS);
    carriageTalon.configPeakOutputForward(1, TIMEOUT_LIMIT_MS);
    carriageTalon.configPeakOutputReverse(-1, TIMEOUT_LIMIT_MS);
    
    //Used for PID constants
    carriageTalon.config_kF(slotIndex, CARRIAGE_kF, TIMEOUT_LIMIT_MS);
    carriageTalon.config_kP(slotIndex, CARRIAGE_kP, TIMEOUT_LIMIT_MS);
    carriageTalon.config_kI(slotIndex, CARRIAGE_kI, TIMEOUT_LIMIT_MS);
    carriageTalon.config_kD(slotIndex, CARRIAGE_kD, TIMEOUT_LIMIT_MS);   
  }
  

  //Initializes with the default command
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new FeedCarriage());
  }

  //Puts the carriage in the mid-angle
  public void setMidCarriageAngle(){
    carriageTalon.set(ControlMode.Position, midAngle);
  }

  //Puts the carriage in the ground angle
  public void setGroundCarriageAngle(){
    carriageTalon.set(ControlMode.Position, groundAngle);
  }

  //Resets the carriage angle
  public void resetCarriageAngle(){
    carriageTalon.set(ControlMode.Position, resetAngle);
  }

  //Sets the setpoints of
  public void carriageSetPoint(double desiredAngleNativeUnits){ 
    carriageTalon.set(ControlMode.Position, desiredAngleNativeUnits);
  }

  //Overrides the carriage control
  public void carriageOverrideMove(double carriagePercentSpeed){
    carriageTalon.set(ControlMode.PercentOutput, carriagePercentSpeed);
  }

  //Sets the neutral mode of the carriage talon
  public void carriageSetTalonNeutralMode(NeutralMode neutralMode){
    carriageTalon.setNeutralMode(neutralMode);
    SmartDashboard.putString("Neutral Mode", neutralMode.toString());
  }

  //Puts the carriage in the feeder
  public void feedIn(){
    feederMotors.set(RobotMap.CARRIAGE_FEEDER_SPEED);
  }

  //Puts the carriage out of the feeder
  public void feedOut(){
    feederMotors.set(-1 * RobotMap.CARRIAGE_FEEDER_SPEED);
  }

  //Stops the feeder motors
  public void feedStop(){
    feederMotors.set(0);
  }

  //Sensor collection
  public double getCarriageAngle(){
    carriageAngle = carriageTalon.getSensorCollection().getQuadraturePosition();
    return carriageAngle;
  }

  //Reports the angles of the carriage
  public void reportCarriageInfeedSensors(){
    SmartDashboard.putNumber("Infeed Direction", feederMotors.getSpeed());
    SmartDashboard.putNumber("Carriage Angle (Native)", carriageAngle);
    SmartDashboard.putNumber("Carriage Angle (Degrees)", (carriageAngle * RobotMap.CARRIAGE_ANGLE_PER_PULSE));
  }
}
