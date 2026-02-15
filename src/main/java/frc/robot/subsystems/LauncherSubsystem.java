// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs;
import frc.robot.Constants.LauncherConstants;

public class LauncherSubsystem extends SubsystemBase {

  public final SparkFlex launcherFlex; //Spark for the Flywheel of the launcher
  public final SparkMax indexerSpark; //Spark for the indexer of the launcher
  public final SparkMax elevatorSpark; //Spark for the elevator of the launcher
  public final SparkMax intakeSpark; //Spark for the intake of the robot
  public final RelativeEncoder launcherEncoder; //Encoder for the Flywheel
  public final RelativeEncoder indexerEncoder; //Encoder for the indexer
  public final RelativeEncoder elevatorEncoder; //Encoder for the elevator
  public final RelativeEncoder intakeEncoder; //Encoder for the intake
  public final SparkClosedLoopController launcherPIDController; //PID controller for the Flywheel
  public final SparkClosedLoopController indexerPIDController; //PID controller for the indexer
  public final SparkClosedLoopController elevatorPIDController; //PID controller for the elevator
  public final SparkClosedLoopController intakePIDController; //PID controller for the intake
  public static boolean runMotors = false; //Flag to indicate if the motors are running

  @Override
  public void periodic() {
    // Put motor status variable on the SmartDashboard
    SmartDashboard.putBoolean("runMotors", runMotors);
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  private static LauncherSubsystem mInstance;
  public static LauncherSubsystem getInstance() {
      if (mInstance == null) {
        mInstance = new LauncherSubsystem();
      }
      return mInstance;
    }

  /** Creates a new ExampleSubsystem. */
  public LauncherSubsystem() {
    launcherFlex = new SparkFlex(LauncherConstants.LauncherCanID, MotorType.kBrushless); //Create the Flywheel Spark object
    indexerSpark = new SparkMax(LauncherConstants.IndexerCanID, MotorType.kBrushless); //Create the indexer Spark object
    elevatorSpark = new SparkMax(LauncherConstants.ElevatorCanID, MotorType.kBrushless); //Create the elevator Spark object
    intakeSpark = new SparkMax(LauncherConstants.IntakeCanID, MotorType.kBrushless); //Create the intake Spark object
    launcherEncoder = launcherFlex.getEncoder(); //Get the Flywheel encoder
    indexerEncoder = indexerSpark.getEncoder(); //Get the indexer encoder
    elevatorEncoder = elevatorSpark.getEncoder(); //Get the elevator encoder
    intakeEncoder = elevatorSpark.getEncoder(); //Get the intake encoder
    launcherPIDController = launcherFlex.getClosedLoopController(); //Get the Flywheel PID controller
    indexerPIDController = indexerSpark.getClosedLoopController(); //Get the indexer PID controller
    elevatorPIDController = elevatorSpark.getClosedLoopController(); //Get the elevator PID controller
    intakePIDController = elevatorSpark.getClosedLoopController(); //Get the intake PID controller

    launcherFlex.configure(Configs.LauncherSubsystem.launcherConfig, ResetMode.kResetSafeParameters,PersistMode.kPersistParameters); //Configure the Flywheel Spark
    indexerSpark.configure(Configs.LauncherSubsystem.indexerConfig, ResetMode.kResetSafeParameters,PersistMode.kPersistParameters); //Configure the indexer Spark
    elevatorSpark.configure(Configs.LauncherSubsystem.elevatorConfig, ResetMode.kResetSafeParameters,PersistMode.kPersistParameters); //Configure the elevator Spark
    intakeSpark.configure(Configs.LauncherSubsystem.intakeConfig, ResetMode.kResetSafeParameters,PersistMode.kPersistParameters); //Configure the intake Spark
    
    //Initialize all of the SmartDashboard values to be used for I/O
    SmartDashboard.putNumber("LauncherPowerLevel", SmartDashboard.getNumber("LauncherPowerLevel", 0.0));
    SmartDashboard.putNumber("IndexerPowerLevel", SmartDashboard.getNumber("IndexerPowerLevel", 0.0));
    SmartDashboard.putNumber("ElevatorPowerLevel", SmartDashboard.getNumber("ElevatorPowerLevel", 0.0));
    SmartDashboard.putNumber("IntakePowerLevel", SmartDashboard.getNumber("IntakeLevel", 0.0));
    SmartDashboard.putBoolean("Launch with PID", SmartDashboard.getBoolean("Launch with PID", false));
    SmartDashboard.putBoolean("Launch with BB", SmartDashboard.getBoolean("Launch with BB", false));
    SmartDashboard.putNumber("Launch P", SmartDashboard.getNumber("Launch P", 0.0));
    SmartDashboard.putNumber("Launch I", SmartDashboard.getNumber("Launch I", 0.0));
    SmartDashboard.putNumber("Launch D", SmartDashboard.getNumber("Launch D", 0.0));
    SmartDashboard.putNumber("Launch Target Speed", SmartDashboard.getNumber("Launch Target Speed", 0.0));
    SmartDashboard.putBoolean("Index with PID", SmartDashboard.getBoolean("Index with PID", false));    
    SmartDashboard.putNumber("Indexer P", SmartDashboard.getNumber("Indexer P", 0.0));
    SmartDashboard.putNumber("Indexer I", SmartDashboard.getNumber("Indexer I", 0.0));
    SmartDashboard.putNumber("Indexer D", SmartDashboard.getNumber("Indexer D", 0.0));
    SmartDashboard.putNumber("Indexer Target Speed", SmartDashboard.getNumber("Indexer Target Speed", 0.0));
    SmartDashboard.putBoolean("Elevate with PID", SmartDashboard.getBoolean("Elevate with PID", false));
    SmartDashboard.putNumber("Elevate P", SmartDashboard.getNumber("Elevate P", 0.0));
    SmartDashboard.putNumber("Elevate I", SmartDashboard.getNumber("Elevate I", 0.0));
    SmartDashboard.putNumber("Elevate D", SmartDashboard.getNumber("Elevate D", 0.0));
    SmartDashboard.putNumber("Elevate Target Speed", SmartDashboard.getNumber("Elevate Target Speed", 0.0));
    SmartDashboard.putBoolean("Intake with PID", SmartDashboard.getBoolean("Intake with PID", false));
    SmartDashboard.putNumber("Intake P", SmartDashboard.getNumber("Intake P", 0.0));
    SmartDashboard.putNumber("Intake I", SmartDashboard.getNumber("Intake I", 0.0));
    SmartDashboard.putNumber("Intake D", SmartDashboard.getNumber("Intake D", 0.0));
    SmartDashboard.putNumber("Intake Target Speed", SmartDashboard.getNumber("Intake Target Speed", 0.0));
  }

  public void runMotorsCmd() {
    runMotors = !runMotors; //toggle the motor flag
    if(runMotors) { //Starting the motors chosen on the SmartDashboard
      if(SmartDashboard.getBoolean("Launch with PID", false)) { //Use PID for the Flywheel?
        Configs.LauncherSubsystem.launcherConfig.closedLoop
          .p(SmartDashboard.getNumber("Launch P", 0.0)) //Get the PID values from the SmartDashboard
          .i(SmartDashboard.getNumber("Launch I", 0.0))
          .d(SmartDashboard.getNumber("Launch D", 0.0));
        launcherFlex.configure(Configs.LauncherSubsystem.launcherConfig,ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters); //Push the PID values to the Spark
        launcherPIDController.setSetpoint(SmartDashboard.getNumber("Launch Target Speed", 0.0), ControlType.kVelocity); //Set the setpoint for the PID controller
      } else { //Use BangBang for the Flywheel?
        if(SmartDashboard.getBoolean("Launch with BB", false)) {
          new RunCommand(() -> BangBangControl(launcherFlex, launcherEncoder, SmartDashboard.getNumber("Launch Target Speed", 0.0), false), mInstance); //Start the BangBang controller for the Flywheel
        } else { // Run Flywheel with set voltage
          launcherFlex.set(SmartDashboard.getNumber("LauncherPowerLevel", 0));
        }
      }
      if(!SmartDashboard.getBoolean("Index with PID", false)) //Run the indexer with set voltage?  
        indexerSpark.set(SmartDashboard.getNumber("IndexerPowerLevel", 0));
      else { //Run indexer with PID
        Configs.LauncherSubsystem.indexerConfig.closedLoop
          .p(SmartDashboard.getNumber("Indexer P", 0.0)) //Get the PID values from the SmartDashboard
          .i(SmartDashboard.getNumber("Indexer I", 0.0))
          .d(SmartDashboard.getNumber("Indexer D", 0.0));
        indexerSpark.configure(Configs.LauncherSubsystem.indexerConfig,ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters); //Push the PID values to the Spark
        indexerPIDController.setSetpoint(SmartDashboard.getNumber("Indexer Target Speed", 0.0), ControlType.kVelocity); //Set the setpoint for the PID controller
      }
      if(!SmartDashboard.getBoolean("Elevate with PID", false))
        elevatorSpark.set(SmartDashboard.getNumber("ElevatorPowerLevel", 0));
      else {
        Configs.LauncherSubsystem.elevatorConfig.closedLoop
          .p(SmartDashboard.getNumber("Elevate P", 0.0)) //Get the PID values from the SmartDashboard
          .i(SmartDashboard.getNumber("Elevate I", 0.0))
          .d(SmartDashboard.getNumber("Elevate D", 0.0));
        elevatorSpark.configure(Configs.LauncherSubsystem.elevatorConfig,ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters); //Push the PID values to the Spark
        elevatorPIDController.setSetpoint(SmartDashboard.getNumber("Elevate Target Speed", 0.0), ControlType.kVelocity); //Set the setpoint for the PID controller
      }
      if(!SmartDashboard.getBoolean("Intake with PID", false))
        intakeSpark.set(SmartDashboard.getNumber("IntakePowerLevel", 0));
      else {
        Configs.LauncherSubsystem.intakeConfig.closedLoop
          .p(SmartDashboard.getNumber("Intake P", 0.0)) //Get the PID values from the SmartDashboard
          .i(SmartDashboard.getNumber("Intake I", 0.0))
          .d(SmartDashboard.getNumber("Intake D", 0.0));
        intakeSpark.configure(Configs.LauncherSubsystem.intakeConfig,ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters); //Push the PID values to the Spark
        intakePIDController.setSetpoint(SmartDashboard.getNumber("Intake Target Speed", 0.0), ControlType.kVelocity); //Set the setpoint for the PID controller
      }
    } else {
      if(SmartDashboard.getBoolean("Launch with BB", false)) { //Flywheel running with BangBang
        new InstantCommand(() -> BangBangControl(launcherFlex, launcherEncoder, 0, true), mInstance); //Stop the controller
      }
      launcherFlex.set(0);  //Set the output to 0
      indexerSpark.set(0);  //Setting the PID to a zero setpoint caused problems with the PID
      elevatorSpark.set(0); //so just set the output to 0
      intakeSpark.set(0);
    }
  }

  public void BangBangControl(SparkFlex controller, RelativeEncoder encoder, double speed, boolean stop) {
    if(stop)  //being asked to stop the flywheel
      controller.set(0);
    else {
      if(encoder.getVelocity() < speed) //If the flywheel speed is less than the desired speed
        controller.set(1);  //Give the flywheel full power
      else
        controller.set(0);  //Cut the power and let it coast
    }
  }

}
