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

import frc.robot.commands.ElevatorControl;
import frc.robot.commands.ElevatorOverride;
import frc.robot.commands.ElevatorPID;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  TalonSRX elevatorSRXMaster, elevatorSRXSlave;
  Solenoid elevatorGearShifter;
  Solenoid elevatorCollapseTop, elevatorCollapseBottom;

  DigitalInput stopHigh, stopLow;

  private boolean elevatorPidEnabled;
  private int loopIndex, slotIndex;
  
  private double ELEVATOR_kF = 0;
  private double ELEVATOR_kP = 0;
  private double ELEVATOR_kI = 0;
  private double ELEVATOR_kD = 0;

    //Previous try was .0003800114;
  public double ELEVATOR_DISTANCE_PER_PULSE = -0.00037948;

  public Elevator(){

    loopIndex = 0;
    slotIndex = 0;
  
    elevatorSRXMaster   = new TalonSRX(RobotMap.ELEVATOR_TALON_MASTER_CHANNEL);
    elevatorSRXSlave    = new TalonSRX(RobotMap.ELEVATOR_TALON_SLAVE_CHANNEL);
    elevatorGearShifter = new Solenoid(RobotMap.ELEVATOR_GEAR_SHIFTER);
    elevatorCollapseTop    = new Solenoid(RobotMap.ELEVATOR_COLLAPSE_TOP);
    elevatorCollapseBottom    = new Solenoid(RobotMap.ELEVATOR_COLLAPSE_BOTTOM);

    //Limits
    stopHigh = new DigitalInput(RobotMap.E_STOP_HIGH);
    stopLow = new DigitalInput(RobotMap.E_STOP_LOW);

    elevatorSRXMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, loopIndex, RobotMap.TIMEOUT_LIMIT_IN_Ms);//10 is a timeout that waits for successful conection to sensor
    elevatorSRXMaster.setSensorPhase(true);

    elevatorSRXMaster.configAllowableClosedloopError(slotIndex, RobotMap.ELEVATOR_THRESHOLD_FOR_PID, RobotMap.TIMEOUT_LIMIT_IN_Ms);
  
      //Configuring the max & min percentage output. 
    elevatorSRXMaster.configNominalOutputForward(0, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRXMaster.configNominalOutputReverse(0, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRXMaster.configPeakOutputForward(1, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRXMaster.configPeakOutputReverse(-1, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);

    elevatorSRXMaster.config_kF(slotIndex, ELEVATOR_kF, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRXMaster.config_kP(slotIndex, ELEVATOR_kP, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRXMaster.config_kI(slotIndex, ELEVATOR_kI, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRXMaster.config_kD(slotIndex, ELEVATOR_kD, RobotMap.TIMEOUT_LIMIT_IN_Ms); 
    elevatorSRXSlave.set(ControlMode.Follower, elevatorSRXMaster.getDeviceID());
  } 

  @Override
  public void initDefaultCommand() {
  }

  //used to override the elevator
  public void overrideElevator(double joystickSpeed){
    elevatorPidEnabled = false;
    joystickSpeed *= (-1 * RobotMap.ELEVATOR_SPEED_SENSITIVITY);
    elevatorSRXMaster.set(ControlMode.PercentOutput, joystickSpeed);
  }

  //used to cancel an override
  public void overrideStopped(){
    elevatorSRXMaster.setNeutralMode(NeutralMode.Brake);
    elevatorSRXMaster.set(ControlMode.PercentOutput, 0);
    elevatorPidEnabled = false;
	}

  //stops the elevator by disabling its pid
  public void elevatorStop(){
		elevatorPidEnabled = false;
  }

  public void setPoint(double setPoint){
		double setPointNativeUnits = setPoint / ELEVATOR_DISTANCE_PER_PULSE;
		elevatorSRXMaster.set(ControlMode.Position, setPointNativeUnits);
		elevatorPidEnabled = true;
  }
  
  //sets the elevator to neutral mode.
  public void setElevatorNeutralMode(NeutralMode neutralMode){
    elevatorSRXMaster.setNeutralMode(neutralMode);
  }

  //shifts the elevator's gear to the value that has been requested for the gear.
  public void elevatorGearShift(boolean shifterValue){
    elevatorGearShifter.set(shifterValue);
  }

  public boolean onTarget(){
		//Method returns true if on target
		boolean onTarget = Math.abs(elevatorSRXMaster.getSensorCollection().getQuadraturePosition() - elevatorSRXMaster.getClosedLoopTarget(loopIndex)) < RobotMap.ELEVATOR_THRESHOLD_FOR_PID;
		return onTarget;
		//getClosedLoopT gets the SetPoint already set (or moving to)
  }
  
  //raises the elevator by setting values to true
  public void riseElevator(){
    elevatorCollapseTop.set(true);
    elevatorCollapseBottom.set(true);
  }

  //raises the elevator by setting values to false
  public void collapseElevator(){
    elevatorCollapseTop.set(false);
    elevatorCollapseBottom.set(false);
  }

  //gets the bottom limit
  public boolean getLimitB(){
    return !stopLow.get();
  }

  //gets the top limit
  public boolean getLimitT(){
    return !stopHigh.get();
  }

  //gets the current elevator gear
  public boolean getElevatorGear(){
    return elevatorGearShifter.get();
  }

  //checks to see if the top is collapsed
  public boolean getElevatorCollapsedTop(){
    return elevatorCollapseTop.get();
  }
  
  //checks to see if the bottom is collapsed
  public boolean getElevatorCollapsedBottom(){
    return elevatorCollapseBottom.get();
  }

  //retrieves the current elevator height
  public double getElevatorHeight(){
    return (elevatorSRXMaster.getSensorCollection().getQuadraturePosition() * ELEVATOR_DISTANCE_PER_PULSE);
  }

  //reports elevator input to the smart dashboard
  public void reportElevatorSensors(){
    SmartDashboard.putBoolean("Top Limit Switch", getLimitT());
    SmartDashboard.putBoolean("Bottom Limit Switch", getLimitB());
    SmartDashboard.putBoolean("Elevator In High Gear", getElevatorGear());
    SmartDashboard.putBoolean("Elevator Collapsed Top", getElevatorCollapsedTop());
    SmartDashboard.putBoolean("Elevator Collapsed Bottom", getElevatorCollapsedBottom());
    SmartDashboard.putNumber("Elevator Height", getElevatorHeight());
    SmartDashboard.putNumber("Elevator Height (Raw)", elevatorSRXMaster.getSensorCollection().getQuadraturePosition());
  }
}
