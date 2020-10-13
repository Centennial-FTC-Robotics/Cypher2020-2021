package org.cypher.util;

import com.qualcomm.robotcore.util.ElapsedTime;

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

    public void start() {
        running = true;
    }

    public void reset() {

    }

    public void editConstants(float kP, float kI, float kD) {

    }

    public void getPower(float error) {

    }


}
