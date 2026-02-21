package frc.robot;

import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.config.*;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;;

public class Configs {
    public static final class LauncherSubsystem {
        public static final SparkFlexConfig launcherConfig = new SparkFlexConfig();
        public static final SparkMaxConfig indexerConfig = new SparkMaxConfig();
        public static final SparkMaxConfig elevatorConfig = new SparkMaxConfig();
        public static final SparkMaxConfig intakeConfig = new SparkMaxConfig();

        static {
            launcherConfig
                .idleMode(IdleMode.kCoast)
                .inverted(false)
                .smartCurrentLimit(40);
            launcherConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                //.pidf(0.5, 0, 0, 0)
                .outputRange(0, 1)
                .positionWrappingEnabled(false)
                .feedForward
                    .kV(.4/2660);

            indexerConfig
                .idleMode(IdleMode.kBrake)
                .inverted(false)
                .smartCurrentLimit(40);
            indexerConfig.closedLoop
                .p(0)
                .i(0)
                .d(0)
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .outputRange(-1, 1)
                .feedForward
                    .kV(.6/3000);
                
            elevatorConfig
                .idleMode(IdleMode.kBrake)
                .inverted(false)
                .smartCurrentLimit(40);
            elevatorConfig.closedLoop
                //.pidf(1, 0, 0, 0)
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .outputRange(-1, 1)
                .feedForward
                    .kV(.6/3000);;
          
            intakeConfig
                .idleMode(IdleMode.kBrake)
                .inverted(true)
                .smartCurrentLimit(40);
            intakeConfig.closedLoop
                //.pidf(1, 0, 0, 0)
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .outputRange(-1, 1);
        }
    }
}
