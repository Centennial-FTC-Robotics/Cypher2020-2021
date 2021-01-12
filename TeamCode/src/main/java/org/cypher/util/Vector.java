package org.cypher.util;

public class Vector {
    private double x = 0;
    private double y = 0;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double angle() {
        double angle = Math.toDegrees(Math.atan(this.y/this.x));
        if (this.x >= 0 && this.y < 0) {
            angle += 360;
        } else if (this.x < 0 && this.y >= 0) {
            angle += 180;
        } else if (this.x < 0 && this.y < 0) {
            angle += 180;
        }
        return angle;
    }

    public double magnitude() {
        return Math.hypot(x, y);
    }

    public void setFromAngle(double angle, double magnitude) {
        this.x = magnitude * Math.cos(Math.toRadians(angle));
        this.y = magnitude * Math.sin(Math.toRadians(angle));
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setComponents(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX () {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void rotate(double degrees) {
        double angle = this.angle() + degrees;
        angle %= 360;
        if (angle < 0) {
            angle += 360;
        }
        this.x = this.magnitude() * Math.cos(Math.toRadians(angle));
        this.y = this.magnitude() * Math.sin(Math.toRadians(angle));
    }


    public static Vector add(Vector v1, Vector v2) {
        double x = v1.x + v2.x;
        double y = v1.y + v2.y;
        return new Vector(x, y);
    }

    public static Vector subtract(Vector v1, Vector v2) {
        double x = v1.x - v2.x;
        double y = v1.y - v2.y;
        return new Vector(x, y);
    }

    public static Vector multiply(Vector v, double scalar) {
        double x = v.x * scalar;
        double y = v.y * scalar;
        return new Vector(x, y);
    }

    public static double dotProduct(Vector v1, Vector v2) {
        double angle = Math.max(v1.angle(), v2.angle()) - Math.min(v1.angle(), v2.angle());
        return v1.magnitude() * v2.magnitude() * Math.cos(Math.toRadians(angle));
    }

    public String toString() {
        return "x component: " + this.x + "\ny component: " + this.y + "\nangle: " + this.angle() + "\nmagnitude: " + this.magnitude() + "\n";
    }

}
