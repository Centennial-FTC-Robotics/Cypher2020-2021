package org.cypher.util;

public class PIDController {
    private double kP, kI, kD;

    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }


}
