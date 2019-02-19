/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.robot.commands.ElevatorControl;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  TalonSRX elevatorSRX1;
  TalonSRX elevatorSRX2;
  Solenoid elevatorGearShifter;
  Solenoid elevatorCollapse;

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
  
    elevatorSRX1 = new TalonSRX(RobotMap.ELEVATOR_TALON_SRX);
    elevatorGearShifter   = new Solenoid(RobotMap.ELEVATOR_GEAR_SHIFTER_SOLENOID);
    elevatorCollapse  = new Solenoid(RobotMap.ELEVATOR_COLLAPSE_SOLENOID);

    //Limits
    stopHigh = new DigitalInput(RobotMap.E_STOP_HIGH);
    stopLow = new DigitalInput(RobotMap.E_STOP_LOW);

    elevatorSRX1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, loopIndex, RobotMap.TIMEOUT_LIMIT_IN_Ms);//10 is a timeout that waits for successful conection to sensor
    elevatorSRX1.setSensorPhase(true);

    elevatorSRX1.configAllowableClosedloopError(slotIndex, RobotMap.ELEVATOR_THRESHOLD_FOR_PID, RobotMap.TIMEOUT_LIMIT_IN_Ms);
  
    elevatorSRX1.configNominalOutputForward(0, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX1.configNominalOutputReverse(0, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX1.configPeakOutputForward(1, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX1.configPeakOutputReverse(-1, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);
     
    elevatorSRX1.config_kF(slotIndex, ELEVATOR_kF, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX1.config_kP(slotIndex, ELEVATOR_kP, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX1.config_kI(slotIndex, ELEVATOR_kI, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX1.config_kD(slotIndex, ELEVATOR_kD, RobotMap.TIMEOUT_LIMIT_IN_Ms); 
  }

  //Override Methods
  public void overrideElevator(double joystickSpeed){
      elevatorPidEnabled = false;
      elevatorSRX1.set(ControlMode.PercentOutput, joystickSpeed);
  }

  public void overrideStopped(){
		elevatorSRX1.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		elevatorPidEnabled = false;
	}

  //Elevator Stopped with PID/Interrupted
  public void elevatorStop(){
		elevatorPidEnabled = false;
		elevatorSRX1.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
  }

  //Sets the point to which the elevator will move
  public void setPoint(double setPoint){
		double setPointNativeUnits = setPoint / ELEVATOR_DISTANCE_PER_PULSE;
    elevatorSRX1.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    elevatorSRX2.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
		elevatorSRX1.set(ControlMode.Position, setPointNativeUnits);
		elevatorSRX2.set(ControlMode.Position, setPointNativeUnits);
		elevatorPidEnabled = true;
	}

  //Allows the elevator to move faster/slower
  public void elevatorGearShiftHightoLow(){
    elevatorGearShifter.set(false);
  }

  public void elevatorGearShiftLowtoHigh(){
    elevatorGearShifter.set(true);
  }

  public boolean onTarget(){
		//Method returns true if on target
		boolean onTarget = Math.abs(elevatorSRX1.getSensorCollection().getQuadraturePosition() - elevatorSRX1.getClosedLoopTarget(loopIndex)) < RobotMap.ELEVATOR_THRESHOLD_FOR_PID;
		return onTarget;
		//getClosedLoopT gets the SetPoint already set (or moving to)
  }

  public boolean getLimitB(){
    return stopLow.get();
  }

  public boolean getLimitT(){
    return stopHigh.get();
  }

    //Lifts the elevator vertical
  public void riseElevator(){
    elevatorCollapse.set(true);
  }

    //Drops the elevator horizontal
  public void collapseElevator(){
    elevatorCollapse.set(false);
  }


  /*
  public void elevatorLimitT(){                           //Meant for Limit Switches, logic is in command now
    if(stopHigh.get() == true){
      elevatorSRX1.set(ControlMode.PercentOutput, 0);
		  elevatorSRX2.set(ControlMode.PercentOutput, 0);
    } else if(stopHigh.get() == false){
      System.out.print("Limit Top has not been hit");
    }
  }
  
  public void elevatorLimitB(){
    if(stopLow.get() == true){
      elevatorSRX1.set(ControlMode.PercentOutput, 0);
		  elevatorSRX2.set(ControlMode.PercentOutput, 0);
    } else if(stopLow.get() == false){
      System.out.print("Limit Bottom has not been hit");
    }
  }
*/

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ElevatorControl());
  }

}
