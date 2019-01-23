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
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  TalonSRX elevatorSRX;
  Solenoid elevatorSolenoid;

  private double kPid, iPid, dPidm, fPid;

  private boolean elevatorPidEnabled;
	private int loopIndex, slotIndex;

  public Elevator(){

    loopIndex = 0;
    slotIndex = 0;
  
    elevatorSRX = new TalonSRX(RobotMap.ELEVATOR_TALON_SRX);
    elevatorSolenoid = new Solenoid(RobotMap.ELEVATOR_SOLENOID);

    elevatorSRX.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, loopIndex, RobotMap.TIMEOUT_LIMIT_IN_Ms);//10 is a timeout that waits for successful conection to sensor
    elevatorSRX.setSensorPhase(true);

    elevatorSRX.configAllowableClosedloopError(slotIndex, RobotMap.ELEVATOR_THRESHOLD_FOR_PID, RobotMap.TIMEOUT_LIMIT_IN_Ms);
  
    elevatorSRX.configNominalOutputForward(0, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX.configNominalOutputReverse(0, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX.configPeakOutputForward(1, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX.configPeakOutputReverse(-1, 	RobotMap.TIMEOUT_LIMIT_IN_Ms);
     
    elevatorSRX.config_kF(slotIndex, RobotMap.ELEVATOR_kF, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX.config_kP(slotIndex, RobotMap.ELEVATOR_kP, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX.config_kI(slotIndex, RobotMap.ELEVATOR_kI, RobotMap.TIMEOUT_LIMIT_IN_Ms);
    elevatorSRX.config_kD(slotIndex, RobotMap.ELEVATOR_kD, RobotMap.TIMEOUT_LIMIT_IN_Ms); 
  }

  //Override Methods
  public void overrideElevator(double joystickSpeed){
      elevatorPidEnabled = false;
      elevatorSRX.set(ControlMode.PercentOutput, joystickSpeed);
  }

  public void overrideStopped(){
		elevatorSRX.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		elevatorPidEnabled = false;
	}

  //Elevator Stopped with PID/Interrupted
  public void elevatorStop(){
		elevatorPidEnabled = false;
		elevatorSRX.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
  }

  //Sets the point to which the elevator will move
  public void setPoint(double setPointIndexInDegrees){
		double setPointNativeUnits = setPointIndexInDegrees / RobotMap.ELEVATOR_DISTANCE_PER_PULSE;
		elevatorSRX.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
		elevatorSRX.set(ControlMode.Position, setPointNativeUnits);
		elevatorPidEnabled = true;
	}

  //Allows the elevator to move faster/slower
  public void elevatorGearShift(int button){
    if(button == RobotMap.XBOX_BUTTON_BACK_OPERATOR){
      elevatorSolenoid.set(false);
    }else if(button == RobotMap.XBOX_BUTTON_START_OPERATOR){
      elevatorSolenoid.set(true);
    }else{
      System.out.print("Some error was thrown, start/back wasn't pressed");
    }
  }

  public boolean onTarget(){
		//Method returns true if on target
		boolean onTarget = Math.abs(elevatorSRX.getSensorCollection().getQuadraturePosition() - elevatorSRX.getClosedLoopTarget(loopIndex)) < RobotMap.ELEVATOR_THRESHOLD_FOR_PID;
		return onTarget;
		//getClosedLoopT gets the SetPoint already set (or moving to)
	}

  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

}
