package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS0
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  /*** Objects ***/
    //Joysticks
    //Need to be public since they are called from other classes. 
  public Joystick xboxDriver = new Joystick(RobotMap.XBOX_CONTROLLER_DRIVER);
  public Joystick xboxOperator = new Joystick(RobotMap.XBOX_CONTROLLER_OPERATOR);

    //Buttons (Driver)
  Button xboxA_Driver			  = new JoystickButton(xboxDriver, RobotMap.XBOX_BUTTON_A_DRIVER);
	Button xboxB_Driver			  = new JoystickButton(xboxDriver, RobotMap.XBOX_BUTTON_B_DRIVER);
	Button xboxX_Driver			  = new JoystickButton(xboxDriver, RobotMap.XBOX_BUTTON_X_DRIVER);
	Button xboxY_Driver			  = new JoystickButton(xboxDriver, RobotMap.XBOX_BUTTON_Y_DRIVER);
	Button xboxLeftBumper_Driver  = new JoystickButton(xboxDriver, RobotMap.XBOX_BUTTON_LEFT_BUMPER_DRIVER);
	Button xboxRightBumper_Driver = new JoystickButton(xboxDriver, RobotMap.XBOX_BUTTON_RIGHT_BUMPER_DRIVER);
	Button xboxBack_Driver		  = new JoystickButton(xboxDriver, RobotMap.XBOX_BUTTON_BACK_DRIVER);
	Button xboxStart_Driver		  = new JoystickButton(xboxDriver, RobotMap.XBOX_BUTTON_START_DRIVER);
	Button xboxL3_Driver		  = new JoystickButton(xboxDriver, RobotMap.XBOX_BUTTON_L3_DRIVER);
	Button xboxR3_Driver		  = new JoystickButton(xboxDriver, RobotMap.XBOX_BUTTON_R3_DRIVER);
  
    //Buttons (Operator)
  Button xboxA_Operator			= new JoystickButton(xboxOperator, RobotMap.XBOX_BUTTON_A_OPERATOR);
	Button xboxB_Operator			= new JoystickButton(xboxOperator, RobotMap.XBOX_BUTTON_B_OPERATOR);
	Button xboxX_Operator			= new JoystickButton(xboxOperator, RobotMap.XBOX_BUTTON_X_OPERATOR);
	Button xboxY_Operator			= new JoystickButton(xboxOperator, RobotMap.XBOX_BUTTON_Y_OPERATOR);
	Button xboxLeftBumper_Operator  = new JoystickButton(xboxOperator, RobotMap.XBOX_BUTTON_LEFT_BUMPER_OPERATOR);
	Button xboxRightBumper_Operator = new JoystickButton(xboxOperator, RobotMap.XBOX_BUTTON_RIGHT_BUMPER_OPERATOR);
	Button xboxBack_Operator		= new JoystickButton(xboxOperator, RobotMap.XBOX_BUTTON_BACK_OPERATOR);
	Button xboxStart_Operator		= new JoystickButton(xboxOperator, RobotMap.XBOX_BUTTON_START_OPERATOR);
	Button xboxL3_Operator		  	= new JoystickButton(xboxOperator, RobotMap.XBOX_BUTTON_L3_OPERATOR);
	Button xboxR3_Operator		  	= new JoystickButton(xboxOperator, RobotMap.XBOX_BUTTON_R3_OPERATOR);

  public OI(){
  }

  public double xboxAxis(Joystick xboxController, int xboxAxis){
    return xboxController.getRawAxis(xboxAxis);
  }

  public boolean xboxButton(Joystick xboxController, int xboxButton){
    return xboxController.getRawButton(xboxButton);
  }

  public int xboxAxisAsButton(Joystick xboxController, int xboxAxis){
    if(xboxController.getRawAxis(xboxAxis) > RobotMap.AXIS_THRESHOLD){
      return 1;
    }
    else if(xboxController.getRawAxis(xboxAxis) < (-1 * RobotMap.AXIS_THRESHOLD)){
      return -1;
    }
    else{
      return 0;
    }
  }

  public int xboxDPad(Joystick xboxController){
    return xboxController.getPOV();
  }
}
