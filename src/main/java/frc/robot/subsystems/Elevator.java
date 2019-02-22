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

  public double ELEVATOR_DISTANCE_PER_PULSE = 0;

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

      //Configuring PID values. 
    elevatorSRXMaster.config_kF(slotIndex, ELEVATOR_kF, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRXMaster.config_kP(slotIndex, ELEVATOR_kP, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRXMaster.config_kI(slotIndex, ELEVATOR_kI, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRXMaster.config_kD(slotIndex, ELEVATOR_kD, RobotMap.TIMEOUT_LIMIT_IN_Ms); 
    elevatorSRXSlave.set(ControlMode.Follower, elevatorSRXMaster.getDeviceID());
  } 

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ElevatorControl());
  }

  //Override Methods
  public void overrideElevator(double joystickSpeed){
    elevatorPidEnabled = false;
    joystickSpeed *= (-1 * RobotMap.ELEVATOR_SPEED_SENSITIVITY);
    elevatorSRXMaster.set(ControlMode.PercentOutput, joystickSpeed);
  }

  public void overrideStopped(){
		elevatorPidEnabled = false;
	}

  //Elevator Stopped with PID/Interrupted
  public void elevatorStop(){
		elevatorPidEnabled = false;
  }

  //Sets the point to which the elevator will move
  public void setPoint(double setPoint){
		double setPointNativeUnits = setPoint / ELEVATOR_DISTANCE_PER_PULSE;
		elevatorSRXMaster.set(ControlMode.Position, setPointNativeUnits);
		elevatorPidEnabled = true;
  }
  
  //Sets the NeutralMode of the elevator (BRAKE or COAST)
  public void setElevatorNeutralMode(NeutralMode neutralMode){
    elevatorSRXMaster.setNeutralMode(neutralMode);
  }

  //Allows the elevator to move faster/slower
  //false = lowgear, true = highgear
  public void elevatorGearShift(boolean shifterValue){
    elevatorGearShifter.set(shifterValue);
  }

  public boolean onTarget(){
		//Method returns true if on target
		boolean onTarget = Math.abs(elevatorSRXMaster.getSensorCollection().getQuadraturePosition() - elevatorSRXMaster.getClosedLoopTarget(loopIndex)) < RobotMap.ELEVATOR_THRESHOLD_FOR_PID;
		return onTarget;
		//getClosedLoopT gets the SetPoint already set (or moving to)
  }
  
    //Stand the elevator UP
  public void riseElevator(){
    elevatorCollapseTop.set(true);
    elevatorCollapseBottom.set(true);
  }

    //Drop the elevator FLAT.
  public void collapseElevator(){
    elevatorCollapseTop.set(false);
    elevatorCollapseBottom.set(false);
  }

  //Get if the BOTTOM limit is tripped. 
  public boolean getLimitB(){
    return !stopLow.get();
  }

  //Get if the TOP limit is tripped. 
  public boolean getLimitT(){
    return !stopHigh.get();
  }

  //Get the elevator's currently shifted GEAR.
  public boolean getElevatorGear(){
    return elevatorGearShifter.get();
  }

  //Get if the elevator is collapsed or not.
  public boolean getElevatorCollapsedTop(){
    return elevatorCollapseTop.get();
  }
  
  public boolean getElevatorCollapsedBottom(){
    return elevatorCollapseBottom.get();
  }

  //Get the HEIGHT of the elevator. 
  public double getElevatorHeight(){
    return (elevatorSRXMaster.getSensorCollection().getQuadraturePosition() * ELEVATOR_DISTANCE_PER_PULSE);
  }

  public void reportElevatorSensors(){
    SmartDashboard.putBoolean("Top Limit Switch", getLimitT());
    SmartDashboard.putBoolean("Bottom Limit Switch", getLimitB());
    SmartDashboard.putBoolean("Elevator In High Gear", getElevatorGear());
    SmartDashboard.putBoolean("Elevator Collapsed Top", getElevatorCollapsedTop());
    SmartDashboard.putBoolean("Elevator Collapsed Bottom", getElevatorCollapsedBottom());
    SmartDashboard.putNumber("Elevator Height", getElevatorHeight());
  }
}
