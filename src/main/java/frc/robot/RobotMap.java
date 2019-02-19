/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  /*** Constants ***/
    //OI
  public static final double AXIS_THRESHOLD = 0.3;

    //DriveBase
  public static final double LOW_GEAR_LEFT_DPP = 0;
  public static final double LOW_GEAR_RIGHT_DPP = 0;
  public static final double HIGH_GEAR_LEFT_DPP = 0;
  public static final double HIGH_GEAR_RIGHT_DPP = 0;

    //XboxMove
  public static final double DRIVE_SENSITIVITY_PRECISION = 0.5;
  public static final double DRIVE_SENSITIVITY_DEFAULT = 1;
  public static final double SPIN_SENSITIVITY = 0.5;


  /*** Operator Interfaces ***/
    //Controllers 
  public static final int XBOX_CONTROLLER_DRIVER = 0;
  public static final int XBOX_CONTROLLER_OPERATOR = 1;

    //Driver Buttons
  public static final int XBOX_BUTTON_A_DRIVER	   		    = 1;
  public static final int XBOX_BUTTON_B_DRIVER		        = 2;
  public static final int XBOX_BUTTON_X_DRIVER		        = 3;
  public static final int XBOX_BUTTON_Y_DRIVER		   	    = 4;
  public static final int XBOX_BUTTON_LEFT_BUMPER_DRIVER  = 5;
  public static final int XBOX_BUTTON_RIGHT_BUMPER_DRIVER = 6;
  public static final int XBOX_BUTTON_BACK_DRIVER		      = 7;
  public static final int XBOX_BUTTON_START_DRIVER	  	  = 8;
  public static final int XBOX_BUTTON_L3_DRIVER		  	    = 9;
  public static final int XBOX_BUTTON_R3_DRIVER		  	    = 10;
  
    //Operator Buttons
  public static final int XBOX_BUTTON_A_OPERATOR	   		    = 1;
  public static final int XBOX_BUTTON_B_OPERATOR		        = 2;
  public static final int XBOX_BUTTON_X_OPERATOR		        = 3;
  public static final int XBOX_BUTTON_Y_OPERATOR		   	    = 4;
  public static final int XBOX_BUTTON_LEFT_BUMPER_OPERATOR  = 5;
  public static final int XBOX_BUTTON_RIGHT_BUMPER_OPERATOR = 6;
  public static final int XBOX_BUTTON_BACK_OPERATOR		      = 7;
  public static final int XBOX_BUTTON_START_OPERATOR	  	  = 8;
  public static final int XBOX_BUTTON_L3_OPERATOR		  	    = 9;
  public static final int XBOX_BUTTON_R3_OPERATOR		  	    = 10;
  
    //Axes
  public static final int XBOX_AXIS_LEFT_X = 0;
  public static final int XBOX_AXIS_LEFT_Y = 1;
  public static final int XBOX_AXIS_LEFT_TRIGGER  = 2;
  public static final int XBOX_AXIS_RIGHT_TRIGGER = 3;
  public static final int XBOX_AXIS_RIGHT_X = 4;
  public static final int XBOX_AXIS_RIGHT_Y = 5;
    
  /*** Motors ***/
    //DriveBase
  public static final int DRIVE_MOTOR_RIGHT_1 = 0;
  public static final int DRIVE_MOTOR_LEFT_1  = 2;
  public static final int DRIVE_MOTOR_RIGHT_2 = 1;
  public static final int DRIVE_MOTOR_LEFT_2  = 3;

  //Elevator
  public static final int ELEVATOR_TALON_SRX  = 0;

  /*** Solenoids (Single and Double) ***/
    //DoubleSolenoids have an IN and a OUT
    //Solenoids have just one. 
    //PCM (Pneumatic Control Module)
  public static final int PCM_ID = 0;

    //DriveBase
  public static final int GEAR_SHIFTER = 0;

    //Elevator
  public static final int ELEVATOR_GEAR_SHIFTER_SOLENOID  = 0;
  public static final int ELEVATOR_COLLAPSE_SOLENOID  = 1;


  /*** Sensors ***/
    //Encoders
  public static final int DRIVE_ENC_LEFT_A = 0;
  public static final int DRIVE_ENC_RIGHT_A = 0;
  public static final int DRIVE_ENC_LEFT_B = 0;
  public static final int DRIVE_ENC_RIGHT_B = 0;

  //New for Elevator, reorganize later
  public static final int TIMEOUT_LIMIT_IN_Ms = 0;
  public static final int ELEVATOR_THRESHOLD_FOR_PID = 0;

    //Limit switches
  public static final int E_STOP_HIGH = 1;
  public static final int E_STOP_LOW = 2;
}