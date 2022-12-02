package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Compressor;


public class Robot extends TimedRobot 
{
  public MotorController frontLeftMotor= new PWMSparkMax(1);
  public MotorController backLeftMotor = new PWMVictorSPX(2);
  public MotorControllerGroup leftDrive = new MotorControllerGroup(frontLeftMotor, backLeftMotor);

  public MotorController frontRightMotor = new PWMSparkMax(3);
  public MotorController backRightMotor = new PWMVictorSPX(4);
  public MotorControllerGroup rightDrive = new MotorControllerGroup(frontRightMotor, backRightMotor);

  public DoubleSolenoid GiantPiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);

  public Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);


  public XboxController controller = new XboxController(0);

  public static int leftStickY = 1;
  public static int rightStickY = 5;

  public static int leftTrigger = 2;
  public static int rightTrigger = 3;

  @Override
  public void disabledInit()
  {

  }

  @Override
  public void robotInit() 
  {
    leftDrive.set(0);
    rightDrive.set(0);
  }

  @Override
  public void robotPeriodic() 
  {
   
  }

  @Override
  public void autonomousInit() 
  {
    
  }

  @Override
  public void autonomousPeriodic() 
  {
    
  }

  @Override
  public void teleopInit() 
  {
    leftDrive.set(0);
    rightDrive.set(0);
    compressor.enableDigital();
  }
  
  @Override
  public void teleopPeriodic() 
  {
    double leftY = controller.getRawAxis(leftStickY);
    double rightY = controller.getRawAxis(rightStickY) * 0.999;
    double rightTrig = controller.getRawAxis(rightTrigger);
    double leftTrig = controller.getRawAxis(leftTrigger);

     if (rightTrig > .35)
    {
      leftY *= rightTrig;
      rightY *= rightTrig;
    }
    else
    {
      leftY *= .35;
      rightY *= .35;
    }
    // if(rightTrig > .5)
    // {
    //   leftY *= .75;
    //   rightY *= .75;
    // } else 
    // {
    //   leftY *= .35;
    //   rightY *= .35;
    // }


    if(leftY < .05) 
    {
      if(leftY > -.05) 
      {
        leftY = 0;
      }
    }

    
    if(rightY < .05) 
    {
      if(rightY > .05) 
      {
        rightY = 0;
      }
    }

    leftDrive.set(-leftY);
    rightDrive.set(-rightY);


    //GIANT PISTON
    if(controller.getRightBumper())
    {
      GiantPiston.set(Value.kForward);
    }
    if(controller.getLeftBumper())
    {
      GiantPiston.set(Value.kReverse);
    }

    //COMPRESSOR
    if(controller.getAButtonPressed())
    {
      compressor.disable();
    }
    if (controller.getBButtonPressed())
    {
      compressor.enableDigital();
    }

  }


}
