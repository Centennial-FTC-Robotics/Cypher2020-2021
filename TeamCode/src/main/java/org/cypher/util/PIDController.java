package org.cypher.util;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.cypher.subsystems.DriveTrain;

import java.nio.file.StandardOpenOption;

public class PIDController {
    private float kP, kI, kD;
    private float i;
    private float oldError;
    private boolean running = false;
    private ElapsedTime time;

    public PIDController(float kP, float kI, float kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public boolean isRunning() {
        return running;
    }

    public void start() {
        running = true;
        time = new ElapsedTime();
        oldError = 0;
        i = 0;
    }

    public void reset() {
        running = false;
    }

    public void editConstants(float kP, float kI, float kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public float getPower(float target, float actual) {
        return getPower(target - actual);
    }

    public float getPower(float error) {
        if (!running)
            start();

        float p = error;
        double toAdd = error * time.seconds();
        if (i > 1.3 && toAdd < 0)
            i += toAdd;
        else if (i < -1.3 && toAdd > 0)
            i += toAdd;
        else if(i > -1.3&& i < 1.3)
            i+= toAdd;
        float d = (float) ((error - oldError) / time.seconds());

        oldError = error;
        time.reset();

        return kP * p + kI * i + kD * d;
    }

    public float getI() {
        return i;
    }

    public float getIDir() {
        return (float) (oldError * time.seconds());
    }

}
